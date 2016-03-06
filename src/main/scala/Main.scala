import CustomPostgresDriver.api._

import akka.actor.ActorSystem
import akka.NotUsed
import akka.stream._
import akka.stream.scaladsl._

import com.sksamuel.elastic4s.streams.ReactiveElastic._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ Await, ExecutionContext, Promise, Future }
import scala.concurrent.duration._

import slick.backend.DatabasePublisher

object Main extends App {
  import Db._
  import Elasticsearch._
  import TaxiRideFunctions._

  implicit lazy val system = ActorSystem("reactive-streams-end-to-end")
  implicit lazy val materializer = ActorMaterializer()

  // A very large query. Loading it all into memory could result in a Bad Time.
  val allTaxiDataQuery = sql"SELECT * FROM nyc_taxi_data;".as[TaxiRide]

  /**
   * Create an akka-streams Source from a reactive-streams publisher,
   * entering akka-streams land where we get access to a richer API for stream element processing
   */
  val taxiRidesSource: Source[TaxiRide, NotUsed] = Source.fromPublisher {
    db.stream {
      allTaxiDataQuery
        .transactionally
        .withStatementParameters(fetchSize = 5000)
    }
  }

  /**
   * Construct a Flow[TaxiRide] that emits TaxiRideWithDescription elements from a function
   * that returns a Future[TaxiRideWithDescription]. Parallelism of the Future-producing
   * call is controlled under the hood by the actor behind the Flow.
   */
  def addFakeDescriptionFlow(f: TaxiRide => Future[TaxiRideWithDescription]): Flow[TaxiRide, TaxiRideWithDescription, NotUsed] =
    Flow[TaxiRide].mapAsync(parallelism = 5) { tr =>
      f(tr)
    }

  /**
   * Construct a Flow[TaxiRideWithDescription] that performs a calculation given TaxiRideWithDescription elements and emits further enriched FullyEnrichedTaxiRide elements.
   */
  val addPricePerDistanceRatioFlow: Flow[TaxiRideWithDescription, FullyEnrichedTaxiRide, NotUsed] =
    Flow[TaxiRideWithDescription].map { trwd =>
      val pricePerDistance = pricePerDistanceRatio(trwd.tr.total_amount, trwd.tr.trip_distance)

      FullyEnrichedTaxiRide(trwd, pricePerDistance)
    }

  def sumElementsSink[T] = Sink.fold[Int, T](0) { (sum, _) =>
    val newSum = sum + 1
    if (newSum % 5000 == 0) {
      print(s"\rCount: $newSum")
    }
    newSum
  }

  def bulkInsertToElasticsearch: Future[Unit] = {
    val p = Promise[Unit]()

    val esSink = Sink.fromSubscriber {
      esClient.subscriber[FullyEnrichedTaxiRide](
        batchSize = 5000,
        completionFn = { () => p.success(()); ()},
        errorFn = { (t: Throwable) => p.failure(t); ()})(FullyEnrichedTaxiRide.builder("nyc-taxi-rides"), system)
    }

    taxiRidesSource // sequence the streaming, whose sink will complete the promise
      .via(addFakeDescriptionFlow(fakeApiCall))
      .via(addPricePerDistanceRatioFlow)
      .alsoTo(sumElementsSink)
      .runWith(esSink)

    p.future
  }

  Await.result(bulkInsertToElasticsearch, Duration.Inf)

  Await.result(system.terminate, Duration.Inf)
}

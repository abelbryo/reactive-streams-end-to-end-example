import CustomPostgresDriver.api._

import akka.stream._
import akka.stream.scaladsl._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._

import slick.backend.DatabasePublisher

object Main extends App {
  import Db._

  val allTaxiDataQuery = sql"SELECT * FROM nyc_taxi_data;".as[TaxiRide]

  val taxiRides: DatabasePublisher[TaxiRide] = db.stream(allTaxiDataQuery)
}

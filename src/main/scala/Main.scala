import CustomPostgresDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._

object Main extends App {
  import config.pgConfig._

  lazy val db = Database.forURL(
    url = s"jdbc:postgresql://$PGHOST:$PGPORT/$PGDATABASE",
    user = PGUSER,
    password = PGPASSWORD,
    driver="org.postgresql.Driver")

  val query = sql"SELECT * FROM nyc_taxi_data LIMIT 1;".as[TaxiRide]

  val s = db.stream(query).foreach(println)

  Await.result(s, Duration.Inf)
}

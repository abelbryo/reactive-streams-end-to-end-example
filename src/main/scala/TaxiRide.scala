import CustomPostgresDriver.api._

import com.sksamuel.elastic4s.BulkCompatibleDefinition
import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.streams.RequestBuilder

import java.time.LocalDateTime

import slick.jdbc.GetResult

object TaxiRide {
  implicit val getTaxiRideResult: GetResult[TaxiRide] = GetResult { r =>
    TaxiRide(
      vendor_id = r.<<,
      tpep_pickup_datetime = r.<<,
      tpep_dropoff_datetime = r.<<,
      passenger_count = r.<<,
      trip_distance = r.<<,
      pickup_longitude = r.<<,
      pickup_latitude = r.<<,
      rate_code_id = r.<<,
      store_and_fwd_flag = r.<<,
      dropoff_longitude = r.<<,
      dropoff_latitude = r.<<,
      payment_type = r.<<,
      fare_amount = r.<<,
      extra = r.<<,
      mta_tax = r.<<,
      tip_amount = r.<<,
      tolls_amount = r.<<,
      improvement_surcharge = r.<<,
      total_amount = r.<<)
  }
}

case class TaxiRide(
  vendor_id: Int,
  tpep_pickup_datetime: LocalDateTime,
  tpep_dropoff_datetime: LocalDateTime,
  passenger_count: Int,
  trip_distance: Double,
  pickup_longitude: Double,
  pickup_latitude: Double,
  rate_code_id: Int,
  store_and_fwd_flag: Boolean,
  dropoff_longitude: Double,
  dropoff_latitude: Double,
  payment_type: Int,
  fare_amount: Double,
  extra: Double,
  mta_tax: Double,
  tip_amount: Double,
  tolls_amount: Double,
  improvement_surcharge: Double,
  total_amount: Double)

// TaxiRide but adds a "fake_description" field.
case class TaxiRideWithDescription(
  tr: TaxiRide,
  fake_description: String)

object FullyEnrichedTaxiRide {
  // EnrichedTaxiRide => Elasticsearch compatible documents
  def builder(indexName: String) = new RequestBuilder[FullyEnrichedTaxiRide] {
    def request(e: FullyEnrichedTaxiRide): BulkCompatibleDefinition = {
      index into indexName -> "taxi_rides" fields (
        "vendor_id"             -> e.trwd.tr.vendor_id,
        "tpep_pickup_datetime"  -> e.trwd.tr.tpep_pickup_datetime,
        "tpep_dropoff_datetime" -> e.trwd.tr.tpep_dropoff_datetime,
        "passenger_count"       -> e.trwd.tr.passenger_count,
        "trip_distance"         -> e.trwd.tr.trip_distance,
        "pickup_longitude"      -> e.trwd.tr.pickup_longitude,
        "pickup_latitude"       -> e.trwd.tr.pickup_latitude,
        "rate_code_id"          -> e.trwd.tr.rate_code_id,
        "store_and_fwd_flag"    -> e.trwd.tr.store_and_fwd_flag,
        "dropoff_longitude"     -> e.trwd.tr.dropoff_longitude,
        "dropoff_latitude"      -> e.trwd.tr.dropoff_latitude,
        "payment_type"          -> e.trwd.tr.payment_type,
        "fare_amount"           -> e.trwd.tr.fare_amount,
        "extra"                 -> e.trwd.tr.extra,
        "mta_tax"               -> e.trwd.tr.mta_tax,
        "tip_amount"            -> e.trwd.tr.tip_amount,
        "tolls_amount"          -> e.trwd.tr.tolls_amount,
        "improvement_surcharge" -> e.trwd.tr.improvement_surcharge,
        "total_amount"          -> e.trwd.tr.total_amount,
        "fake_description"      -> e.trwd.fake_description,
        "price_per_distance"    -> e.price_per_distance.getOrElse(null)
      )
    }
  }
}
// Fully 'enriched' TaxiRide - adds "fake_description" and "price_per_distance" fields.
case class FullyEnrichedTaxiRide(
  trwd: TaxiRideWithDescription,
  price_per_distance: Option[Double])

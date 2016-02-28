import CustomPostgresDriver.api._

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

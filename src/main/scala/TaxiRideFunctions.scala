import scala.concurrent.Future

object TaxiRideFunctions {
  // Function that mocks some kind of API call or code that produces a Future
  // Essentially adds a fake_description field to TaxiRide.
  def fakeApiCall(tr: TaxiRide): Future[TaxiRideWithDescription] = {
    Future.successful {
      TaxiRideWithDescription(
        tr = tr,
        fake_description = "I'm a fake description of some sort from calling a fake Future API.")
    }
  }

  // function for calculating ratio of total_amount / trip_distance
  def pricePerDistanceRatio(totalAmount: Double, tripDistance: Double): Option[Double] = {
    if (tripDistance <= 0d)
      None
    else
      Some(totalAmount / tripDistance)
  }
}

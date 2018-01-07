package models
import slick.jdbc.PostgresProfile.api._

case class Location(locationId: Long, country: String, city: String) {
  override lazy val toString = s"$city, $country"
}

class LocationTable(tag: Tag) extends Table[Location](tag, "locations"){
  val locationId = column[Long]("locationId", O.PrimaryKey, O.AutoInc)
  val country = column[String]("country")
  val city = column[String]("city")

  def * = (locationId, country, city) <> (Location.apply _ tupled, Location.unapply)
}

object LocationTable{
  lazy val table = TableQuery[LocationTable]
}
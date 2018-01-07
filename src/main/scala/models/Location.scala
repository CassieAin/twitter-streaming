package models
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

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

class LocationRepository(db: Database) {
  lazy val locationTableQuery = TableQuery[LocationTable]

  def create(location: Location): Future[Location] =
    db.run(locationTableQuery returning locationTableQuery += location)

  def createInBatch(locationSeq: Seq[Location]) =
    db.run(DBIO.sequence(locationSeq.map(t=>locationTableQuery+=t) ).transactionally)

  def update(location: Location): Future[Int] =
    db.run(locationTableQuery.filter(_.locationId === location.locationId).update(location))

  def delete(location: Location): Future[Int] =
    db.run(locationTableQuery.filter(_.locationId === location.locationId).delete)

  def deleteById(id: Option[Long]): Future[Int] =
    db.run(locationTableQuery.filter(_.locationId === id).delete)

  def getById(idLocation: Option[Long]): Future[Option[Location]] =
    db.run(locationTableQuery.filter(_.locationId === idLocation).result.headOption)
}
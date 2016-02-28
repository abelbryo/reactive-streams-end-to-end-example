import CustomPostgresDriver.api._

object Db {
  import config.pgConfig._

  lazy val db = Database.forURL(
    url = s"jdbc:postgresql://$PGHOST:$PGPORT/$PGDATABASE",
    user = PGUSER,
    password = PGPASSWORD,
    driver="org.postgresql.Driver")
}

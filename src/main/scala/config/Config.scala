import com.typesafe.config.{ Config, ConfigFactory }

import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._

package object config {
  case class ElasticsearchConfig(
    ESHOST: String,
    ESPORT: String,
    ESCLUSTERNAME: String)

  case class PostgresConfig(
    PGPORT: Int,
    PGUSER: String,
    PGPASSWORD: String,
    PGDATABASE: String,
    PGHOST: String)

  lazy val config: Config = ConfigFactory.load()

  lazy val esConfig: ElasticsearchConfig = config.as[ElasticsearchConfig]("elasticsearch")
  lazy val pgConfig: PostgresConfig = config.as[PostgresConfig]("postgres")
}

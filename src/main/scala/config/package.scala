import com.typesafe.config.{ Config, ConfigFactory }

import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._

package object config {
  lazy val config: Config = ConfigFactory.load()

  lazy val esConfig: ElasticsearchConfig = config.as[ElasticsearchConfig]("elasticsearch")
  lazy val pgConfig: PostgresConfig = config.as[PostgresConfig]("postgres")
}

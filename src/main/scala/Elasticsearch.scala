import com.sksamuel.elastic4s.{ ElasticClient, ElasticsearchClientUri }
import com.sksamuel.elastic4s.streams.ReactiveElastic._

import org.elasticsearch.common.settings.Settings

object Elasticsearch {
  import config.esConfig._

  val esClient = {
    val isRemote = ESHOST != "localhost"

    val settings = Settings
      .settingsBuilder
      .put("cluster.name", ESCLUSTERNAME)
      .put("client.transport.sniff", isRemote) // don't set sniff = true if local
      .build

    ElasticClient.transport(settings, ElasticsearchClientUri(ESHOST, ESPORT.toInt))
  }
}

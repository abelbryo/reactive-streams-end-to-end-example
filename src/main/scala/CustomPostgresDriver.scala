import com.github.tminglei.slickpg._

object CustomPostgresDriver extends ExPostgresDriver
  with PgDate2Support {

  override val api = ExtendedAPI

  object ExtendedAPI extends API
    with Date2DateTimePlainImplicits
}


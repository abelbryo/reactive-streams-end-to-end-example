package config

case class PostgresConfig(
  PGPORT: Int,
  PGUSER: String,
  PGPASSWORD: String,
  PGDATABASE: String,
  PGHOST: String)

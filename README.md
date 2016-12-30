reactive streams end-to-end
===========================

This is a repository containing an example application using reactive streams to:

* Stream records from a PostgreSQL table
* Process those records with akka-streams
* and bulk indexing into Elasticsearch with elastic4s

## Running

To run, make sure to have these environment variables set:

* Elasticsearch
    * ESHOST
    * ESPORT
    * ESCLUSTERNAME

* PostgreSQL
    * PGPORT
    * PGUSER
    * PGPASSWORD
    * PGDATABASE
    * PGHOST

and `sbt run` will stream results from PostgreSQL to Elasticsearch, printing a tally to the console as it runs.


Related blog post http://longcao.org/2016/03/10/reactive-streams-end-to-end-example

#!/bin/sh

# get some taxi data - January 2015
printf "\n-----> Download some sample data, NYC yellow cab data (Jan 2015)...\n\n"
curl -o taxi_data.csv 'https://storage.googleapis.com/tlc-trip-data/2015/yellow_tripdata_2015-12.csv'

# create the schema
printf "\n-----> Create 'nyc_taxi_data' table...\n\n"
psql -f create-schema.sql

# load the data with COPY
printf "\n-----> Loading data...\n\n"
psql -f load.sql

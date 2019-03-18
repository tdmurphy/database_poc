# database_poc

This is the repository to hold the code required for the Customer Transaction Search and Filter PoC

- The service connects to the following databases:
    - Postgresql - Running on GCP - can be accessed from the '/poc/psql' endpoint.
    - BigQuery - can be accessed from the '/poc/bq' endpoint.
    - ElasticSearch - Running on GCP - can be accessed from the '/poc/els' endpoint.
    - hazelcast - (currently will run locally when app is running - data deleted when shutdown) - can be accessed from the '/poc/hzl' endpoint.
- All db endpoints have the same 4 query endpoints to performance test the same queries as well as a **/'/timings'**/ endpoints to display the queries performance:
    - 

# database_poc

# This is the repository to hold the code required for the Customer Transaction Search and Filter PoC

- The service connects to the following databases:
    - **Postgresql** - Running on GCP - can be accessed from the *'/poc/psql'* endpoint.
    - **BigQuery** - can be accessed from the *'/poc/bq'* endpoint.
    - **ElasticSearch** - Running on GCP - can be accessed from the *'/poc/els'* endpoint.
    - **Hazelcast** - (currently will run locally when app is running - data deleted when shutdown) - can be accessed from the *'/poc/hzl'* endpoint.
- Each of the 4 db's have the same following 4 endpoints for performance testing each of the queries as well a *'/timings'* endpoint to show the performance results
for each run of each query:
    - */test-query1-2/{roldIdCust}/{counterpartyCin}*
    - */test-query10-11/{roleIdCust}/{transType}*
    - */test-query20/{roleIdCust}/{transType}/{transType}/{creditOrDebit}/{transCurrency}*
    - */test-query21/{roleIdCust}/{transType}/{transType}/{transType}/{creditOrDebit}*

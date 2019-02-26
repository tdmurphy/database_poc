# database_poc

This is the repository to hold the code required for the Customer Transaction Search and Filter PoC

- To get the local postgres example working:
    - The server must be running on port 5432
    - There should be a table titled "test"
    - The table must have the following columns:
        - id - Integer primary key
        - city - String
        - code - String
        - population - Integer
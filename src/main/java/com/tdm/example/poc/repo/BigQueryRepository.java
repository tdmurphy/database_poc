package com.tdm.example.poc.repo;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BigQueryRepository {

    private BigQuery bigQuery;

    public BigQueryRepository(){
        bigQuery = BigQueryOptions.getDefaultInstance().getService();
        log.info("BigQuery client initialised.");
    }


}

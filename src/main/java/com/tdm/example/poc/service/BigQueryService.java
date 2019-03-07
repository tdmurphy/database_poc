package com.tdm.example.poc.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.*;
import com.tdm.example.poc.config.BigQueryConfig;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@EnableConfigurationProperties({BigQueryConfig.class})
@Slf4j
public class BigQueryService {

    private final BigQueryConfig bigQueryConfig;

    private BigQuery bigQuery;

    public BigQueryService(BigQueryConfig bigQueryConfig){
        this.bigQueryConfig = bigQueryConfig;

        establishConnection();
    }

    private void establishConnection(){

        GoogleCredentials credentials;
        File credentialsFile = new File(bigQueryConfig.getCredentialsLocation(),bigQueryConfig.getCredentialsName());

        try{
            log.info("Attempting to establish connection to Bigquery...");
            FileInputStream inputStream = new FileInputStream(credentialsFile);
            credentials = ServiceAccountCredentials.fromStream(inputStream);

            bigQuery = BigQueryOptions.newBuilder().setProjectId(bigQueryConfig.getProjectId())
                    .setCredentials(credentials).build().getService();
        }catch (IOException e){
            log.error("IO Exception - Cannot locate GCP credentials json",e);
        }
        log.info("Successful connection made to Bigquery service.");

    }

    public List<String> testQuery(Long roleIdCust){

        QueryJobConfiguration query = QueryJobConfiguration.newBuilder(
                "SELECT month_id " +
                        "FROM [client-intelligence-705116:pocTransactions.Transactions] " +
                        "WHERE role_id_cust = " + roleIdCust)
                .setUseLegacySql(true)
                .build();

        JobId jobId = JobId.of(UUID.randomUUID().toString());
        log.info("query sent");
        Job job = bigQuery.create(JobInfo.newBuilder(query).setJobId(jobId).build());

        try {

            TableResult queryResult = job.getQueryResults();

            log.info("Result retrieved");

            List<String> fields = queryResult.getSchema().getFields().stream()
                    .map(Field::getName)
                    .collect(Collectors.toList());

            List<String> queryResultList = new ArrayList<>();

            for(FieldValueList row : queryResult.iterateAll()){
                JSONObject jsonResult = new JSONObject();


                fields.forEach(field -> jsonResult.put(field,row.get(field).getStringValue()));

                queryResultList.add(jsonResult.toString());
            }

            log.info("ResultList size: " + queryResultList.size());

            return queryResultList;


        }catch (InterruptedException e){
            log.error("InterruptedException: Job " + jobId + " ended before completion.",e);
        }

        return Collections.EMPTY_LIST;
    }

}

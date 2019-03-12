package com.tdm.example.poc.service;

import com.google.api.gax.retrying.RetrySettings;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.*;
import com.tdm.example.poc.config.BigQueryConfig;
import com.tdm.example.poc.utility.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.threeten.bp.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@EnableConfigurationProperties({BigQueryConfig.class})
@Slf4j
public class BigQueryService {

    private final BigQueryConfig bigQueryConfig;

    private BigQuery bigQuery;

    private TimeUtil timeUtil = new TimeUtil();

    private HashMap<String,ArrayList<TimeUtil>> timings = new HashMap<>();

    public BigQueryService(BigQueryConfig bigQueryConfig){
        this.bigQueryConfig = bigQueryConfig;

        timings.put("query_1_2",new ArrayList<>());
        timings.put("query_10_11",new ArrayList<>());
        timings.put("query_20",new ArrayList<>());
        timings.put("query_21",new ArrayList<>());

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
                    .setCredentials(credentials).setRetrySettings(
                            RetrySettings.newBuilder()
                                    .setTotalTimeout(Duration.ofMinutes(10)).build()
                    ).build().getService();
        }catch (IOException e){
            log.error("IO Exception - Cannot locate GCP credentials json",e);
        }
        log.info("Successful connection made to Bigquery service.");

    }

    public List<String> testQuery12(Long roleIdCust, String counterpartyCin){

        Long executionStartTime = System.currentTimeMillis();

        QueryJobConfiguration query = QueryJobConfiguration.newBuilder(
                "SELECT trans_date, counterparty_name, counterparty_ctry_code, trans_currency, lcy, rcy, trans_type " +
                        "FROM pocTransactions.Transactions " +
                        "WHERE role_id_cust = @roleIdCust " +
                        "AND counterparty_cin = @counterpartyCin " +
                        "AND trans_date BETWEEN @startDate AND @endDate")
                .addNamedParameter("roleIdCust",QueryParameterValue.int64(roleIdCust))
                .addNamedParameter("counterpartyCin",QueryParameterValue.string(counterpartyCin))
                .addNamedParameter("startDate",QueryParameterValue.string("2018-01-01"))
                .addNamedParameter("endDate",QueryParameterValue.string("2018-12-31"))
                .setUseLegacySql(false)
                .build();

        TimeUtil executeUtil = new TimeUtil();
        List<String> result = executeQuery(query,executeUtil);

        Long executionEndTime = System.currentTimeMillis();

        log.info("TOTAL EXECUTION TIME = {}",executionEndTime-executionStartTime);
        executeUtil.setTotalExecutionTime(executionEndTime-executionStartTime);

        ArrayList<TimeUtil> timeUtils = timings.get("query_1_2");
        timeUtils.add(executeUtil);
        timings.put("query_1_2",timeUtils);
        return result;
    }

    public List<String> testQuery1011(Long roleIdCust, String transType){

        Long executionStartTime = System.currentTimeMillis();

        QueryJobConfiguration query = QueryJobConfiguration.newBuilder(
                "SELECT trans_date, counterparty_name, counterparty_ctry_code, trans_currency, lcy, rcy, trans_type " +
                        "FROM pocTransactions.Transactions " +
                        "WHERE role_id_cust = @roleIdCust " +
                        "AND trans_type = @transType " +
                        "AND trans_date BETWEEN @startDate AND @endDate")
                .addNamedParameter("roleIdCust", QueryParameterValue.int64(roleIdCust))
                .addNamedParameter("transType", QueryParameterValue.string(transType))
                .addNamedParameter("startDate",QueryParameterValue.string("2018-01-01"))
                .addNamedParameter("endDate",QueryParameterValue.string("2018-01-31"))
                .setUseLegacySql(false)
                .build();

        TimeUtil executeUtil = new TimeUtil();
        List<String> result = executeQuery(query,executeUtil);
        Long executionEndTime = System.currentTimeMillis();

        log.info("TOTAL EXECUTION TIME = {}",executionEndTime-executionStartTime);
        executeUtil.setTotalExecutionTime(executionEndTime-executionStartTime);

        ArrayList<TimeUtil> timeUtils = timings.get("query_10_11");
        timeUtils.add(executeUtil);
        timings.put("query_10_11",timeUtils);
        return result;
    }

    public List<String> testQuery20(Long roleIdCust, String transType, String transType2, String creditDebit, String transCurrency){

        Long executionStartTime = System.currentTimeMillis();

        QueryJobConfiguration query = QueryJobConfiguration.newBuilder(
                "SELECT trans_date, counterparty_name, counterparty_ctry_code, trans_currency, lcy, rcy, trans_type " +
                        "FROM pocTransactions.Transactions " +
                        "WHERE role_id_cust = @roleIdCust " +
                        "AND (trans_type = @transType OR trans_type = @transType2) " +
                        "AND trans_date BETWEEN @startDate AND @endDate " +
                        "AND credit_debit = @creditDebit " +
                        "AND trans_currency = @transCurrency")
                .addNamedParameter("roleIdCust",QueryParameterValue.int64(roleIdCust))
                .addNamedParameter("transType",QueryParameterValue.string(transType))
                .addNamedParameter("transType2",QueryParameterValue.string(transType2))
                .addNamedParameter("startDate",QueryParameterValue.string("2018-01-01"))
                .addNamedParameter("endDate",QueryParameterValue.string("2018-02-28"))
                .addNamedParameter("creditDebit",QueryParameterValue.string(creditDebit))
                .addNamedParameter("transCurrency",QueryParameterValue.string(transCurrency))
                .setUseLegacySql(false)
                .build();

        TimeUtil executeUtil = new TimeUtil();
        List<String> result = executeQuery(query,executeUtil);
        Long executionEndTime = System.currentTimeMillis();

        log.info("TOTAL EXECUTION TIME = {}",executionEndTime-executionStartTime);
        executeUtil.setTotalExecutionTime(executionEndTime-executionStartTime);

        ArrayList<TimeUtil> timeUtils = timings.get("query_20");
        timeUtils.add(executeUtil);
        timings.put("query_20",timeUtils);
        return result;
    }

    public List<String> testQuery21(Long roleIdCust, String transType, String transType2, String transType3, String creditDebit){

        Long executionStartTime = System.currentTimeMillis();

        QueryJobConfiguration query = QueryJobConfiguration.newBuilder(
                "SELECT trans_date, counterparty_name, counterparty_ctry_code, trans_currency, lcy, rcy, trans_type " +
                        "FROM pocTransactions.Transactions " +
                        "WHERE role_id_cust = @roleIdCust " +
                        "AND (trans_type = @transType  OR trans_type = @transType2 OR trans_type = @transType3) " +
                        "AND trans_date BETWEEN @startDate AND @endDate " +
                        "AND credit_debit = @creditDebit")
                .addNamedParameter("roleIdCust",QueryParameterValue.int64(roleIdCust))
                .addNamedParameter("transType",QueryParameterValue.string(transType))
                .addNamedParameter("transType2",QueryParameterValue.string(transType2))
                .addNamedParameter("transType3",QueryParameterValue.string(transType3))
                .addNamedParameter("startDate",QueryParameterValue.string("2018-06-01"))
                .addNamedParameter("endDate",QueryParameterValue.string("2018-08-31"))
                .addNamedParameter("creditDebit",QueryParameterValue.string(creditDebit))
                .setUseLegacySql(false)
                .build();

        TimeUtil executeUtil = new TimeUtil();
        List<String> result = executeQuery(query,executeUtil);
        Long executionEndTime = System.currentTimeMillis();

        log.info("TOTAL EXECUTION TIME = {}",executionEndTime-executionStartTime);
        executeUtil.setTotalExecutionTime(executionEndTime-executionStartTime);

        ArrayList<TimeUtil> timeUtils = timings.get("query_21");
        timeUtils.add(executeUtil);
        timings.put("query_21",timeUtils);
        return result;
    }

    private List<String> executeQuery(QueryJobConfiguration query,TimeUtil timeUtil){
        JobId jobId = JobId.of(UUID.randomUUID().toString());

        log.info("query sent");
        Long queryStartTime = System.currentTimeMillis();
        Job job = bigQuery.create(JobInfo.newBuilder(query).setJobId(jobId).build());

        try {

            TableResult queryResult = job.getQueryResults();

            log.info("Result retrieved");
            Long queryEndTime = System.currentTimeMillis();
            log.info("TIME TO RUN QUERY = {}",queryEndTime-queryStartTime);

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

            timeUtil.setQueryTime(queryEndTime-queryStartTime);
            timeUtil.setResultSetSize(queryResultList.size());

            return queryResultList;


        }catch (InterruptedException e){
            log.error("InterruptedException: Job " + jobId + " ended before completion.",e);
        }

        return Collections.emptyList();
    }

    public String getTimings(){
        return timeUtil.getTimings(timings);
    }
}

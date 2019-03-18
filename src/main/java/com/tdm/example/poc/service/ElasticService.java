package com.tdm.example.poc.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdm.example.poc.entity.document.TransactionDocument;
import com.tdm.example.poc.entity.document.TransactionResultDocument;
import com.tdm.example.poc.utility.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class ElasticService {

    private static final String INDEX_NAME = "transaction";

    private RestHighLevelClient restClient;

    private ObjectMapper objectMapper;

    private TimeUtil timeUtil = new TimeUtil();

    private HashMap<String,ArrayList<TimeUtil>> timings = new HashMap<>();

    @Autowired
    public ElasticService(RestHighLevelClient restClient, ObjectMapper objectMapper){
        this.restClient = restClient;
        this.objectMapper = objectMapper;

        timings.put("query_1_2",new ArrayList<>());
        timings.put("query_10_11",new ArrayList<>());
        timings.put("query_20",new ArrayList<>());
        timings.put("query_21",new ArrayList<>());

    }

    public List<TransactionDocument> searchByRoleIdCust(Long roleIdCust) throws IOException {

        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();

        QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(
                QueryBuilders.matchQuery("roleIdCust",roleIdCust));

        searchBuilder.query(queryBuilder);

        searchRequest.source(searchBuilder);

        SearchResponse searchResponse = restClient.search(searchRequest,RequestOptions.DEFAULT);

        return getFullResults(searchResponse);

    }

    public List<TransactionResultDocument> testQuery12(Long roleIdCust, String counterpartyCin) throws IOException {

        Long executionStartTime = System.currentTimeMillis();

        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        searchBuilder.fetchSource(new String[]{"transDate","counterpartyName","counterpartyCtryCode","transCurrency","lcy","rcy","transType"},null);

        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("roleIdCust",roleIdCust))
                .must(QueryBuilders.termQuery("counterpartyCin",counterpartyCin))
                .must(QueryBuilders.rangeQuery("transDate").from("2018-01-01")
                                                .to("2018-12-31").includeUpper(true).includeLower(true));

        searchBuilder.query(queryBuilder);

        searchRequest.source(searchBuilder);

        TimeUtil executeUtil = new TimeUtil();
        Long queryStartTime = System.currentTimeMillis();
        SearchResponse searchResponse = restClient.search(searchRequest,RequestOptions.DEFAULT);
        Long queryEndTime = System.currentTimeMillis();

        List<TransactionResultDocument> result =  getLimitedResults(searchResponse);

        Long executionEndTime = System.currentTimeMillis();

        executeUtil.setQueryTime(queryEndTime-queryStartTime);
        executeUtil.setResultSetSize(result.size());
        executeUtil.setTotalExecutionTime(executionEndTime-executionStartTime);
        ArrayList<TimeUtil> timeUtils = timings.get("query_1_2");
        timeUtils.add(executeUtil);
        timings.put("query_1_2",timeUtils);

        return result;
    }

    public List<TransactionResultDocument> testQuery1011(Long roleIdCust, String transType) throws IOException {

        Long executionStartTime = System.currentTimeMillis();

        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        searchBuilder.fetchSource(new String[]{"transDate","counterpartyName","counterpartyCtryCode","transCurrency","lcy","rcy","transType"},null);

        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("roleIdCust",roleIdCust))
                .must(QueryBuilders.termQuery("transType",transType))
                .must(QueryBuilders.rangeQuery("transDate").from("2018-01-01")
                        .to("2018-01-31").includeUpper(true).includeLower(true));

        searchBuilder.query(queryBuilder);

        searchRequest.source(searchBuilder);

        TimeUtil executeUtil = new TimeUtil();
        Long queryStartTime = System.currentTimeMillis();
        SearchResponse searchResponse = restClient.search(searchRequest,RequestOptions.DEFAULT);
        Long queryEndTime = System.currentTimeMillis();

        List<TransactionResultDocument> result =  getLimitedResults(searchResponse);

        Long executionEndTime = System.currentTimeMillis();

        executeUtil.setQueryTime(queryEndTime-queryStartTime);
        executeUtil.setResultSetSize(result.size());
        executeUtil.setTotalExecutionTime(executionEndTime-executionStartTime);
        ArrayList<TimeUtil> timeUtils = timings.get("query_10_11");
        timeUtils.add(executeUtil);
        timings.put("query_10_11",timeUtils);

        return result;
    }

    public List<TransactionResultDocument> testQuery20(Long roleIdCust, String transType, String transType2, String creditDebit, String transCurrency) throws IOException {

        Long executionStartTime = System.currentTimeMillis();

        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        searchBuilder.fetchSource(new String[]{"transDate","counterpartyName","counterpartyCtryCode","transCurrency","lcy","rcy","transType"},null);

        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("roleIdCust",roleIdCust))
                .must(QueryBuilders.termsQuery("transType",transType,transType2))
                .must(QueryBuilders.rangeQuery("transDate").from("2018-01-01")
                        .to("2018-02-28").includeUpper(true).includeLower(true))
                .must(QueryBuilders.termQuery("creditOrDebit",creditDebit))
                .must(QueryBuilders.termQuery("transCurrency",transCurrency));

        searchBuilder.query(queryBuilder);

        searchRequest.source(searchBuilder);

        TimeUtil executeUtil = new TimeUtil();
        Long queryStartTime = System.currentTimeMillis();
        SearchResponse searchResponse = restClient.search(searchRequest,RequestOptions.DEFAULT);
        Long queryEndTime = System.currentTimeMillis();

        List<TransactionResultDocument> result =  getLimitedResults(searchResponse);

        Long executionEndTime = System.currentTimeMillis();

        executeUtil.setQueryTime(queryEndTime-queryStartTime);
        executeUtil.setResultSetSize(result.size());
        executeUtil.setTotalExecutionTime(executionEndTime-executionStartTime);
        ArrayList<TimeUtil> timeUtils = timings.get("query_20");
        timeUtils.add(executeUtil);
        timings.put("query_20",timeUtils);

        return result;
    }

    public List<TransactionResultDocument> testQuery21(Long roleIdCust, String transType, String transType2, String transType3, String creditDebit) throws IOException {

        Long executionStartTime = System.currentTimeMillis();

        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        searchBuilder.fetchSource(new String[]{"transDate","counterpartyName","counterpartyCtryCode","transCurrency","lcy","rcy","transType"},null);

        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("roleIdCust",roleIdCust))
                .must(QueryBuilders.termsQuery("transType",transType,transType2,transType3))
                .must(QueryBuilders.rangeQuery("transDate").from("2018-06-01")
                        .to("2018-08-31").includeUpper(true).includeLower(true))
                .must(QueryBuilders.termQuery("creditOrDebit",creditDebit));

        searchBuilder.query(queryBuilder);

        searchRequest.source(searchBuilder);

        TimeUtil executeUtil = new TimeUtil();
        Long queryStartTime = System.currentTimeMillis();
        SearchResponse searchResponse = restClient.search(searchRequest,RequestOptions.DEFAULT);
        Long queryEndTime = System.currentTimeMillis();

        List<TransactionResultDocument> result =  getLimitedResults(searchResponse);

        Long executionEndTime = System.currentTimeMillis();

        executeUtil.setQueryTime(queryEndTime-queryStartTime);
        executeUtil.setResultSetSize(result.size());
        executeUtil.setTotalExecutionTime(executionEndTime-executionStartTime);
        ArrayList<TimeUtil> timeUtils = timings.get("query_21");
        timeUtils.add(executeUtil);
        timings.put("query_21",timeUtils);

        return result;
    }

    public String getTimings(){
        return timeUtil.getTimings(timings);
    }

    private List<TransactionDocument> getFullResults(SearchResponse searchResponse){
        SearchHit[] searchHit = searchResponse.getHits().getHits();

        List<TransactionDocument> transactionDocuments = new ArrayList<>();

        if (searchHit.length > 0) {

            Arrays.stream(searchHit)
                    .forEach(hit -> transactionDocuments
                            .add(objectMapper
                                    .convertValue(hit.getSourceAsMap(),
                                            TransactionDocument.class))
                    );
        }

        return transactionDocuments;
    }

    private List<TransactionResultDocument> getLimitedResults(SearchResponse searchResponse){
        SearchHit[] searchHit = searchResponse.getHits().getHits();

        List<TransactionResultDocument> transactionDocuments = new ArrayList<>();

        if (searchHit.length > 0) {

            Arrays.stream(searchHit)
                    .forEach(hit -> transactionDocuments
                            .add(objectMapper
                                    .convertValue(hit.getSourceAsMap(),
                                            TransactionResultDocument.class))
                    );
        }

        return transactionDocuments;
    }

}

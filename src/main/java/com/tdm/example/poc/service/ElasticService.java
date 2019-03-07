package com.tdm.example.poc.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.tdm.example.poc.entity.document.TransactionDocument;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
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

import java.io.FileInputStream;
import java.util.*;

@Service
@Slf4j
public class ElasticService {

    private RestHighLevelClient restClient;

    private ObjectMapper objectMapper;

    private Storage storage;

    @Autowired
    public ElasticService(RestHighLevelClient restClient, ObjectMapper objectMapper){
        this.restClient = restClient;
        this.objectMapper = objectMapper;

        makeGcsConnection();
    }

    private void makeGcsConnection(){
        log.info("Establishing connection to google cloud storage...");

        try {
            Credentials credentials = GoogleCredentials
                    .fromStream(new FileInputStream("C:/Users/thoma/Documents/csv_poc/gcp-cred.json"));
            storage = StorageOptions.newBuilder().setCredentials(credentials)
                    .setProjectId("client-intelligence-705116").build().getService();
        } catch(Exception e){
            log.error("Exception when attempting gcs connection.",e);
        }

        log.info("Successful connection made to Gcs");
    }

    public String loadExampleDocument(TransactionDocument transactionDocument) throws Exception {

        transactionDocument.setUniqueTransactionReference(UUID.randomUUID().toString());

        Map<String,Object> mapping = objectMapper.convertValue(transactionDocument,Map.class);

        IndexRequest indexRequest = new IndexRequest("transaction","_doc",transactionDocument.getUniqueTransactionReference()).source(mapping);

        return restClient.index(indexRequest,RequestOptions.DEFAULT).getResult().toString();

    }

    public List<TransactionDocument> searchByRoleIdCust(Long roleIdCust) throws Exception{

        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();

        QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(
                QueryBuilders.matchQuery("roleIdCust",roleIdCust));

        searchBuilder.query(queryBuilder);

        searchRequest.source(searchBuilder);

        SearchResponse searchResponse = restClient.search(searchRequest,RequestOptions.DEFAULT);

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

}

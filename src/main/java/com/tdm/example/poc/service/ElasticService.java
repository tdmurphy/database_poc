package com.tdm.example.poc.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.gax.paging.Page;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.tdm.example.poc.entity.document.TransactionDocument;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class ElasticService {

    private RestHighLevelClient restClient;

    private ObjectMapper objectMapper;

    private Storage storage;
    private Page<Blob> objectBlobs;

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
        objectBlobs = storage.list("poc-datastaging-london",Storage.BlobListOption.pageSize(100));
        for(Blob blob : objectBlobs.iterateAll()){
            log.info(blob.getName());
        }
    }

    public String loadExampleDocument(TransactionDocument transactionDocument) throws IOException {

        transactionDocument.setUniqueTransactionReference(UUID.randomUUID().toString());

        Map<String,Object> mapping = objectMapper.convertValue(transactionDocument,Map.class);

        IndexRequest indexRequest = new IndexRequest("transaction","_doc",transactionDocument.getUniqueTransactionReference()).source(mapping);

        return restClient.index(indexRequest,RequestOptions.DEFAULT).getResult().toString();

    }

    public List<TransactionDocument> searchByRoleIdCust(Long roleIdCust) throws IOException{

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

    public String ingestGcsFile(String startFile) throws InterruptedException {

        List<Blob> blobList = StreamSupport.stream(objectBlobs.getValues().spliterator(),false).collect(Collectors.toList());

        ArrayList<IndexThread> indexThreads = new ArrayList<>();

        int tracker = 0;

        for(Blob blob : blobList){
            if(blob.getName().equals(startFile)){
                tracker = blobList.indexOf(blob);
                break;
            }
        }

        for(int i = 0; i < 5; i++){
            indexThreads.add(new IndexThread("Thread " + i,blobList.get(tracker)));
            indexThreads.get(i).start();
            tracker++;
            Thread.sleep(2000);
        }

        while(tracker < blobList.size()){
            for(IndexThread thread : indexThreads){
                if(thread.getState() == Thread.State.TERMINATED){
                    String name = thread.getName();
                    thread.interrupt();
                    thread = new IndexThread(name,blobList.get(tracker++));
                    thread.start();
                }
            }
        }



        return "Process finished";
    }

    private Map<String,Object> buildDocument(String[] documentValues){
        TransactionDocument document =  TransactionDocument.builder()
                .uniqueTransactionReference(documentValues[0])
                .month(Integer.parseInt(documentValues[1]))
                .countryCode(documentValues[2])
                .realCustId(documentValues[3])
                .roleIdCust(Long.parseLong(documentValues[4]))
                .accountNum(documentValues[5])
                .creditOrDebit(documentValues[6])
                .counterpartyType(documentValues[7])
                .counterpartyCin(documentValues[8])
                .counterpartyAccountNum(documentValues[9])
                .counterpartyName(documentValues[10])
                .counterpartySource(documentValues[11])
                .counterpartyCtryCode(documentValues[12])
                .sourceSystem(documentValues[13])
                .transDate(Date.valueOf(documentValues[14]))
                .approvalDate(Date.valueOf(documentValues[15]))
                .endDate(Date.valueOf(documentValues[16]))
                .transCode(Integer.parseInt(documentValues[17]))
                .transType(documentValues[18])
                .transDesc(documentValues[19])
                .transChannel(documentValues[20])
                .transId(Integer.parseInt(documentValues[21]))
                .transCurrency(documentValues[22])
                .lcy(Float.parseFloat(documentValues[23]))
                .rcy(Float.parseFloat(documentValues[24]))
                .convRate(Float.parseFloat(documentValues[25]))
                .intra(Integer.parseInt(documentValues[26]))
                .reversal(Integer.parseInt(documentValues[27]))
                .duplicate(Integer.parseInt(documentValues[28])).build();

        return objectMapper.convertValue(document,Map.class);
    }

    class IndexThread extends Thread{
        private Thread t;
        private String name;
        private Blob fileToInsert;

        IndexThread(String name,Blob fileToInsert){
            this.name = name;
            this.fileToInsert = fileToInsert;
            log.info("Creating Thread - {}", name);
        }

        public void start(){
            log.info("Starting Thread - {} with file {}",name,fileToInsert.getName());
            if(t == null){
                t = new Thread(this,name);
                t.start();
            }
        }

        @Override
        public void run() {

            BulkRequest bulkRequest = new BulkRequest();

            ReadChannel blobReader = null;
            BufferedReader reader;
            try{
                log.info("Thread {} running",name);
                blobReader = fileToInsert.reader(Blob.BlobSourceOption.userProject("client-intelligence-705116"));
                reader = new BufferedReader(Channels.newReader(blobReader,"UTF-8"));
                int i = 1;
                String line;
                while ((line = reader.readLine()) != null) {
                    if(i == 5000){
                        log.info("Sending bulk index request...");
                        String result = restClient.bulk(bulkRequest,RequestOptions.DEFAULT).buildFailureMessage();
                        log.info("Bulk request response: {}",result);
                        bulkRequest = new BulkRequest();
                        i = 1;
                    }
                    String[] dataValues = line.split(",");
                    Map<String,Object> documentObject = buildDocument(dataValues);

                    bulkRequest.add( new IndexRequest("transaction","_doc",dataValues[0]).source(documentObject));
                    i++;
                }
                reader.close();
            } catch (Exception e){
                log.error("Exception: ",e);
            }finally {
                blobReader.close();
            }

            log.info("Thread {} with file {} finished",name,fileToInsert.getName());
        }
    }

}

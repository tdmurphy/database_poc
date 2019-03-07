package com.tdm.example.poc.controller;

import com.google.cloud.storage.Bucket;
import com.tdm.example.poc.entity.document.TransactionDocument;
import com.tdm.example.poc.service.ElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/poc/els")
public class ElasticSearchController {

    @Autowired
    private ElasticService elasticService;

    /*
    @GetMapping("/list-buckets")
    public String listBuckets(){
        String res = "";

        for(Bucket bucket : elasticService.listGcsBuckets()){
            res = res + bucket.getName() + "\n";
        }
        return res;
    }
    */

    @PostMapping("/load-example-document")
    public ResponseEntity loadExampleDocument(@RequestBody TransactionDocument transactionDocument) throws Exception{
        return new ResponseEntity(elasticService.loadExampleDocument(transactionDocument),HttpStatus.ACCEPTED);
    }

    @GetMapping("/test-query/{roleIdCust}")
    public List<TransactionDocument> searchByRoleIdCust(@PathVariable("roleIdCust") Long roleIdCust) throws Exception{
        return elasticService.searchByRoleIdCust(roleIdCust);
    }

}

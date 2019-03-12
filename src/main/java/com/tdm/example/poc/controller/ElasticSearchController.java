package com.tdm.example.poc.controller;

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

    @PostMapping("/load-example-document")
    public ResponseEntity loadExampleDocument(@RequestBody TransactionDocument transactionDocument) throws Exception{
        return new ResponseEntity(elasticService.loadExampleDocument(transactionDocument),HttpStatus.ACCEPTED);
    }

    @GetMapping("/test-query/{roleIdCust}")
    public List<TransactionDocument> searchByRoleIdCust(@PathVariable("roleIdCust") Long roleIdCust) throws Exception{
        return elasticService.searchByRoleIdCust(roleIdCust);
    }

    @GetMapping("/ingest-gcs-file/{fileName}")
    public String ingestGcsFile(@PathVariable("fileName") String fileName) throws Exception{
        return elasticService.ingestGcsFile(fileName);
    }

}

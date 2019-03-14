package com.tdm.example.poc.controller;

import com.tdm.example.poc.entity.document.TransactionDocument;
import com.tdm.example.poc.service.ElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/poc/els")
public class ElasticSearchController {

    private ElasticService elasticService;

    @Autowired
    public ElasticSearchController(ElasticService elasticService){
        this.elasticService = elasticService;
    }

    @GetMapping("/test-query/{roleIdCust}")
    public List<TransactionDocument> searchByRoleIdCust(@PathVariable("roleIdCust") Long roleIdCust) throws IOException {
        return elasticService.searchByRoleIdCust(roleIdCust);
    }

}

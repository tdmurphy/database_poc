package com.tdm.example.poc.controller;


import com.tdm.example.poc.config.BigQueryConfig;
import com.tdm.example.poc.service.BigQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/poc/bq")
public class BigQueryController {

    private BigQueryService bigQueryService = new BigQueryService(new BigQueryConfig());

    @GetMapping("/test-query/{roleIdCust}")
    public List<String> testQuery(@PathVariable("roleIdCust") Long roleIdCust){
        return bigQueryService.testQuery(roleIdCust);
    }

}

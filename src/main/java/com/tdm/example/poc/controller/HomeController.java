package com.tdm.example.poc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/poc")
public class HomeController {

    @GetMapping("/")
    public String home(){
        String response = "This is the homepage. Below is the available endpoints to query the various databases:<br/>";

        response = response + "   - /bq = contains endpoints to query BigQuery<br/>";
        response = response + "   - /psql = contains endpoints to query Postgresql db hosted on GCP<br/>";
        response = response + "   - /els = contains endpoints to query ElasticSearch<br/>";
        response = response + "   - /hzl = contains endpoints to query Hazelcast<br/>";

        return response;
    }

}

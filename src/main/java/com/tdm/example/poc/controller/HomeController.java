package com.tdm.example.poc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/poc")
public class HomeController {

    @GetMapping("/home")
    public String home(){
        String response = "This is the homepage. Below is the available endpoints to query the various databases:<br/>";

        response = response + "   - /test = contains endpoints to query to test localhost db (Will only work if running in dev profile)<br/>";
        response = response + "   - /bq = contains endpoints to query BigQuery<br/>";
        response = response + "   - /psql = contains endpoints to query Postgresql db hosted on GCP<br/>";
        response = response + "   - /els = contains endpoints to query ElasticSearch<br/>";
        response = response + "   - /mongo = contains endpoints to query MongoDb<br/>";

        return response;
    }

}

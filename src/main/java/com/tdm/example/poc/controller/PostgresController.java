package com.tdm.example.poc.controller;

import com.tdm.example.poc.repo.PostgresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/poc/psql")
public class PostgresController {

    @Autowired
    PostgresRepository postgresRepository;

}

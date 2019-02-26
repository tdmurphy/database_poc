package com.tdm.example.poc.controller;

import com.tdm.example.poc.entity.DatabaseEntity;
import com.tdm.example.poc.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/poc")
public class DatabaseController {

    @Autowired
    private TestRepository repository;

    @GetMapping("/home")
    public String home(){
        return "This the homepage. Below is the available endpoints to query the various databases:";
    }

    @GetMapping("/findAll")
    public List<DatabaseEntity> findAll(){
        return repository.findAll();
    }

    @GetMapping("/find-code/{code}")
    public DatabaseEntity findbyCode(@PathVariable String code){
        return repository.findByCode(code);
    }

    @GetMapping("/find-population/{population}")
    public List<DatabaseEntity> findByPopulation(@PathVariable int population){
        return repository.findByPopulation(population);
    }
}

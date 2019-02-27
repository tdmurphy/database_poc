package com.tdm.example.poc.controller;

import com.tdm.example.poc.entity.TestEntity;
import com.tdm.example.poc.repo.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/poc/test")
public class TestController {

    @Autowired
    private TestRepository testRepository;

    @GetMapping("/findAll")
    public List<TestEntity> findAll(){
        return testRepository.findAll();
    }

    @GetMapping("/find-code/{code}")
    public TestEntity findbyCode(@PathVariable String code){
        return testRepository.findByCode(code);
    }

    @GetMapping("/find-population/{population}")
    public List<TestEntity> findByPopulation(@PathVariable int population){
        return testRepository.findByPopulation(population);
    }

}

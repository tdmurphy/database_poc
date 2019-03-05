package com.tdm.example.poc.controller;

import com.tdm.example.poc.entity.PostgresEntity;
import com.tdm.example.poc.entity.PostgresLimitedEntity;
import com.tdm.example.poc.repo.PostgresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/poc/psql")
public class PostgresController {

    @Autowired
    PostgresRepository postgresRepository;


    @GetMapping("/find-by-id/{id}")
    public List<PostgresEntity> findById(@PathVariable Long id){
        return postgresRepository.findByRoleIdCust(id);
    }

    @GetMapping("/test-query/{roleIdCust}/{pageNo}")
    public List<PostgresLimitedEntity> testQuery(@PathVariable("roleIdCust") Long roleIdCust, @PathVariable("pageNo") Integer pageNo) {
        return postgresRepository.testQuery(roleIdCust, PageRequest.of(pageNo,10));
    }

    @GetMapping("/entity-count")
    public Long entityCount(){
        return postgresRepository.count();
    }

}

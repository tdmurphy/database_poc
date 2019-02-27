package com.tdm.example.poc.repo;

import com.tdm.example.poc.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<TestEntity,Integer>{
    List<TestEntity> findAll();
    TestEntity findByCode(String code);
    List<TestEntity> findByPopulation(Integer population);
}


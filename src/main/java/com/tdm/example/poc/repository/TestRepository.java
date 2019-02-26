package com.tdm.example.poc.repository;

import com.tdm.example.poc.entity.DatabaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<DatabaseEntity,Integer>{
    List<DatabaseEntity> findAll();
    DatabaseEntity findByCode(String code);
    List<DatabaseEntity> findByPopulation(Integer population);
}


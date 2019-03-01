package com.tdm.example.poc.repo;

import com.tdm.example.poc.entity.PostgresEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostgresRepository extends JpaRepository<PostgresEntity,Integer>{
    List<PostgresEntity> findAll();
    PostgresEntity findByCode(String code);
    List<PostgresEntity> findByPopulation(Integer population);
}


package com.tdm.example.poc.repo;

import com.tdm.example.poc.entity.PostgresEntity;
import com.tdm.example.poc.entity.PostgresLimitedEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostgresRepository extends CrudRepository<PostgresEntity,String> {
    List<PostgresEntity> findAll();

    List<PostgresEntity> findByUniqueTransactionReference(String id);

    List<PostgresEntity> findByRoleIdCust(Long roleIdCust);


    @Query(value = "SELECT  new com.tdm.example.poc.entity.PostgresLimitedEntity(uniqueTransactionReference, month, transCurrency) " +
            "FROM transactions " +
            "WHERE roleIdCust = :custId")
    List<PostgresLimitedEntity> testQuery(@Param("custId") Long roleIdCust, Pageable pageable);
}


package com.tdm.example.poc.repo;

import com.tdm.example.poc.entity.PostgresEntity;
import com.tdm.example.poc.entity.PostgresLimitedEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostgresRepository extends CrudRepository<PostgresEntity,String> {


    @Query(value = "SELECT new com.tdm.example.poc.entity.PostgresLimitedEntity(transDate, counterpartyName, counterpartyCtryCode, transCurrency, lcy, rcy, transType) " +
            "FROM transactions " +
            "WHERE roleIdCust = :custId " +
            "AND counterpartyCin = :ctrprty " +
            "AND transDate BETWEEN '2018-01-01' AND '2018-12-31' " +
            "ORDER BY transDate ASC")
    List<PostgresLimitedEntity> testQuery12(@Param("custId") Long roleIdCust, @Param("ctrprty") String counterpartyCin, Pageable pageable);


    @Query(value = "SELECT  new com.tdm.example.poc.entity.PostgresLimitedEntity(transDate, counterpartyName, counterpartyCtryCode," +
            "transCurrency, lcy, rcy, transType) " +
            "FROM transactions " +
            "WHERE roleIdCust = :custId " +
            "AND transType = :transType " +
            "AND transDate BETWEEN '2018-01-01' AND '2018-01-31' " +
            "ORDER BY transDate ASC")
    List<PostgresLimitedEntity> testQuery1011(@Param("custId") Long roleIdCust, @Param("transType") String transType, Pageable pageable);

    @Query(value = "SELECT  new com.tdm.example.poc.entity.PostgresLimitedEntity(transDate, counterpartyName, counterpartyCtryCode," +
            "transCurrency, lcy, rcy, transType) " +
            "FROM transactions " +
            "WHERE roleIdCust = :custId " +
            "AND (transType = :transType OR transType = :transType2)" +
            "AND transDate BETWEEN '2018-01-01' AND '2018-02-28' " +
            "AND creditOrDebit = :creditDebit AND transCurrency = :transCurrency " +
            "ORDER BY transDate ASC")
    List<PostgresLimitedEntity> testQuery20(@Param("custId") Long roleIdCust, @Param("transType") String transType,
                                            @Param("transType2") String transType2, @Param("creditDebit") String creditDebit,
                                            @Param("transCurrency") String transCurrency, Pageable pageable);

    @Query(value = "SELECT  new com.tdm.example.poc.entity.PostgresLimitedEntity(transDate, counterpartyName, counterpartyCtryCode," +
            "transCurrency, lcy, rcy, transType) " +
            "FROM transactions " +
            "WHERE roleIdCust = :custId " +
            "AND (transType = :transType OR transType = :transType2 OR transType = :transType3)" +
            "AND transDate BETWEEN '2018-06-01' AND '2018-08-31' " +
            "AND creditOrDebit = :creditDebit " +
            "ORDER BY transDate ASC")
    List<PostgresLimitedEntity> testQuery21(@Param("custId") Long roleIdCust, @Param("transType") String transType,
                                            @Param("transType2") String transType2, @Param("transType3") String transType3,
                                            @Param("creditDebit") String creditDebit, Pageable pageable);

}


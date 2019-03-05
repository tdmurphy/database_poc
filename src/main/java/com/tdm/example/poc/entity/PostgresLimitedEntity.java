package com.tdm.example.poc.entity;

import lombok.Value;

@Value
public class PostgresLimitedEntity {
    private String uniqueTransactionReference;
    private Integer month;
    private String transCurrency;
}

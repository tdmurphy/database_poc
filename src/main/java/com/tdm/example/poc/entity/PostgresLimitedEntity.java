package com.tdm.example.poc.entity;

import lombok.Value;

import java.math.BigDecimal;
import java.util.Date;

@Value
public class PostgresLimitedEntity {
    Date transDate;
    String counterpartyName;
    String counterpartyCtryCode;
    String transCurrency;
    BigDecimal lcy;
    BigDecimal rcy;
    String transType;
}

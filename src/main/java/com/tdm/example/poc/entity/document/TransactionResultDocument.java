package com.tdm.example.poc.entity.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResultDocument {

    private Date transDate;
    private String counterpartyName;
    private String counterpartyCtryCode;
    private String transCurrency;
    private Float lcy;
    private Float rcy;
    private String transType;

}

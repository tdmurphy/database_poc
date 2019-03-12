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
public class TransactionDocument {

    private String uniqueTransactionReference;
    private Integer month;
    private String countryCode;
    private String realCustId;
    private Long roleIdCust;
    private String accountNum;
    private String creditOrDebit;
    private String counterpartyType;
    private String counterpartyCin;
    private String counterpartyAccountNum;
    private String counterpartyName;
    private String counterpartySource;
    private String counterpartyCtryCode;
    private String sourceSystem;
    private Date transDate;
    private Date approvalDate;
    private Date endDate;
    private Integer transCode;
    private String transType;
    private String transDesc;
    private String transChannel;
    private Integer transId;
    private String transCurrency;
    private Float lcy;
    private Float rcy;
    private Float convRate;
    private Integer intra;
    private Integer reversal;
    private Integer duplicate;

}

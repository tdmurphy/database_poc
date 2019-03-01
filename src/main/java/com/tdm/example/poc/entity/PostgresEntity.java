package com.tdm.example.poc.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostgresEntity {

    @Id
    private String uniqueTransactionReference;

    @Column(name = "month_id")
    private int month;

    @Column(name = "iso_ctry_code")
    private String countryCode;

    @Column(name = "real_cust_id")
    private String realCustId;

    @Column(name = "role_id_cust")
    private Long roleIdCust;

    @Column(name = "account_num")
    private String accountNum;

    @Column(name = "credit/debit")
    private String creditOrDebit;

    @Column(name = "counterparty_type")
    private String counterpartyType;

    @Column(name = "counterparty_cin")
    private String counterpartyCin;

    @Column(name = "counterparty_account_num")
    private String counterpartyAccountNum;

    @Column(name = "counterparty_name")
    private String counterpartyName;

    @Column(name = "counterparty_source")
    private String counterpartySource;

    @Column(name = "counterparty_ctry_code")
    private String counterpartyCtryCode;

    @Column(name = "source_system")
    private String sourceSystem;

    @Column(name = "trans_date")
    private Date transDate;

    @Column(name = "approval_date")
    private Date approvalDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "trans_code")
    private int transCode;

    @Column(name = "trans_type")
    private String transType;

    @Column(name = "trans_desc")
    private String transDesc;

    @Column(name = "trans_channel")
    private String transChannel;

    @Column(name = "trans_id")
    private int transId;

    @Column(name = "trans_currency")
    private String transCurrency;

    @Column(name = "lcy")
    private float lcy;

    @Column(name = "rcy")
    private float rcy;

    @Column(name = "conv_rate")
    private float convRate;

    @Column(name = "intra")
    private int intra;

    @Column(name = "reversal")
    private int reversal;

    @Column(name = "duplicate")
    private int duplicate;

}

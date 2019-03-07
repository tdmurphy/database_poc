package com.tdm.example.poc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.bigquery")
public class BigQueryConfig {
    private String credentialsLocation = "C:/Users/thoma/Documents/csv_poc/";
    private String credentialsName = "gcp-cred.json";
    private String projectId = "client-intelligence-705116";
}

package com.tdm.example.poc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.bigquery")
public class BigQueryConfig {
    private String credentialsLocation = "";
    private String credentialsName = "";
    private String projectId = "";
}

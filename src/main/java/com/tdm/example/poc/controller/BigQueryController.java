package com.tdm.example.poc.controller;


import com.tdm.example.poc.config.BigQueryConfig;
import com.tdm.example.poc.service.BigQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/poc/bq")
public class BigQueryController {

    private BigQueryService bigQueryService = new BigQueryService(new BigQueryConfig());

    @GetMapping("/timings")
    public String getQueryTimings(){
        return bigQueryService.getTimings();
    }


    @GetMapping("/test-query1-2/{roleIdCust}/{counterpartyCin}")
    public List<String> testQuery12(@PathVariable("roleIdCust") Long roleIdCust, @PathVariable("counterpartyCin") String counterpartyCin){
        return bigQueryService.testQuery12(roleIdCust,counterpartyCin);
    }

    @GetMapping("/test-query10-11/{roleIdCust}/{transType}")
    public List<String> testQuery1011(@PathVariable("roleIdCust") Long roleIdCust, @PathVariable("transType") String transType){
        return bigQueryService.testQuery1011(roleIdCust,transType);
    }

    @GetMapping("/test-query20/{roleIdCust}/{transType}/{transType2}/{creditDebit}/{transCurrency}")
    public List<String> testQuery20(@PathVariable("roleIdCust") Long roleIdCust, @PathVariable("transType") String transType,
                                    @PathVariable("transType2") String transType2, @PathVariable("creditDebit") String creditDebit,
                                    @PathVariable("transCurrency") String transCurrency){
        return bigQueryService.testQuery20(roleIdCust,transType,transType2,creditDebit,transCurrency);
    }

    @GetMapping("/test-query21/{roleIdCust}/{transType}/{transType2}/{transType3}/{creditDebit}")
    public List<String> testQuery21(@PathVariable("roleIdCust") Long roleIdCust, @PathVariable("transType") String transType,
                                    @PathVariable("transType2") String transType2, @PathVariable("transType3") String transType3,
                                    @PathVariable("creditDebit") String creditDebit){
        return bigQueryService.testQuery21(roleIdCust,transType,transType2,transType3,creditDebit);
    }

}

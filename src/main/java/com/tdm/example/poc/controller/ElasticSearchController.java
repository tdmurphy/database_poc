package com.tdm.example.poc.controller;

import com.tdm.example.poc.entity.document.TransactionDocument;
import com.tdm.example.poc.entity.document.TransactionResultDocument;
import com.tdm.example.poc.service.ElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/poc/els")
public class ElasticSearchController {

    private ElasticService elasticService;

    @Autowired
    public ElasticSearchController(ElasticService elasticService){
        this.elasticService = elasticService;
    }

    @GetMapping("/test-query/{roleIdCust}")
    public List<TransactionDocument> searchByRoleIdCust(@PathVariable("roleIdCust") Long roleIdCust) throws IOException {
        return elasticService.searchByRoleIdCust(roleIdCust);
    }

    @GetMapping("/test-query1-2/{roleIdCust}/{counterpartyCin}")
    public List<TransactionResultDocument> testQuery12(@PathVariable("roleIdCust") Long roleIdCust, @PathVariable("counterpartyCin") String counterpartyCin) throws IOException{
        return elasticService.testQuery12(roleIdCust,counterpartyCin);
    }

    @GetMapping("/test-query10-11/{roleIdCust}/{transType}")
    public List<TransactionResultDocument> testQuery1011(@PathVariable("roleIdCust") Long roleIdCust, @PathVariable("transType") String transType) throws IOException{
        return elasticService.testQuery1011(roleIdCust,transType);
    }

    @GetMapping("/test-query20/{roleIdCust}/{transType}/{transType2}/{creditDebit}/{transCurrency}")
    public List<TransactionResultDocument> testQuery20(@PathVariable("roleIdCust") Long roleIdCust, @PathVariable("transType") String transType,
                                                 @PathVariable("transType2") String transType2, @PathVariable("creditDebit") String creditDebit,
                                                 @PathVariable("transCurrency") String transCurrency) throws IOException{
        return elasticService.testQuery20(roleIdCust,transType,transType2,creditDebit,transCurrency);
    }

    @GetMapping("/test-query21/{roleIdCust}/{transType}/{transType2}/{transType3}/{creditDebit}")
    public List<TransactionResultDocument> testQuery21(@PathVariable("roleIdCust") Long roleIdCust, @PathVariable("transType") String transType,
                                                   @PathVariable("transType2") String transType2, @PathVariable("transType3") String transType3,
                                                   @PathVariable("creditDebit") String creditDebit) throws IOException {
        return elasticService.testQuery21(roleIdCust,transType,transType2,transType3,creditDebit);
    }

    @GetMapping("/timings")
    public String getTimings(){
        return elasticService.getTimings();
    }

}

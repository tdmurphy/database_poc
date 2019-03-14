package com.tdm.example.poc.controller;

import com.tdm.example.poc.entity.PostgresLimitedEntity;
import com.tdm.example.poc.repo.PostgresRepository;
import com.tdm.example.poc.utility.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/poc/psql")
public class PostgresController {

    private PostgresRepository postgresRepository;

    private TimeUtil timeUtil = new TimeUtil();

    private HashMap<String,ArrayList<TimeUtil>> timings = new HashMap<>();

    private boolean timingsInitialised = false;

    @Autowired
    public PostgresController(PostgresRepository postgresRepository){
        this.postgresRepository = postgresRepository;
    }

    @GetMapping("/timings")
    public String getTimings(){
        if(!timingsInitialised) {
            timings.put("query_1_2", new ArrayList<>());
            timings.put("query_10_11", new ArrayList<>());
            timings.put("query_20", new ArrayList<>());
            timings.put("query_21", new ArrayList<>());
            timingsInitialised = true;
            return "Query timing logger initialised";
        }
        return timeUtil.getTimings(timings);
    }


    @GetMapping("/test-query1-2/{roleIdCust}/{counterpartyCin}")
    public PostgresLimitedEntity testQuery12(@PathVariable("roleIdCust") Long roleIdCust, @PathVariable("counterpartyCin") String counterpartyCin) {

        Long executionStartTime = System.currentTimeMillis();
        PostgresLimitedEntity resultSet = postgresRepository.testQuery12(roleIdCust, counterpartyCin);
        TimeUtil executeUtil = new TimeUtil();
        Long executionEndTime = System.currentTimeMillis();
        executeUtil.setTotalExecutionTime(executionEndTime-executionStartTime);
        executeUtil.setQueryTime(executionEndTime-executionStartTime);
        executeUtil.setResultSetSize(resultSet != null ? 1 : 0);

        ArrayList<TimeUtil> timeUtils = timings.get("query_1_2");
        timeUtils.add(executeUtil);
        timings.put("query_1_2",timeUtils);

        return resultSet;
    }

    @GetMapping("/test-query10-11/{roleIdCust}/{transType}")
    public List<PostgresLimitedEntity> testQuery1011(@PathVariable("roleIdCust") Long roleIdCust, @PathVariable("transType") String transType) {
        Long executionStartTime = System.currentTimeMillis();
        List<PostgresLimitedEntity> resultSet =  postgresRepository.testQuery1011(roleIdCust, transType, PageRequest.of(0,100));
        TimeUtil executeUtil = new TimeUtil();
        Long executionEndTime = System.currentTimeMillis();
        executeUtil.setTotalExecutionTime(executionEndTime-executionStartTime);
        executeUtil.setQueryTime(executionEndTime-executionStartTime);
        executeUtil.setResultSetSize(resultSet.size());

        ArrayList<TimeUtil> timeUtils = timings.get("query_10_11");
        timeUtils.add(executeUtil);
        timings.put("query_10_11",timeUtils);

        return resultSet;
    }

    @GetMapping("/test-query20/{roleIdCust}/{transType}/{transType2}/{creditDebit}/{transCurrency}")
    public List<PostgresLimitedEntity> testQuery20(@PathVariable("roleIdCust") Long roleIdCust, @PathVariable("transType") String transType,
                                                    @PathVariable("transType2") String transType2, @PathVariable("creditDebit") String creditDebit,
                                                   @PathVariable("transCurrency") String transCurrency) {
        Long executionStartTime = System.currentTimeMillis();
        List<PostgresLimitedEntity> resultSet = postgresRepository.testQuery20(roleIdCust, transType, transType2,creditDebit,transCurrency, PageRequest.of(0,100));
        TimeUtil executeUtil = new TimeUtil();
        Long executionEndTime = System.currentTimeMillis();
        executeUtil.setTotalExecutionTime(executionEndTime-executionStartTime);
        executeUtil.setQueryTime(executionEndTime-executionStartTime);
        executeUtil.setResultSetSize(resultSet.size());

        ArrayList<TimeUtil> timeUtils = timings.get("query_20");
        timeUtils.add(executeUtil);
        timings.put("query_20",timeUtils);

        return resultSet;
    }

    @GetMapping("/test-query21/{roleIdCust}/{transType}/{transType2}/{transType3}/{creditDebit}")
    public List<PostgresLimitedEntity> testQuery21(@PathVariable("roleIdCust") Long roleIdCust, @PathVariable("transType") String transType,
                                                   @PathVariable("transType2") String transType2, @PathVariable("transType3") String transType3,
                                                   @PathVariable("creditDebit") String creditDebit) {
        Long executionStartTime = System.currentTimeMillis();
        List<PostgresLimitedEntity> resultSet = postgresRepository.testQuery21(roleIdCust, transType, transType2, transType3, creditDebit, PageRequest.of(0,100));
        TimeUtil executeUtil = new TimeUtil();
        Long executionEndTime = System.currentTimeMillis();
        executeUtil.setTotalExecutionTime(executionEndTime-executionStartTime);
        executeUtil.setQueryTime(executionEndTime-executionStartTime);
        executeUtil.setResultSetSize(resultSet.size());

        ArrayList<TimeUtil> timeUtils = timings.get("query_21");
        timeUtils.add(executeUtil);
        timings.put("query_21",timeUtils);

        return resultSet;
    }

}

package com.tdm.example.poc.utility;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

@Data
public class TimeUtil {
    private Long queryTime;
    private Long totalExecutionTime;
    private Integer resultSetSize;

    public String getTimings(HashMap<String,ArrayList<TimeUtil>> timings){
        StringBuilder queryTimes = new StringBuilder();
        queryTimes.append("Query Timings: </br></br>");

        for(String query : timings.keySet()){
            queryTimes.append(query + "</br> Execution Time | Query Time | ResultSet Size </br>");
            long overallExecutionTime = 0;
            long overallQueryTime = 0;

            for(TimeUtil timeUtil : timings.get(query)){
                queryTimes.append(timeUtil.getTotalExecutionTime() + " | " + timeUtil.getQueryTime() + " | " + timeUtil.getResultSetSize() + "</br>");
                overallExecutionTime = overallExecutionTime + timeUtil.getTotalExecutionTime();
                overallQueryTime = overallQueryTime + timeUtil.getQueryTime();
            }
            queryTimes.append("AVG EXECUTION TIME : " + (timings.get(query).size() == 0 ? 0 : overallExecutionTime/timings.get(query).size()) + "</br></br>");

        }

        return queryTimes.toString();
    }
}

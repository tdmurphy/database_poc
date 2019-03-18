package com.tdm.example.poc.controller;

import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/poc/hzl")
public class HazelCastController {

    private final HazelcastInstance hazelcastInstance;

    @Autowired
    HazelCastController(HazelcastInstance hazelcastInstance){
        this.hazelcastInstance = hazelcastInstance;
    }

    @PostMapping("/write-data")
    public String writeData(@RequestParam String key, @RequestParam String value){
        Map<String,String> hazelcastMap = hazelcastInstance.getMap("my-map");
        hazelcastMap.put(key, value);
        return "Data has been stored in hazelcast.";
    }

    @GetMapping("/read-data")
    public String readData(@RequestParam String key){
        Map<String,String> hazelcastMap = hazelcastInstance.getMap("my-map");
        return hazelcastMap.get(key);
    }

    @GetMapping("/get-all")
    public Map<String,String> getAllData(){
        return hazelcastInstance.getMap("my-map");
    }

}

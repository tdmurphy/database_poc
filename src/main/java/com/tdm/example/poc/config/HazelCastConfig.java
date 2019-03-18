package com.tdm.example.poc.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelCastConfig {

    @Bean
    public Config hazelCastConfiguration(){
        Config config = new Config();
        config.setInstanceName("hazelcast-test-instance")
                .addMapConfig(new MapConfig()
                .setName("config")
                .setMaxSizeConfig(new MaxSizeConfig(200,MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                .setEvictionPolicy(EvictionPolicy.LRU)
                .setTimeToLiveSeconds(-1));

        return config;
    }
}

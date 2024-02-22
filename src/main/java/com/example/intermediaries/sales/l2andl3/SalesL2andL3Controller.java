package com.example.intermediaries.sales.l2andl3;

import com.example.intermediaries.datagenerator.DataGeneratorCyclic;
import com.example.intermediaries.statistics.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@RestController
@RequestMapping("/company")
public class SalesL2andL3Controller {
    @Autowired
    private final SalesL2AndL3Service salesL2AndL3Service;
    private final DataGeneratorCyclic dataGeneratorCyclic;
    public SalesL2andL3Controller(DataGeneratorCyclic dataGeneratorCyclic, SalesL2AndL3Service salesL2AndL3Service){
        this.dataGeneratorCyclic = dataGeneratorCyclic;
        this.salesL2AndL3Service = salesL2AndL3Service;
    }


    @GetMapping("/salesL2andL3")
    public Flux<ServerSentEvent<ConcurrentHashMap<Integer,SalesL2L3DTO>>> streamStatistics() {
        return salesL2AndL3Service.getSalesL2L3Stream()
                .map(data -> ServerSentEvent.builder(data).build())
                .delayElements(Duration.ofSeconds(5));
    }

}

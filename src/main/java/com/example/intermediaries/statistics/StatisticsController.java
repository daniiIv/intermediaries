package com.example.intermediaries.statistics;

import com.example.intermediaries.datagenerator.DataGeneratorCyclic;
import com.example.intermediaries.sales.l2andl3.SalesL2AndL3;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/company")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(DataGeneratorCyclic dataGeneratorCyclic, StatisticsService statisticsService){

        this.statisticsService = statisticsService;
    }
    @GetMapping("/statistics")
    public Flux<ServerSentEvent<Map<String, Double>>> streamStatistics() {
            return statisticsService.getStatisticsStream()
                    .map(data -> ServerSentEvent.builder(data).build())
                    .delayElements(Duration.ofSeconds(5));
    }
//sales L2 and l3 data stream produced

    /*
    @GetMapping(value = "/salesL2L3", produces = "text/event-stream")
    public Flux<ServerSentEvent<SalesL2AndL3>> streamDataSalesL2L3(){
        return dataGeneratorCyclic.getDataStreamSalesL2AndL3()
                .map(data-> ServerSentEvent.builder(data).build())
                .delayElements(Duration.ofSeconds(1));
    }
*/
 /*   @GetMapping(path = "/companyProfit")
    public Flux<ServerSentEvent<String>> streamStatistics() {
        return Flux.interval(Duration.ofSeconds(5))
                .log()
                .map(sequence -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(sequence))
                        .event("test")
                        .data("Flux - " + LocalTime.now().toString())
                        .build());
    }
   /* @CrossOrigin
@RequestMapping(value = "/subscribe")
public SseEmitter subscribe(){
        SseEmitter sseEmitter = new SseEmitter();
                return sseEmitter;
}
*/


}

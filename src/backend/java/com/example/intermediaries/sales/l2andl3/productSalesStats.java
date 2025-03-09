package com.example.intermediaries.sales.l2andl3;

import com.example.intermediaries.datagenerator.DataGeneratorCyclic;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class productSalesStats {
    productSalesStats(){
        executorService.scheduleAtFixedRate(this::updateStatisticsData, 0, 5, TimeUnit.SECONDS);
    }
    private final AtomicInteger requestCounter = new AtomicInteger(0);
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public static Map<String, Long> statsList = new ConcurrentHashMap();
    private void updateStatisticsData() {
        statsList.put("Citizens responsibility", DataGeneratorCyclic.salesL2AndL3DTOCollection.values().stream().filter(a-> a.productTitle().equals("Citizens responsibility")).count());
        statsList.put("Motor Hull", DataGeneratorCyclic.salesL2AndL3DTOCollection.values().stream().filter(a-> a.productTitle().equals("Motor Hull")).count());
        statsList.put("Life insurance 1",DataGeneratorCyclic.salesL2AndL3DTOCollection.values().stream().filter(a-> a.productTitle().equals("Life insurance_1")).count());
        statsList.put("ife insurance 2",DataGeneratorCyclic.salesL2AndL3DTOCollection.values().stream().filter(a-> a.productTitle().equals("Life insurance_2")).count());
        statsList.put("House insurance 1", DataGeneratorCyclic.salesL2AndL3DTOCollection.values().stream().filter(a-> a.productTitle().equals("House insurance_1")).count());
        statsList.put("House insurance 2", DataGeneratorCyclic.salesL2AndL3DTOCollection.values().stream().filter(a-> a.productTitle().equals("House insurance_2")).count());
    }

    public Flux<Map<String, Long>> getProductsStatsStream() {
        return Flux.interval(Duration.ofSeconds(5))
                .flatMap(ignore -> Flux.just(statsList).subscribeOn(Schedulers.boundedElastic()))
                .doOnRequest(requests -> requestCounter.addAndGet((int)requests))
                //.onBackpressureBuffer(requestCounter::getAndDecrement)
                .delayElements(Duration.ofSeconds(5));
    }
}

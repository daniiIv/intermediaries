package com.example.intermediaries.sales.l2andl3;

import com.example.intermediaries.datagenerator.DataGeneratorCyclic;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SalesL2AndL3Service {
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public static Map<Integer,List<Double>> intermediariesAAndBData = new HashMap<>();
    public static Map<String, Double> intermediariesData = new HashMap<>();

    private final AtomicInteger requestCounter = new AtomicInteger(0);

    public Flux<ConcurrentHashMap<Integer,SalesL2L3DTO>> getSalesL2L3Stream() {
        return Flux.interval(Duration.ofSeconds(5))
                .flatMap(ignore -> Flux.just(DataGeneratorCyclic.salesL2AndL3DTOCollection).subscribeOn(Schedulers.boundedElastic()))
                .doOnRequest(requests -> requestCounter.addAndGet((int)requests))
                //.onBackpressureBuffer(requestCounter::getAndDecrement)
                .delayElements(Duration.ofSeconds(5));
    }
}

package com.example.intermediaries.statistics;

import com.example.intermediaries.datagenerator.DataGeneratorCyclic;
import com.example.intermediaries.sales.l2andl3.SalesL2AndL3;
import com.example.intermediaries.sales.l2andl3.SalesL2AndL3Repository;
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
public class StatisticsService {

    public StatisticsService() {
        // Schedule task to update statistics data every 5 seconds
        executorService.scheduleAtFixedRate(this::updateStatisticsData, 0, 5, TimeUnit.SECONDS);
    }
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private volatile double companyProfit = 0;
    private volatile double numberOfSales = 0;
    private volatile double premiunsIncome = 0;
    public static Map<String, Double> statsList = new ConcurrentHashMap();

    private final AtomicInteger requestCounter = new AtomicInteger(0);

    private void updateCompanyProfit(){
        companyProfit = DataGeneratorCyclic.salesL2AndL3Collection
                .values()
                .stream()
                .map(SalesL2AndL3::getCompanyProfit)
                .mapToDouble(a-> a).sum();
    }

    private void updateStatisticsData(){
        companyProfit = DataGeneratorCyclic.salesL2AndL3Collection
                .values()
                .stream()
                .map(SalesL2AndL3::getCompanyProfit)
                .mapToDouble(a-> a).sum();

        numberOfSales = DataGeneratorCyclic.salesL2AndL3Collection.size();
        premiunsIncome = DataGeneratorCyclic.salesL2AndL3Collection
                .values()
                .stream()
                .map(SalesL2AndL3::getPremiumCalculated)
                .mapToDouble(a->a)
                .sum();

        statsList.put("companyProfit", companyProfit);
        statsList.put("numberOfSales", numberOfSales);
        statsList.put("premiumIncome",premiunsIncome);
    }

    //data stream
  /*  public Flux<Map<String, Double>> getStatisticsStream() {
        return Flux.interval(Duration.ofSeconds(5)) // Emit every 1 second
                .map(ignore -> statsList);
    }*/

    public Flux<Map<String, Double>> getStatisticsStream() {
        return Flux.interval(Duration.ofSeconds(5))
                .flatMap(ignore -> Flux.just(statsList).subscribeOn(Schedulers.boundedElastic()))
                .doOnRequest(requests -> requestCounter.addAndGet((int)requests))
                //.onBackpressureBuffer(requestCounter::getAndDecrement)
                .delayElements(Duration.ofSeconds(5));
    }
}

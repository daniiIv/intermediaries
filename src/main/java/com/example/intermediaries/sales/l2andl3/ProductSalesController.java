package com.example.intermediaries.sales.l2andl3;

import com.example.intermediaries.datagenerator.DataGeneratorCyclic;
import com.example.intermediaries.statistics.StatisticsService;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Map;

@Controller
public class ProductSalesController {
    private final productSalesStats productSalesStats;

    public ProductSalesController( productSalesStats productSalesStats){

        this.productSalesStats = productSalesStats;
    }
    @GetMapping("/productStatistics")
    public Flux<ServerSentEvent<Map<String, Long>>> streamStatistics() {
        return productSalesStats.getProductsStatsStream()
                .map(data -> ServerSentEvent.builder(data).build())
                .delayElements(Duration.ofSeconds(5));
    }
}

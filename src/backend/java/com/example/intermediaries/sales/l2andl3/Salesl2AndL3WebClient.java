package com.example.intermediaries.sales.l2andl3;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class Salesl2AndL3WebClient {
    WebClient client = WebClient.create("http://localhost:8080");
   public void retreaveSales(){
       Flux<SalesL2AndL3> salesL2AndL3Flux = client.get()
               .uri("/sales")
               .retrieve()
               .bodyToFlux(SalesL2AndL3.class);
       salesL2AndL3Flux.subscribe(System.out::println);
   }

}

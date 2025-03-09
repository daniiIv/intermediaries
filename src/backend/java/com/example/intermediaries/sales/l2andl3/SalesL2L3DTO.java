package com.example.intermediaries.sales.l2andl3;

import java.time.Instant;

public record SalesL2L3DTO(Integer id, String intermediaryTitle, String productTitle, Double premiumCalcuated, Double commissionCalculated, Instant salesDate, Double companyProfit) {
}

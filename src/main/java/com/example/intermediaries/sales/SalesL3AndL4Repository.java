package com.example.intermediaries.sales;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesL3AndL4Repository extends JpaRepository<SalesL3AndL4, Integer> {
}

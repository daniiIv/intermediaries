package com.example.intermediaries.repositories;

import com.example.intermediaries.entities.comissions.CommissionRateL3andL4;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommissionRateL3andL4Repository extends JpaRepository<CommissionRateL3andL4, Integer> {
}

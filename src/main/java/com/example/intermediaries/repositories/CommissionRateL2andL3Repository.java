package com.example.intermediaries.repositories;

import com.example.intermediaries.entities.comissions.CommissionRateL2andL3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommissionRateL2andL3Repository extends JpaRepository<CommissionRateL2andL3, Integer> {
}

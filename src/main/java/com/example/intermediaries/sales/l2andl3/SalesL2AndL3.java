package com.example.intermediaries.sales.l2andl3;

import com.example.intermediaries.comissions.CommissionRateL2andL3;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
public class SalesL2AndL3 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Double ibm;
    private int premium;
    private Double premiumCalculated;
    @ManyToOne
    @JoinColumn(name = "FK_commissionRateL3L2", referencedColumnName = "id")
    private CommissionRateL2andL3 FK_commissionRateL3L2;

    private Double commissionCalculated;
    @CreationTimestamp
    private Instant salesDate;
    private Double companyProfit;

}

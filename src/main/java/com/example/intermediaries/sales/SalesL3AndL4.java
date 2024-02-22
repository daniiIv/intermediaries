package com.example.intermediaries.sales;

import com.example.intermediaries.comissions.CommissionRateL3andL4;
import com.example.intermediaries.intermediaries.childrenIntermediaries.ChildrenIntermediaries;
import com.example.intermediaries.intermediaries.parentIntermediaries.ParentIntermediaries;
import com.example.intermediaries.sales.l2andl3.SalesL2AndL3;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class SalesL3AndL4 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
@ManyToOne
private ParentIntermediaries parentIntermediariesFK;
private Double parentCommissionCalculated;
@ManyToOne
private ChildrenIntermediaries childrenIntermediariesFK;
private Double childCommissionCalculated;
@ManyToOne()
private SalesL2AndL3 salesL2AndL3Id;

@ManyToOne()
private CommissionRateL3andL4 commissionRateL3AndL4Id;

}

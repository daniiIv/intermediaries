package com.example.intermediaries.comissions;

import com.example.intermediaries.intermediaries.childrenIntermediaries.ChildrenIntermediaries;
import com.example.intermediaries.intermediaries.parentIntermediaries.ParentIntermediaries;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
public class CommissionRateL3andL4 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne()
    @JoinColumn(name = "FK_commission_rate_L3_id", referencedColumnName = "id")
    private CommissionRateL2andL3 commissionRateL2andL3;

    private int commissionRateTotal;
    private int commissionRatePersonal;

    @ManyToOne()
    @JoinColumn(name = "FK_parentIntermediaries_id", referencedColumnName = "id" , nullable = true)
    private ParentIntermediaries parentIntermediaries;

    @ManyToOne()
    @JoinColumn(name = "FK_childrenIntermediaries_id", referencedColumnName = "id" , nullable = true)
    private ChildrenIntermediaries childrenIntermediaries;

}

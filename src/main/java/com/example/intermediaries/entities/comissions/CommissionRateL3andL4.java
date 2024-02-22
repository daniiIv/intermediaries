package com.example.intermediaries.entities.comissions;

import com.example.intermediaries.entities.registrations.ChildrenIntermediaries;
import com.example.intermediaries.entities.registrations.ParentIntermediaries;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

@Table
@Entity
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

    public int getId() {
        return id;
    }

    public CommissionRateL2andL3 getCommissionRateL2andL3() {
        return commissionRateL2andL3;
    }

    public void setCommissionRateL2andL3(CommissionRateL2andL3 commissionRateL2andL3) {
        this.commissionRateL2andL3 = commissionRateL2andL3;
    }

    public int getCommissionRateTotal() {
        return commissionRateTotal;
    }

    public void setCommissionRateTotal() {
        if (getCommissionRateL2andL3()!=null)
        this.commissionRateTotal= commissionRateL2andL3.getCommissionRateTotal();
    }

    public ParentIntermediaries getParentIntermediaries() {
        return parentIntermediaries;
    }

    public void setParentIntermediaries(ParentIntermediaries parentIntermediaries) {
        this.parentIntermediaries = parentIntermediaries;
    }

    public ChildrenIntermediaries getChildrenIntermediaries() {
        return childrenIntermediaries;
    }

    public void setChildrenIntermediaries(ChildrenIntermediaries childrenIntermediaries) {
        this.childrenIntermediaries = childrenIntermediaries;
    }

    public int getCommissionRatePersonal() {
        return commissionRatePersonal;
    }

    public void setCommissionRatePersonal(int commissionRatePersonal) {
        this.commissionRatePersonal = commissionRatePersonal;
    }
}

package com.example.intermiteries.comissions;

import com.example.intermiteries.info.IntermidiaryTypes;
import com.example.intermiteries.products.ProductType;
import com.example.intermiteries.registrations.ParentIntermediaries;
import jakarta.persistence.*;

@Table
@Entity
public class CommissionRateL2andL3 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private  int commissionRateTotal;

    @ManyToOne()
    @JoinColumn(name = "FK_product_type_id", referencedColumnName = "id")
    private ProductType productType;

    @ManyToOne()
    @JoinColumn(name = "FK_parent_intermediaries_id", referencedColumnName = "id")
    private ParentIntermediaries parentIntermediaries;

    public int getId() {
        return id;
    }

    public int getCommissionRateTotal() {
        return commissionRateTotal;
    }

    public void setCommissionRateTotal(int commissionRateTotal) {
        this.commissionRateTotal = commissionRateTotal;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public ParentIntermediaries getParentIntermediaries() {
        return parentIntermediaries;
    }

    public void setParentIntermediaries(ParentIntermediaries parentIntermediaries) {
        this.parentIntermediaries = parentIntermediaries;
    }
}

package com.example.intermediaries.comissions;

import com.example.intermediaries.intermediaries.IntermediaryTypes;
import com.example.intermediaries.products.ProductType;
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
public class CommissionRateL2andL3 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private int commissionRateTotal;

    @ManyToOne()
    @JoinColumn(name = "FK_product_type_id", referencedColumnName = "id")
    private ProductType productType;

    @ManyToOne()
    @JoinColumn(name = "FK_parent_intermediaries_id", referencedColumnName = "id")
    private ParentIntermediaries parentIntermediaries;

    @ManyToOne
    IntermediaryTypes intermediaryTypes;
}


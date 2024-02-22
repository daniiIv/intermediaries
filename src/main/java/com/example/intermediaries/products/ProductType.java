package com.example.intermediaries.products;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class ProductType {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private ProductCodeAndTitle code;
    private String title ;

}

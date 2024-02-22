package com.example.intermiteries.products;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.*;

@Entity
@Table
public class ProductType {
    @Id
    @GeneratedValue(strategy = AUTO)
    private int id;
    private ProductCodeAndTitle code;

    private String title ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductCodeAndTitle getCode() {
        return code;
    }

    public void setCode(ProductCodeAndTitle code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle() {
        if (getCode()!=null){
            this.title = code.getTitle();
        }
    }
}

package com.example.intermediaries.products;

public enum ProductCodeAndTitle {
    GO("Citizens responsibility"),
    MH("Motor Hull"),
    LF1("Life insurance_1"),
    LF2("Life insurance_2"),
    H1("House insurance_1"),
    H2("House insurance_2");


    private String title;

    ProductCodeAndTitle(String title) {
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
}

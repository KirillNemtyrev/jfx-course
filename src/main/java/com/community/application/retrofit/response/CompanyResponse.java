package com.community.application.retrofit.response;

import java.math.BigDecimal;
import java.util.List;

public class CompanyResponse {
    private Long id;
    private String name;
    private String owner;
    private List<StockResponse> stocks;
    private Long stock;
    private Long countHave;
    private BigDecimal much;
    private String description;

    private BigDecimal cost;
    private float procent;
    private boolean upper;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public List<StockResponse> getStocks() {
        return stocks;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public float getProcent() {
        return procent;
    }

    public boolean isUpper() {
        return upper;
    }

    public Long getStock() {
        return stock;
    }

    public BigDecimal getMuch() {
        return much;
    }

    public String getDescription() {
        return description;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getCountHave() {
        return countHave;
    }
}

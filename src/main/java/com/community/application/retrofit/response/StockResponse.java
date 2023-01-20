package com.community.application.retrofit.response;

import java.math.BigDecimal;

public class StockResponse {
    private BigDecimal cost;
    private float procent;
    private boolean upper;
    private Long much;
    private Long date;

    public BigDecimal getCost() {
        return cost;
    }

    public float getProcent() {
        return procent;
    }

    public boolean isUpper() {
        return upper;
    }

    public Long getMuch() {
        return much;
    }

    public Long getDate() {
        return date;
    }
}

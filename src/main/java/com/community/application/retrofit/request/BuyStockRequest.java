package com.community.application.retrofit.request;

public class BuyStockRequest {
    private Long id;
    private Long count;

    public BuyStockRequest(Long id, Long count) {
        this.id = id;
        this.count = count;
    }

    public Long getCount() {
        return count;
    }

    public Long getId() {
        return id;
    }
}

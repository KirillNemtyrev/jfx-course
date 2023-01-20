package com.community.application.retrofit.request;

public class SellStockRequest {
    private Long id;
    private Long count;

    public SellStockRequest(Long id, Long count) {
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

package com.community.application.retrofit.response;

import java.math.BigDecimal;
import java.util.List;

public class ProfileResponse {
    private String username;
    private String name;
    private BigDecimal balance;
    private BigDecimal backpack;
    private List<CompanyResponse> company;

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getBackpack() {
        return backpack;
    }

    public List<CompanyResponse> getCompany() {
        return company;
    }
}

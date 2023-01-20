package com.community.application.retrofit.response;

import java.math.BigDecimal;
import java.util.List;

public class BackpackResponse {
    private BigDecimal balance;
    private BigDecimal brokerage;
    private List<CompanyResponse> companyList;

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getBrokerage() {
        return brokerage;
    }

    public List<CompanyResponse> getCompanyList() {
        return companyList;
    }
}

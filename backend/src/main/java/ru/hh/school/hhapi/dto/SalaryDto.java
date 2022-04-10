package ru.hh.school.hhapi.dto;

import java.math.BigDecimal;

public class SalaryDto {

    private BigDecimal from;
    private BigDecimal to;
    private String currency;
    private boolean gross;

    public SalaryDto() {}

    public BigDecimal getFrom() {
        return from;
    }

    public void setFrom(BigDecimal from) {
        this.from = from;
    }

    public BigDecimal getTo() {
        return to;
    }

    public void setTo(BigDecimal to) {
        this.to = to;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isGross() {
        return gross;
    }

    public void setGross(boolean gross) {
        this.gross = gross;
    }
}

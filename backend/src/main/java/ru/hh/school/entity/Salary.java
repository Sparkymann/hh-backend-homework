package ru.hh.school.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigInteger;

@Embeddable
public class Salary {

    @Column(name = "salary_from")
    private BigInteger from;

    @Column(name = "salary_to")
    private BigInteger to;

    @Column(name = "currency")
    private String currency;

    @Column(name = "gross")
    private Boolean gross;

    public Boolean getGross() {
        return gross;
    }

    public void setGross(Boolean gross) {
        this.gross = gross;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigInteger getTo() {
        return to;
    }

    public void setTo(BigInteger to) {
        this.to = to;
    }

    public BigInteger getFrom() {
        return from;
    }

    public void setFrom(BigInteger from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "from = " + from + ", " +
                "to = " + to + ", " +
                "currency = " + currency + ", " +
                "gross = " + gross + ")";
    }
}
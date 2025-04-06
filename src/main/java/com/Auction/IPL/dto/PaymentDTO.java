package com.Auction.IPL.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentDTO {

    private Long id;
    private String teamName;
    private BigDecimal amount;
    private String paymentStatus;
    private LocalDateTime paymentDate;

    // Constructors
    public PaymentDTO() {}

    public PaymentDTO(Long id, String teamName, BigDecimal amount, String paymentStatus, LocalDateTime paymentDate) {
        this.id = id;
        this.teamName = teamName;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
}


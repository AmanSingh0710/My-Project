package com.Auction.IPL.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BidDTO {

    private Long id;
    private String playerName;
    private String teamName;
    private BigDecimal bidAmount;
    private LocalDateTime bidTime;

    // Constructors
    public BidDTO() {}

    public BidDTO(Long id, String playerName, String teamName, BigDecimal bidAmount, LocalDateTime bidTime) {
        this.id = id;
        this.playerName = playerName;
        this.teamName = teamName;
        this.bidAmount = bidAmount;
        this.bidTime = bidTime;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public BigDecimal getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(BigDecimal bidAmount) {
        this.bidAmount = bidAmount;
    }

    public LocalDateTime getBidTime() {
        return bidTime;
    }

    public void setBidTime(LocalDateTime bidTime) {
        this.bidTime = bidTime;
    }
}

package com.Auction.IPL.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AuctionDTO {

    private Long id;
    private String playerName;
    private String teamName;
    private BigDecimal basePrice;
    private BigDecimal soldPrice;
    private LocalDateTime auctionDate;

    // Constructors
    public AuctionDTO() {}

    public AuctionDTO(Long id, String playerName, String teamName, BigDecimal basePrice, BigDecimal soldPrice, LocalDateTime auctionDate) {
        this.id = id;
        this.playerName = playerName;
        this.teamName = teamName;
        this.basePrice = basePrice;
        this.soldPrice = soldPrice;
        this.auctionDate = auctionDate;
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

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(BigDecimal soldPrice) {
        this.soldPrice = soldPrice;
    }

    public LocalDateTime getAuctionDate() {
        return auctionDate;
    }

    public void setAuctionDate(LocalDateTime auctionDate) {
        this.auctionDate = auctionDate;
    }
}


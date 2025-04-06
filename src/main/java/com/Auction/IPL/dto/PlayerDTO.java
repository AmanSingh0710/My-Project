package com.Auction.IPL.dto;

public class PlayerDTO {

    private Long id;
    private String name;
    private String teamName;
    private String role;
    private double basePrice;
    private double soldPrice;
    
    // Constructors
    public PlayerDTO() {}

    public PlayerDTO(Long id, String name, String teamName, String role, double basePrice, double soldPrice) {
        this.id = id;
        this.name = name;
        this.teamName = teamName;
        this.role = role;
        this.basePrice = basePrice;
        this.soldPrice = soldPrice;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(double soldPrice) {
        this.soldPrice = soldPrice;
    }
}


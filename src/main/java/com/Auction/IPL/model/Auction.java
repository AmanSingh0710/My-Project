package com.Auction.IPL.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auctions")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_id")
    private Long teamId;
    

    @Column(nullable = false)
    private LocalDateTime auctionDate;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private boolean isActive;

    private String status; 

    private boolean completed;

    // Constructors
    public Auction() {}

    public Auction(String name, LocalDateTime auctionDate, String location, boolean isActive, String status) {
        this.name = name;
        this.auctionDate = auctionDate;
        this.location = location;
        this.isActive = isActive;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getteamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public LocalDateTime getAuctionDate() {
        return auctionDate;
    }

    public void setAuctionDate(LocalDateTime auctionDate) {
        this.auctionDate = auctionDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}


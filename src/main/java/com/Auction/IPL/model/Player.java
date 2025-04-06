package com.Auction.IPL.model;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age; 

    @Column(nullable = false)
    private String role; // Example: Batsman, Bowler, All-Rounder, Wicketkeeper

    @Column(nullable = false)
    private double basePrice;

    @Column(nullable = false)
    private double soldPrice;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    // Constructors
    public Player() {}

    public Player(String name, int age, String role, double basePrice, double soldPrice, Team team) {
        this.name = name;
        this.role = role;
        this.basePrice = basePrice;
        this.soldPrice = soldPrice;
        this.team = team;
        this.age = age;
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

    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age = age;
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}


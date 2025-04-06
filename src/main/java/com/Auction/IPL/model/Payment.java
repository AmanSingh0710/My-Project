package com.Auction.IPL.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "bid_id", nullable = false)
    private Bid bid;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne
    @JoinColumn(name = "user_id") 
    private User user;

    @ManyToOne
    @JoinColumn(name = "auction_id", referencedColumnName = "id")
    private Auction auction;


    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String paymentMethod;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    // Constructors
    public Payment() {}

    public Payment(Bid bid,Team team, Player player, BigDecimal amount, String paymentMethod, LocalDateTime paymentDate, LocalDateTime timestamp,PaymentStatus status) {
        this.team = team;
        this.player = player;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.status = status;
        this.bid = bid;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) { 
        this.bid = bid;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = BigDecimal.valueOf(amount);
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) { 
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) { 
        this.timestamp = timestamp;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}


package com.Auction.IPL.repository;

import com.Auction.IPL.model.Auction;
import com.Auction.IPL.model.Payment;
import com.Auction.IPL.model.Team;
import com.Auction.IPL.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Find all payments by user ID
    List<Payment> findByUser(User user);

    // Find all payments related to a specific auction
    List<Payment> findByAuction(Auction auction);

    List<Payment> findByBid_PlayerId(Long playerId);

    List<Payment> findByBid_Team(Team team);
}


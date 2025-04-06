package com.Auction.IPL.repository;

import com.Auction.IPL.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

    // Find all auctions by team ID
    List<Auction> findByTeamId(Long teamId);

    // Find all auctions by status (e.g., ACTIVE, COMPLETED)
    List<Auction> findByStatus(String status);
}

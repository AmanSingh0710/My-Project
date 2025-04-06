package com.Auction.IPL.repository;

import com.Auction.IPL.model.Auction;
import com.Auction.IPL.model.Bid;
import com.Auction.IPL.model.Team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    // Find all bids for a specific auction
    List<Bid> findByAuctionId(Long auctionId);

    // Find all bids by a specific player
    List<Bid> findByPlayerId(Long playerId);

    List<Bid> findByAuction(Auction auction);

    // Find the highest bid for a specific auction
    Bid findTopByAuctionIdOrderByBidAmountDesc(Long auctionId);

    List<Bid> findByTeam(Team team);
}


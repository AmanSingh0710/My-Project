package com.Auction.IPL.service;

import com.Auction.IPL.model.Auction;
import com.Auction.IPL.model.Bid;
import com.Auction.IPL.model.Player;
import com.Auction.IPL.model.Team;
import com.Auction.IPL.repository.AuctionRepository;
import com.Auction.IPL.repository.BidRepository;
import com.Auction.IPL.repository.PlayerRepository;
import com.Auction.IPL.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private BidRepository bidRepository;

    // Get all auctions
    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    // Get an auction by ID
    public Auction getAuctionById(Long auctionId) {
        return auctionRepository.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("Auction not found with ID: " + auctionId));
    }

    // Create a new auction
    public Auction startAuction(Auction auction) {
        return auctionRepository.save(auction);
    }

    // Place a bid in an auction
    public Bid placeBid(Long auctionId, Long teamId, Long playerId, double bidAmount) {
        Auction auction = getAuctionById(auctionId);
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found with ID: " + teamId));
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with ID: " + playerId));

        // Create and save the bid
        Bid bid = new Bid();
        bid.setAuction(auction);
        bid.setTeam(team);
        bid.setPlayer(player);
        bid.setBidAmount(bidAmount);

        return bidRepository.save(bid);
    }

    // Get all bids for a specific auction
    public List<Bid> getBidsByAuction(Long auctionId) {
        Auction auction = getAuctionById(auctionId);
        return bidRepository.findByAuction(auction);
    }

    // End the auction and finalize the highest bid
    public Bid finalizeAuction(Long auctionId) {
        Auction auction = getAuctionById(auctionId);
        List<Bid> bids = bidRepository.findByAuction(auction);

        if (bids.isEmpty()) {
            throw new RuntimeException("No bids placed for this auction.");
        }

        // Find the highest bid
        Bid highestBid = bids.stream().max((b1, b2) -> Double.compare(b1.getBidAmount(), b2.getBidAmount()))
                .orElseThrow(() -> new RuntimeException("Error determining highest bid."));

        // Assign the player to the winning team
        Player player = highestBid.getPlayer();
        player.setTeam(highestBid.getTeam());
        playerRepository.save(player);

        // Mark the auction as completed
        auction.setCompleted(true);
        auctionRepository.save(auction);

        return highestBid;
    }
}

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
public class BidService {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    // Get all bids
    public List<Bid> getAllBids() {
        return bidRepository.findAll();
    }

    // Get bids for a specific player
    public List<Bid> getBidsByPlayer(Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with ID: " + playerId));
        return bidRepository.findByPlayerId(playerId);
    }

     // Get bids for a specific team
     public List<Bid> getBidsByTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found with ID: " + teamId));
        return bidRepository.findByTeam(team);
    }

    // Place a new bid
    public Bid placeBid(Long auctionId, Long teamId, Long playerId, double bidAmount) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("Auction not found with ID: " + auctionId));
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
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("Auction not found with ID: " + auctionId));
        return bidRepository.findByAuction(auction);
    }

      // Finalize the highest bid for a player
      public void finalizeBid(Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with ID: " + playerId));

        List<Bid> bids = bidRepository.findByPlayerId(playerId);
        if (bids.isEmpty()) {
            throw new RuntimeException("No bids found for player ID: " + playerId);
        }

        // Find the highest bid
        Bid highestBid = bids.stream().max((b1, b2) -> Double.compare(b1.getBidAmount(), b2.getBidAmount()))
                .orElseThrow(() -> new RuntimeException("Error determining highest bid."));

        // Assign the player to the winning team
        player.setTeam(highestBid.getTeam());
        playerRepository.save(player);
    }

    // Get the highest bid for a given auction
    public Optional<Bid> getHighestBidForAuction(Long auctionId) {
        List<Bid> bids = getBidsByAuction(auctionId);
        return bids.stream().max((b1, b2) -> Double.compare(b1.getBidAmount(), b2.getBidAmount()));
    }
}


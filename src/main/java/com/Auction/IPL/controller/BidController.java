package com.Auction.IPL.controller;

import com.Auction.IPL.model.Bid;
import com.Auction.IPL.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bids")
public class BidController{
    
    @Autowired
    private BidService bidService;

    // Get all bids
    @GetMapping
    public ResponseEntity<List<Bid>> getAllBids() {
        return ResponseEntity.ok(bidService.getAllBids());
    }

    // Get bids for a specific player
    @GetMapping("/player/{playerId}")
    public ResponseEntity<List<Bid>> getBidsByPlayer(@PathVariable Long playerId) {
        return ResponseEntity.ok(bidService.getBidsByPlayer(playerId));
    }

    // Get bids for a specific team
    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<Bid>> getBidsByTeam(@PathVariable Long teamId) {
        return ResponseEntity.ok(bidService.getBidsByTeam(teamId));
    }

    // Place a new bid
    @PostMapping("/place")
    public ResponseEntity<Bid> placeBid(@RequestParam Long auctionId, @RequestParam Long playerId, @RequestParam Long teamId, @RequestParam double bidAmount) {
        return ResponseEntity.ok(bidService.placeBid(auctionId, playerId, teamId, bidAmount));
    }

    // Finalize the highest bid for a player
    @PostMapping("/finalize/{playerId}")
    public ResponseEntity<String> finalizeBid(@PathVariable Long playerId) {
        bidService.finalizeBid(playerId);
        return ResponseEntity.ok("Bid finalized successfully.");
    }
}

package com.Auction.IPL.controller;

import com.Auction.IPL.model.Auction;
import com.Auction.IPL.model.Bid;
import com.Auction.IPL.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5500")
@RestController
@RequestMapping("/auction")
public class AuctionController {
    
    @Autowired
    private AuctionService auctionService;

    // Get all auctions
    @GetMapping
    public ResponseEntity<List<Auction>> getAllAuctions() {
        return ResponseEntity.ok(auctionService.getAllAuctions());
    }

    // Get auction details by ID
    @GetMapping("/{id}")
    public ResponseEntity<Auction> getAuctionById(@PathVariable Long id) {
        return ResponseEntity.ok(auctionService.getAuctionById(id));
    }

    // Start a new auction
    @PostMapping("/start")
    public ResponseEntity<Auction> startAuction(@RequestBody Auction auction) {
        return ResponseEntity.ok(auctionService.startAuction(auction));
    }

    // Place a bid for a player
    @PostMapping("/bid")
    public ResponseEntity<Bid> placeBid(@RequestParam Long auctionId, @RequestParam Long playerId, @RequestParam Long teamId, @RequestParam double bidAmount) {
        return ResponseEntity.ok(auctionService.placeBid(auctionId, playerId, teamId, bidAmount));
    }

    // Finalize the auction
    @PostMapping("/finalize/{id}")
    public ResponseEntity<String> finalizeAuction(@PathVariable Long id) {
        auctionService.finalizeAuction(id);
        return ResponseEntity.ok("Auction finalized successfully.");
    }
}

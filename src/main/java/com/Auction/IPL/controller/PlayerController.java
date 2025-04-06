package com.Auction.IPL.controller;

import com.Auction.IPL.model.Player;
import com.Auction.IPL.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")  // Allows frontend to access backend
@RestController
@RequestMapping("/players")
public class PlayerController {
    
    @Autowired
    private PlayerService playerService;

    // Get all players
    @GetMapping("/all")
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok(playerService.getAllPlayers());
    }

    // Get player by ID
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.getPlayerById(id));
    }

    // Add a new player
    @PostMapping("/add")
    public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
        return ResponseEntity.ok(playerService.addPlayer(player));
    }

    // Update a player
    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @RequestBody Player player) {
        return ResponseEntity.ok(playerService.updatePlayer(id, player));
    }

    // Delete a player
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.ok("Player deleted successfully");
    }
}

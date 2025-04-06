package com.Auction.IPL.service;

import com.Auction.IPL.model.Player;
import com.Auction.IPL.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    // Get all players
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    // Get a player by ID
    public Player getPlayerById(Long playerId) {
        Optional<Player> player = playerRepository.findById(playerId);
        return player.orElseThrow(() -> new RuntimeException("Player not found with ID: " + playerId));
    }

    // Add a new player
    public Player addPlayer(Player player) {
        return playerRepository.save(player);
    }

    // Update an existing player
    public Player updatePlayer(Long playerId, Player playerDetails) {
        Player player = getPlayerById(playerId);
        player.setName(playerDetails.getName());
        player.setAge(playerDetails.getAge());
        player.setRole(playerDetails.getRole());
        player.setBasePrice(playerDetails.getBasePrice());
        player.setTeam(playerDetails.getTeam());
        return playerRepository.save(player);
    }

    // Delete a player by ID
    public void deletePlayer(Long playerId) {
        playerRepository.deleteById(playerId);
    }
}

package com.Auction.IPL.repository;

import com.Auction.IPL.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    // Find all players by team ID
    List<Player> findByTeamId(Long teamId);

    // Find a player by name
    Optional<Player> findByName(String name);
}


package com.Auction.IPL.repository;

import com.Auction.IPL.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    // Find a team by name
    Optional<Team> findByName(String name);
}


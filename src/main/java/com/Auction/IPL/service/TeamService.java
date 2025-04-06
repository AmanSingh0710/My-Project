package com.Auction.IPL.service;

import com.Auction.IPL.model.Team;
import com.Auction.IPL.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    // Get all teams
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    // Get a team by ID
    public Team getTeamById(Long teamId) {
        Optional<Team> team = teamRepository.findById(teamId);
        return team.orElseThrow(() -> new RuntimeException("Team not found with ID: " + teamId));
    }

    // Add a new team
    public Team addTeam(Team team) {
        return teamRepository.save(team);
    }

    // Update an existing team
    public Team updateTeam(Long teamId, Team teamDetails) {
        Team team = getTeamById(teamId);
        team.setName(teamDetails.getName());
        team.setOwner(teamDetails.getOwner());
        team.setBudget(teamDetails.getBudget());
        return teamRepository.save(team);
    }

    // Delete a team by ID
    public void deleteTeam(Long teamId) {
        teamRepository.deleteById(teamId);
    }
}


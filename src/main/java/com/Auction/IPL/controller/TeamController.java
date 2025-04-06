package com.Auction.IPL.controller;

import com.Auction.IPL.model.Team;
import com.Auction.IPL.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {
    
    @Autowired
    private TeamService teamService;

    // Get all teams
    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    // Get team by ID
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    // Add a new team
    @PostMapping
    public ResponseEntity<Team> addTeam(@RequestBody Team team) {
        return ResponseEntity.ok(teamService.addTeam(team));
    }

    // Update a team
    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody Team team) {
        return ResponseEntity.ok(teamService.updateTeam(id, team));
    }

    // Delete a team
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.ok("Team deleted successfully");
    }
}

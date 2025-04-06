document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const playerId = parseInt(urlParams.get('id'));
    const playerProfile = document.getElementById('player-profile');

    if (!playerId) {
        playerProfile.innerHTML = '<div class="error">Player ID not specified</div>';
        return;
    }

    fetch('data/players.json')
        .then(response => response.json())
        .then(players => {
            const player = players.find(p => p.id === playerId);
            
            if (!player) {
                playerProfile.innerHTML = '<div class="error">Player not found</div>';
                return;
            }

            // Fetch teams data to show which team bought the player
            fetch('data/teams.json')
                .then(response => response.json())
                .then(teams => {
                    const team = player.soldTo ? teams.find(t => t.id === player.soldTo) : null;
                    
                    playerProfile.innerHTML = `
                        <div class="player-header">
                            <img src="images/players/${player.image}" alt="${player.name}" class="player-image">
                            <div class="player-basic-info">
                                <h2>${player.name}</h2>
                                <div class="player-meta">
                                    <div class="player-meta-item">
                                        <span>Role:</span>
                                        <strong>${player.role.toUpperCase()}</strong>
                                    </div>
                                    <div class="player-meta-item">
                                        <span>Country:</span>
                                        <strong>${player.country}</strong>
                                    </div>
                                    <div class="player-meta-item">
                                        <span>Age:</span>
                                        <strong>${player.age}</strong>
                                    </div>
                                </div>
                                <div class="player-meta">
                                    <div class="player-meta-item">
                                        <span>Batting:</span>
                                        <strong>${player.battingStyle}</strong>
                                    </div>
                                    <div class="player-meta-item">
                                        <span>Bowling:</span>
                                        <strong>${player.bowlingStyle || "N/A"}</strong>
                                    </div>
                                </div>
                                <p class="player-bio">${player.bio}</p>
                            </div>
                        </div>

                        <div class="player-stats">
                            <div class="stat-card">
                                <h3>Batting Career</h3>
                                <p>Matches: <span class="stat-value">${player.matches}</span></p>
                                <p>Runs: <span class="stat-value">${player.runs}</span></p>
                                <p>Highest Score: <span class="stat-value">${player.highestScore}</span></p>
                                <p>Average: <span class="stat-value">${player.average}</span></p>
                                <p>Strike Rate: <span class="stat-value">${player.strikeRate}</span></p>
                                <p>100s/50s: <span class="stat-value">${player.centuries}/${player.fifties}</span></p>
                            </div>

                            <div class="stat-card">
                                <h3>Bowling Career</h3>
                                <p>Wickets: <span class="stat-value">${player.wickets || 0}</span></p>
                                <p>Best Bowling: <span class="stat-value">${player.bestBowling || "N/A"}</span></p>
                                ${player.economy ? `<p>Economy: <span class="stat-value">${player.economy}</span></p>` : ''}
                            </div>

                            <div class="stat-card">
                                <h3>Recent Form</h3>
                                <p>Last 5 IPL Matches: <span class="stat-value">45, 12, 78, 103*, 56</span></p>
                                <p>Recent Tournament: <span class="stat-value">382 runs @ 63.66</span></p>
                            </div>
                        </div>

                        <div class="auction-info">
                            <h3>Auction Details</h3>
                            <div class="player-meta">
                                <div class="player-meta-item">
                                    <span>Base Price:</span>
                                    <strong>₹${player.basePrice.toFixed(2)} Cr</strong>
                                </div>
                                <div class="player-meta-item">
                                    <span>Status:</span>
                                    <strong>${player.sold ? 'SOLD' : 'UNSOLD'}</strong>
                                </div>
                                ${player.sold ? `
                                    <div class="player-meta-item">
                                        <span>Sold For:</span>
                                        <strong>₹${player.soldPrice.toFixed(2)} Cr</strong>
                                    </div>
                                    <div class="player-meta-item">
                                        <span>Team:</span>
                                        <strong>${team.name}</strong>
                                    </div>
                                ` : ''}
                            </div>
                        </div>
                    `;
                })
                .catch(error => {
                    console.error('Error loading teams:', error);
                    playerProfile.innerHTML = '<div class="error">Error loading team data</div>';
                });
        })
        .catch(error => {
            console.error('Error loading players:', error);
            playerProfile.innerHTML = '<div class="error">Error loading player data</div>';
        });
});
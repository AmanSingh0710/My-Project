document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const teamId = urlParams.get('id');
    const teamProfile = document.getElementById('team-profile');
    const teamTitle = document.getElementById('team-title');
    const squadModal = document.getElementById('squad-modal');
    const closeModalBtn = document.querySelector('#squad-modal .close-btn');
    const squadModalTitle = document.getElementById('squad-modal-title');
    const squadContainer = document.getElementById('squad-container');

    if (!teamId) {
        teamProfile.innerHTML = '<div class="error">Team ID not specified</div>';
        return;
    }

    // Load team data
    Promise.all([
        fetch('data/teams.json').then(res => res.json()),
        fetch('data/players.json').then(res => res.json())
    ])
    .then(([teams, players]) => {
        const team = teams.find(t => t.id === teamId);
        
        if (!team) {
            teamProfile.innerHTML = '<div class="error">Team not found</div>';
            return;
        }

        teamTitle.textContent = `${team.name} Profile`;
        
        // Calculate total spent and remaining budget
        const totalSpent = team.players.reduce((sum, player) => sum + player.price, 0);
        const remainingBudget = team.budget - totalSpent;

        // Render team profile
        teamProfile.innerHTML = `
            <div class="team-header">
                <img src="images/logos/${team.id.toLowerCase()}.png" alt="${team.name}" class="team-logo">
                <div class="team-basic-info">
                    <h2>${team.name}</h2>
                    <div class="team-meta">
                        <div class="team-meta-item">
                            <span>🏙️ City:</span>
                            <strong>${team.city}</strong>
                        </div>
                        <div class="team-meta-item">
                            <span>👔 Owner:</span>
                            <strong>${team.owner}</strong>
                        </div>
                        <div class="team-meta-item">
                            <span>👨‍🏫 Coach:</span>
                            <strong>${team.coach}</strong>
                        </div>
                        <div class="team-meta-item">
                            <span>👤 Captain:</span>
                            <strong>${team.captain}</strong>
                        </div>
                        <div class="team-meta-item">
                            <span>💰 Budget:</span>
                            <strong>₹${remainingBudget.toFixed(2)} Cr remaining</strong>
                        </div>
                    </div>
                </div>
            </div>

            <div class="team-actions">
                <button class="view-squad-btn" id="view-squad">View Full Squad (${team.players.length})</button>
            </div>

            <div class="team-stats">
                <div class="team-stat-card">
                    <h3>Team Performance</h3>
                    <p>Matches: <span class="stat-value-large">${team.stats.matches}</span></p>
                    <p>Wins: <span class="stat-value-large">${team.stats.wins}</span></p>
                    <p>Win %: <span class="stat-value-large">${team.stats.winPercentage}</span></p>
                </div>

                <div class="team-stat-card">
                    <h3>Records</h3>
                    <p>Highest Score: <span class="stat-value-large">${team.stats.highestScore}</span></p>
                    <p>Lowest Score: <span class="stat-value-large">${team.stats.lowestScore}</span></p>
                </div>

                <div class="team-stat-card">
                    <h3>Trophies Won</h3>
                    <div class="team-trophies">
                        ${Array(team.trophies).fill().map(() => 
                            `<img src="images/trophy.png" class="trophy" alt="IPL Trophy">`
                        ).join('')}
                    </div>
                    <p>Last Title: <span class="stat-value-large">${team.lastTitle}</span></p>
                </div>
            </div>

            <div class="team-stat-card">
                <h3>Home Ground</h3>
                <p><strong>${team.homeGround}</strong> - Capacity: ${team.capacity}</p>
                <img src="images/stadiums/${team.stadiumImage}" alt="${team.homeGround}" class="stadium-image">
            </div>

            <div class="team-squad-preview">
                <div class="squad-preview-header">
                    <h3>Key Players</h3>
                    <a href="#" id="view-all-players">View All</a>
                </div>
                <div class="squad-preview-players">
                    ${team.players.slice(0, 6).map(player => {
                        const playerData = players.find(p => p.id === player.id);
                        return `
                            <a href="player.html?id=${player.id}" class="squad-preview-player">
                                <img src="images/players/${playerData.image}" alt="${player.name}">
                                <div>
                                    <div class="squad-player-name">${player.name}</div>
                                    <div class="squad-player-price">₹${player.price.toFixed(2)} Cr</div>
                                </div>
                            </a>
                        `;
                    }).join('')}
                </div>
            </div>
        `;

        // Set up event listeners
        document.getElementById('view-squad').addEventListener('click', () => {
            showSquadModal(team, players);
        });

        document.getElementById('view-all-players').addEventListener('click', (e) => {
            e.preventDefault();
            showSquadModal(team, players);
        });
    })
    .catch(error => {
        console.error('Error loading data:', error);
        teamProfile.innerHTML = '<div class="error">Error loading team data</div>';
    });

    function showSquadModal(team, players) {
        squadModalTitle.textContent = `${team.name} Squad`;
        
        squadContainer.innerHTML = team.players.map(player => {
            const playerData = players.find(p => p.id === player.id);
            return `
                <div class="squad-player">
                    <img src="images/players/${playerData.image}" alt="${player.name}" class="squad-player-img">
                    <div class="squad-player-info">
                        <a href="player.html?id=${player.id}" class="squad-player-name">${player.name}</a>
                        <div class="squad-player-role">${playerData.role.toUpperCase()}</div>
                    </div>
                    <div class="squad-player-price">₹${player.price.toFixed(2)} Cr</div>
                </div>
            `;
        }).join('');

        squadModal.style.display = 'flex';
    }

    // Close modal
    closeModalBtn.addEventListener('click', () => {
        squadModal.style.display = 'none';
    });

    window.addEventListener('click', (e) => {
        if (e.target === squadModal) {
            squadModal.style.display = 'none';
        }
    });
});
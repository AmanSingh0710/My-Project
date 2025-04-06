document.addEventListener('DOMContentLoaded', () => {
    const teamsContainer = document.getElementById('teams-container');
    const searchInput = document.getElementById('search');
    const trophiesFilter = document.getElementById('filter-trophies');
    const sortBySelect = document.getElementById('sort-by');

    let allTeams = [];

    // Load team data
    fetch('data/teams.json')
        .then(response => response.json())
        .then(teams => {
            allTeams = teams;
            renderTeams(teams);
            
            // Set up event listeners for filtering
            searchInput.addEventListener('input', filterTeams);
            trophiesFilter.addEventListener('change', filterTeams);
            sortBySelect.addEventListener('change', filterTeams);
        })
        .catch(error => {
            console.error('Error loading teams:', error);
            teamsContainer.innerHTML = '<div class="error">Error loading team data</div>';
        });

    function renderTeams(teams) {
        teamsContainer.innerHTML = teams.map(team => {
            const totalSpent = team.players.reduce((sum, player) => sum + player.price, 0);
            const remainingBudget = team.budget - totalSpent;
            
            return `
                <div class="team-card">
                    <div class="trophy-count" title="${team.trophies} trophies">
                        ${team.trophies}
                    </div>
                    <div class="team-header">
                        <img src="images/logos/${team.id.toLowerCase()}.png" alt="${team.name}" class="team-logo">
                        <div class="team-info">
                            <h3 class="team-name"><a href="team.html?id=${team.id}">${team.name}</a></h3>
                            <div class="team-meta">
                                <span class="team-meta-item">${team.city}</span>
                                <span class="team-meta-item">Since ${team.founded}</span>
                            </div>
                            <div class="team-players-count">
                                ${team.players.length} players | ₹${remainingBudget.toFixed(2)} Cr left
                            </div>
                        </div>
                    </div>
                    <div class="team-stats">
                        <div class="stat-item">
                            <div class="stat-label">Matches</div>
                            <div class="stat-value">${team.stats.matches}</div>
                        </div>
                        <div class="stat-item">
                            <div class="stat-label">Wins</div>
                            <div class="stat-value">${team.stats.wins}</div>
                        </div>
                        <div class="stat-item">
                            <div class="stat-label">Win %</div>
                            <div class="stat-value">${team.stats.winPercentage}</div>
                        </div>
                        <div class="stat-item">
                            <div class="stat-label">Last Title</div>
                            <div class="stat-value">${team.lastTitle || '-'}</div>
                        </div>
                    </div>
                </div>
            `;
        }).join('');
    }

    function filterTeams() {
        const searchTerm = searchInput.value.toLowerCase();
        const trophiesValue = trophiesFilter.value;
        const sortBy = sortBySelect.value;

        let filteredTeams = allTeams.filter(team => {
            const matchesSearch = team.name.toLowerCase().includes(searchTerm);
            const matchesTrophies = trophiesValue === 'all' || 
                                  (trophiesValue === '5' && team.trophies >= 5) ||
                                  (trophiesValue === '3' && team.trophies >= 3) ||
                                  (trophiesValue === '1' && team.trophies >= 1) ||
                                  (trophiesValue === '0' && team.trophies === 0);
            
            return matchesSearch && matchesTrophies;
        });

        // Sort teams
        filteredTeams.sort((a, b) => {
            if (sortBy === 'name') {
                return a.name.localeCompare(b.name);
            } else if (sortBy === 'trophies') {
                return b.trophies - a.trophies;
            } else if (sortBy === 'wins') {
                return b.stats.wins - a.stats.wins;
            }
            return 0;
        });

        renderTeams(filteredTeams);
    }
});
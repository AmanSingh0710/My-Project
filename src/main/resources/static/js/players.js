document.addEventListener('DOMContentLoaded', () => {
    const playersContainer = document.getElementById('players-container');
    const searchInput = document.getElementById('search');
    const roleFilter = document.getElementById('filter-role');
    const statusFilter = document.getElementById('filter-status');
    const countryFilter = document.getElementById('filter-country');

    const API_BASE_URL = 'http://localhost:8080/api'; // Change to your backend URL

    // Load player data from backend
    async function fetchPlayers() {
        try {
            const response = await fetch(`${API_BASE_URL}/players`);
            if (!response.ok) throw new Error('Failed to fetch players');
            return await response.json();
        } catch (error) {
            console.error('Error fetching players:', error);
            playersContainer.innerHTML = '<div class="error">Error loading players</div>';
            return [];
        }
    }

    // Initial load
    fetchPlayers().then(players => {
        renderPlayers(players);
        
        // Set up event listeners for filtering
        searchInput.addEventListener('input', () => filterPlayers(players));
        roleFilter.addEventListener('change', () => filterPlayers(players));
        statusFilter.addEventListener('change', () => filterPlayers(players));
        countryFilter.addEventListener('change', () => filterPlayers(players));
    });

    function renderPlayers(players) {
        playersContainer.innerHTML = players.map(player => `
            <div class="player-card">
                <div class="player-header">
                    <img src="images/players/${player.image}" alt="${player.name}" class="player-image">
                    <div class="player-info">
                        <h3 class="player-name"><a href="player.html?id=${player.id}">${player.name}</a></h3>
                        <div class="player-meta">
                            <span class="player-meta-item">${player.role.toUpperCase()}</span>
                            <span class="player-meta-item">${player.country}</span>
                            ${player.sold ? 
                                `<span class="player-status status-sold">SOLD</span>` : 
                                `<span class="player-status status-unsold">UNSOLD</span>`
                            }
                        </div>
                        ${player.sold ? 
                            `<div class="player-price">₹${player.soldPrice.toFixed(2)} Cr to ${player.soldTo}</div>` : 
                            `<div class="player-price">Base: ₹${player.basePrice.toFixed(2)} Cr</div>`
                        }
                    </div>
                </div>
                ${!player.sold ? `
                <div class="player-actions">
                    <button class="bid-btn" data-id="${player.id}">BID NOW</button>
                </div>
                ` : ''}
            </div>
        `).join('');

        // Add event listeners to bid buttons
        document.querySelectorAll('.bid-btn').forEach(btn => {
            btn.addEventListener('click', async (e) => {
                const playerId = e.target.getAttribute('data-id');
                const player = players.find(p => p.id == playerId);
                openBidModal(player);
            });
        });
    }

    async function openBidModal(player) {
        // Fetch teams for the bid dropdown
        try {
            const response = await fetch(`${API_BASE_URL}/teams`);
            if (!response.ok) throw new Error('Failed to fetch teams');
            const teams = await response.json();
            
            // Create and show modal (implementation depends on your modal code)
            showBidModal(player, teams);
        } catch (error) {
            console.error('Error:', error);
            alert('Failed to load teams for bidding');
        }
    }

    // Filter function remains similar but works with live data
    function filterPlayers(players) {
        const searchTerm = searchInput.value.toLowerCase();
        const roleValue = roleFilter.value;
        const statusValue = statusFilter.value;
        const countryValue = countryFilter.value;

        const filteredPlayers = players.filter(player => {
            const matchesSearch = player.name.toLowerCase().includes(searchTerm);
            const matchesRole = roleValue === 'all' || player.role === roleValue;
            const matchesStatus = statusValue === 'all' || 
                                (statusValue === 'sold' && player.sold) || 
                                (statusValue === 'unsold' && !player.sold);
            const matchesCountry = countryValue === 'all' || player.country === countryValue;

            return matchesSearch && matchesRole && matchesStatus && matchesCountry;
        });

        renderPlayers(filteredPlayers);
    }
});
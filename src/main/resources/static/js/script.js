// Auction System Class
class IPLAuction {
    constructor() {
        this.players = [];
        this.teams = [];
        this.state = {
            isRunning: false,
            currentPlayer: null,
            currentBid: 0,
            currentBidder: null,
            timer: null,
            timeRemaining: 30
        };
        this.init();
    }

static API_BASE = 'http://localhost:8080/api';

// Example: Fetch players
    async fetchPlayers() {
        try {
             const response = await fetch(`${API_BASE}/players`);
              return await response.json();
        } catch (error) {
              console.error('Error:', error);
        }
    }

    async init() {
        await this.loadData();
        this.setupDOM();
        this.setupEventListeners();
        this.render();
    }

    async loadData() {
        try {
            const [playersRes, teamsRes] = await Promise.all([
                fetch('data/players.json'),
                fetch('data/teams.json')
            ]);
            
            this.players = await playersRes.json();
            this.teams = await teamsRes.json();
        } catch (error) {
            console.error("Error loading data:", error);
        }
    }

    setupDOM() {
        this.dom = {
            playersContainer: document.getElementById('players-container'),
            teamsContainer: document.getElementById('teams-container'),
            searchInput: document.getElementById('search'),
            filterSelect: document.getElementById('filter'),
            bidModal: document.getElementById('bid-modal'),
            closeModalBtn: document.querySelector('#bid-modal .close-btn'),
            submitBidBtn: document.getElementById('submit-bid'),
            bidAmountInput: document.getElementById('bid-amount'),
            bidTeamSelect: document.getElementById('bid-team'),
            modalPlayerName: document.getElementById('modal-player-name'),
            modalBasePrice: document.getElementById('modal-base-price'),
            modalCurrentBid: document.getElementById('modal-current-bid'),
            startAuctionBtn: document.getElementById('start-auction'),
            pauseAuctionBtn: document.getElementById('pause-auction'),
            endAuctionBtn: document.getElementById('end-auction'),
            totalPlayersEl: document.getElementById('total-players'),
            soldPlayersEl: document.getElementById('sold-players'),
            totalAmountEl: document.getElementById('total-amount'),
            currentBidDisplay: document.getElementById('current-bid'),
            currentPlayerEl: document.getElementById('current-player'),
            currentBidAmountEl: document.getElementById('current-bid-amount'),
            currentBidderEl: document.getElementById('current-bidder'),
            timerEl: document.getElementById('timer')
        };
    }

    setupEventListeners() {
        this.dom.searchInput.addEventListener('input', () => this.renderPlayers());
        this.dom.filterSelect.addEventListener('change', () => this.renderPlayers());
        this.dom.closeModalBtn.addEventListener('click', () => this.closeModal());
        this.dom.submitBidBtn.addEventListener('click', () => this.placeBid());
        this.dom.startAuctionBtn.addEventListener('click', () => this.startAuction());
        this.dom.pauseAuctionBtn.addEventListener('click', () => this.pauseAuction());
        this.dom.endAuctionBtn.addEventListener('click', () => this.endAuction());
        
        window.addEventListener('click', (e) => {
            if (e.target === this.dom.bidModal) {
                this.closeModal();
            }
        });
    }

    render() {
        this.renderPlayers();
        this.renderTeams();
        this.updateStats();
    }

    renderPlayers() {
        const searchTerm = this.dom.searchInput.value.toLowerCase();
        const filterValue = this.dom.filterSelect.value;
        
        const filteredPlayers = this.players.filter(player => {
            const matchesSearch = player.name.toLowerCase().includes(searchTerm);
            const matchesFilter = filterValue === 'all' || player.role === filterValue;
            return matchesSearch && matchesFilter;
        });
        
        this.dom.playersContainer.innerHTML = filteredPlayers.map(player => `
            <div class="player-card" data-id="${player.id}">
                <div class="player-info">
                    <a href="player.html?id=${player.id}" class="player-link">
                        <div class="player-name">${player.name}</div>
                        <div class="player-details">
                            <span>${player.role.toUpperCase()}</span>
                            <span>${player.country}</span>
                            <span>Base: ₹${player.basePrice.toFixed(2)} Cr</span>
                        </div>
                    </a>
                </div>
                <div class="player-actions">
                    ${player.sold ? 
                        `<button class="btn sold-btn">SOLD (₹${player.soldPrice.toFixed(2)} Cr)</button>` : 
                        `<button class="btn bid-btn" data-id="${player.id}">BID NOW</button>
                         <button class="btn unsold-btn" data-id="${player.id}">UNSOLD</button>`
                    }
                </div>
            </div>
        `).join('');

        // Add event listeners to buttons
        document.querySelectorAll('.bid-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                const playerId = parseInt(e.target.getAttribute('data-id'));
                this.openBidModal(playerId);
            });
        });

        document.querySelectorAll('.unsold-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                const playerId = parseInt(e.target.getAttribute('data-id'));
                this.markAsUnsold(playerId);
            });
        });
    }

    renderTeams() {
        this.dom.teamsContainer.innerHTML = this.teams.map(team => {
            const totalSpent = team.players.reduce((sum, player) => sum + player.price, 0);
            const remainingBudget = team.budget - totalSpent;
            
            return `
                <div class="team-card" data-id="${team.id}">
                    <div class="team-header">
                        <a href="team.html?id=${team.id}" class="team-link">
                            <img src="images/logos/${team.id.toLowerCase()}.png" alt="${team.name}" class="team-logo-small">
                            <div class="team-name">${team.name}</div>
                        </a>
                        <div class="team-budget">₹${remainingBudget.toFixed(2)} Cr left</div>
                    </div>
                    <ul class="team-players">
                        ${team.players.length > 0 ? 
                            team.players.slice(0, 3).map(player => `
                                <li class="team-player">
                                    <a href="player.html?id=${player.id}" class="player-link">${player.name}</a>
                                    <span class="player-price">₹${player.price.toFixed(2)} Cr</span>
                                </li>
                            `).join('') : 
                            '<li>No players bought yet</li>'
                        }
                        ${team.players.length > 3 ? 
                            `<li class="team-player-more">
                                <a href="team.html?id=${team.id}">+ ${team.players.length - 3} more</a>
                            </li>` : ''
                        }
                    </ul>
                </div>
            `;
        }).join('');
    }

    updateStats() {
        const totalPlayers = this.players.length;
        const soldPlayers = this.players.filter(p => p.sold).length;
        const totalAmount = this.players.reduce((sum, player) => sum + (player.sold ? player.soldPrice : 0), 0);
        
        this.dom.totalPlayersEl.textContent = totalPlayers;
        this.dom.soldPlayersEl.textContent = soldPlayers;
        this.dom.totalAmountEl.textContent = `₹${totalAmount.toFixed(2)} Cr`;
    }

    openBidModal(playerId) {
        const player = this.players.find(p => p.id === playerId);
        if (!player) return;
        
        this.dom.modalPlayerName.textContent = player.name;
        this.dom.modalBasePrice.textContent = `₹${player.basePrice.toFixed(2)} Cr`;
        this.dom.modalCurrentBid.textContent = player.sold ? 
            `SOLD for ₹${player.soldPrice.toFixed(2)} Cr` : 
            `₹${player.basePrice.toFixed(2)} Cr`;
        
        this.dom.bidAmountInput.value = player.basePrice;
        this.dom.bidAmountInput.min = player.basePrice;
        
        // Populate teams dropdown
        this.dom.bidTeamSelect.innerHTML = `
            <option value="">Select a team</option>
            ${this.teams.map(team => `
                <option value="${team.id}">${team.name} (₹${(team.budget - team.players.reduce((sum, p) => sum + p.price, 0)).toFixed(2)} Cr left)</option>
            `).join('')}
        `;
        
        this.dom.bidModal.style.display = 'flex';
    }

    closeModal() {
        this.dom.bidModal.style.display = 'none';
    }

    placeBid() {
        const bidAmount = parseFloat(this.dom.bidAmountInput.value);
        const teamId = this.dom.bidTeamSelect.value;
        
        if (!teamId) {
            alert('Please select a team');
            return;
        }
        
        if (isNaN(bidAmount)) {
            alert('Please enter a valid bid amount');
            return;
        }
        
        const team = this.teams.find(t => t.id === teamId);
        const remainingBudget = team.budget - team.players.reduce((sum, p) => sum + p.price, 0);
        
        if (bidAmount > remainingBudget) {
            alert(`Team doesn't have enough budget! Remaining: ₹${remainingBudget.toFixed(2)} Cr`);
            return;
        }
        
        // In a real app, you would send this to the server
        console.log(`Bid placed: ₹${bidAmount} Cr by ${team.name}`);
        
        this.closeModal();
        alert(`Bid of ₹${bidAmount} Cr placed successfully by ${team.name}!`);
    }

    markAsUnsold(playerId) {
        const player = this.players.find(p => p.id === playerId);
        if (!player) return;
        
        player.sold = false;
        player.soldPrice = 0;
        player.soldTo = '';
        
        this.renderPlayers();
        this.updateStats();
    }

    startAuction() {
        if (this.state.isRunning) return;
        
        this.state.isRunning = true;
        this.state.timeRemaining = 30;
        this.updateTimerDisplay();
        
        this.state.timer = setInterval(() => {
            this.state.timeRemaining--;
            this.updateTimerDisplay();
            
            if (this.state.timeRemaining <= 0) {
                this.endBidding();
            }
        }, 1000);
        
        this.dom.startAuctionBtn.disabled = true;
        this.dom.pauseAuctionBtn.disabled = false;
        this.dom.endAuctionBtn.disabled = false;
        
        // Start with first unsold player
        this.nextPlayer();
    }

    pauseAuction() {
        if (!this.state.isRunning) return;
        
        clearInterval(this.state.timer);
        this.state.isRunning = false;
        
        this.dom.startAuctionBtn.disabled = false;
        this.dom.pauseAuctionBtn.disabled = true;
    }

    endAuction() {
        if (!this.state.isRunning) return;
        
        clearInterval(this.state.timer);
        this.state.isRunning = false;
        this.state.currentPlayer = null;
        this.state.currentBid = 0;
        this.state.currentBidder = null;
        
        this.updateCurrentBidDisplay();
        this.updateTimerDisplay();
        
        this.dom.startAuctionBtn.disabled = false;
        this.dom.pauseAuctionBtn.disabled = true;
        this.dom.endAuctionBtn.disabled = true;
    }

    nextPlayer() {
        const unsoldPlayer = this.players.find(p => !p.sold);
        if (!unsoldPlayer) {
            this.endAuction();
            alert("All players have been sold or marked unsold!");
            return;
        }
        
        this.state.currentPlayer = unsoldPlayer;
        this.state.currentBid = unsoldPlayer.basePrice;
        this.state.currentBidder = null;
        this.state.timeRemaining = 30;
        
        this.updateCurrentBidDisplay();
    }

    endBidding() {
        clearInterval(this.state.timer);
        
        if (this.state.currentBidder) {
            const player = this.state.currentPlayer;
            const team = this.teams.find(t => t.id === this.state.currentBidder);
            
            player.sold = true;
            player.soldPrice = this.state.currentBid;
            player.soldTo = this.state.currentBidder;
            
            team.players.push({
                id: player.id,
                name: player.name,
                role: player.role,
                price: player.soldPrice
            });
            
            alert(`${player.name} sold to ${team.name} for ₹${player.soldPrice.toFixed(2)} Cr!`);
        } else {
            alert(`${this.state.currentPlayer.name} went unsold!`);
        }
        
        this.render();
        this.nextPlayer();
    }

    updateCurrentBidDisplay() {
        if (this.state.currentPlayer) {
            this.dom.currentBidDisplay.style.display = 'block';
            this.dom.currentPlayerEl.textContent = this.state.currentPlayer.name;
            this.dom.currentBidAmountEl.textContent = `₹${this.state.currentBid.toFixed(2)} Cr`;
            this.dom.currentBidderEl.textContent = this.state.currentBidder ? 
                this.teams.find(t => t.id === this.state.currentBidder).name : 
                "No bids yet";
        } else {
            this.dom.currentBidDisplay.style.display = 'none';
        }
    }

    updateTimerDisplay() {
        this.dom.timerEl.textContent = `${this.state.timeRemaining}s`;
        
        if (this.state.timeRemaining <= 10) {
            this.dom.timerEl.style.color = 'var(--secondary)';
            this.dom.timerEl.style.fontWeight = 'bold';
        } else {
            this.dom.timerEl.style.color = '';
            this.dom.timerEl.style.fontWeight = '';
        }
    }
}

// Initialize the auction when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    new IPLAuction();
});
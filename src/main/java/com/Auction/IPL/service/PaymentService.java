package com.Auction.IPL.service;

import com.Auction.IPL.model.Bid;
import com.Auction.IPL.model.Payment;
import com.Auction.IPL.model.PaymentStatus;
import com.Auction.IPL.model.Team;
import com.Auction.IPL.repository.BidRepository;
import com.Auction.IPL.repository.PaymentRepository;
import com.Auction.IPL.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private TeamRepository teamRepository;

    // Get all payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<Payment> getPaymentsByPlayer(Long playerId) {
        return paymentRepository.findByBid_PlayerId(playerId);
    }
    

    // Get payment by ID
    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + paymentId));
    }

    // Process a payment for a bid
    public Payment processPayment(Long bidId, String paymentMethod) {
        Bid bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new RuntimeException("Bid not found with ID: " + bidId));

        Team team = bid.getTeam();
        double bidAmount = bid.getBidAmount();

        // Check if the team has enough balance
        if (team.getBudget() < bidAmount) {
            throw new RuntimeException("Insufficient balance for Team: " + team.getName());
        }

        // Deduct the amount from the team's budget
        team.setBudget(team.getBudget() - bidAmount);
        teamRepository.save(team);

        // Create and save the payment transaction
        Payment payment = new Payment();
        payment.setBid(bid);
        payment.setAmount(bidAmount);
        payment.setPaymentMethod(paymentMethod);
        payment.setTimestamp(LocalDateTime.now());
        payment.setStatus(PaymentStatus.SUCCESS);

        return paymentRepository.save(payment);
    }

    // Get all payments for a team
    public List<Payment> getPaymentsByTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found with ID: " + teamId));
        return paymentRepository.findByBid_Team(team);
    }
}


package com.Auction.IPL.controller;

import com.Auction.IPL.model.Payment;
import com.Auction.IPL.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    
    @Autowired
    private PaymentService paymentService;

    // Get all payments
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    // Get payment by ID
    @GetMapping("/{paymentId}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long paymentId) {
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }

    // Process a new payment
    @PostMapping("/process")
    public ResponseEntity<Payment> processPayment(
            @RequestParam Long teamId,
            @RequestParam Long playerId,
            @RequestParam double amount) {
        return ResponseEntity.ok(paymentService.processPayment(playerId, "Online"));
    }

    // Get payments by Team
    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<Payment>> getPaymentsByTeam(@PathVariable Long teamId) {
        return ResponseEntity.ok(paymentService.getPaymentsByTeam(teamId));
    }

    // Get payments by Player
    @GetMapping("/player/{playerId}")
    public ResponseEntity<List<Payment>> getPaymentsByPlayer(@PathVariable Long playerId) {
        return ResponseEntity.ok(paymentService.getPaymentsByPlayer(playerId));
    }
}

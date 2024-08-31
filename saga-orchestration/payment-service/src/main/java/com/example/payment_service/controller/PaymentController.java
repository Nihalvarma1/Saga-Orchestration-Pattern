package com.example.payment_service.controller;

import com.example.payment_service.model.Payment;
import com.example.payment_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping
    public boolean processPayment(@RequestParam Long orderId, @RequestParam double amount) {
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        paymentRepository.save(payment);

        System.out.println("Payment processed for Order ID: " + orderId);
        return true;
    }

    @PutMapping("/refund")
    public boolean refundPayment(@RequestParam Long orderId) {
        // In a real-world scenario, you'd implement more robust logic here.
        paymentRepository.deleteById(orderId);
        System.out.println("Payment refunded for Order ID: " + orderId);
        return true;
    }
}

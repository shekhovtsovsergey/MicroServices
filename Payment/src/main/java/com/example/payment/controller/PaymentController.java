package com.example.payment.controller;

import com.example.payment.dto.PaymentDtoResponse;
import com.example.payment.model.Payment;
import com.example.payment.service.PaymentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentServiceImpl paymentService;

    @PostMapping("/reserve")
    public ResponseEntity<PaymentDtoResponse> reservePayment(@RequestBody Payment payment) {
        System.out.println("           reservePayment " + payment.toString());
        try {
            return ResponseEntity.ok(paymentService.reservePayment(payment));
        } catch (Exception e) {
            PaymentDtoResponse paymentDtoResponse = new PaymentDtoResponse(null, "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(paymentDtoResponse);
        }
    }

    @PostMapping("/pay")
    public ResponseEntity<String> makePayment(@RequestBody Long id) {
        paymentService.makePayment(id);
        return ResponseEntity.ok("Payment made.");
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelPaymentReservation(@RequestBody Long id) {
        paymentService.cancelPaymentReservation(id);
        return ResponseEntity.ok("Payment reservation canceled.");
    }

}
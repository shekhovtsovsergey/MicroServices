package com.example.order.controller;

import com.example.order.dto.OrderDto;
import com.example.order.dto.PaymentDto;
import com.example.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ResponseEntity<String>> getOrder (@RequestBody OrderDto orderDto) {
        System.out.println(orderDto.toString()+orderDto.getDebitAccount());
        return ResponseEntity.status(HttpStatus.OK).body(orderService.createOrder(orderDto));
    }

}

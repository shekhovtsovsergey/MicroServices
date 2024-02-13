package com.example.delivery;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeliveryController {

    @PostMapping("/booking")
    public ResponseEntity<Long> handlePostRequest() {
        System.out.println("DeliveryController");
        return ResponseEntity.status(HttpStatus.OK).body(1L);
    }

}

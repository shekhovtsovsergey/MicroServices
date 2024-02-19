package com.example.warehouse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WarehouseController {

    @PostMapping("/booking")
    public ResponseEntity<Long> handlePostRequest() {
        int random = (int)(Math.random() * 2);
        if (random == 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(2L);
        }
    }

}

package com.example.core.dto;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class BookingDto {
    private int amount;
    private String debitAccount;
}

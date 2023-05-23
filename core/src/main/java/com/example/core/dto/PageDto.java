package com.example.core.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class PageDto<E> {
    private List<E> items;
    private int page;
    private int totalPages;

}

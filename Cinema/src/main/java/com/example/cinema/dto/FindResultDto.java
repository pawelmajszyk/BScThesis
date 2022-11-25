package com.example.cinema.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindResultDto<T> {

    private Long startElement;
    private Long totalCount;
    private Long count;
    private List<T> results;
}
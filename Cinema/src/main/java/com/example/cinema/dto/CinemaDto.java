package com.example.cinema.dto;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CinemaDto {

    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipCode;
    private boolean isEnabled;
}

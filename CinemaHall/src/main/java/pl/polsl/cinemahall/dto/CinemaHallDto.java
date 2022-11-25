package pl.polsl.cinemahall.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CinemaHallDto {

    private Long id;
    private String name;
    private List<SeatDto> seats;
    private Long cinemaId;
    private Boolean enabled;
}

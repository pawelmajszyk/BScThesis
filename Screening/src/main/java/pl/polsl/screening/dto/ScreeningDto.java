package pl.polsl.screening.dto;

import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScreeningDto {

    private Long id;
    private Long movieId;
    private Long cinemaHallId;
    private Boolean isActive;
}

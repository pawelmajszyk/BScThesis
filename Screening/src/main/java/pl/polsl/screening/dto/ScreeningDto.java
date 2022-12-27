package pl.polsl.screening.dto;

import lombok.*;

import java.time.OffsetDateTime;


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
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
}

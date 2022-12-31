package pl.polsl.reservation.dto;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieReservationDto {

    private String title;
    private ReservationDto reservation;
}

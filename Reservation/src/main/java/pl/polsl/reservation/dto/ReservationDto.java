package pl.polsl.reservation.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {

    private Long id;
    private Long userId;
    private Long screeningId;
    private List<Long> seatIds;
    private Boolean isActive;
}

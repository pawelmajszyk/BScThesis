package pl.polsl.cinemahall.dto;


import lombok.*;
import pl.polsl.cinemahall.dto.enums.SeatStateDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatDto {

    private Long id;
    private SeatStateDto seatState;
    private Integer row;
    private Integer column;
}

package pl.polsl.cinemahall.entity;


import lombok.*;
import pl.polsl.cinemahall.entity.enums.SeatState;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "auto_gen")
    @SequenceGenerator(name = "auto_gen", sequenceName = "A")
    private Long id;

    @Enumerated(EnumType.STRING)
    private SeatState seatState;

    private Integer row;

    @Column(name = "columns")
    private Integer column;
}

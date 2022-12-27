package pl.polsl.screening.entity;

import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "auto_gen")
    @SequenceGenerator(name = "auto_gen", sequenceName = "A")
    private Long id;

    private Long movieId;
    private Long cinemaHallId;
    private Boolean isActive;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
}

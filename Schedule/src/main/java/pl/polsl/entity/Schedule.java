package pl.polsl.entity;

import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "auto_gen")
    @SequenceGenerator(name = "auto_gen", sequenceName = "A")
    private Long id;

    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private Boolean isSignedByManager;
    private Boolean isSignedByWorker;
    private Long managerId;
    private Long workerId;
}

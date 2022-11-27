package pl.polsl.dto;

import lombok.*;

import java.time.OffsetDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {

    private Long id;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private Boolean isSignedByManager;
    private Boolean isSignedByWorker;
    private Long managerId;
    private Long workerId;
}

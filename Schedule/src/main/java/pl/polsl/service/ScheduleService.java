package pl.polsl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.dto.ScheduleDto;
import pl.polsl.entity.Schedule;
import pl.polsl.exception.NoAuthorityForWorkerException;
import pl.polsl.exception.ScheduleMissingException;
import pl.polsl.exception.ScheduleOverlapsTime;
import pl.polsl.exception.UserMissingException;
import pl.polsl.mapper.ScheduleMapper;
import pl.polsl.repository.ScheduleRepository;
import pl.polsl.service.feignClient.UsersClient;
import pl.polsl.users.api.model.WorkerResponseModelApi;


import javax.ws.rs.NotAuthorizedException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final UsersClient usersClient;

    @Transactional
    public ScheduleDto createSchedule(ScheduleDto scheduleDto) {
        Schedule schedule = scheduleMapper.mapDtoToEntity(scheduleDto);
        WorkerResponseModelApi managerInfo = usersClient.getSelfInfo();

        schedule.setManagerId(managerInfo.getId());

        if (Boolean.TRUE.equals(validateSchedule(scheduleDto, schedule, managerInfo))) {
            return scheduleMapper.mapEntityToDto(scheduleRepository.save(schedule));
        }

        throw new ScheduleOverlapsTime();
    }

    private Boolean validateSchedule(ScheduleDto scheduleDto, Schedule schedule, WorkerResponseModelApi managerInfo) {
        WorkerResponseModelApi singleWorker;
        try {
            singleWorker = usersClient.getSingleWorker(scheduleDto.getWorkerId());
        } catch (Exception e) {
            throw new UserMissingException();
        }

        if(!Objects.equals(singleWorker.getCinemaId(), managerInfo.getCinemaId())) {
            throw new NoAuthorityForWorkerException();
        }

        return scheduleRepository.findAllByWorkerId(scheduleDto.getWorkerId()).stream()
                .map(scheduleMapper::mapEntityToDto)
                .noneMatch(s ->
                        scheduleDto.getStartTime().isBefore(s.getEndTime()) && scheduleDto.getEndTime().isAfter(s.getStartTime()));
    }

    @Transactional
    public ScheduleDto deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(ScheduleMissingException::new);

        scheduleRepository.delete(schedule);

        return scheduleMapper.mapEntityToDto(schedule);
    }

    @Transactional
    public ScheduleDto selfSignSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(ScheduleMissingException::new);

        WorkerResponseModelApi selfInfo = usersClient.getSelfInfo();

        if(!Objects.equals(schedule.getWorkerId(), selfInfo.getId())) {
            throw new NotAuthorizedException("Not owner of resource");
        }

        schedule.setIsSignedByWorker(true);

        return scheduleMapper.mapEntityToDto(scheduleRepository.save(schedule));
    }

    @Transactional
    public ScheduleDto managerSingSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(ScheduleMissingException::new);

        WorkerResponseModelApi selfInfo = usersClient.getSelfInfo();

        WorkerResponseModelApi singleWorker;
        try {
            singleWorker = usersClient.getSingleManager(schedule.getManagerId());
        } catch (Exception e) {
            throw new UserMissingException();
        }
        if(!Objects.equals(selfInfo.getCinemaId(), singleWorker.getCinemaId())) {
            throw new NotAuthorizedException("Not owner of resource");
        }

        schedule.setIsSignedByManager(true);

        return scheduleMapper.mapEntityToDto(scheduleRepository.save(schedule));
    }

    public List<ScheduleDto> getSchedulesForWorker(Long id) {
        return scheduleRepository.findAllByWorkerId(id).stream()
                .map(scheduleMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }
}

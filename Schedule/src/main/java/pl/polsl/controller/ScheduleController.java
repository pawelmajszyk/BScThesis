package pl.polsl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import pl.polsl.dto.ScheduleDto;
import pl.polsl.mapper.ScheduleMapper;
import pl.polsl.schedule.api.controller.ScheduleApi;
import pl.polsl.schedule.api.model.ScheduleModelApi;
import pl.polsl.schedule.api.model.ScheduleRequestModelApi;
import pl.polsl.service.ScheduleService;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ScheduleController implements ScheduleApi {

    private final ScheduleMapper scheduleMapper;
    private final ScheduleService scheduleService;

    @Override
    @RolesAllowed({"manager"})
    public ResponseEntity<ScheduleModelApi> createSchedule(ScheduleRequestModelApi scheduleRequestModelApi) {
        ScheduleDto request = scheduleMapper.mapModelApiToDto(scheduleRequestModelApi);

        ScheduleDto result = scheduleService.createSchedule(request);

        return new ResponseEntity<>(scheduleMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }

    @Override
    @RolesAllowed({"manager"})
    public ResponseEntity<ScheduleModelApi> deleteSchedule(Long id) {
        ScheduleDto result = scheduleService.deleteSchedule(id);

        return new ResponseEntity<>(scheduleMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }

    @Override
    @RolesAllowed({"manager"})
    public ResponseEntity<List<ScheduleModelApi>> getScheduleList(Long id) {
        List<ScheduleModelApi> result = scheduleService.getSchedulesForWorker(id).stream()
                .map(scheduleMapper::mapDtoToModelApi)
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    @RolesAllowed({"worker"})
    public ResponseEntity<ScheduleModelApi> selfSignSchedule(Long id) {
        ScheduleDto result = scheduleService.selfSignSchedule(id);

        return new ResponseEntity<>(scheduleMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }

    @Override
    @RolesAllowed({"manager"})
    public ResponseEntity<ScheduleModelApi> signWorkerSchedule(Long id) {
        ScheduleDto result = scheduleService.managerSingSchedule(id);

        return new ResponseEntity<>(scheduleMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ScheduleModelApi>> getScheduleListForWorker() {
        List<ScheduleModelApi> result = scheduleService.getScheuleListForSelfWorker().stream()
                .map(scheduleMapper::mapDtoToModelApi)
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

package pl.polsl.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.polsl.dto.ScheduleDto;
import pl.polsl.entity.Schedule;
import pl.polsl.schedule.api.model.ScheduleModelApi;
import pl.polsl.schedule.api.model.ScheduleRequestModelApi;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ScheduleMapper {

    @Mapping(target = "isSignedByManager", constant = "false")
    @Mapping(target = "isSignedByWorker", constant = "false")
    Schedule mapDtoToEntity(ScheduleDto scheduleDto);

    ScheduleDto mapEntityToDto(Schedule schedule);
    ScheduleModelApi mapDtoToModelApi(ScheduleDto scheduleDto);
    ScheduleDto mapModelApiToDto(ScheduleRequestModelApi scheduleRequestModelApi);
}

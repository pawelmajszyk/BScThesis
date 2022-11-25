package pl.polsl.cinemahall.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.polsl.cinemahall.api.model.SeatModelApi;
import pl.polsl.cinemahall.dto.SeatDto;
import pl.polsl.cinemahall.entity.Seat;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SeatMapper {

    Seat mapDtoToEntity(SeatDto seatDto);
    SeatDto mapEntityToDto(Seat seat);
    SeatModelApi mapDtoToModelApi(SeatDto seatDto);
}

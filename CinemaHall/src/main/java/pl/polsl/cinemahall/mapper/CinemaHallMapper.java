package pl.polsl.cinemahall.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.cinemahall.api.model.CinemaHallModelApi;
import pl.polsl.cinemahall.dto.CinemaHallDto;
import pl.polsl.cinemahall.entity.CinemaHall;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = SeatMapper.class)
public interface CinemaHallMapper {

    CinemaHall mapDtoToEntity(CinemaHallDto cinemaHallDto);
    CinemaHallDto mapEnityToDto(CinemaHall cinemaHall);
    CinemaHallModelApi mapDtoToModelApi(CinemaHallDto cinemaHallDto);
}

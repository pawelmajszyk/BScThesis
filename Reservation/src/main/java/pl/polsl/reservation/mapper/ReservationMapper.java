package pl.polsl.reservation.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.polsl.reservation.api.model.ReservationModelApi;
import pl.polsl.reservation.api.model.ReservationMovieModelApi;
import pl.polsl.reservation.api.model.ReservationRequestModelApi;
import pl.polsl.reservation.dto.MovieReservationDto;
import pl.polsl.reservation.dto.ReservationDto;
import pl.polsl.reservation.entity.Reservation;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ReservationMapper {

    ReservationDto mapModelApiToDto(ReservationRequestModelApi reservationRequestModelApie);
    ReservationModelApi mapDtoToModelApi(ReservationDto reservationDto);
    ReservationDto mapEntityToDto(Reservation reservation);
    ReservationMovieModelApi mapDtoToModelApi(MovieReservationDto movieReservationDto);

    @Mapping(target = "reservation", source = "reservationDto")
    MovieReservationDto mapDtoToDto(ReservationDto reservationDto, String title);

    @Mapping(target = "isActive", constant = "true")
    Reservation mapDtoToEntity(ReservationDto reservationDto);

}

package pl.polsl.reservation.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.reservation.api.model.ReservationModelApi;
import pl.polsl.reservation.api.model.ReservationRequestModelApi;
import pl.polsl.reservation.dto.ReservationDto;
import pl.polsl.reservation.entity.Reservation;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ReservationMapper {

    ReservationDto mapModelApiToDto(ReservationRequestModelApi reservationRequestModelApie);
    Reservation mapDtoToEntity(ReservationDto reservationDto);
    ReservationModelApi mapDtoToModelApi(ReservationDto reservationDto);
    ReservationDto mapEntityToDto(Reservation reservation);
}

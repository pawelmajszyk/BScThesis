package pl.polsl.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.reservation.api.controller.ReservationApi;
import pl.polsl.reservation.api.model.ReservationModelApi;
import pl.polsl.reservation.api.model.ReservationMovieModelApi;
import pl.polsl.reservation.api.model.ReservationRequestModelApi;
import pl.polsl.reservation.dto.ReservationDto;
import pl.polsl.reservation.mapper.ReservationMapper;
import pl.polsl.reservation.service.ReservationService;
import pl.polsl.reservation.service.feignClients.MovieClient;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ReservationController implements ReservationApi {

    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;
    private final MovieClient movieClient;

    @Override
    @RolesAllowed({"manager", "admin", "worker", "customer"})
    public ResponseEntity<ReservationModelApi> createReservetion(ReservationRequestModelApi reservationRequestModelApi) {
        ReservationDto reservationDto = reservationMapper.mapModelApiToDto(reservationRequestModelApi);

        ReservationDto result = reservationService.createReservation(reservationDto);

        return new ResponseEntity<>(reservationMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }


    @Override
    @RolesAllowed({"manager", "admin", "worker", "customer"})
    public ResponseEntity<List<ReservationModelApi>> getReservationsForScreening(Long id) {
        List<ReservationModelApi> result = reservationService.getReservationListForScreening(id).stream()
                .map(reservationMapper::mapDtoToModelApi)
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    @RolesAllowed({"customer"})
    public ResponseEntity<List<ReservationMovieModelApi>> getReservationsForSelfUser() {
        List<ReservationMovieModelApi> result = reservationService.gerReservationListForCustomer().stream()
                .map(reservationMapper::mapDtoToModelApi)
                .collect(Collectors.toList());


        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    @RolesAllowed({"manager", "admin", "worker", "customer"})
    public ResponseEntity<ReservationModelApi> getReservationDetail(Long id) {
        ReservationDto result = reservationService.findReservation(id);

        return new ResponseEntity<>(reservationMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ReservationModelApi> deletesReservation(Long id) {
        ReservationDto result = reservationService.deleteReservation(id);

        return new ResponseEntity<>(reservationMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }
}

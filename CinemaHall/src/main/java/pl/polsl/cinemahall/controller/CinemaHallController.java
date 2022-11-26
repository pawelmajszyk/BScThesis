package pl.polsl.cinemahall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import pl.polsl.cinemahall.api.controller.DetailsApi;
import pl.polsl.cinemahall.api.controller.IdApi;
import pl.polsl.cinemahall.api.model.CinemaHallModelApi;
import pl.polsl.cinemahall.api.model.CinemaHallRequestModelApi;
import pl.polsl.cinemahall.api.model.SeatModelApi;
import pl.polsl.cinemahall.api.model.SeatStateModelApi;
import pl.polsl.cinemahall.dto.CinemaHallDto;
import pl.polsl.cinemahall.dto.SeatDto;
import pl.polsl.cinemahall.mapper.CinemaHallMapper;
import pl.polsl.cinemahall.mapper.SeatMapper;
import pl.polsl.cinemahall.service.CinemaHallService;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CinemaHallController implements IdApi, DetailsApi {

    private final CinemaHallService cinemaHallService;
    private final CinemaHallMapper cinemaHallMapper;
    private final SeatMapper seatMapper;

    @Override
    @RolesAllowed({"admin"})
    public ResponseEntity<CinemaHallModelApi> createCinemaHall(Long id, CinemaHallRequestModelApi cinemaHallRequestModelApi) {
        CinemaHallDto cinemaHallDto = cinemaHallService.createCinemaHall(id, cinemaHallRequestModelApi.getName(),
                cinemaHallRequestModelApi.getxRows(),
                cinemaHallRequestModelApi.getyRows());


        return new ResponseEntity<>(cinemaHallMapper.mapDtoToModelApi(cinemaHallDto), HttpStatus.OK);
    }

    @Override
    @RolesAllowed({"manager", "admin"})
    public ResponseEntity<SeatModelApi> changeSeatState(Long id, SeatStateModelApi state) {
        SeatDto seatDto = cinemaHallService.changeSeatState(id, seatMapper.mapModelApiToDto(state));

        return new ResponseEntity<>(seatMapper.mapDtoToModelApi(seatDto), HttpStatus.OK);
    }

    @Override
    @RolesAllowed({"admin"})
    public ResponseEntity<CinemaHallModelApi> deleteCinemaHall(Long id) {
        CinemaHallDto cinemaHallDto = cinemaHallService.deleteCinemaHall(id);

        return new ResponseEntity<>(cinemaHallMapper.mapDtoToModelApi(cinemaHallDto), HttpStatus.OK);
    }

    @Override
    @RolesAllowed({"admin", "manager", "customer"})
    public ResponseEntity<CinemaHallModelApi> getCinemaHall(Long id) {
        CinemaHallDto cinemaHallDto = cinemaHallService.findById(id);

        return new ResponseEntity<>(cinemaHallMapper.mapDtoToModelApi(cinemaHallDto), HttpStatus.OK);
    }

    @Override
    @RolesAllowed({"admin", "manager", "customer"})
    public ResponseEntity<List<CinemaHallModelApi>> getCinemaHalls(Long id) {
        List<CinemaHallModelApi> result = cinemaHallService.findAllForCinema(id).stream()
                .map(cinemaHallMapper::mapDtoToModelApi)
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return IdApi.super.getRequest();
    }
}

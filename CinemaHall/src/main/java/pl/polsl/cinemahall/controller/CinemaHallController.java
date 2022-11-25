package pl.polsl.cinemahall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.cinemahall.api.controller.IdApi;
import pl.polsl.cinemahall.api.model.CinemaHallModelApi;
import pl.polsl.cinemahall.api.model.CinemaHallRequestModelApi;
import pl.polsl.cinemahall.dto.CinemaHallDto;
import pl.polsl.cinemahall.mapper.CinemaHallMapper;
import pl.polsl.cinemahall.service.CinemaHallService;

import javax.annotation.security.RolesAllowed;

@RestController
@RequiredArgsConstructor
public class CinemaHallController implements IdApi {

    private final CinemaHallService cinemaHallService;
    private final CinemaHallMapper cinemaHallMapper;

    @Override
    @RolesAllowed({"admin"})
    public ResponseEntity<CinemaHallModelApi> createCinemaHall(Long id, CinemaHallRequestModelApi cinemaHallRequestModelApi) {
        CinemaHallDto cinemaHallDto = cinemaHallService.createCinemaHall(id, cinemaHallRequestModelApi.getName(),
                cinemaHallRequestModelApi.getxRows(),
                cinemaHallRequestModelApi.getyRows());


        return new ResponseEntity<>(cinemaHallMapper.mapDtoToModelApi(cinemaHallDto), HttpStatus.OK);
    }
}

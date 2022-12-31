package pl.polsl.screening.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.screening.api.controller.ScreeningApi;
import pl.polsl.screening.api.model.MovieScreeningModelApi;
import pl.polsl.screening.api.model.ScreeningModelApi;
import pl.polsl.screening.api.model.ScreeningRequestModelApi;
import pl.polsl.screening.api.model.ScreeningUpdateRequestModelApi;
import pl.polsl.screening.dto.MovieScreeningDto;
import pl.polsl.screening.dto.ScreeningDto;
import pl.polsl.screening.mapper.ScreeningMapper;
import pl.polsl.screening.service.ScreeningService;

import javax.annotation.security.RolesAllowed;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ScreeningController implements ScreeningApi {

    private final ScreeningService service;
    private final ScreeningMapper screeningMapper;

    @Override
    @RolesAllowed({"manager", "admin", "worker"})
    public ResponseEntity<ScreeningModelApi> updateScreening(Long id, ScreeningUpdateRequestModelApi screeningUpdateRequestModelApi) {
        return ScreeningApi.super.updateScreening(id, screeningUpdateRequestModelApi);
    }

    @Override
    @RolesAllowed({"manager", "admin", "worker"})
    public ResponseEntity<ScreeningModelApi> createScreening(ScreeningRequestModelApi screeningRequestModelApi) {
        ScreeningDto screeningDto = screeningMapper.mapModelApiToDto(screeningRequestModelApi);

        ScreeningDto result = service.createScreening(screeningDto);

        return new ResponseEntity<>(screeningMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }

    @Override
    @RolesAllowed({"manager", "admin", "worker"})
    public ResponseEntity<ScreeningModelApi> deleteScreening(Long id) {
        ScreeningDto result = service.deleteScreening(id);

        return new ResponseEntity<>(screeningMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ScreeningModelApi> getScreeningById(Long id) {
        ScreeningDto result = service.getScreeningById(id);

        return new ResponseEntity<>(screeningMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<MovieScreeningModelApi>> getScreeningByCinemaId(Long id, OffsetDateTime time) {
        List<MovieScreeningModelApi> result = service.getScreeningsForCinema(id, time).stream()
                .map(screeningMapper::mapDtoToModelApi)
                .collect(Collectors.toList());


        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MovieScreeningModelApi> getScreeningBySreeningId(Long id) {
        MovieScreeningDto result = service.getScreeningMovieById(id);

        return new ResponseEntity<>(screeningMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }

    //    @Override
//    public ResponseEntity<List<ScreeningModelApi>> getScreenings() {
//        List<ScreeningDto> result = service.findScreenings();
//
//        return new ResponseEntity<>()
//    }
}

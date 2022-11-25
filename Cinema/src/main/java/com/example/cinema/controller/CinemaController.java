package com.example.cinema.controller;

import com.example.cinema.api.controller.CinemaApi;
import com.example.cinema.api.model.CinemaFindResultModelApi;
import com.example.cinema.api.model.CinemaModelApi;
import com.example.cinema.api.model.CinemaRequestModelApi;
import com.example.cinema.dto.CinemaDto;
import com.example.cinema.dto.FindResultDto;
import com.example.cinema.dto.SearchDto;
import com.example.cinema.mapper.CinemaMapper;
import com.example.cinema.mapper.FindResultMapper;
import com.example.cinema.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequiredArgsConstructor
public class CinemaController implements CinemaApi {

    private final CinemaMapper cinemaMapper;
    private final CinemaService cinemaService;
    private final FindResultMapper findResultMapper;

    @Override
    @RolesAllowed({"admin"})
    public ResponseEntity<CinemaModelApi> createCinema(CinemaRequestModelApi cinemaRequestModelApi) {
        CinemaDto cinemaDto = cinemaMapper.mapModelApiToDto(cinemaRequestModelApi);

        CinemaDto result = cinemaService.createCinema(cinemaDto);

        return new ResponseEntity<>(cinemaMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }

    @Override
    @RolesAllowed({"admin"})
    public ResponseEntity<CinemaModelApi> deleteCinema(Long id) {
        CinemaDto cinemaDto = cinemaService.deleteCinema(id);

        return  new ResponseEntity<>(cinemaMapper.mapDtoToModelApi(cinemaDto), HttpStatus.OK);
    }

    @Override
    @RolesAllowed({"admin"})
    public ResponseEntity<CinemaModelApi> getCinemaById(Long id) {
        CinemaDto result = cinemaService.findCinemaById(id);

        return new ResponseEntity<>(cinemaMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CinemaFindResultModelApi> getCinemas(Long limit, Integer page) {
        SearchDto searchDto = SearchDto.builder()
                .limit(limit)
                .page(page).build();

        FindResultDto<CinemaDto> resultDto = cinemaService.findCinemas(searchDto);

        CinemaFindResultModelApi result = findResultMapper.mapAdminDtoToModelApi(resultDto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CinemaModelApi> updateCinema(Long id, CinemaRequestModelApi cinemaRequestModelApi) {
        CinemaDto cinemaDto = cinemaMapper.mapModelApiToDto(cinemaRequestModelApi);

        CinemaDto result = cinemaService.updateCinema(cinemaDto, id);

        return new ResponseEntity<>(cinemaMapper.mapDtoToModelApi(result), HttpStatus.OK);
    }
}

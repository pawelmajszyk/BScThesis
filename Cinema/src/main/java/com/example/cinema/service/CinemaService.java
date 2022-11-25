package com.example.cinema.service;

import com.example.cinema.dto.CinemaDto;
import com.example.cinema.dto.FindResultDto;
import com.example.cinema.dto.SearchDto;
import com.example.cinema.entity.Cinema;
import com.example.cinema.exception.CinemaMissingException;
import com.example.cinema.mapper.CinemaMapper;
import com.example.cinema.repository.CinemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CinemaService {

    private final CinemaRepository cinemaRepository;
    private final CinemaMapper cinemaMapper;

    @Transactional
    public CinemaDto createCinema(CinemaDto cinemaDto) {
        Cinema cinema = cinemaMapper.mapDtoToEntity(cinemaDto);

        Cinema save = cinemaRepository.save(cinema);

        return cinemaMapper.mapEntityToDto(save);
    }

    @Transactional
    public CinemaDto deleteCinema(Long id) {
        Cinema cinea = cinemaRepository.findById(id).orElseThrow(CinemaMissingException::new);

        cinea.setEnabled(false);

        return cinemaMapper.mapEntityToDto(cinea);
    }

    public CinemaDto findCinemaById(Long id) {
        Cinema cinea = cinemaRepository.findById(id).orElseThrow(CinemaMissingException::new);

        return cinemaMapper.mapEntityToDto(cinea);
    }

    public CinemaDto updateCinema(CinemaDto cinemaDto,Long id) {
        cinemaRepository.findById(id).orElseThrow(CinemaMissingException::new);
        Cinema result = cinemaMapper.mapDtoToEntity(cinemaDto);

        result.setId(id);

        return cinemaMapper.mapEntityToDto(cinemaRepository.save(result));
    }

    public FindResultDto<CinemaDto> findCinemas(SearchDto searchDto) {
        PageRequest pageRequest = PageRequest.of(searchDto.getPage(), Math.toIntExact(searchDto.getLimit()));

        Page<Cinema> page = cinemaRepository.findAllByIsEnabledTrue(pageRequest);

        return FindResultDto.<CinemaDto>builder()
                .totalCount(page.getTotalElements())
                .count((long) page.getNumberOfElements())
                .startElement(searchDto.getLimit() * searchDto.getPage())
                .results(page.getContent().stream()
                        .map(cinemaMapper::mapEntityToDto)
                        .collect(Collectors.toList()))
                .build();
    }
}

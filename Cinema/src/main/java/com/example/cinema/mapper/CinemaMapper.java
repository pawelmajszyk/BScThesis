package com.example.cinema.mapper;

import com.example.cinema.api.controller.CinemaApi;
import com.example.cinema.api.model.CinemaModelApi;
import com.example.cinema.api.model.CinemaRequestModelApi;
import com.example.cinema.dto.CinemaDto;
import com.example.cinema.entity.Cinema;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CinemaMapper {

    CinemaDto mapModelApiToDto(CinemaRequestModelApi cinemaRequestModelApi);

    @Mapping(target = "isEnabled", constant = "true")
    Cinema mapDtoToEntity(CinemaDto cinemaDto);

    CinemaDto mapEntityToDto(Cinema save);

    CinemaModelApi mapDtoToModelApi(CinemaDto result);
}

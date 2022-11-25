package com.example.cinema.mapper;

import com.example.cinema.api.model.CinemaFindResultModelApi;
import com.example.cinema.dto.CinemaDto;
import com.example.cinema.dto.FindResultDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = CinemaMapper.class)
public interface FindResultMapper {

    CinemaFindResultModelApi mapAdminDtoToModelApi(FindResultDto<CinemaDto> findResultDto);
}

package pl.polsl.movie.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.movie.api.model.MovieFindResultModelApi;
import pl.polsl.movie.dto.FindResultDto;
import pl.polsl.movie.dto.MovieDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = MovieMapper.class)
public interface FindResultMapper {

    MovieFindResultModelApi mapDtoToModelApi(FindResultDto<MovieDto> findResultDto);
}

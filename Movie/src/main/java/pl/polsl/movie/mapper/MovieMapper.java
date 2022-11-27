package pl.polsl.movie.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.movie.dto.MovieDto;
import pl.polsl.movie.entity.Movie;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MovieMapper {

    Movie mapDtoToEntity(MovieDto movieDto);
    MovieDto mapEntityToDto(Movie movie);
}

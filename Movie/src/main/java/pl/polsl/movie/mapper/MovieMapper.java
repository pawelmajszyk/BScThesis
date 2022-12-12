package pl.polsl.movie.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.polsl.movie.api.model.MovieModelApi;
import pl.polsl.movie.dto.MovieDto;
import pl.polsl.movie.entity.Movie;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MovieMapper {

    MovieDto mapModelApiToDto(String title, String description, String director, String category, String ageCategory, String length, String trailerLink, String shortDescription);

    @Mapping(target = "isEnabled", constant = "true")
    @Mapping(target = "poster", ignore = true)
    Movie mapDtoToEntity(MovieDto movieDto);

    @Mapping(target = "poster", source = "poster.poster")
    MovieDto mapEntityToDto(Movie movie);
    MovieModelApi mapDtoToModelApi(MovieDto movieDto);

    @Mapping(target = "poster", ignore = true)
    MovieDto mapEntityToDtoIgnorePoster(Movie movie);

    @Mapping(target = "poster", ignore = true)
    MovieDto mapEntityToSkipPosterDto(Movie movie);
}

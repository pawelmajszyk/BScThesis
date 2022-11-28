package pl.polsl.movie.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;
import pl.polsl.movie.api.model.AgeCategoryModelApi;
import pl.polsl.movie.api.model.CategoryModelApi;
import pl.polsl.movie.api.model.MovieModelApi;
import pl.polsl.movie.dto.MovieDto;
import pl.polsl.movie.entity.Movie;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MovieMapper {

    MovieDto mapModelApiToDto(String title, String description, String director, CategoryModelApi category, AgeCategoryModelApi ageCategory);

    @Mapping(target = "isEnabled", constant = "true")
    Movie mapDtoToEntity(MovieDto movieDto);
    MovieDto mapEntityToDto(Movie movie);
    MovieModelApi mapDtoToModelApi(MovieDto movieDto);
}

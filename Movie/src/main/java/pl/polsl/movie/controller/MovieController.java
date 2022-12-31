package pl.polsl.movie.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.polsl.movie.api.controller.MovieApi;
import pl.polsl.movie.api.model.AgeCategoryModelApi;
import pl.polsl.movie.api.model.CategoryModelApi;
import pl.polsl.movie.api.model.MovieFindResultModelApi;
import pl.polsl.movie.api.model.MovieModelApi;
import pl.polsl.movie.dto.FindResultDto;
import pl.polsl.movie.dto.MovieDto;
import pl.polsl.movie.dto.SearchDto;
import pl.polsl.movie.mapper.FindResultMapper;
import pl.polsl.movie.mapper.MovieMapper;
import pl.polsl.movie.service.MovieService;

import javax.annotation.security.RolesAllowed;

@RequiredArgsConstructor
@RestController
public class MovieController implements MovieApi {

    private final MovieService movieService;
    private final MovieMapper mapper;
    private final FindResultMapper findResultMapper;

    @SneakyThrows
    @Override
    @RolesAllowed({"manager", "admin", "worker"})
    public ResponseEntity<MovieModelApi> createMovie(String title, String description, String director, String category, String ageCategory, String length, MultipartFile poster, String trailerLink, String shortDescription) {
        MovieDto movieDto = mapper.mapModelApiToDto(title, description, director, category, ageCategory, length, trailerLink, shortDescription);

        movieDto.setPoster(poster.getBytes());

        MovieDto result = movieService.createMovie(movieDto);

        return new ResponseEntity<>(mapper.mapDtoToModelApi(result), HttpStatus.OK);
    }

    @Override
    @RolesAllowed({"manager", "admin", "worker"})
    public ResponseEntity<MovieModelApi> deletesSingleMovie(Long id) {
        MovieDto movieDto = movieService.deleteMovie(id);

        return new ResponseEntity<>(mapper.mapDtoToModelApi(movieDto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MovieFindResultModelApi> getMovieList(Long limit, Integer page, Boolean complete) {
        SearchDto searchDto = SearchDto.builder()
                .limit(limit)
                .page(page).build();

        FindResultDto<MovieDto> movieList = movieService.getMovieList(searchDto, complete);

        return new ResponseEntity<>(findResultMapper.mapDtoToModelApi(movieList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MovieModelApi> getSingleMovie(Long id) {
        MovieDto movieDto = movieService.getSingleMovie(id);

        return new ResponseEntity<>(mapper.mapDtoToModelApi(movieDto), HttpStatus.OK);
    }

//    @Override
//    public ResponseEntity<MovieModelApi> updateMovie(Long id, String title, String description, String director, CategoryModelApi category, AgeCategoryModelApi ageCategory, MultipartFile poster) {
//        MovieDto movieDto = mapper.mapModelApiToDto(title, description, director, category, ageCategory);
//
//        MovieDto result = movieService.updateMovie(id, movieDto);
//
//        return new ResponseEntity<>(mapper.mapDtoToModelApi(result), HttpStatus.OK);
//    }


    @Override
    @RolesAllowed({"manager", "admin", "worker"})
    public ResponseEntity<MovieModelApi> updateMovie(Long id, String title, String description, String director, Long length, CategoryModelApi category, AgeCategoryModelApi ageCategory, MultipartFile poster, String trailerLink, String shortDescription) {
        return MovieApi.super.updateMovie(id, title, description, director, length, category, ageCategory, poster, trailerLink, shortDescription);
    }
}

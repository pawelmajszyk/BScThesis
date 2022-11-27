package pl.polsl.movie.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.polsl.movie.api.controller.MovieApi;
import pl.polsl.movie.api.model.AgeCategoryModelApi;
import pl.polsl.movie.api.model.CategoryModelApi;
import pl.polsl.movie.api.model.MovieModelApi;
import pl.polsl.movie.mapper.MovieMapper;
import pl.polsl.movie.service.MovieService;

@RequiredArgsConstructor
@RestController
public class MovieController implements MovieApi {

    private final MovieService movieService;
    private final MovieMapper mapper;

    @Override
    public ResponseEntity<MovieModelApi> createMovie(String title, String description, String director, CategoryModelApi category, AgeCategoryModelApi ageCategory, MultipartFile poster) {
        return MovieApi.super.createMovie(title, description, director, category, ageCategory, poster);
    }
}

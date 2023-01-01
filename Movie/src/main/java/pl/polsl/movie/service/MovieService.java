package pl.polsl.movie.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.movie.dto.FindResultDto;
import pl.polsl.movie.dto.MovieDto;
import pl.polsl.movie.dto.SearchDto;
import pl.polsl.movie.entity.Movie;
import pl.polsl.movie.entity.Poster;
import pl.polsl.movie.exception.MovieMissingException;
import pl.polsl.movie.mapper.MovieMapper;
import pl.polsl.movie.repository.MovieRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper mapper;

    @Transactional
    public MovieDto createMovie(MovieDto movieDto) {
        Movie movie = mapper.mapDtoToEntity(movieDto);

        movie.setPoster(Poster.builder().poster(movieDto.getPoster()).build());

        return mapper.mapEntityToDto(movieRepository.save(movie));
    }

    @Transactional
    public MovieDto deleteMovie(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(MovieMissingException::new);

        movie.setIsEnabled(false);

        return mapper.mapEntityToDto(movie);
    }

    @Transactional
    public MovieDto updateMovie(Long id, MovieDto movieDto) {
        Movie movie = movieRepository.findById(id).orElseThrow(MovieMissingException::new);

        Movie edditedMovie = mapper.mapDtoToEntity(movieDto);

        edditedMovie.setId(id);

        if(movieDto.getPoster() == null) {
            edditedMovie.setPoster(movie.getPoster());
        }

        return mapper.mapEntityToDto(movieRepository.save(edditedMovie));
    }

    @Transactional
    public MovieDto getSingleMovie(Long id) {
        return mapper.mapEntityToDto(movieRepository.findById(id).orElseThrow(MovieMissingException::new));
    }

    @Transactional
    public FindResultDto<MovieDto> getMovieList(SearchDto searchDto, Boolean complete) {
        PageRequest pageRequest = PageRequest.of(searchDto.getPage(), Math.toIntExact(searchDto.getLimit()));

        Page<Movie> page = movieRepository.findAllByIsEnabledTrue(pageRequest);

        return FindResultDto.<MovieDto>builder()
                .totalCount(page.getTotalElements())
                .count((long) page.getNumberOfElements())
                .startElement(searchDto.getLimit() * searchDto.getPage())
                .results(page.getContent().stream()
                        .map(map -> Boolean.TRUE.equals(complete) ? mapper.mapEntityToDto(map) : mapper.mapEntityToDtoIgnorePoster(map))
                        .collect(Collectors.toList()))
                .build();
    }
}

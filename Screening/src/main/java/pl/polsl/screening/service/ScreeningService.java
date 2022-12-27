package pl.polsl.screening.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.cinemahall.api.model.CinemaHallModelApi;
import pl.polsl.movie.api.model.MovieModelApi;
import pl.polsl.screening.dto.MovieScreeningDto;
import pl.polsl.screening.dto.ScreeningDto;
import pl.polsl.screening.entity.Screening;
import pl.polsl.screening.exception.CinemaHallMissingException;
import pl.polsl.screening.exception.MovieMissingException;
import pl.polsl.screening.exception.ScreeningMissingException;
import pl.polsl.screening.mapper.ScreeningMapper;
import pl.polsl.screening.repository.ScreeningRepository;
import pl.polsl.screening.service.feignClient.CinemaHallClient;
import pl.polsl.screening.service.feignClient.MovieClient;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final ScreeningMapper screeningMapper;
    private final MovieClient movieClient;
    private final CinemaHallClient cinemaHallClient;

    @Transactional
    public ScreeningDto createScreening(ScreeningDto screeningDto) {
        MovieModelApi movieModelApi = validateScreening(screeningDto);

        Screening screening = screeningMapper.mapDtoToEntity(screeningDto);

        screening.setEndTime(screening.getStartTime().plusSeconds(movieModelApi.getLength()));

        return screeningMapper.mapEntityToDto(screeningRepository.save(screening));
    }

    @Transactional
    public ScreeningDto updateScreening(ScreeningDto screeningDto) {
        validateScreening(screeningDto);

        Screening screening = screeningRepository.findById(screeningDto.getId()).orElseThrow(ScreeningMissingException::new);

        return null;
    }

    @Transactional
    public ScreeningDto deleteScreening(Long id) {
        Screening screening = screeningRepository.findById(id).orElseThrow(ScreeningMissingException::new);

        screening.setIsActive(false);

        return screeningMapper.mapEntityToDto(screening);
    }

    public List<MovieScreeningDto> getScreeningsForCinema(Long id, OffsetDateTime time) {
        List<Long> cinemaHallsIds = cinemaHallClient.getCinemaHallsForCinema(id).stream()
                .map(CinemaHallModelApi::getId)
                .collect(Collectors.toList());

        List<Screening> screenings = screeningRepository.findAllByCinemaHallIdInAndAndIsActiveTrue(cinemaHallsIds, time);

        Map<Long, List<Screening>> screeningsForMovie = new HashMap<>();

        screenings.forEach(object ->
                screeningsForMovie.computeIfAbsent(object.getMovieId(), k -> new ArrayList<>())
                        .add(object));

        return screeningsForMovie.entrySet().stream()
                .map(screening -> {
                    MovieModelApi singleMovie = movieClient.getSingleMovie(screening.getKey());

                    MovieScreeningDto movieScreeningDto = screeningMapper.mapMovieApiToDto(singleMovie);

                    movieScreeningDto.setScreenings(screening.getValue().stream()
                            .map(screeningMapper::mapEntityToDto).collect(Collectors.toList()));

                    return movieScreeningDto;
                }).collect(Collectors.toList());
    }

    public ScreeningDto getScreeningById(Long id) {
        Screening screening = screeningRepository.findById(id).orElseThrow(ScreeningMissingException::new);

        return screeningMapper.mapEntityToDto(screening);
    }

//    public List<ScreeningDto> findScreenings() {
//        List<Screening> screenings = screeningRepository.findAll();
//    }

    private MovieModelApi validateScreening(ScreeningDto screeningDto) {
        try {
            cinemaHallClient.getSingleCinemaHall(screeningDto.getCinemaHallId());
        } catch (Exception e) {
            throw new CinemaHallMissingException();
        }

        try {
            return  movieClient.getSingleMovie(screeningDto.getMovieId());
        } catch (Exception e) {
            throw new MovieMissingException();
        }
    }
}

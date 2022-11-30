package pl.polsl.screening.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.screening.dto.ScreeningDto;
import pl.polsl.screening.entity.Screening;
import pl.polsl.screening.exception.CinemaHallMissingException;
import pl.polsl.screening.exception.MovieMissingException;
import pl.polsl.screening.exception.ScreeningMissingException;
import pl.polsl.screening.mapper.ScreeningMapper;
import pl.polsl.screening.repository.ScreeningRepository;
import pl.polsl.screening.service.feignClient.CinemaHallClient;
import pl.polsl.screening.service.feignClient.MovieClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final ScreeningMapper screeningMapper;
    private final MovieClient movieClient;
    private final CinemaHallClient cinemaHallClient;

    @Transactional
    public ScreeningDto createScreening(ScreeningDto screeningDto) {
        validateScreening(screeningDto);

        Screening screening = screeningMapper.mapDtoToEntity(screeningDto);

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

    public ScreeningDto getScreeningById(Long id) {
        Screening screening = screeningRepository.findById(id).orElseThrow(ScreeningMissingException::new);

        return screeningMapper.mapEntityToDto(screening);
    }

//    public List<ScreeningDto> findScreenings() {
//        List<Screening> screenings = screeningRepository.findAll();
//    }

    private void validateScreening(ScreeningDto screeningDto) {
        try {
            movieClient.getSingleMovie(screeningDto.getMovieId());
        } catch (Exception e) {
            throw new MovieMissingException();
        }

        try {
            cinemaHallClient.getSingleCinemaHall(screeningDto.getCinemaHallId());
        } catch (Exception e) {
            throw new CinemaHallMissingException();
        }
    }
}

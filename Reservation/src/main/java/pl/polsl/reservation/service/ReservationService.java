package pl.polsl.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.movie.api.model.MovieModelApi;
import pl.polsl.reservation.dto.MovieReservationDto;
import pl.polsl.reservation.dto.ReservationDto;
import pl.polsl.reservation.entity.Reservation;
import pl.polsl.reservation.mapper.ReservationMapper;
import pl.polsl.reservation.repository.ReservationRepository;
import pl.polsl.reservation.service.feignClients.MovieClient;
import pl.polsl.reservation.service.feignClients.ScreeningClient;
import pl.polsl.reservation.service.feignClients.UsersClient;
import pl.polsl.screening.api.model.ScreeningModelApi;
import pl.polsl.users.api.model.WorkerResponseModelApi;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationMapper reservationMapper;;
    private final ReservationRepository reservationRepository;
    private final UsersClient usersClient;
    private final MovieClient movieClient;
    private final ScreeningClient screeningClient;

    @Transactional
    public ReservationDto createReservation(ReservationDto reservationDto) {
        WorkerResponseModelApi selfInfo = usersClient.getSelfInfo();

        reservationDto.setUserId(selfInfo.getId());

        Reservation reservation = reservationMapper.mapDtoToEntity(reservationDto);

        return reservationMapper.mapEntityToDto(reservationRepository.save(reservation));
    }

    public List<ReservationDto> getReservationListForScreening(Long id) {
        return reservationRepository.findAllByScreeningIdAndIsActiveTrue(id).stream()
                .map(reservationMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public List<MovieReservationDto> gerReservationListForCustomer() {
        WorkerResponseModelApi selfInfo = usersClient.getSelfInfo();

        return reservationRepository.findAllByUserId(selfInfo.getId()).stream()
                .map(reservationMapper::mapEntityToDto)
                .map(r -> {
                    ScreeningModelApi singleScreening = screeningClient.getSingleScreening(r.getScreeningId());
                    MovieModelApi singleMovie = movieClient.getSingleMovie(singleScreening.getMovieId());
                    return reservationMapper.mapDtoToDto(r, singleMovie.getTitle());
                })
                .collect(Collectors.toList());
    }

    public ReservationDto findReservation(Long id) {
        return reservationMapper.mapEntityToDto(reservationRepository.getById(id));
    }

    @Transactional
    public ReservationDto deleteReservation(Long id) {
        Reservation reservtion = reservationRepository.getById(id);

        reservtion.setIsActive(false);

        return reservationMapper.mapEntityToDto(reservtion);
    }
}

package pl.polsl.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.reservation.dto.ReservationDto;
import pl.polsl.reservation.entity.Reservation;
import pl.polsl.reservation.mapper.ReservationMapper;
import pl.polsl.reservation.repository.ReservationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationMapper reservationMapper;;
    private final ReservationRepository reservationRepository;

    public ReservationDto createReservation(ReservationDto reservationDto) {
        Reservation reservation = reservationMapper.mapDtoToEntity(reservationDto);

        return reservationMapper.mapEntityToDto(reservationRepository.save(reservation));
    }


    public List<ReservationDto> getReservationListForScreening(Long id) {
        return reservationRepository.findAllByScreeningId(id).stream()
                .map(reservationMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }
}

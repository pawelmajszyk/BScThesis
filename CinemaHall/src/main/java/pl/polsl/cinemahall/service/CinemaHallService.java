package pl.polsl.cinemahall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.cinemahall.dto.CinemaHallDto;
import pl.polsl.cinemahall.entity.CinemaHall;
import pl.polsl.cinemahall.entity.Seat;
import pl.polsl.cinemahall.entity.enums.SeatState;
import pl.polsl.cinemahall.exception.CinemaMissingException;
import pl.polsl.cinemahall.mapper.CinemaHallMapper;
import pl.polsl.cinemahall.repository.CinemaHallRepository;
import pl.polsl.cinemahall.service.clients.CinemaClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CinemaHallService {

    private final CinemaHallRepository cinemaHallRepository;
    private final CinemaClient cinemaClient;
    private final CinemaHallMapper cinemaHallMapper;

    @Transactional
    public CinemaHallDto createCinemaHall(Long id, String name, Integer getxRows, Integer getyRows) {
        try {
            cinemaClient.getSingleCinema(id);
        } catch (Exception e) {
            throw new CinemaMissingException();
        }

        List<Seat> seats= new ArrayList<>();

        for(int x = 0; x< getxRows ; ++x) {
            for (int y = 0; y < getyRows; ++y) {
                seats.add(Seat.builder().seatState(SeatState.AVAILABLE)
                        .column(x)
                        .row(y).build());
            }
        }

        CinemaHall cinemaHall = CinemaHall.builder()
                .cinemaId(id)
                .enabled(true)
                .name(name)
                .seats(seats)
                .build();

        return cinemaHallMapper.mapEnityToDto(cinemaHallRepository.save(cinemaHall));
    }
}

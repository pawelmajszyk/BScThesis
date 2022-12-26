package pl.polsl.cinemahall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.PropertyValues;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.cinemahall.dto.CinemaHallDto;
import pl.polsl.cinemahall.dto.SeatDto;
import pl.polsl.cinemahall.dto.enums.SeatStateDto;
import pl.polsl.cinemahall.entity.CinemaHall;
import pl.polsl.cinemahall.entity.Seat;
import pl.polsl.cinemahall.entity.enums.SeatState;
import pl.polsl.cinemahall.exception.CinemaHallMissingException;
import pl.polsl.cinemahall.exception.CinemaMissingException;
import pl.polsl.cinemahall.exception.SeatMissingException;
import pl.polsl.cinemahall.mapper.CinemaHallMapper;
import pl.polsl.cinemahall.mapper.SeatMapper;
import pl.polsl.cinemahall.repository.CinemaHallRepository;
import pl.polsl.cinemahall.repository.SeatRepository;
import pl.polsl.cinemahall.service.clients.CinemaClient;
import pl.polsl.cinemahall.service.clients.UsersClient;
import pl.polsl.users.api.model.WorkerResponseModelApi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CinemaHallService {

    private final CinemaHallRepository cinemaHallRepository;
    private final CinemaClient cinemaClient;
    private final CinemaHallMapper cinemaHallMapper;
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;
    private final UsersClient usersClient;

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

    @Transactional
    public CinemaHallDto deleteCinemaHall(Long id) {
        CinemaHall cinemaHall = cinemaHallRepository.findById(id).orElseThrow(CinemaHallMissingException::new);

        cinemaHall.setEnabled(false);

        return cinemaHallMapper.mapEnityToDto(cinemaHall);
    }

    public CinemaHallDto findById(Long id) {
        CinemaHall cinemaHall = cinemaHallRepository.findById(id).orElseThrow(CinemaHallMissingException::new);

        return cinemaHallMapper.mapEnityToDto(cinemaHall);
    }

    public List<CinemaHallDto> findAllForCinema(Long id) {
        CinemaHall cinemaHall = CinemaHall.builder().cinemaId(id).build();

        Example<CinemaHall> example = Example.of(cinemaHall);

        List<CinemaHall> cinemaHalls = cinemaHallRepository.findAll(example);

        return cinemaHalls.stream()
                .map(cinemaHallMapper::mapEnityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public SeatDto changeSeatState(Long id, SeatStateDto seatStateDto) {
        Seat seat = seatRepository.findById(id).orElseThrow(SeatMissingException::new);

        SeatState seatState = seatMapper.mapDtoToEntity(seatStateDto);

        seat.setSeatState(seatState);

        return seatMapper.mapEntityToDto(seat);
    }

    public List<CinemaHallDto> findAllForWorker() {
        WorkerResponseModelApi workerInfo = usersClient.getSelfInfo();

        return findAllForCinema(workerInfo.getCinemaId());
    }
}

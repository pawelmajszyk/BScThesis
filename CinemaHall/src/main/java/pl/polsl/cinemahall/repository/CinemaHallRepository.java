package pl.polsl.cinemahall.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.cinemahall.entity.CinemaHall;

import java.util.List;

@Repository
public interface CinemaHallRepository extends JpaRepository<CinemaHall, Long> {

    List<CinemaHall> findAllByCinemaId(Example example);
}

package pl.polsl.cinemahall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.cinemahall.entity.CinemaHall;

@Repository
public interface CinemaHallRepository extends JpaRepository<CinemaHall, Long> {
}

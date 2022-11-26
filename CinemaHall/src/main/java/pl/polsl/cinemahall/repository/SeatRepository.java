package pl.polsl.cinemahall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.cinemahall.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}

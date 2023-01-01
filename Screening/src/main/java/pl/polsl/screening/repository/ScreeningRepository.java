package pl.polsl.screening.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.polsl.screening.entity.Screening;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    @Query(value = "select * from screening s where s.cinema_hall_id in ?1 and DATE_PART('day', TO_TIMESTAMP(date(s.start_time)\\:\\:text, 'YYYY-MM-DD HH:MI:SS') - TO_TIMESTAMP(date(?2)\\:\\:text, 'YYYY-MM-DD HH:MI:SS')) = 0 and s.is_active = true", nativeQuery = true)
    List<Screening> findAllByCinemaHallIdInAndAndIsActiveTrue(List<Long> cinemaIds, OffsetDateTime time);
}

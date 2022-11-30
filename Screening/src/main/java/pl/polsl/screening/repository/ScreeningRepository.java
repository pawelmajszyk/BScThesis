package pl.polsl.screening.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.screening.entity.Screening;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
}

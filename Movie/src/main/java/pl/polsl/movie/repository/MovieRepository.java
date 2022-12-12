package pl.polsl.movie.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.movie.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Page<Movie> findAllByIsEnabledTrue(PageRequest pageRequest);
}

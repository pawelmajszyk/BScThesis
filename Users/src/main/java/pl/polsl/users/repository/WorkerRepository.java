package pl.polsl.users.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.users.entity.Worker;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {

    Page<Worker> findAll(Example example, Pageable pageable);
}

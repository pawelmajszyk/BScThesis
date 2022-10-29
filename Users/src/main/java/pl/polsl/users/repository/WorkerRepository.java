package pl.polsl.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.users.entity.Worker;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
}

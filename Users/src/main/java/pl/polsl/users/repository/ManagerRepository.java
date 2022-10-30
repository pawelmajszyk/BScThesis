package pl.polsl.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.users.entity.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
}

package pl.polsl.users.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.users.entity.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Page<Manager> findAllByIsEnabledTrue(Pageable pageable);

    Manager findByUserId(String currentPrincipalName);
}

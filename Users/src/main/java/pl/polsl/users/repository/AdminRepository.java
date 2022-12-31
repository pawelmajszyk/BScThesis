package pl.polsl.users.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.users.entity.Admin;
import pl.polsl.users.entity.Customer;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Page<Admin> findAllByIsEnabledTrue(Pageable pageable);
}

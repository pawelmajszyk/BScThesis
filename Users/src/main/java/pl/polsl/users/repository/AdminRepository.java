package pl.polsl.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.users.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}

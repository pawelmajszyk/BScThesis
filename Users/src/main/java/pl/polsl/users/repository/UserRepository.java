package pl.polsl.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.users.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

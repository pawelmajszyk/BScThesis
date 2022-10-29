package pl.polsl.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.users.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

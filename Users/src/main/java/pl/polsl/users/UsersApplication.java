package pl.polsl.users;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import pl.polsl.users.dto.UserDto;
import pl.polsl.users.entity.Admin;
import pl.polsl.users.repository.AdminRepository;
import pl.polsl.users.service.AdminService;

@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
public class UsersApplication implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final AdminService adminService;

    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (adminRepository.findAll().isEmpty()) {
            try {
                adminService.createAdmin(UserDto.builder().firstName("admin")
                    .isEnabled(true)
                    .lastName("admin")
                    .email("admin@gmail.com")
                    .username("admin")
                    .password("admin").build());

            } catch (Exception e) {

            }
        }
    }
}

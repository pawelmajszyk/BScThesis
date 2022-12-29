package pl.polsl.reservation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationApplicationTests {

    @MockBean
    JwtDecoder jwtDecoder;

    @Test
    void contextLoads() {
    }

}

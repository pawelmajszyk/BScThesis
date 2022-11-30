package pl.polsl.screening;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;

@SpringBootTest
@AutoConfigureMockMvc
class ScreeningApplicationTests {

    @MockBean
    JwtDecoder jwtDecoder;


    @Test
    void contextLoads() {
    }

}

package pl.polsl.users.config;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;

@Configuration
@Slf4j
public class KeycloakConfig {

    static Keycloak keycloak = null;

    @Value("${users.secret-key}")
    private String name;

    private static String NAME_STATIC;

    @Value("${users.secret-key}")
    public void setNameStatic(String name){
        KeycloakConfig.NAME_STATIC = name;
    }

    public KeycloakConfig() {
    }

    public static Keycloak getInstance(){
        if(keycloak == null){

            return KeycloakBuilder.builder()
                    .grantType(CLIENT_CREDENTIALS)
                    .serverUrl("http://localhost:28080/auth")
                    .clientId("eminem")
                    .realm("cinema")
                    .clientSecret("cceba170-b75c-4025-811e-afbe38245bc1")
                    .build();
        }

        return keycloak;
    }
}
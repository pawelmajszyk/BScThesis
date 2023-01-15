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

    @Value("${keycloak.uri}")
    private String keycloakuri;

    private static String NAME_STATIC;
    private static String KEYLOCAK_URI;

    @Value("${users.secret-key}")
    public void setNameStatic(String name){
        KeycloakConfig.NAME_STATIC = name;
    }

    @Value("${keycloak.uri}")
    public void setKeycloakuri(String name){
        KeycloakConfig.KEYLOCAK_URI = keycloakuri;
    }


    public KeycloakConfig() {
    }

    public static Keycloak getInstance(){
        if(keycloak == null){
            System.out.println((String.format("%s%s%s","http://", KEYLOCAK_URI, ":8080/auth")));
            System.out.println(NAME_STATIC);
            System.out.println((String.format("%s%s%s","http://", KEYLOCAK_URI, ":8080/auth")));
            System.out.println((String.format("%s%s%s","http://", KEYLOCAK_URI, ":8080/auth")));
            return KeycloakBuilder.builder()
                    .grantType(CLIENT_CREDENTIALS)
                    .serverUrl(String.format("%s%s%s","http://", KEYLOCAK_URI, ":8080/auth"))
                    .clientId("eminem")
                    .realm("cinema")
                    .clientSecret(NAME_STATIC)
                    .build();
        }

        return keycloak;
    }
}
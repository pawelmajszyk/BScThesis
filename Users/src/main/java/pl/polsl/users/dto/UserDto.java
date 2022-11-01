package pl.polsl.users.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String username;
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private Boolean isEnabled;
    private AddressDto address;
}

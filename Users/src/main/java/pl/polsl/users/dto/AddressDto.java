package pl.polsl.users.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {

    private String city;
    private String street;
    private String zipCode;
}

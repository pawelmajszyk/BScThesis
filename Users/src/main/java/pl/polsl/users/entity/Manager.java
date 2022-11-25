package pl.polsl.users.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("2")

@SuperBuilder
public class Manager extends User{

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    private Long cinemaId;
}

package pl.polsl.users.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("3")
@SuperBuilder
public class Worker extends User{

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    private Long cinemaId;
}

package pl.polsl.users.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@DiscriminatorValue("1")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Admin extends User{


}

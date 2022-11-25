package pl.polsl.cinemahall.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CinemaHall {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "auto_gen")
    @SequenceGenerator(name = "auto_gen", sequenceName = "A")
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="cinema_hall_id", nullable=false)
    private List<Seat> seats;

    private Long cinemaId;
    private Boolean enabled;
}

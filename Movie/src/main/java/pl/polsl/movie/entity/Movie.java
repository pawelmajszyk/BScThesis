package pl.polsl.movie.entity;

import lombok.*;
import pl.polsl.movie.entity.enums.AgeCategory;
import pl.polsl.movie.entity.enums.Category;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "auto_gen")
    @SequenceGenerator(name = "auto_gen", sequenceName = "a")
    private Long id;

    private String title;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Enumerated(value = EnumType.STRING)
    private AgeCategory ageCategory;

    @Column(name = "description", length = 200041)
    private String description;
    private String shortDescription;
    private Long length;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Poster poster;

    private String director;

    private Boolean isEnabled;
    private String trailerLink;
}

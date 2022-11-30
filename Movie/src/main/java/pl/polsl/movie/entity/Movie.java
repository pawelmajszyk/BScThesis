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
    @SequenceGenerator(name = "auto_gen", sequenceName = "A")
    private Long id;

    private String title;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Enumerated(value = EnumType.STRING)
    private AgeCategory ageCategory;

    private String description;
    private Long length;

    @Lob
    private byte[] poster;

    private String director;

    private Boolean isEnabled;
}

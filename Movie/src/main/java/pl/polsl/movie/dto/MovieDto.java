package pl.polsl.movie.dto;

import lombok.*;
import pl.polsl.movie.entity.enums.AgeCategory;
import pl.polsl.movie.entity.enums.Category;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDto {

    private Long id;
    private String title;
    private Category category;
    private AgeCategory ageCategory;
    private String description;
    private byte[] poster;
    private String director;
    private Boolean isEnabled;
}

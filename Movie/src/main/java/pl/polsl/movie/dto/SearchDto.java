package pl.polsl.movie.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {

    private Long limit;
    private Integer page;
}

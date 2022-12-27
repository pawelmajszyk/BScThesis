package pl.polsl.screening.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pl.polsl.screening.api.model.ScreeningModelApi;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieScreeningDto {

    private Long id;
    private String title;
    private String category;
    private String shortDescription;
    private byte[] poster;
    private List<ScreeningDto> screenings = new ArrayList<>();
}

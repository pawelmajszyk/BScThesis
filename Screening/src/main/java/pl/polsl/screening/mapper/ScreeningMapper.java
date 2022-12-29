package pl.polsl.screening.mapper;

import org.mapstruct.*;
import pl.polsl.movie.api.model.MovieModelApi;
import pl.polsl.screening.api.model.MovieScreeningModelApi;
import pl.polsl.screening.api.model.ScreeningModelApi;
import pl.polsl.screening.api.model.ScreeningRequestModelApi;
import pl.polsl.screening.api.model.ScreeningUpdateRequestModelApi;
import pl.polsl.screening.dto.MovieScreeningDto;
import pl.polsl.screening.dto.ScreeningDto;
import pl.polsl.screening.entity.Screening;

import java.util.Comparator;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ScreeningMapper {

    @Mapping(target = "isActive", constant = "true")
    Screening mapDtoToEntity(ScreeningDto screeningDto);
    ScreeningDto mapEntityToDto(Screening screening);
    ScreeningDto mapModelApiToDto(ScreeningRequestModelApi screeningRequestModelApi);
    ScreeningDto mapModelApiToDto(ScreeningUpdateRequestModelApi screeningUpdateRequestModelApi);
    ScreeningModelApi mapDtoToModelApi(ScreeningDto screeningDto);

    MovieScreeningDto mapMovieApiToDto(MovieModelApi movieModelApi);

    MovieScreeningModelApi mapDtoToModelApi(MovieScreeningDto screeningDto);

    @AfterMapping
    default void afterAgreementDtoTOAgreementMapping(@MappingTarget MovieScreeningModelApi movieScreeningModelApi) {
       movieScreeningModelApi.setScreenings(movieScreeningModelApi.getScreenings().stream()
               .sorted(Comparator.comparing(ScreeningModelApi::getStartTime))
               .collect(Collectors.toList()));
    }
}

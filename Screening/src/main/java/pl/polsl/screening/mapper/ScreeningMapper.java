package pl.polsl.screening.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.polsl.screening.api.model.ScreeningModelApi;
import pl.polsl.screening.api.model.ScreeningRequestModelApi;
import pl.polsl.screening.api.model.ScreeningUpdateRequestModelApi;
import pl.polsl.screening.dto.ScreeningDto;
import pl.polsl.screening.entity.Screening;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ScreeningMapper {

    @Mapping(target = "isActive", constant = "true")
    Screening mapDtoToEntity(ScreeningDto screeningDto);
    ScreeningDto mapEntityToDto(Screening screening);
    ScreeningDto mapModelApiToDto(ScreeningRequestModelApi screeningRequestModelApi);
    ScreeningDto mapModelApiToDto(ScreeningUpdateRequestModelApi screeningUpdateRequestModelApi);
    ScreeningModelApi mapDtoToModelApi(ScreeningDto screeningDto);
}

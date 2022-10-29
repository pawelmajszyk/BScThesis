package pl.polsl.users.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.polsl.users.api.model.AddressModelApi;
import pl.polsl.users.dto.AddressDto;
import pl.polsl.users.entity.Address;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AddressMapper {

    AddressDto mapModelApiToDto(AddressModelApi addressModelApi);
    Address mapDtoToEntity(AddressDto addressDto);
}

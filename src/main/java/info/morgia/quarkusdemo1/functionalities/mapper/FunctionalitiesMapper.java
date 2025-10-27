package info.morgia.quarkusdemo1.functionalities.mapper;


import info.morgia.quarkusdemo1.functionalities.dto.FunctionalitiesListResponseDto;
import info.morgia.quarkusdemo1.functionalities.dto.FunctionalityPostRequestDto;
import info.morgia.quarkusdemo1.functionalities.entity.Functionalities;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.JAKARTA_CDI;

@Mapper(componentModel = JAKARTA_CDI)
public interface FunctionalitiesMapper {

    @Mapping(target = "id", expression = "java(entity.getId() != null ? entity.getId().toHexString() : null)")
    FunctionalitiesListResponseDto toResponseDto(Functionalities entity);


    Functionalities toEntity(FunctionalityPostRequestDto dto);
}

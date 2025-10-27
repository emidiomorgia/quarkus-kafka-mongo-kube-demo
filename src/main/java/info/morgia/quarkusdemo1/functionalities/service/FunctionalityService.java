package info.morgia.quarkusdemo1.functionalities.service;

import info.morgia.quarkusdemo1.functionalities.dto.FunctionalitiesListResponseDto;
import info.morgia.quarkusdemo1.functionalities.dto.FunctionalityPostRequestDto;
import info.morgia.quarkusdemo1.functionalities.entity.Functionalities;
import info.morgia.quarkusdemo1.functionalities.mapper.FunctionalitiesMapper;
import info.morgia.quarkusdemo1.functionalities.repository.FunctionalitiesRepository;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.Nullable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class FunctionalityService {
    @Inject
    FunctionalitiesRepository functionalitiesRepository;

    @Inject
    FunctionalitiesMapper functionalitiesMapper;

    public Uni<List<FunctionalitiesListResponseDto>> findFiltered(@Nullable String nameFilter){

        return functionalitiesRepository
                .findFiltered(nameFilter)
                .onItem().transform(list ->
                    list.stream().map(functionalitiesMapper::toResponseDto)
                            .collect(Collectors.toList())
                );
    }

    public Uni<Optional<FunctionalitiesListResponseDto>> getById(String id){
        return functionalitiesRepository.findByIdOptional(new ObjectId(id)) // Uni<Optional<Functionalities>>
                .map(optEntity -> {
                    return optEntity.map(functionalitiesMapper::toResponseDto);
                });

    }

    public Uni<FunctionalitiesListResponseDto> saveFunctionality(FunctionalityPostRequestDto functionality) {
        Functionalities entity = functionalitiesMapper.toEntity(functionality);
        return functionalitiesRepository
                .persist(entity)
                .replaceWith(entity)
                .onItem().transform(e -> {
                    return functionalitiesMapper.toResponseDto(e);
                });
    }

}

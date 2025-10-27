package info.morgia.quarkusdemo1.functionalities.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record FunctionalitiesListResponseDto(String id, String name, String description) {

}

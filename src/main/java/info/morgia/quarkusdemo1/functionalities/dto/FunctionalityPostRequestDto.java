package info.morgia.quarkusdemo1.functionalities.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@RegisterForReflection
public class FunctionalityPostRequestDto {
    private String name;
    private String description;
}

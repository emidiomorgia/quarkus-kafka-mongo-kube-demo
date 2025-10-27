package info.morgia.quarkusdemo1.functionalities.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RegisterForReflection
public class MyDto {
    private int n1;
    private double n2;
}

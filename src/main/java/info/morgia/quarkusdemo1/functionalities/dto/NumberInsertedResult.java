package info.morgia.quarkusdemo1.functionalities.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.Instant;


public record NumberInsertedResult(int inserted, Instant timestamp) {

}

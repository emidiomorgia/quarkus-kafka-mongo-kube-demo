package info.morgia.quarkusdemo1.functionalities.dto;

public record TodoResponseDto(
        int id,
        String todo,
        boolean completed,
        int userId
) {}

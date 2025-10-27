package info.morgia.quarkusdemo1.functionalities.clients;

import info.morgia.quarkusdemo1.functionalities.dto.TodoResponseDto;

import java.util.List;

public record TodoListResponseDto(
        List<TodoResponseDto> todos,
        int total,
        int skip,
        int limit
) {}
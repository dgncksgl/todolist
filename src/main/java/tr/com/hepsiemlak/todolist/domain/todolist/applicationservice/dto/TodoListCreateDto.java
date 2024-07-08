package tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import tr.com.hepsiemlak.todolist.domain.todolist.*;

import java.time.LocalDateTime;
import java.util.List;

public record TodoListCreateDto(
        @NotNull(message = "title should not be null")
        @NotBlank(message = "title should not be blank")
        @Size(min = 2, message = "title should be min 2")
        @Size(max = 50, message = "title should be max 50")
        String title,
        @Size(max = 200, message = "note should be max 50")
        String note,
        TodoListItemPriority priority,
        LocalDateTime endTime,
        List<Tags> tags,
        List<AssignedUsers> assignedUsers,
        List<Documents> documents
) {

    public static TodoList convertToTodoListFromTodoListCreateDto(TodoListCreateDto todoListCreateDto, String userId) {
        return new TodoList()
                .setTitle(todoListCreateDto.title())
                .setNote(todoListCreateDto.note())
                .setPriority(todoListCreateDto.priority())
                .setEndTime(todoListCreateDto.endTime())
                .setTags(todoListCreateDto.tags())
                .setAssignedUsers(todoListCreateDto.assignedUsers())
                .setDocuments(todoListCreateDto.documents())
                .setUserId(userId);
    }
}

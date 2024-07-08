package tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import tr.com.hepsiemlak.todolist.domain.todolist.*;

import java.time.LocalDateTime;
import java.util.List;

public record TodoListUpdateDto(
        @NotNull(message = "id should not be null")
        @NotBlank(message = "id should not be blank")
        String id,
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

    public static TodoList convertToTodoListFromTodoListUpdateDto(TodoListUpdateDto todoListUpdateDto, String userId) {
        return new TodoList()
                .setId(todoListUpdateDto.id())
                .setTitle(todoListUpdateDto.title())
                .setNote(todoListUpdateDto.note())
                .setPriority(todoListUpdateDto.priority())
                .setEndTime(todoListUpdateDto.endTime())
                .setTags(todoListUpdateDto.tags())
                .setAssignedUsers(todoListUpdateDto.assignedUsers())
                .setDocuments(todoListUpdateDto.documents())
                .setUserId(userId);
    }
}

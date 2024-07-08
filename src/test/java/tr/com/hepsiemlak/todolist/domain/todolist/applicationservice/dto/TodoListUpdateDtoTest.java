package tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.dto;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tr.com.hepsiemlak.todolist.InvalidParametersUtil;
import tr.com.hepsiemlak.todolist.domain.todolist.AssignedUsers;
import tr.com.hepsiemlak.todolist.domain.todolist.Documents;
import tr.com.hepsiemlak.todolist.domain.todolist.Tags;
import tr.com.hepsiemlak.todolist.domain.todolist.TodoListItemPriority;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

class TodoListUpdateDtoTest extends InvalidParametersUtil {

    public static final String ID = "ID";

    public static final String TITLE = "title";

    public static final String NOTE = "note";

    public static final TodoListItemPriority PRIORITY = TodoListItemPriority.LOW;

    public static final LocalDateTime END_TIME = LocalDateTime.now();

    public static final List<Tags> TAGS = Arrays.asList(new Tags("tag0"), new Tags("tag1"));

    public static final List<AssignedUsers> ASSIGNED_USERS = Arrays.asList(
            new AssignedUsers("user0"), new AssignedUsers("user1")
    );

    public static final List<Documents> DOCUMENTS = Arrays.asList(
            new Documents("name0", "data0", "mimeType0"),
            new Documents("name1", "data1", "mimeType1")
    );

    @Test
    void shouldHaveNoViolations() {
        TodoListUpdateDto dto = new TodoListUpdateDto(
                ID,
                TITLE,
                NOTE,
                PRIORITY,
                END_TIME,
                TAGS,
                ASSIGNED_USERS,
                DOCUMENTS
        );
        Set<ConstraintViolation<TodoListUpdateDto>> violations = validator.validate(dto);
        Assertions.assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("invalidNameValues")
    void shouldHaveInvalidId(String value) {

        TodoListUpdateDto dto = new TodoListUpdateDto(
                value,
                TITLE,
                NOTE,
                PRIORITY,
                END_TIME,
                TAGS,
                ASSIGNED_USERS,
                DOCUMENTS
        );

        Set<ConstraintViolation<TodoListUpdateDto>> violations = validator.validate(dto);

        Assertions.assertTrue(2 >= violations.size());

        for (ConstraintViolation<TodoListUpdateDto> violation : violations) {
            Assertions.assertEquals("id", violation.getPropertyPath().toString());
        }

    }

    @ParameterizedTest
    @MethodSource("invalidNameValues")
    void shouldHaveInvalidTitle(String value) {

        TodoListUpdateDto dto = new TodoListUpdateDto(
                ID,
                value,
                NOTE,
                PRIORITY,
                END_TIME,
                TAGS,
                ASSIGNED_USERS,
                DOCUMENTS
        );

        Set<ConstraintViolation<TodoListUpdateDto>> violations = validator.validate(dto);

        Assertions.assertTrue(2 >= violations.size());

        for (ConstraintViolation<TodoListUpdateDto> violation : violations) {
            Assertions.assertEquals("title", violation.getPropertyPath().toString());
        }

    }

    @ParameterizedTest
    @MethodSource("invalidNameValues")
    void shouldHaveInvalidNote(String value) {

        TodoListUpdateDto dto = new TodoListUpdateDto(
                ID,
                TITLE,
                value,
                PRIORITY,
                END_TIME,
                TAGS,
                ASSIGNED_USERS,
                DOCUMENTS
        );

        Set<ConstraintViolation<TodoListUpdateDto>> violations = validator.validate(dto);

        Assertions.assertTrue(2 >= violations.size());

        for (ConstraintViolation<TodoListUpdateDto> violation : violations) {
            Assertions.assertEquals("note", violation.getPropertyPath().toString());
        }

    }
}
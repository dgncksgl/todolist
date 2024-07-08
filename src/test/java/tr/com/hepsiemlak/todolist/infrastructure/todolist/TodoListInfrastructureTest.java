package tr.com.hepsiemlak.todolist.infrastructure.todolist;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tr.com.hepsiemlak.todolist.domain.todolist.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class TodoListInfrastructureTest {

    public static final String ID = "ID";

    public static final String USER_ID = "USERID";

    public static final String TITLE = "title";

    public static final String NOTE = "note";

    public static final TodolistItemStatus STATUS = TodolistItemStatus.NOT_DONE;

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

    TodoListInfrastructure todoListInfrastructure;

    TodoListInfrastructureRepository repository;

    TodoList todoList;

    @BeforeEach
    void setUp() {
        todoList = new TodoList()
                .setId(ID)
                .setTitle(TITLE)
                .setNote(NOTE)
                .setStatus(STATUS)
                .setPriority(PRIORITY)
                .setEndTime(END_TIME)
                .setTags(TAGS)
                .setAssignedUsers(ASSIGNED_USERS)
                .setDocuments(DOCUMENTS);
        repository = Mockito.mock(TodoListInfrastructureRepository.class);
        todoListInfrastructure = new TodoListInfrastructure(repository);
    }

    @Test
    void findAllByUserId() {

        Mockito.when(repository.findAllByUserId(USER_ID))
                .thenReturn(
                        Arrays.asList(
                                Mockito.mock(TodoList.class),
                                Mockito.mock(TodoList.class),
                                Mockito.mock(TodoList.class)
                        )
                );

        List<TodoList> response = todoListInfrastructure.findAllByUserId(USER_ID);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
        Assertions.assertEquals(3, response.size());
    }

    @Test
    void findById() {

        Mockito.when(repository.findById(ID))
                .thenReturn(Optional.of(todoList));

        Optional<TodoList> response = todoListInfrastructure.findById(ID);

        Assertions.assertTrue(response.isPresent());
        Assertions.assertEquals(ID, response.get().getId());
        Assertions.assertEquals(TITLE, response.get().getTitle());
        Assertions.assertEquals(NOTE, response.get().getNote());
        Assertions.assertEquals(STATUS, response.get().getStatus());
        Assertions.assertEquals(PRIORITY, response.get().getPriority());
        Assertions.assertEquals(END_TIME, response.get().getEndTime());
        Assertions.assertEquals(2, response.get().getTags().size());
        Assertions.assertEquals(2, response.get().getAssignedUsers().size());
        Assertions.assertEquals(2, response.get().getDocuments().size());
    }

    @Test
    void save() {

        Mockito.when(repository.save(todoList))
                .thenReturn(todoList);

        TodoList response = todoListInfrastructure.save(todoList);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(TITLE, response.getTitle());
        Assertions.assertEquals(NOTE, response.getNote());
        Assertions.assertEquals(STATUS, response.getStatus());
        Assertions.assertEquals(PRIORITY, response.getPriority());
        Assertions.assertEquals(END_TIME, response.getEndTime());
        Assertions.assertEquals(2, response.getTags().size());
        Assertions.assertEquals(2, response.getAssignedUsers().size());
        Assertions.assertEquals(2, response.getDocuments().size());
    }

    @Test
    void delete() {

        Mockito.doNothing()
                .when(repository).delete(todoList);

        todoListInfrastructure.delete(todoList);

        Mockito
                .verify(repository, Mockito.times(1))
                .delete(todoList);
    }
}
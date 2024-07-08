package tr.com.hepsiemlak.todolist.application.todolist;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import tr.com.hepsiemlak.todolist.domain.auth.user.User;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.dto.UserGetDto;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.port.UserService;
import tr.com.hepsiemlak.todolist.domain.todolist.*;
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.dto.TodoListCreateDto;
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.dto.TodoListGetDto;
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.dto.TodoListUpdateDto;
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.port.TodoListService;
import tr.com.hepsiemlak.todolist.shared.util.JwtTokenUtil;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class TodoListResourceTest {

    public static final String ID = "ID";

    public static final String USERNAME = "ADMIN";

    public static final String PASSWORD = "PASSWORD";

    public static final String GSM = "05553331111";

    public static final String EMAIL = "test@test.com";

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

    TodoListResource todoListResource;

    TodoListService todoListService;

    UserService userService;

    JwtTokenUtil jwtTokenUtil;

    User user;

    TodoList todoList;

    @BeforeEach
    void setUp() {
        user = new User()
                .setId(ID)
                .setUsername(USERNAME)
                .setPassword(PASSWORD)
                .setActive(true)
                .setName(USERNAME)
                .setSurname(USERNAME)
                .setGsm(GSM)
                .setEmail(EMAIL);
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
        todoListService = Mockito.mock(TodoListService.class);
        userService = Mockito.mock(UserService.class);
        jwtTokenUtil = Mockito.mock(JwtTokenUtil.class);
        todoListResource = new TodoListResource(todoListService, userService, jwtTokenUtil);
    }

    @Test
    void getAllTodoList() {

        UserGetDto userGetDto = UserGetDto.convertToUserGetDtoFromUser(user);

        Mockito.when(jwtTokenUtil.getCurrentUser())
                .thenReturn(Optional.of(USERNAME));

        Mockito.when(userService.getUserByName(USERNAME))
                .thenReturn(userGetDto);

        Mockito.when(todoListService.getAllTodoListByUserId(ID))
                .thenReturn(
                        Arrays.asList(
                                Mockito.mock(TodoListGetDto.class),
                                Mockito.mock(TodoListGetDto.class),
                                Mockito.mock(TodoListGetDto.class)
                        )
                );

        ResponseEntity<List<TodoListGetDto>> response = todoListResource.getAllTodoList();

        Assertions.assertNotNull(response.getBody());
        Assertions.assertFalse(response.getBody().isEmpty());
        Assertions.assertEquals(3, response.getBody().size());
    }

    @Test
    void getTodoListItemById() {

        TodoListGetDto todoListGetDto = TodoListGetDto.convertToTodoListGetDtoFromTodoList(todoList);

        Mockito.when(todoListService.getTodoListById(ID))
                .thenReturn(todoListGetDto);

        ResponseEntity<TodoListGetDto> response = todoListResource.getTodoListItemById(ID);

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(ID, response.getBody().getId());
        Assertions.assertEquals(TITLE, response.getBody().getTitle());
        Assertions.assertEquals(NOTE, response.getBody().getNote());
        Assertions.assertEquals(STATUS, response.getBody().getStatus());
        Assertions.assertEquals(PRIORITY, response.getBody().getPriority());
        Assertions.assertEquals(END_TIME, response.getBody().getEndTime());
        Assertions.assertEquals(2, response.getBody().getTags().size());
        Assertions.assertEquals(2, response.getBody().getAssignedUsers().size());
        Assertions.assertEquals(2, response.getBody().getDocuments().size());
    }

    @Test
    void addTodoListItem() {

        TodoListCreateDto todoListCreateDto = new TodoListCreateDto(
                TITLE,
                NOTE,
                PRIORITY,
                END_TIME,
                TAGS,
                ASSIGNED_USERS,
                DOCUMENTS
        );

        UserGetDto userGetDto = UserGetDto.convertToUserGetDtoFromUser(user);

        Mockito.when(jwtTokenUtil.getCurrentUser())
                .thenReturn(Optional.of(USERNAME));

        Mockito.when(userService.getUserByName(USERNAME))
                .thenReturn(userGetDto);

        Mockito.when(todoListService.addTodoList(todoListCreateDto, ID))
                .thenReturn(ID);

        ResponseEntity<String> response = todoListResource.addTodoListItem(todoListCreateDto);

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(ID, response.getBody());
    }

    @Test
    void updateTodoListItem() {

        TodoListGetDto todoListGetDto = TodoListGetDto.convertToTodoListGetDtoFromTodoList(todoList);

        TodoListUpdateDto todoListUpdateDto = new TodoListUpdateDto(
                ID,
                TITLE,
                NOTE,
                PRIORITY,
                END_TIME,
                TAGS,
                ASSIGNED_USERS,
                DOCUMENTS
        );

        UserGetDto userGetDto = UserGetDto.convertToUserGetDtoFromUser(user);

        Mockito.when(jwtTokenUtil.getCurrentUser())
                .thenReturn(Optional.of(USERNAME));

        Mockito.when(userService.getUserByName(USERNAME))
                .thenReturn(userGetDto);

        Mockito.when(todoListService.updateTodoList(todoListUpdateDto, ID))
                .thenReturn(todoListGetDto);

        ResponseEntity<TodoListGetDto> response = todoListResource.updateTodoListItem(todoListUpdateDto);

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(ID, response.getBody().getId());
        Assertions.assertEquals(TITLE, response.getBody().getTitle());
        Assertions.assertEquals(NOTE, response.getBody().getNote());
        Assertions.assertEquals(STATUS, response.getBody().getStatus());
        Assertions.assertEquals(PRIORITY, response.getBody().getPriority());
        Assertions.assertEquals(END_TIME, response.getBody().getEndTime());
        Assertions.assertEquals(2, response.getBody().getTags().size());
        Assertions.assertEquals(2, response.getBody().getAssignedUsers().size());
        Assertions.assertEquals(2, response.getBody().getDocuments().size());
    }

    @Test
    void deleteTodoListItem() {
        Mockito.doNothing().when(todoListService).delete(ID);
        todoListResource.deleteTodoListItem(ID);
        Mockito.verify(todoListService, Mockito.times(1)).delete(ID);
    }

    @Test
    void doneTodoListItem() {

        TodoListGetDto todoListGetDto = TodoListGetDto.convertToTodoListGetDtoFromTodoList(todoList);

        Mockito.when(todoListService.doneTodoListItem(ID))
                .thenReturn(todoListGetDto);

        ResponseEntity<TodoListGetDto> response = todoListResource.doneTodoListItem(ID);

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(ID, response.getBody().getId());
        Assertions.assertEquals(TITLE, response.getBody().getTitle());
        Assertions.assertEquals(NOTE, response.getBody().getNote());
        Assertions.assertEquals(STATUS, response.getBody().getStatus());
        Assertions.assertEquals(PRIORITY, response.getBody().getPriority());
        Assertions.assertEquals(END_TIME, response.getBody().getEndTime());
        Assertions.assertEquals(2, response.getBody().getTags().size());
        Assertions.assertEquals(2, response.getBody().getAssignedUsers().size());
        Assertions.assertEquals(2, response.getBody().getDocuments().size());
    }
}
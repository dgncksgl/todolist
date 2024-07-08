package tr.com.hepsiemlak.todolist.domain.todolist;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.dto.TodoListItemPriorityGetDto;
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.dto.TodoListCreateDto;
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.dto.TodoListGetDto;
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.dto.TodoListUpdateDto;
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.port.TodoListRepository;
import tr.com.hepsiemlak.todolist.shared.exception.type.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

class TodoListDomainServiceTest {

    public static final String ID = "ID";

    public static final String USER_ID = "USER_ID";

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

    TodoListDomainService todoListDomainService;

    TodoListRepository todoListRepository;

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
        todoListRepository = Mockito.mock(TodoListRepository.class);
        todoListDomainService = new TodoListDomainService(todoListRepository);
    }

    @Test
    void getAllTodoListByUserId() {

        Mockito.when(todoListRepository.findAllByUserId(USER_ID))
                .thenReturn(
                        Arrays.asList(
                                new TodoList(),
                                new TodoList(),
                                new TodoList()
                        )
                );

        List<TodoListGetDto> response = todoListDomainService.getAllTodoListByUserId(USER_ID);

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
        Assertions.assertEquals(3, response.size());
    }

    @Test
    void getTodoListByIdSuccess() {

        Mockito.when(todoListRepository.findById(ID))
                .thenReturn(Optional.of(todoList));

        TodoListGetDto response = todoListDomainService.getTodoListById(ID);

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
    void getTodoListByIdNotFound() {

        Mockito.when(todoListRepository.findById(ID))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> todoListDomainService.getTodoListById(ID));
    }

    @Test
    void addTodoList() {

        TodoListCreateDto todoListCreateDto = new TodoListCreateDto(
                TITLE,
                NOTE,
                PRIORITY,
                END_TIME,
                TAGS,
                ASSIGNED_USERS,
                DOCUMENTS
        );

        Mockito.when(todoListRepository.save(any(TodoList.class)))
                .thenReturn(todoList);

        String response = todoListDomainService.addTodoList(todoListCreateDto, USER_ID);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ID, response);
    }

    @Test
    void updateTodoListSuccess() {

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

        Mockito.when(todoListRepository.findById(ID))
                .thenReturn(Optional.of(todoList));

        Mockito.when(todoListRepository.save(any(TodoList.class)))
                .thenReturn(todoList);

        TodoListGetDto response = todoListDomainService.updateTodoList(todoListUpdateDto, USER_ID);

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
    void updateTodoListNotFound() {

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

        Mockito.when(todoListRepository.findById(ID))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> todoListDomainService.updateTodoList(todoListUpdateDto, USER_ID));
    }

    @Test
    void deleteSuccess() {

        Mockito.when(todoListRepository.findById(ID))
                .thenReturn(Optional.of(todoList));

        Mockito.doNothing().when(todoListRepository).delete(todoList);

        todoListDomainService.delete(ID);

        Mockito
                .verify(todoListRepository, Mockito.times(1))
                .delete(todoList);
    }

    @Test
    void deleteNotFound() {

        Mockito.when(todoListRepository.findById(ID))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> todoListDomainService.delete(ID));
    }

    @Test
    void doneTodoListItemSuccess() {

        todoList.setStatus(TodolistItemStatus.DONE);

        Mockito.when(todoListRepository.findById(ID))
                .thenReturn(Optional.of(todoList));

        Mockito.when(todoListRepository.save(any(TodoList.class)))
                .thenReturn(todoList);

        TodoListGetDto response = todoListDomainService.doneTodoListItem(ID);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(TITLE, response.getTitle());
        Assertions.assertEquals(NOTE, response.getNote());
        Assertions.assertEquals(TodolistItemStatus.DONE, response.getStatus());
        Assertions.assertEquals(PRIORITY, response.getPriority());
        Assertions.assertEquals(END_TIME, response.getEndTime());
        Assertions.assertEquals(2, response.getTags().size());
        Assertions.assertEquals(2, response.getAssignedUsers().size());
        Assertions.assertEquals(2, response.getDocuments().size());
    }

    @Test
    void doneTodoListItemNotFound() {

        Mockito.when(todoListRepository.findById(ID))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> todoListDomainService.doneTodoListItem(ID));
    }

    @Test
    void getTodoListItemPriorityList() {

        List<TodoListItemPriorityGetDto> response = todoListDomainService.getTodoListItemPriorityList();

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
        Assertions.assertEquals(3, response.size());
    }
}
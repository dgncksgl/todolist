package tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.port;

import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.dto.TodoListItemPriorityGetDto;
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.dto.TodoListCreateDto;
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.dto.TodoListGetDto;
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.dto.TodoListUpdateDto;

import java.util.List;

public interface TodoListService {

    List<TodoListGetDto> getAllTodoListByUserId(String userId);

    TodoListGetDto getTodoListById(String todoListId);

    String addTodoList(TodoListCreateDto todoListCreateDto, String userId);

    TodoListGetDto updateTodoList(TodoListUpdateDto todoListUpdateDto, String userId);

    void delete(String todoListId);

    TodoListGetDto doneTodoListItem(String todoListId);

    List<TodoListItemPriorityGetDto> getTodoListItemPriorityList();

}


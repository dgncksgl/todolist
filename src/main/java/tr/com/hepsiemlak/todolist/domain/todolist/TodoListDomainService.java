package tr.com.hepsiemlak.todolist.domain.todolist;

import org.springframework.stereotype.Service;
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.dto.TodoListItemPriorityGetDto;
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.dto.TodoListCreateDto;
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.dto.TodoListGetDto;
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.dto.TodoListUpdateDto;
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.port.TodoListRepository;
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.port.TodoListService;
import tr.com.hepsiemlak.todolist.shared.exception.type.NotFoundException;

import java.util.Arrays;
import java.util.List;

@Service
public class TodoListDomainService implements TodoListService {

    private final TodoListRepository todoListRepository;

    public TodoListDomainService(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    @Override
    public List<TodoListGetDto> getAllTodoListByUserId(String userId) {
        return todoListRepository.findAllByUserId(userId).stream()
                .map(TodoListGetDto::convertToTodoListGetDtoFromTodoList)
                .toList();
    }

    @Override
    public TodoListGetDto getTodoListById(String todoListId) {
        return todoListRepository.findById(todoListId)
                .map(TodoListGetDto::convertToTodoListGetDtoFromTodoList)
                .orElseThrow(() -> new NotFoundException(TodoList.class.getSimpleName()));
    }

    @Override
    public String addTodoList(TodoListCreateDto todoListCreateDto, String userId) {
        return todoListRepository.save(
                        TodoListCreateDto.convertToTodoListFromTodoListCreateDto(todoListCreateDto, userId)
                )
                .getId();
    }

    @Override
    public TodoListGetDto updateTodoList(TodoListUpdateDto todoListUpdateDto, String userId) {

        if (todoListRepository.findById(todoListUpdateDto.id()).isEmpty()) {
            throw new NotFoundException(TodoList.class.getSimpleName());
        }

        return TodoListGetDto.convertToTodoListGetDtoFromTodoList(
                todoListRepository.save(
                        TodoListUpdateDto.convertToTodoListFromTodoListUpdateDto(todoListUpdateDto, userId)
                )
        );
    }

    @Override
    public void delete(String todoListId) {
        TodoList todoList = todoListRepository.findById(todoListId)
                .orElseThrow(() -> new NotFoundException(TodoList.class.getSimpleName()));
        todoListRepository.delete(todoList);
    }

    @Override
    public TodoListGetDto doneTodoListItem(String todoListId) {
        TodoList todoList = todoListRepository.findById(todoListId)
                .orElseThrow(() -> new NotFoundException(TodoList.class.getSimpleName()));
        return TodoListGetDto.convertToTodoListGetDtoFromTodoList(
                todoListRepository.save(todoList.setStatus(TodolistItemStatus.DONE))
        );
    }

    @Override
    public List<TodoListItemPriorityGetDto> getTodoListItemPriorityList() {
        return Arrays.stream(TodoListItemPriority.values())
                .map(priority -> new TodoListItemPriorityGetDto(priority.name(), priority.getLabel()))
                .toList();
    }
}

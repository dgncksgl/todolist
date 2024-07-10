package tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.port;

import tr.com.hepsiemlak.todolist.domain.todolist.TodoList;

import java.util.List;
import java.util.Optional;

public interface TodoListRepository {

    List<TodoList> findAllByUserId(String userId);

    Optional<TodoList> findById(String id);

    TodoList save(TodoList todoList);

    void delete(TodoList todoList);

    void deleteAllByUserId(String userId);


}

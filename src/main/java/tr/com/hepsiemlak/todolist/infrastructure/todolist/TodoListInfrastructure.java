package tr.com.hepsiemlak.todolist.infrastructure.todolist;

import org.springframework.stereotype.Component;
import tr.com.hepsiemlak.todolist.domain.todolist.TodoList;
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.port.TodoListRepository;

import java.util.List;
import java.util.Optional;

@Component
public class TodoListInfrastructure implements TodoListRepository {

    private final TodoListInfrastructureRepository repository;

    public TodoListInfrastructure(TodoListInfrastructureRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TodoList> findAllByUserId(String userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public Optional<TodoList> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public TodoList save(TodoList todoList) {
        return repository.save(todoList);
    }

    @Override
    public void delete(TodoList todoList) {
        repository.delete(todoList);
    }

    @Override
    public void deleteAllByUserId(String userId) {
        repository.deleteAllByUserId(userId);
    }
}

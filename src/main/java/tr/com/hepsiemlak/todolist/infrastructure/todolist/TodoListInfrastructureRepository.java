package tr.com.hepsiemlak.todolist.infrastructure.todolist;

import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;
import tr.com.hepsiemlak.todolist.domain.todolist.TodoList;

import java.util.List;

@Repository
public interface TodoListInfrastructureRepository extends CouchbaseRepository<TodoList, String> {

    List<TodoList> findAllByUserId(String userId);

    void deleteAllByUserId(String userId);
}

package tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.port;


import tr.com.hepsiemlak.todolist.domain.auth.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(String userId);

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndExceptId(String id, String username);

    User save(User user);

    void delete(User user);
}

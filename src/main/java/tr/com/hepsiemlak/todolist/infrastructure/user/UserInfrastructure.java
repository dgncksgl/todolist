package tr.com.hepsiemlak.todolist.infrastructure.user;

import org.springframework.stereotype.Component;
import tr.com.hepsiemlak.todolist.domain.auth.user.User;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.port.UserRepository;

import java.util.List;
import java.util.Optional;

@Component
public class UserInfrastructure implements UserRepository {

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(String userId) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByIdAndUsername(String id, String username) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void delete(User user) {

    }
}

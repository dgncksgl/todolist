package tr.com.hepsiemlak.todolist.infrastructure.auth.user;

import org.springframework.stereotype.Component;
import tr.com.hepsiemlak.todolist.domain.auth.user.User;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.port.UserRepository;

import java.util.List;
import java.util.Optional;

@Component
class UserInfrastructure implements UserRepository {

    private final UserInfrastructureRepository repository;

    public UserInfrastructure(UserInfrastructureRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<User> findById(String userId) {
        return repository.findById(userId);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Optional<User> findByUsernameAndExceptId(String id, String username) {
        return repository.findByUsernameAndExceptId(id, username);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }
}

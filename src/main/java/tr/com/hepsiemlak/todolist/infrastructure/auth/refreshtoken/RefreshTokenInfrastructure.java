package tr.com.hepsiemlak.todolist.infrastructure.auth.refreshtoken;

import org.springframework.stereotype.Component;
import tr.com.hepsiemlak.todolist.domain.auth.refreshtoken.RefreshToken;
import tr.com.hepsiemlak.todolist.domain.auth.refreshtoken.applicationservice.port.RefreshTokenRepository;

import java.util.Optional;

@Component
class RefreshTokenInfrastructure implements RefreshTokenRepository {

    private final RefreshTokenInfrastructureRepository repository;

    public RefreshTokenInfrastructure(RefreshTokenInfrastructureRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<RefreshToken> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return repository.findByToken(token);
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return repository.save(refreshToken);
    }

    @Override
    public void deleteByUserId(String userId) {
        repository.deleteByUserId(userId);
    }
}

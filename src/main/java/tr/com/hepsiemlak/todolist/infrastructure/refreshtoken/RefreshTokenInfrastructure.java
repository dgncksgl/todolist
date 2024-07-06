package tr.com.hepsiemlak.todolist.infrastructure.refreshtoken;

import org.springframework.stereotype.Component;
import tr.com.hepsiemlak.todolist.domain.auth.refreshtoken.RefreshToken;
import tr.com.hepsiemlak.todolist.domain.auth.refreshtoken.applicationservice.port.RefreshTokenRepository;

import java.util.Optional;

@Component
public class RefreshTokenInfrastructure implements RefreshTokenRepository {
    @Override
    public Optional<RefreshToken> findById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<RefreshToken> findByRefreshToken(String token) {
        return Optional.empty();
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return null;
    }

    @Override
    public void deleteByUserId(String userId) {

    }
}

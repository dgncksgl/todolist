package tr.com.hepsiemlak.todolist.domain.auth.refreshtoken.applicationservice.port;

import tr.com.hepsiemlak.todolist.domain.auth.refreshtoken.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {

    Optional<RefreshToken> findById(String id);

    Optional<RefreshToken> findByRefreshToken(String token);

    RefreshToken save(RefreshToken refreshToken);

    void deleteByUserId(String userId);
}

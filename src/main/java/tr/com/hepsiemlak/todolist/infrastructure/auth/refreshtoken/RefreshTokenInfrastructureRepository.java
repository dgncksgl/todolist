package tr.com.hepsiemlak.todolist.infrastructure.auth.refreshtoken;

import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;
import tr.com.hepsiemlak.todolist.domain.auth.refreshtoken.RefreshToken;

import java.util.Optional;

@Repository
public interface RefreshTokenInfrastructureRepository extends CouchbaseRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUserId(String userId);
}

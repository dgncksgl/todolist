package tr.com.hepsiemlak.todolist.infrastructure.auth.user;

import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;
import tr.com.hepsiemlak.todolist.domain.auth.user.User;

import java.util.Optional;

@Repository
public interface UserInfrastructureRepository extends CouchbaseRepository<User, String> {

    Optional<User> findByUsername(String username);

    @Query("#{#n1ql.selectEntity} WHERE META().id != $1 AND username = $2")
    Optional<User> findByUsernameAndExceptId(String id, String username);
}

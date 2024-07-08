package tr.com.hepsiemlak.todolist.domain.auth.refreshtoken;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.time.Instant;
import java.util.UUID;

@Document
public class RefreshToken {

    @Id
    private String id = UUID.randomUUID().toString();

    @NotNull
    @Field("token")
    private String token;

    @NotNull
    @Field("expirationDate")
    private Instant expirationDate;

    @NotNull
    @Field("userId")
    private String userId;

    public String getId() {
        return id;
    }

    public RefreshToken setId(String id) {
        this.id = id;
        return this;
    }

    public String getToken() {
        return token;
    }

    public RefreshToken setToken(String token) {
        this.token = token;
        return this;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public RefreshToken setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public RefreshToken setUserId(String userId) {
        this.userId = userId;
        return this;
    }
}

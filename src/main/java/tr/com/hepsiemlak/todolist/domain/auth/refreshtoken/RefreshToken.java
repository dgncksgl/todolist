package tr.com.hepsiemlak.todolist.domain.auth.refreshtoken;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;

public class RefreshToken {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(name = "ID", length = 36, nullable = false, updatable = false)
    private String id;

    @Column(name = "TOKEN", nullable = false)
    private String token;

    @Column(name = "EXPIRATION_DATE", nullable = false)
    private Instant expirationDate;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    public RefreshToken(String token, Instant expirationDate, String userId) {
        this.token = token;
        this.expirationDate = expirationDate;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public String getUserId() {
        return userId;
    }
}

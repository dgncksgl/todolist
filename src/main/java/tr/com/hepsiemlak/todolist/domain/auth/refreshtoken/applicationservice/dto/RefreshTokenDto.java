package tr.com.hepsiemlak.todolist.domain.auth.refreshtoken.applicationservice.dto;

import tr.com.hepsiemlak.todolist.domain.auth.refreshtoken.RefreshToken;

import java.time.Instant;
import java.util.UUID;

public class RefreshTokenDto {

    private String id;

    private String token;

    private Instant expirationDate;

    private String userId;

    private RefreshTokenDto() {
    }

    public String getId() {
        return id;
    }

    public RefreshTokenDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getToken() {
        return token;
    }

    public RefreshTokenDto setToken(String token) {
        this.token = token;
        return this;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public RefreshTokenDto setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public RefreshTokenDto setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public static RefreshTokenDto convertToDtoFromRefreshToken(RefreshToken refreshToken) {
        return new RefreshTokenDto()
                .setId(refreshToken.getId())
                .setToken(refreshToken.getToken())
                .setExpirationDate(refreshToken.getExpirationDate())
                .setUserId(refreshToken.getUserId());
    }

    public static RefreshToken convertToRefreshToken(String userId, Long expirationTime) {
        return new RefreshToken()
                .setToken(UUID.randomUUID().toString())
                .setExpirationDate(Instant.now().plusMillis(expirationTime))
                .setUserId(userId);
    }
}

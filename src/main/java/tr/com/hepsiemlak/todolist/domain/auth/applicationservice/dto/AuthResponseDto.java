package tr.com.hepsiemlak.todolist.domain.auth.applicationservice.dto;

import tr.com.hepsiemlak.todolist.domain.auth.user.UserAuthModel;

public class AuthResponseDto {

    private String username;

    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private AuthResponseDto() {
    }

    public String getUsername() {
        return username;
    }

    public AuthResponseDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public AuthResponseDto setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public AuthResponseDto setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public String getTokenType() {
        return tokenType;
    }

    public AuthResponseDto setTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public static AuthResponseDto convertToAuthDto(UserAuthModel userDetails,
                                                   String accessToken,
                                                   String refreshToken) {
        return new AuthResponseDto()
                .setUsername(userDetails.getUsername())
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken)
                .setTokenType("Bearer");
    }
}

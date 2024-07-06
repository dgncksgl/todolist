package tr.com.hepsiemlak.todolist.domain.auth.applicationservice.dto;

public record AuthRefreshTokenResponseDto(
        String accessToken,
        String refreshToken,
        String tokenType
) {

    public static AuthRefreshTokenResponseDto convertToAuthRefreshTokenResponseDto(String accessToken, String refreshToken) {
        return new AuthRefreshTokenResponseDto(
                accessToken,
                refreshToken,
                "Bearer"
        );
    }
}

package tr.com.hepsiemlak.todolist.application.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import tr.com.hepsiemlak.todolist.domain.auth.applicationservice.dto.AuthRefreshTokenRequestDto;
import tr.com.hepsiemlak.todolist.domain.auth.applicationservice.dto.AuthRefreshTokenResponseDto;
import tr.com.hepsiemlak.todolist.domain.auth.applicationservice.dto.AuthRequestDto;
import tr.com.hepsiemlak.todolist.domain.auth.applicationservice.dto.AuthResponseDto;
import tr.com.hepsiemlak.todolist.domain.auth.applicationservice.port.AuthService;
import tr.com.hepsiemlak.todolist.domain.auth.user.User;
import tr.com.hepsiemlak.todolist.domain.auth.user.UserAuthModel;

class AuthResourceTest {

    AuthResource authResource;

    AuthService authService;

    @BeforeEach
    void setUp() {
        authService = Mockito.mock(AuthService.class);
        authResource = new AuthResource(authService);
    }

    @Test
    void signIn() {

        AuthRequestDto dto = new AuthRequestDto("username", "password");

        User user = new User()
                .setId("id")
                .setUsername("username")
                .setPassword("password")
                .setActive(true);

        AuthResponseDto responseDto = AuthResponseDto.convertToAuthDto(
                new UserAuthModel(user),
                "accessToken",
                "refreshToken"
        );

        Mockito.when(authService.authenticateAndGenerateToken(dto))
                .thenReturn(
                        responseDto
                );

        ResponseEntity<AuthResponseDto> response = authResource.singIn(dto);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("username", response.getBody().getUsername());
        Assertions.assertEquals("accessToken", response.getBody().getAccessToken());
        Assertions.assertEquals("refreshToken", response.getBody().getRefreshToken());
        Assertions.assertEquals("Bearer", response.getBody().getTokenType());
    }

    @Test
    void regenerateAccessTokenByRefreshToken() {

        AuthRefreshTokenRequestDto dto = new AuthRefreshTokenRequestDto("refreshToken");

        AuthRefreshTokenResponseDto responseDto = AuthRefreshTokenResponseDto.convertToAuthRefreshTokenResponseDto(
                "accessToken",
                "refreshToken"
        );

        Mockito.when(authService.regenerateAccessTokenByRefreshToken(dto.refreshToken()))
                .thenReturn(
                        responseDto
                );

        ResponseEntity<AuthRefreshTokenResponseDto> response = authResource.regenerateAccessTokenByRefreshToken(dto);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("accessToken", response.getBody().accessToken());
        Assertions.assertEquals("refreshToken", response.getBody().refreshToken());
        Assertions.assertEquals("Bearer", response.getBody().tokenType());
    }
}
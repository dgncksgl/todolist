package tr.com.hepsiemlak.todolist.domain.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.util.ReflectionTestUtils;
import tr.com.hepsiemlak.todolist.domain.auth.applicationservice.dto.AuthRefreshTokenResponseDto;
import tr.com.hepsiemlak.todolist.domain.auth.applicationservice.dto.AuthRequestDto;
import tr.com.hepsiemlak.todolist.domain.auth.applicationservice.dto.AuthResponseDto;
import tr.com.hepsiemlak.todolist.domain.auth.refreshtoken.RefreshToken;
import tr.com.hepsiemlak.todolist.domain.auth.refreshtoken.applicationservice.port.RefreshTokenRepository;
import tr.com.hepsiemlak.todolist.domain.auth.user.User;
import tr.com.hepsiemlak.todolist.domain.auth.user.UserAuthModel;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.port.UserRepository;
import tr.com.hepsiemlak.todolist.shared.exception.type.NotActiveException;
import tr.com.hepsiemlak.todolist.shared.exception.type.NotFoundException;
import tr.com.hepsiemlak.todolist.shared.exception.type.TokenRefreshException;
import tr.com.hepsiemlak.todolist.shared.util.JwtTokenUtil;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;

class AuthDomainServiceTest {

    public static final String ID = "ID";

    public static final String USER_ID = "USER_ID";

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    public static final String GSM = "05553331111";

    public static final String EMAIL = "test@test.com";

    public static final String ACCESS_TOKEN = "accessToken";

    public static final String REFRESH_TOKEN = "refreshToken";

    AuthDomainService authDomainService;

    AuthenticationManager authenticationManager;

    JwtTokenUtil jwtTokenUtil;

    RefreshTokenRepository refreshTokenRepository;

    UserRepository userRepository;

    AuthRequestDto authRequestDto;

    User user;

    @BeforeEach
    void setUp() {

        authRequestDto = new AuthRequestDto(USERNAME, PASSWORD);
        user = new User()
                .setId(ID)
                .setUsername(USERNAME)
                .setPassword(PASSWORD)
                .setActive(true)
                .setName(USERNAME)
                .setSurname(USERNAME)
                .setGsm(GSM)
                .setEmail(EMAIL);

        authenticationManager = Mockito.mock(AuthenticationManager.class);
        jwtTokenUtil = Mockito.mock(JwtTokenUtil.class);
        refreshTokenRepository = Mockito.mock(RefreshTokenRepository.class);
        userRepository = Mockito.mock(UserRepository.class);

        authDomainService = new AuthDomainService(
                authenticationManager,
                jwtTokenUtil,
                refreshTokenRepository,
                userRepository
        );

        ReflectionTestUtils.setField(
                authDomainService,
                "refreshExpirationTime",
                720000000L
        );
    }

    @Test
    void authenticateAndGenerateTokenSuccess() {

        UserAuthModel userAuthModel = new UserAuthModel(user);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userAuthModel, null);

        RefreshToken refreshToken = new RefreshToken()
                .setId(ID)
                .setToken(REFRESH_TOKEN)
                .setExpirationDate(Instant.now())
                .setUserId(USER_ID);

        Mockito
                .when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(token);

        Mockito.when(refreshTokenRepository.save(isA(RefreshToken.class)))
                .thenReturn(refreshToken);

        Mockito
                .when(jwtTokenUtil.generateToken(isA(String.class)))
                .thenReturn(ACCESS_TOKEN);

        AuthResponseDto response = authDomainService.authenticateAndGenerateToken(authRequestDto);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(USERNAME, response.getUsername());
        Assertions.assertEquals(ACCESS_TOKEN, response.getAccessToken());
        Assertions.assertEquals(REFRESH_TOKEN, response.getRefreshToken());
        Assertions.assertEquals("Bearer", response.getTokenType());
    }

    @Test
    void authenticateAndGenerateTokenBadCredentials() {

        Mockito
                .when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad Credentials"));

        Assertions.assertThrows(BadCredentialsException.class,
                () -> authDomainService.authenticateAndGenerateToken(authRequestDto));
    }

    @Test
    void authenticateAndGenerateTokenUserNotEnabled() {

        UserAuthModel userAuthModel = new UserAuthModel(user.setActive(Boolean.FALSE));

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userAuthModel, null);

        Mockito
                .when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(token);

        Assertions.assertThrows(NotActiveException.class,
                () -> authDomainService.authenticateAndGenerateToken(authRequestDto));
    }

    @Test
    void regenerateAccessTokenByRefreshTokenSuccess() {

        RefreshToken refreshToken = new RefreshToken()
                .setId(ID)
                .setToken(REFRESH_TOKEN)
                .setExpirationDate(Instant.now().plusMillis(10000000))
                .setUserId(USER_ID);

        Mockito.when(refreshTokenRepository.findByToken(REFRESH_TOKEN))
                .thenReturn(Optional.of(refreshToken));

        Mockito.when(userRepository.findById(USER_ID))
                .thenReturn(Optional.of(user));

        Mockito
                .when(jwtTokenUtil.generateToken(isA(String.class)))
                .thenReturn(ACCESS_TOKEN);

        AuthRefreshTokenResponseDto response = authDomainService.regenerateAccessTokenByRefreshToken(REFRESH_TOKEN);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ACCESS_TOKEN, response.accessToken());
        Assertions.assertEquals(REFRESH_TOKEN, response.refreshToken());
        Assertions.assertEquals("Bearer", response.tokenType());
    }

    @Test
    void regenerateAccessTokenByRefreshTokenNotFoundRefreshToken() {

        Mockito.when(refreshTokenRepository.findByToken(REFRESH_TOKEN))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> authDomainService.regenerateAccessTokenByRefreshToken(REFRESH_TOKEN));

    }

    @Test
    void regenerateAccessTokenByRefreshTokenExpiredDate() {

        RefreshToken refreshToken = new RefreshToken()
                .setId(ID)
                .setToken(REFRESH_TOKEN)
                .setExpirationDate(Instant.now().minusMillis(10000000))
                .setUserId(USER_ID);

        Mockito.when(refreshTokenRepository.findByToken(REFRESH_TOKEN))
                .thenReturn(Optional.of(refreshToken));

        Mockito.doNothing().when(refreshTokenRepository).deleteByUserId(USER_ID);

        Assertions.assertThrows(TokenRefreshException.class,
                () -> authDomainService.regenerateAccessTokenByRefreshToken(REFRESH_TOKEN));

    }

    @Test
    void regenerateAccessTokenByRefreshTokenUserNotFound() {

        RefreshToken refreshToken = new RefreshToken()
                .setId(ID)
                .setToken(REFRESH_TOKEN)
                .setExpirationDate(Instant.now().plusMillis(10000000))
                .setUserId(USER_ID);

        Mockito.when(refreshTokenRepository.findByToken(REFRESH_TOKEN))
                .thenReturn(Optional.of(refreshToken));

        Mockito.when(userRepository.findById(USER_ID))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> authDomainService.regenerateAccessTokenByRefreshToken(REFRESH_TOKEN));

    }
}
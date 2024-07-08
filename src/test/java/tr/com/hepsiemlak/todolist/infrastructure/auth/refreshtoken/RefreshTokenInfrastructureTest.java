package tr.com.hepsiemlak.todolist.infrastructure.auth.refreshtoken;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tr.com.hepsiemlak.todolist.domain.auth.refreshtoken.RefreshToken;

import java.time.Instant;
import java.util.Optional;

class RefreshTokenInfrastructureTest {

    public static final String ID = "ID";

    public static final String TOKEN = "TOKEN";

    public static final Instant EXPIRATION_DATE = Instant.now();

    public static final String USER_ID = "USERID";

    RefreshTokenInfrastructure refreshTokenInfrastructure;

    RefreshTokenInfrastructureRepository repository;

    RefreshToken refreshToken;

    @BeforeEach
    void setUp() {
        refreshToken = new RefreshToken()
                .setId(ID)
                .setToken(TOKEN)
                .setExpirationDate(EXPIRATION_DATE)
                .setUserId(USER_ID);
        repository = Mockito.mock(RefreshTokenInfrastructureRepository.class);
        refreshTokenInfrastructure = new RefreshTokenInfrastructure(repository);
    }

    @Test
    void findById() {

        Mockito.when(repository.findById(ID))
                .thenReturn(Optional.of(refreshToken));

        Optional<RefreshToken> response = refreshTokenInfrastructure.findById(ID);

        Assertions.assertTrue(response.isPresent());
        Assertions.assertEquals(ID, response.get().getId());
        Assertions.assertEquals(TOKEN, response.get().getToken());
        Assertions.assertEquals(EXPIRATION_DATE, response.get().getExpirationDate());
        Assertions.assertEquals(USER_ID, response.get().getUserId());
    }

    @Test
    void findByToken() {

        Mockito.when(repository.findByToken(TOKEN))
                .thenReturn(Optional.of(refreshToken));

        Optional<RefreshToken> response = refreshTokenInfrastructure.findByToken(TOKEN);

        Assertions.assertTrue(response.isPresent());
        Assertions.assertEquals(ID, response.get().getId());
        Assertions.assertEquals(TOKEN, response.get().getToken());
        Assertions.assertEquals(EXPIRATION_DATE, response.get().getExpirationDate());
        Assertions.assertEquals(USER_ID, response.get().getUserId());
    }

    @Test
    void save() {

        Mockito.when(repository.save(refreshToken))
                .thenReturn(refreshToken);

        RefreshToken response = refreshTokenInfrastructure.save(refreshToken);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(TOKEN, response.getToken());
        Assertions.assertEquals(EXPIRATION_DATE, response.getExpirationDate());
        Assertions.assertEquals(USER_ID, response.getUserId());
    }

    @Test
    void deleteByUserId() {

        Mockito.doNothing()
                .when(repository).deleteByUserId(USER_ID);

        refreshTokenInfrastructure.deleteByUserId(USER_ID);

        Mockito
                .verify(repository, Mockito.times(1))
                .deleteByUserId(USER_ID);
    }
}
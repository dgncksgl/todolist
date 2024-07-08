package tr.com.hepsiemlak.todolist.infrastructure.auth.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tr.com.hepsiemlak.todolist.domain.auth.user.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class UserInfrastructureTest {

    public static final String ID = "ID";

    public static final String USERNAME = "ADMIN";

    public static final String PASSWORD = "PASSWORD";

    public static final String GSM = "05553331111";

    public static final String EMAIL = "test@test.com";

    UserInfrastructure userInfrastructure;

    UserInfrastructureRepository repository;

    User user;

    @BeforeEach
    void setUp() {
        user = new User()
                .setId(ID)
                .setUsername(USERNAME)
                .setPassword(PASSWORD)
                .setActive(true)
                .setName(USERNAME)
                .setSurname(USERNAME)
                .setGsm(GSM)
                .setEmail(EMAIL);
        repository = Mockito.mock(UserInfrastructureRepository.class);
        userInfrastructure = new UserInfrastructure(repository);
    }

    @Test
    void findAll() {

        Mockito.when(repository.findAll())
                .thenReturn(
                        Arrays.asList(
                                Mockito.mock(User.class),
                                Mockito.mock(User.class),
                                Mockito.mock(User.class)
                        )
                );

        List<User> response = userInfrastructure.findAll();

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
        Assertions.assertEquals(3, response.size());
    }

    @Test
    void findById() {

        Mockito.when(repository.findById(ID))
                .thenReturn(Optional.of(user));

        Optional<User> response = userInfrastructure.findById(ID);

        Assertions.assertTrue(response.isPresent());
        Assertions.assertEquals(ID, response.get().getId());
        Assertions.assertEquals(USERNAME, response.get().getUsername());
        Assertions.assertEquals(PASSWORD, response.get().getPassword());
        Assertions.assertTrue(response.get().getActive());
        Assertions.assertEquals(USERNAME, response.get().getName());
        Assertions.assertEquals(USERNAME, response.get().getSurname());
        Assertions.assertEquals(GSM, response.get().getGsm());
        Assertions.assertEquals(EMAIL, response.get().getEmail());
    }

    @Test
    void findByUsername() {

        Mockito.when(repository.findByUsername(ID))
                .thenReturn(Optional.of(user));

        Optional<User> response = userInfrastructure.findByUsername(ID);

        Assertions.assertTrue(response.isPresent());
        Assertions.assertEquals(ID, response.get().getId());
        Assertions.assertEquals(USERNAME, response.get().getUsername());
        Assertions.assertEquals(PASSWORD, response.get().getPassword());
        Assertions.assertTrue(response.get().getActive());
        Assertions.assertEquals(USERNAME, response.get().getName());
        Assertions.assertEquals(USERNAME, response.get().getSurname());
        Assertions.assertEquals(GSM, response.get().getGsm());
        Assertions.assertEquals(EMAIL, response.get().getEmail());
    }

    @Test
    void findByUsernameAndExceptId() {

        Mockito.when(repository.findByUsernameAndExceptId(ID, USERNAME))
                .thenReturn(Optional.of(user));

        Optional<User> response = userInfrastructure.findByUsernameAndExceptId(ID, USERNAME);

        Assertions.assertTrue(response.isPresent());
        Assertions.assertEquals(ID, response.get().getId());
        Assertions.assertEquals(USERNAME, response.get().getUsername());
        Assertions.assertEquals(PASSWORD, response.get().getPassword());
        Assertions.assertTrue(response.get().getActive());
        Assertions.assertEquals(USERNAME, response.get().getName());
        Assertions.assertEquals(USERNAME, response.get().getSurname());
        Assertions.assertEquals(GSM, response.get().getGsm());
        Assertions.assertEquals(EMAIL, response.get().getEmail());
    }

    @Test
    void save() {

        Mockito.when(repository.save(user))
                .thenReturn(user);

        User response = userInfrastructure.save(user);

        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(USERNAME, response.getUsername());
        Assertions.assertEquals(PASSWORD, response.getPassword());
        Assertions.assertTrue(response.getActive());
        Assertions.assertEquals(USERNAME, response.getName());
        Assertions.assertEquals(USERNAME, response.getSurname());
        Assertions.assertEquals(GSM, response.getGsm());
        Assertions.assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void delete() {

        Mockito.doNothing()
                .when(repository).delete(user);

        userInfrastructure.delete(user);

        Mockito
                .verify(repository, Mockito.times(1))
                .delete(user);
    }
}
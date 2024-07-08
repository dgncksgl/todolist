package tr.com.hepsiemlak.todolist.domain.auth.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.dto.UserCreateDto;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.dto.UserGetDto;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.dto.UserUpdateDto;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.port.UserRepository;
import tr.com.hepsiemlak.todolist.shared.exception.type.AlreadyExistException;
import tr.com.hepsiemlak.todolist.shared.exception.type.NotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

class UserDomainServiceTest {

    public static final String ID = "ID";

    public static final String USERNAME = "ADMIN";

    public static final String PASSWORD = "PASSWORD";

    public static final String GSM = "05553331111";

    public static final String EMAIL = "test@test.com";

    UserDomainService userDomainService;

    UserRepository userRepository;

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
        userRepository = Mockito.mock(UserRepository.class);
        userDomainService = new UserDomainService(userRepository);
    }

    @Test
    void getAllUserList() {

        Mockito.when(userRepository.findAll())
                .thenReturn(
                        Arrays.asList(
                                new User(),
                                new User(),
                                new User()
                        )
                );

        List<UserGetDto> response = userDomainService.getAllUserList();

        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
        Assertions.assertEquals(3, response.size());
    }

    @Test
    void getUserByIdSuccess() {

        Mockito.when(userRepository.findById(ID))
                .thenReturn(Optional.of(user));

        UserGetDto response = userDomainService.getUserById(ID);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(USERNAME, response.getUsername());
        Assertions.assertEquals(PASSWORD, response.getPassword());
        Assertions.assertEquals(Boolean.TRUE, response.getActive());
        Assertions.assertEquals(USERNAME, response.getName());
        Assertions.assertEquals(USERNAME, response.getSurname());
        Assertions.assertEquals(GSM, response.getGsm());
        Assertions.assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void getUserByIdNotFoundUser() {

        Mockito.when(userRepository.findById(ID))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> userDomainService.getUserById(ID));
    }

    @Test
    void getUserByNameSuccess() {

        Mockito.when(userRepository.findByUsername(USERNAME))
                .thenReturn(Optional.of(user));

        UserGetDto response = userDomainService.getUserByName(USERNAME);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(USERNAME, response.getUsername());
        Assertions.assertEquals(PASSWORD, response.getPassword());
        Assertions.assertEquals(Boolean.TRUE, response.getActive());
        Assertions.assertEquals(USERNAME, response.getName());
        Assertions.assertEquals(USERNAME, response.getSurname());
        Assertions.assertEquals(GSM, response.getGsm());
        Assertions.assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void getUserByNameNotFoundUser() {

        Mockito.when(userRepository.findByUsername(USERNAME))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> userDomainService.getUserByName(ID));
    }

    @Test
    void addAdminUser() {

        UserCreateDto userCreateDto = new UserCreateDto(
                USERNAME,
                PASSWORD,
                Boolean.TRUE,
                USERNAME,
                USERNAME,
                GSM,
                EMAIL
        );

        Mockito.when(userRepository.findByUsername(USERNAME))
                .thenReturn(Optional.empty());

        Mockito.when(userRepository.save(any(User.class)))
                .thenReturn(user);

        userDomainService.addAdminUser(userCreateDto);

        Mockito
                .verify(userRepository, Mockito.times(1))
                .save(any(User.class));
    }

    @Test
    void addUserSuccess() {

        UserCreateDto userCreateDto = new UserCreateDto(
                USERNAME,
                PASSWORD,
                Boolean.TRUE,
                USERNAME,
                USERNAME,
                GSM,
                EMAIL
        );

        Mockito.when(userRepository.findByUsername(USERNAME))
                .thenReturn(Optional.empty());

        Mockito.when(userRepository.save(any(User.class)))
                .thenReturn(user);

        String response = userDomainService.addUser(userCreateDto);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ID, response);
    }

    @Test
    void addUserAlreadyExist() {

        UserCreateDto userCreateDto = new UserCreateDto(
                USERNAME,
                PASSWORD,
                Boolean.TRUE,
                USERNAME,
                USERNAME,
                GSM,
                EMAIL
        );

        Mockito.when(userRepository.findByUsername(USERNAME))
                .thenReturn(Optional.of(user));

        Assertions.assertThrows(AlreadyExistException.class,
                () -> userDomainService.addUser(userCreateDto));
    }

    @Test
    void updateUserSuccess() {

        UserUpdateDto userUpdateDto = new UserUpdateDto(
                ID,
                USERNAME,
                PASSWORD,
                Boolean.TRUE,
                USERNAME,
                USERNAME,
                GSM,
                EMAIL
        );

        Mockito.when(userRepository.findById(ID))
                .thenReturn(Optional.of(user));

        Mockito.when(userRepository.findByUsernameAndExceptId(ID, USERNAME))
                .thenReturn(Optional.empty());

        Mockito.when(userRepository.save(any(User.class)))
                .thenReturn(user);

        UserGetDto response = userDomainService.updateUser(userUpdateDto);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(USERNAME, response.getUsername());
        Assertions.assertEquals(PASSWORD, response.getPassword());
        Assertions.assertEquals(Boolean.TRUE, response.getActive());
        Assertions.assertEquals(USERNAME, response.getName());
        Assertions.assertEquals(USERNAME, response.getSurname());
        Assertions.assertEquals(GSM, response.getGsm());
        Assertions.assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void updateUserNotFound() {

        UserUpdateDto userUpdateDto = new UserUpdateDto(
                ID,
                USERNAME,
                PASSWORD,
                Boolean.TRUE,
                USERNAME,
                USERNAME,
                GSM,
                EMAIL
        );

        Mockito.when(userRepository.findById(ID))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> userDomainService.updateUser(userUpdateDto));
    }

    @Test
    void updateUserAlreadyExist() {

        UserUpdateDto userUpdateDto = new UserUpdateDto(
                ID,
                USERNAME,
                PASSWORD,
                Boolean.TRUE,
                USERNAME,
                USERNAME,
                GSM,
                EMAIL
        );

        Mockito.when(userRepository.findById(ID))
                .thenReturn(Optional.of(user));

        Mockito.when(userRepository.findByUsernameAndExceptId(ID, USERNAME))
                .thenReturn(Optional.of(user));

        Assertions.assertThrows(AlreadyExistException.class,
                () -> userDomainService.updateUser(userUpdateDto));
    }

    @Test
    void deleteSuccess() {

        Mockito.when(userRepository.findById(ID))
                .thenReturn(Optional.of(user));

        Mockito.doNothing().when(userRepository).delete(user);

        userDomainService.delete(ID);

        Mockito
                .verify(userRepository, Mockito.times(1))
                .delete(user);
    }

    @Test
    void deleteNotFound() {

        Mockito.when(userRepository.findById(ID))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> userDomainService.delete(ID));
    }

    @Test
    void loadUserByUsernameSuccess() {

        Mockito.when(userRepository.findByUsername(USERNAME))
                .thenReturn(Optional.of(user));

        UserDetails response = userDomainService.loadUserByUsername(USERNAME);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(USERNAME, response.getUsername());
        Assertions.assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void loadUserByUsernameNotFound() {

        Mockito.when(userRepository.findByUsername(USERNAME))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(InternalAuthenticationServiceException.class,
                () -> userDomainService.loadUserByUsername(USERNAME));
    }
}
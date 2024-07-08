package tr.com.hepsiemlak.todolist.application.auth.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import tr.com.hepsiemlak.todolist.domain.auth.user.User;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.dto.UserCreateDto;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.dto.UserGetDto;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.dto.UserUpdateDto;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.port.UserService;

import java.util.Arrays;
import java.util.List;

class UserResourceTest {

    public static final String ID = "ID";

    public static final String USERNAME = "ADMIN";

    public static final String PASSWORD = "PASSWORD";

    public static final String GSM = "05553331111";

    public static final String EMAIL = "test@test.com";

    UserResource userResource;

    UserService userService;

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
        userService = Mockito.mock(UserService.class);
        userResource = new UserResource(userService);
    }

    @Test
    void getAllUserList() {

        Mockito.when(userService.getAllUserList())
                .thenReturn(
                        Arrays.asList(
                                Mockito.mock(UserGetDto.class),
                                Mockito.mock(UserGetDto.class),
                                Mockito.mock(UserGetDto.class)
                        )
                );

        ResponseEntity<List<UserGetDto>> response = userResource.getAllUserList();

        Assertions.assertNotNull(response.getBody());
        Assertions.assertFalse(response.getBody().isEmpty());
        Assertions.assertEquals(3, response.getBody().size());
    }

    @Test
    void getUserById() {

        UserGetDto userGetDto = UserGetDto.convertToUserGetDtoFromUser(user);

        Mockito.when(userService.getUserById(ID))
                .thenReturn(userGetDto);

        ResponseEntity<UserGetDto> response = userResource.getUserById(ID);

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(ID, response.getBody().getId());
        Assertions.assertEquals(USERNAME, response.getBody().getUsername());
        Assertions.assertEquals(PASSWORD, response.getBody().getPassword());
        Assertions.assertEquals(Boolean.TRUE, response.getBody().getActive());
        Assertions.assertEquals(USERNAME, response.getBody().getName());
        Assertions.assertEquals(USERNAME, response.getBody().getSurname());
        Assertions.assertEquals(GSM, response.getBody().getGsm());
        Assertions.assertEquals(EMAIL, response.getBody().getEmail());
    }

    @Test
    void addUser() {

        UserCreateDto userCreateDto = new UserCreateDto(
                USERNAME,
                PASSWORD,
                Boolean.TRUE,
                USERNAME,
                USERNAME,
                GSM,
                EMAIL
        );

        Mockito.when(userService.addUser(userCreateDto))
                .thenReturn(ID);

        ResponseEntity<String> response = userResource.addUser(userCreateDto);

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(ID, response.getBody());
    }

    @Test
    void updateUser() {

        UserGetDto userGetDto = UserGetDto.convertToUserGetDtoFromUser(user);

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

        Mockito.when(userService.updateUser(userUpdateDto))
                .thenReturn(userGetDto);

        ResponseEntity<UserGetDto> response = userResource.updateUser(userUpdateDto);

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(ID, response.getBody().getId());
        Assertions.assertEquals(USERNAME, response.getBody().getUsername());
        Assertions.assertEquals(PASSWORD, response.getBody().getPassword());
        Assertions.assertEquals(Boolean.TRUE, response.getBody().getActive());
        Assertions.assertEquals(USERNAME, response.getBody().getName());
        Assertions.assertEquals(USERNAME, response.getBody().getSurname());
        Assertions.assertEquals(GSM, response.getBody().getGsm());
        Assertions.assertEquals(EMAIL, response.getBody().getEmail());
    }

    @Test
    void deleteUser() {
        Mockito.doNothing().when(userService).delete(ID);
        userResource.deleteUser(ID);
        Mockito.verify(userService, Mockito.times(1)).delete(ID);
    }
}
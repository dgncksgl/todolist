package tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tr.com.hepsiemlak.todolist.domain.auth.user.User;

public record UserUpdateDto(
        @NotNull(message = "id should not be null")
        @NotBlank(message = "id should not be blank")
        String id,
        @NotNull(message = "username should not be null")
        @NotBlank(message = "username should not be blank")
        @Size(min = 2, message = "username should be min 2")
        @Size(max = 50, message = "username should be max 50")
        String username,
        @NotNull(message = "password should not be null")
        @NotBlank(message = "password should not be blank")
        @Size(min = 2, message = "password should be min 2")
        String password,
        @NotNull(message = "active should not be null")
        Boolean active,
        @NotNull(message = "name should not be null")
        @NotBlank(message = "name should not be blank")
        @Size(min = 2, message = "name should be min 2")
        @Size(max = 100, message = "name should be max 50")
        String name,
        @NotNull(message = "surname should not be null")
        @NotBlank(message = "surname should not be blank")
        @Size(min = 2, message = "surname should be min 2")
        @Size(max = 100, message = "surname should be max 50")
        String surname,
        String gsm,
        String email
) {

    public static User convertToUserFromUserUpdateDto(UserUpdateDto userUpdateDto) {
        return new User()
                .setId(userUpdateDto.id())
                .setUsername(userUpdateDto.username())
                .setPassword(new BCryptPasswordEncoder().encode(userUpdateDto.password()))
                .setActive(userUpdateDto.active())
                .setName(userUpdateDto.name())
                .setSurname(userUpdateDto.surname())
                .setGsm(userUpdateDto.gsm())
                .setEmail(userUpdateDto.email());
    }
}

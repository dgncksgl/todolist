package tr.com.hepsiemlak.todolist.domain.auth.applicationservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthRequestDto(
        @NotNull(message = "username should not be null")
        @NotBlank(message = "username should not be blank")
        String username,
        @NotNull(message = "password should not be null")
        @NotBlank(message = "password should not be blank")
        String password
) {
}

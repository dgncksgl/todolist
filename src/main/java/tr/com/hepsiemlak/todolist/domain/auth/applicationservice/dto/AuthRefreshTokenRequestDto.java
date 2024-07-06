package tr.com.hepsiemlak.todolist.domain.auth.applicationservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthRefreshTokenRequestDto(
        @NotNull(message = "refreshToken should not be null")
        @NotBlank(message = "refreshToken should not be blank")
        String refreshToken
) {

}

package tr.com.hepsiemlak.todolist.domain.auth.applicationservice.dto;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tr.com.hepsiemlak.todolist.InvalidParametersUtil;

import java.util.Set;

class AuthRefreshTokenRequestDtoTest extends InvalidParametersUtil {

    @Test
    void shouldHaveNoViolations() {
        AuthRefreshTokenRequestDto dto = new AuthRefreshTokenRequestDto(
                "refreshToken"
        );
        Set<ConstraintViolation<AuthRefreshTokenRequestDto>> violations = validator.validate(dto);
        Assertions.assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("invalidNameValues")
    void shouldHaveInvalidRefreshToken(String value) {

        AuthRefreshTokenRequestDto dto = new AuthRefreshTokenRequestDto(
                value
        );

        Set<ConstraintViolation<AuthRefreshTokenRequestDto>> violations = validator.validate(dto);

        Assertions.assertTrue(2 >= violations.size());

        for (ConstraintViolation<AuthRefreshTokenRequestDto> violation : violations) {
            Assertions.assertEquals("refreshToken", violation.getPropertyPath().toString());
        }

    }
}
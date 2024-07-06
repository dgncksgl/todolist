package tr.com.hepsiemlak.todolist.domain.auth.applicationservice.dto;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tr.com.hepsiemlak.todolist.InvalidParametersUtil;

import java.util.Set;

class AuthRequestDtoTest extends InvalidParametersUtil {

    @Test
    void shouldHaveNoViolations() {
        AuthRequestDto dto = new AuthRequestDto(
                "username",
                "password"
        );
        Set<ConstraintViolation<AuthRequestDto>> violations = validator.validate(dto);
        Assertions.assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("invalidNameValues")
    void shouldHaveInvalidUsername(String value) {

        AuthRequestDto dto = new AuthRequestDto(
                value,
                "password"
        );

        Set<ConstraintViolation<AuthRequestDto>> violations = validator.validate(dto);

        Assertions.assertTrue(2 >= violations.size());

        for (ConstraintViolation<AuthRequestDto> violation : violations) {
            Assertions.assertEquals("username", violation.getPropertyPath().toString());
        }

    }

    @ParameterizedTest
    @MethodSource("invalidNameValues")
    void shouldHaveInvalidPassword(String value) {

        AuthRequestDto dto = new AuthRequestDto(
                "username",
                value
        );

        Set<ConstraintViolation<AuthRequestDto>> violations = validator.validate(dto);

        Assertions.assertTrue(2 >= violations.size());

        for (ConstraintViolation<AuthRequestDto> violation : violations) {
            Assertions.assertEquals("password", violation.getPropertyPath().toString());
        }

    }

}
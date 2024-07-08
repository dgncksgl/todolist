package tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.dto;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tr.com.hepsiemlak.todolist.InvalidParametersUtil;

import java.util.Set;

class UserCreateDtoTest extends InvalidParametersUtil {

    public static final String ID = "ID";

    public static final String USERNAME = "ADMIN";

    public static final String PASSWORD = "PASSWORD";

    public static final String GSM = "05553331111";

    public static final String EMAIL = "test@test.com";

    @Test
    void shouldHaveNoViolations() {
        UserCreateDto dto = new UserCreateDto(
                USERNAME,
                PASSWORD,
                Boolean.TRUE,
                USERNAME,
                USERNAME,
                GSM,
                EMAIL
        );
        Set<ConstraintViolation<UserCreateDto>> violations = validator.validate(dto);
        Assertions.assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("invalidNameValues")
    void shouldHaveInvalidUsername(String value) {

        UserCreateDto dto = new UserCreateDto(
                value,
                PASSWORD,
                Boolean.TRUE,
                USERNAME,
                USERNAME,
                GSM,
                EMAIL
        );

        Set<ConstraintViolation<UserCreateDto>> violations = validator.validate(dto);

        Assertions.assertTrue(2 >= violations.size());

        for (ConstraintViolation<UserCreateDto> violation : violations) {
            Assertions.assertEquals("username", violation.getPropertyPath().toString());
        }

    }

    @ParameterizedTest
    @MethodSource("invalidNameValues")
    void shouldHaveInvalidPassword(String value) {

        UserCreateDto dto = new UserCreateDto(
                USERNAME,
                value,
                Boolean.TRUE,
                USERNAME,
                USERNAME,
                GSM,
                EMAIL
        );

        Set<ConstraintViolation<UserCreateDto>> violations = validator.validate(dto);

        Assertions.assertTrue(2 >= violations.size());

        for (ConstraintViolation<UserCreateDto> violation : violations) {
            Assertions.assertEquals("password", violation.getPropertyPath().toString());
        }

    }

    @Test
    void shouldHaveInvalidActive() {

        UserCreateDto dto = new UserCreateDto(
                USERNAME,
                PASSWORD,
                null,
                USERNAME,
                USERNAME,
                GSM,
                EMAIL
        );

        Set<ConstraintViolation<UserCreateDto>> violations = validator.validate(dto);

        Assertions.assertTrue(2 >= violations.size());

        for (ConstraintViolation<UserCreateDto> violation : violations) {
            Assertions.assertEquals("active", violation.getPropertyPath().toString());
        }

    }

    @ParameterizedTest
    @MethodSource("invalidNameValues")
    void shouldHaveInvalidName(String value) {

        UserCreateDto dto = new UserCreateDto(
                USERNAME,
                PASSWORD,
                Boolean.TRUE,
                value,
                USERNAME,
                GSM,
                EMAIL
        );

        Set<ConstraintViolation<UserCreateDto>> violations = validator.validate(dto);

        Assertions.assertTrue(2 >= violations.size());

        for (ConstraintViolation<UserCreateDto> violation : violations) {
            Assertions.assertEquals("name", violation.getPropertyPath().toString());
        }

    }

    @ParameterizedTest
    @MethodSource("invalidNameValues")
    void shouldHaveInvalidSurname(String value) {

        UserCreateDto dto = new UserCreateDto(
                USERNAME,
                PASSWORD,
                Boolean.TRUE,
                USERNAME,
                value,
                GSM,
                EMAIL
        );

        Set<ConstraintViolation<UserCreateDto>> violations = validator.validate(dto);

        Assertions.assertTrue(2 >= violations.size());

        for (ConstraintViolation<UserCreateDto> violation : violations) {
            Assertions.assertEquals("surname", violation.getPropertyPath().toString());
        }

    }
}
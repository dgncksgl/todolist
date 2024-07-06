package tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.dto;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tr.com.hepsiemlak.todolist.InvalidParametersUtil;

import java.util.Set;

class UserCreateDtoTest extends InvalidParametersUtil {

    @Test
    void shouldHaveNoViolations() {
        UserCreateDto dto = new UserCreateDto(
                "username",
                "password",
                true,
                "name",
                "surname",
                "05553331111",
                "emal@email.com"
        );
        Set<ConstraintViolation<UserCreateDto>> violations = validator.validate(dto);
        Assertions.assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("invalidNameValues")
    void shouldHaveInvalidUsername(String value) {

        UserCreateDto dto = new UserCreateDto(
                value,
                "password",
                true,
                "name",
                "surname",
                "05553331111",
                "emal@email.com"
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
                "username",
                value,
                true,
                "name",
                "surname",
                "05553331111",
                "emal@email.com"
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
                "username",
                "password",
                null,
                "name",
                "surname",
                "05553331111",
                "emal@email.com"
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
                "username",
                "password",
                true,
                value,
                "surname",
                "05553331111",
                "emal@email.com"
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
                "username",
                "password",
                true,
                "name",
                value,
                "05553331111",
                "emal@email.com"
        );

        Set<ConstraintViolation<UserCreateDto>> violations = validator.validate(dto);

        Assertions.assertTrue(2 >= violations.size());

        for (ConstraintViolation<UserCreateDto> violation : violations) {
            Assertions.assertEquals("surname", violation.getPropertyPath().toString());
        }

    }
}
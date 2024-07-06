package tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.dto;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tr.com.hepsiemlak.todolist.InvalidParametersUtil;

import java.util.Set;

class UserUpdateDtoTest extends InvalidParametersUtil {

    @Test
    void shouldHaveNoViolations() {
        UserUpdateDto dto = new UserUpdateDto(
                "id",
                "username",
                "password",
                true,
                "name",
                "surname",
                "05553331111",
                "emal@email.com"
        );
        Set<ConstraintViolation<UserUpdateDto>> violations = validator.validate(dto);
        Assertions.assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("invalidNameValues")
    void shouldHaveInvalidId(String value) {

        UserUpdateDto dto = new UserUpdateDto(
                value,
                "username",
                "password",
                true,
                "name",
                "surname",
                "05553331111",
                "emal@email.com"
        );

        Set<ConstraintViolation<UserUpdateDto>> violations = validator.validate(dto);

        Assertions.assertTrue(2 >= violations.size());

        for (ConstraintViolation<UserUpdateDto> violation : violations) {
            Assertions.assertEquals("id", violation.getPropertyPath().toString());
        }

    }

    @ParameterizedTest
    @MethodSource("invalidNameValues")
    void shouldHaveInvalidUsername(String value) {

        UserUpdateDto dto = new UserUpdateDto(
                "id",
                value,
                "password",
                true,
                "name",
                "surname",
                "05553331111",
                "emal@email.com"
        );

        Set<ConstraintViolation<UserUpdateDto>> violations = validator.validate(dto);

        Assertions.assertTrue(2 >= violations.size());

        for (ConstraintViolation<UserUpdateDto> violation : violations) {
            Assertions.assertEquals("username", violation.getPropertyPath().toString());
        }

    }

    @ParameterizedTest
    @MethodSource("invalidNameValues")
    void shouldHaveInvalidPassword(String value) {

        UserUpdateDto dto = new UserUpdateDto(
                "id",
                "username",
                value,
                true,
                "name",
                "surname",
                "05553331111",
                "emal@email.com"
        );

        Set<ConstraintViolation<UserUpdateDto>> violations = validator.validate(dto);

        Assertions.assertTrue(2 >= violations.size());

        for (ConstraintViolation<UserUpdateDto> violation : violations) {
            Assertions.assertEquals("password", violation.getPropertyPath().toString());
        }

    }

    @Test
    void shouldHaveInvalidActive() {


        UserUpdateDto dto = new UserUpdateDto(
                "id",
                "username",
                "password",
                null,
                "name",
                "surname",
                "05553331111",
                "emal@email.com"
        );

        Set<ConstraintViolation<UserUpdateDto>> violations = validator.validate(dto);

        Assertions.assertTrue(2 >= violations.size());

        for (ConstraintViolation<UserUpdateDto> violation : violations) {
            Assertions.assertEquals("active", violation.getPropertyPath().toString());
        }

    }

    @ParameterizedTest
    @MethodSource("invalidNameValues")
    void shouldHaveInvalidName(String value) {

        UserUpdateDto dto = new UserUpdateDto(
                "id",
                "username",
                "password",
                true,
                value,
                "surname",
                "05553331111",
                "emal@email.com"
        );

        Set<ConstraintViolation<UserUpdateDto>> violations = validator.validate(dto);

        Assertions.assertTrue(2 >= violations.size());

        for (ConstraintViolation<UserUpdateDto> violation : violations) {
            Assertions.assertEquals("name", violation.getPropertyPath().toString());
        }

    }

    @ParameterizedTest
    @MethodSource("invalidNameValues")
    void shouldHaveInvalidSurname(String value) {

        UserUpdateDto dto = new UserUpdateDto(
                "id",
                "username",
                "password",
                true,
                "name",
                value,
                "05553331111",
                "emal@email.com"
        );

        Set<ConstraintViolation<UserUpdateDto>> violations = validator.validate(dto);

        Assertions.assertTrue(2 >= violations.size());

        for (ConstraintViolation<UserUpdateDto> violation : violations) {
            Assertions.assertEquals("surname", violation.getPropertyPath().toString());
        }

    }
}
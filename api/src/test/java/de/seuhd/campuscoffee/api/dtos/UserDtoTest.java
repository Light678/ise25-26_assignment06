package de.seuhd.campuscoffee.api.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private UserDto createValidUserDto() {
        return UserDto.builder()
                .loginName("validLogin123")
                .emailAddress("valid.user@example.com")
                .firstName("Max")
                .lastName("Mustermann")
                .build();
    }

    @Test
    void validUserDto_hasNoViolations() {
        UserDto validUserDto = createValidUserDto();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(validUserDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidLoginName_containsSpace() {
        UserDto invalidUserDto = createValidUserDto().toBuilder()
                .loginName("invalid login") // verletzt @Pattern("\\w+")
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(invalidUserDto);

        assertFalse(violations.isEmpty());
    }

    @Test
    void invalidEmail_notAnEmail() {
        UserDto invalidUserDto = createValidUserDto().toBuilder()
                .emailAddress("not-an-email") // verletzt @Email
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(invalidUserDto);

        assertFalse(violations.isEmpty());
    }

    @Test
    void invalidFirstName_empty() {
        UserDto invalidUserDto = createValidUserDto().toBuilder()
                .firstName("") // verletzt @Size(min = 1, ...)
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(invalidUserDto);

        assertFalse(violations.isEmpty());
    }

    @Test
    void invalidLastName_empty() {
        UserDto invalidUserDto = createValidUserDto().toBuilder()
                .lastName("") // verletzt @Size(min = 1, ...)
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(invalidUserDto);

        assertFalse(violations.isEmpty());
    }
}

package de.seuhd.campuscoffee.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    @Nullable
    private Long id;

    @Nullable
    private LocalDateTime createdAt;

    @Nullable
    private LocalDateTime updatedAt;

    @NotNull
    @Pattern(regexp = "\\w+", message = "Login name may only contain word characters.")
    @Size(min = 1, max = 255)
    @NonNull
    private String loginName;

    @NotNull
    @Email
    @Size(max = 255)
    @NonNull
    private String emailAddress;

    @NotNull
    @Size(min = 1, max = 255, message = "First name must be between 1 and 255 characters long.")
    @NonNull
    private String firstName;

    @NotNull
    @Size(min = 1, max = 255, message = "Last name must be between 1 and 255 characters long.")
    @NonNull
    private String lastName;
}


@Builder(toBuilder = true)
public record UserDto (
        @Nullable Long id, // id is null when creating a new user
        @Nullable LocalDateTime createdAt, // is null when using DTO to create a new user
        @Nullable LocalDateTime updatedAt, // is set when creating or updating a user

        @NotNull
        @Size(min = 1, max = 255, message = "Login name must be between 1 and 255 characters long.")
        @Pattern(regexp = "\\w+", message = "Login name can only contain word characters: [a-zA-Z_0-9]+") // implies non-empty
        @NonNull String loginName,

        @NotNull
        @Email
        @NonNull String emailAddress,

        @NotNull
        @Size(min = 1, max = 255, message = "First name must be between 1 and 255 characters long.")
        @NonNull String firstName,


        @NotNull
        @Size(min = 1, max = 255, message = "Last name must be between 1 and 255 characters long.")
        @NonNull String lastName
) {}


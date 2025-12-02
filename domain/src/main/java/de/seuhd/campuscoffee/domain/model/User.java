package de.seuhd.campuscoffee.domain.model;

import lombok.Builder;
import java.time.LocalDateTime;
import java.util.Objects;
import java.io.Serial;
import java.io.Serializable;


@Builder(toBuilder = true)
public record User(
        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String loginName,
        String emailAddress,
        String firstName,
        String lastName
) implements Serializable { // serializable to allow cloning (see TestFixtures class).
    @Serial
    private static final long serialVersionUID = 1L;
    public User {
        // Statische Null-Checks im Domain-Modul
        Objects.requireNonNull(loginName, "loginName must not be null");
        Objects.requireNonNull(emailAddress, "emailAddress must not be null");
        Objects.requireNonNull(firstName, "firstName must not be null");
        Objects.requireNonNull(lastName, "lastName must not be null");
        // id / createdAt / updatedAt dürfen null sein (werden von DB/Adapter gesetzt)
    }
}



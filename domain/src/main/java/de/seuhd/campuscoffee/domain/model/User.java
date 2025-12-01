package de.seuhd.campuscoffee.domain.model;

import lombok.Builder;
import java.time.LocalDateTime;
import java.util.Objects;
import java.io.Serial;
import java.io.Serializable;

public class User {

    private final Long id;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String loginName;
    private final String emailAddress;
    private final String firstName;
    private final String lastName;

    public User(
            Long id,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String loginName,
            String emailAddress,
            String firstName,
            String lastName
    ) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        this.loginName = Objects.requireNonNull(loginName, "loginName must not be null");
        this.emailAddress = Objects.requireNonNull(emailAddress, "emailAddress must not be null");
        this.firstName = Objects.requireNonNull(firstName, "firstName must not be null");
        this.lastName = Objects.requireNonNull(lastName, "lastName must not be null");
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

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



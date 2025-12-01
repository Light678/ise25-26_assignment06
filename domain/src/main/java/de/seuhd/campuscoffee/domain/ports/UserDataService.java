package de.seuhd.campuscoffee.domain.ports;

import de.seuhd.campuscoffee.domain.model.User;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * Port interface for user data operations.
 * This port is implemented by the data layer (adapter) and defines the contract
 * for persistence operations on user entities.
 */
public interface UserDataService {

    /**
     * Clears all user data from the data store.
     * Typically used for testing or administrative purposes.
     */
    void clear();

    /**
     * Returns all users.
     *
     * @return a list of all users; never null
     */
    @NonNull
    List<User> findAll();

    /**
     * Finds a user by their unique identifier.
     *
     * @param id the unique identifier of the user; must not be null
     * @return an Optional containing the user if found, or empty if not found
     */
    @NonNull
    Optional<User> findById(@NonNull Long id);

    /**
     * Creates or updates a user.
     *
     * @param user the user to create or update; must not be null
     * @return the persisted user entity with updated timestamps and ID; never null
     */
    @NonNull
    User upsert(@NonNull User user);

    /**
     * Deletes a user by their unique identifier.
     *
     * @param id the unique identifier of the user to delete; must not be null
     */
    void delete(@NonNull Long id);
}


package de.seuhd.campuscoffee.domain.ports;

import de.seuhd.campuscoffee.domain.model.User;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * Port interface for user data operations.
 * Implementiert im Data-Modul (Adapter).
 */
public interface UserDataService {

    /**
     * Löscht alle Benutzer (hauptsächlich für Tests).
     */
    void clear();

    /**
     * Liefert alle Benutzer.
     */
    @NonNull
    List<User> findAll();

    /**
     * Findet Benutzer nach ID.
     */
    @NonNull
    Optional<User> findById(@NonNull Long id);

    /**
     * Findet Benutzer nach Login-Name.
     * (für /api/users/filter?login_name=...)
     */
    @NonNull
    Optional<User> findByLoginName(@NonNull String loginName);

    /**
     * Erzeugt oder aktualisiert einen Benutzer.
     */
    @NonNull
    User upsert(@NonNull User user);

    /**
     * Löscht Benutzer nach ID.
     */
    void delete(@NonNull Long id);
}



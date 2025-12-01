package de.seuhd.campuscoffee.tests.system;

import de.seuhd.campuscoffee.domain.model.User;
import de.seuhd.campuscoffee.tests.TestFixtures;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UsersSystemTests extends AbstractSysTest {

    @Test
    void createUser() {
        User userToCreate = TestFixtures.getUserListForInsertion().getFirst();

        // User über API anlegen
        User createdUser = userDtoMapper.toDomain(
                userRequests.create(
                        List.of(userDtoMapper.fromDomain(userToCreate))
                ).getFirst()
        );

        // Vergleich: gleiche Daten, aber IDs/Timestamps dürfen sich unterscheiden
        assertEqualsIgnoringIdAndTimestamps(createdUser, userToCreate);
    }

    /**
     * Zusätzlicher Test 1:
     * Abrufen eines Benutzers über seine ID.
     */
    @Test
    void getUserByIdReturnsCreatedUser() {
        User userToCreate = TestFixtures.getUserListForInsertion().getFirst();

        // Zuerst User anlegen
        User createdUser = userDtoMapper.toDomain(
                userRequests.create(
                        List.of(userDtoMapper.fromDomain(userToCreate))
                ).getFirst()
        );

        // Dann per ID wieder abrufen
        var fetchedUserDto = userRequests.getById(createdUser.id());
        User fetchedUser = userDtoMapper.toDomain(fetchedUserDto);

        assertEqualsIgnoringIdAndTimestamps(fetchedUser, userToCreate);
    }

    /**
     * Zusätzlicher Test 2:
     * Abrufen eines Benutzers über den Login-Namen mit dem Filter-Endpoint.
     */
    @Test
    void getUserByLoginNameFilterReturnsCreatedUser() {
        User userToCreate = TestFixtures.getUserListForInsertion().getFirst();

        // User anlegen
        User createdUser = userDtoMapper.toDomain(
                userRequests.create(
                        List.of(userDtoMapper.fromDomain(userToCreate))
                ).getFirst()
        );

        // Über Filter-Endpoint (analog PosController) abrufen
        var fetchedUserDto = userRequests.getByLoginName(createdUser.loginName());
        User fetchedUser = userDtoMapper.toDomain(fetchedUserDto);

        assertEqualsIgnoringIdAndTimestamps(fetchedUser, userToCreate);
    }
}

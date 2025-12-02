package de.seuhd.campuscoffee.tests.system;

import de.seuhd.campuscoffee.domain.model.User;
import de.seuhd.campuscoffee.domain.ports.UserDataService;
import de.seuhd.campuscoffee.domain.tests.TestFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UsersSystemTests extends AbstractSysTest {

    @Autowired
    private UserDataService userDataService;

    @Test
    void createUser() {
        // Beispiel-User aus Fixtures, aber ohne ID/Timestamps (für Insert)
        User userToCreate = TestFixtures.getUserListForInsertion().getFirst();

        // direkt über den Domain-Port speichern
        User createdUser = userDataService.upsert(userToCreate);

        // gleiche Daten, IDs/Timestamps dürfen abweichen
        assertEqualsIgnoringIdAndTimestamps(createdUser, userToCreate);
    }

    @Test
    void getUserByIdReturnsCreatedUser() {
        User userToCreate = TestFixtures.getUserListForInsertion().getFirst();

        User createdUser = userDataService.upsert(userToCreate);

        User fetchedUser = userDataService.getById(createdUser.id());

        assertEqualsIgnoringIdAndTimestamps(fetchedUser, userToCreate);
    }

    @Test
    void getUserByLoginNameReturnsCreatedUser() {
        User userToCreate = TestFixtures.getUserListForInsertion().getFirst();

        User createdUser = userDataService.upsert(userToCreate);

        User fetchedUser = userDataService.getByLoginName(createdUser.loginName());

        assertEqualsIgnoringIdAndTimestamps(fetchedUser, userToCreate);
    }
}





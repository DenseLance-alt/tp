package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.formatDelivery;
import static seedu.address.logic.Messages.formatPerson;
import static seedu.address.testutil.TypicalDeliveries.DELIVERY_ALICE;
import static seedu.address.testutil.TypicalDeliveries.DELIVERY_CARL;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains tests for {@code Messages}.
 */
public class MessagesTest {

    @Test
    void formatPerson_samePersonMessage() {
        // ensures that Alice has delivery and Benson has no delivery
        assertTrue(ALICE.hasDelivery());
        assertFalse(BENSON.hasDelivery());

        // same person and same non-null delivery -> same person message displayed -> returns true
        assertEquals(formatPerson(ALICE), formatPerson(ALICE));

        // same person and both delivery are null -> same person message displayed -> returns true
        assertEquals(formatPerson(BENSON), formatPerson(BENSON));

        // same person and different non-null delivery -> same person message displayed -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withDelivery(DELIVERY_CARL).build();
        assertEquals(formatPerson(ALICE), formatPerson(editedAlice));

        // same person and only one delivery is null -> same person message displayed -> returns true
        editedAlice = new PersonBuilder(ALICE).withDelivery(null).build();
        assertEquals(formatPerson(ALICE), formatPerson(editedAlice));
    }

    @Test
    void formatPerson_differentPersonMessage() {
        // ensures that Alice and Carl have delivery and Benson has no delivery
        assertTrue(ALICE.hasDelivery());
        assertTrue(CARL.hasDelivery());
        assertFalse(BENSON.hasDelivery());

        // different person and same non-null delivery -> different person message displayed -> returns false
        Person editedBenson = new PersonBuilder(BENSON).withDelivery(DELIVERY_ALICE).build();
        assertNotEquals(formatPerson(ALICE), formatPerson(editedBenson));

        // different person and both delivery are null -> different person message displayed -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withDelivery(null).build();
        assertNotEquals(formatPerson(BENSON), formatPerson(editedAlice));

        // different person and different non-null delivery -> different person message displayed -> returns false
        assertNotEquals(formatPerson(ALICE), formatPerson(CARL));

        // different person and only one delivery is null -> different person message displayed -> returns false
        assertNotEquals(formatPerson(ALICE), formatPerson(BENSON));
    }

    @Test
    void formatDelivery_sameDeliveryMessage() {
        // ensures that Alice has delivery and Benson has no delivery
        assertTrue(ALICE.hasDelivery());
        assertFalse(BENSON.hasDelivery());

        // same person and same non-null delivery -> same delivery message displayed -> returns true
        assertEquals(formatDelivery(ALICE), formatDelivery(ALICE));

        // same person and both delivery are null -> same delivery message displayed -> returns true
        assertEquals(formatDelivery(BENSON), formatDelivery(BENSON));
    }

    @Test
    void formatDelivery_differentDeliveryMessage() {
        // ensures that Alice and Carl have delivery and Benson has no delivery
        assertTrue(ALICE.hasDelivery());
        assertTrue(CARL.hasDelivery());
        assertFalse(BENSON.hasDelivery());

        // different person and same non-null delivery -> different delivery message displayed -> returns false
        Person editedBenson = new PersonBuilder(BENSON).withDelivery(DELIVERY_ALICE).build();
        assertNotEquals(formatDelivery(ALICE), formatDelivery(editedBenson));

        // different person and both delivery are null -> different delivery message displayed -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withDelivery(null).build();
        assertNotEquals(formatDelivery(BENSON), formatDelivery(editedAlice));

        // different person and different non-null delivery -> different delivery message displayed -> returns false
        assertNotEquals(formatDelivery(ALICE), formatDelivery(CARL));

        // different person and only one delivery is null -> different delivery message displayed -> returns false
        assertNotEquals(formatDelivery(ALICE), formatDelivery(BENSON));

        // same person and different non-null delivery -> different delivery message displayed -> returns true
        editedAlice = new PersonBuilder(ALICE).withDelivery(DELIVERY_CARL).build();
        assertNotEquals(formatDelivery(ALICE), formatDelivery(editedAlice));

        // same person and only one delivery is null -> different delivery message displayed -> returns true
        editedAlice = new PersonBuilder(ALICE).withDelivery(null).build();
        assertNotEquals(formatDelivery(ALICE), formatDelivery(editedAlice));
    }

}

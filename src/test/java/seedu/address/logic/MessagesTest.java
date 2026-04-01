package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.formatDeliveryFromPerson;
import static seedu.address.logic.Messages.formatPerson;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDeliveries.DELIVERY_ALICE;
import static seedu.address.testutil.TypicalDeliveries.DELIVERY_CARL;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

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
        assertFalse(BENSON.hasDelivery());

        // different person and same non-null delivery -> different person message displayed -> returns false
        Person editedBenson = new PersonBuilder(BENSON).withDelivery(DELIVERY_ALICE).build();
        assertNotEquals(formatPerson(ALICE), formatPerson(editedBenson));

        // different person and both delivery are null -> different person message displayed -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withDelivery(null).build();
        assertNotEquals(formatPerson(BENSON), formatPerson(editedAlice));

        // different person and different non-null delivery -> different person message displayed -> returns false
        editedBenson = new PersonBuilder(BENSON).withDelivery(DELIVERY_CARL).build();
        assertNotEquals(formatPerson(ALICE), formatPerson(editedBenson));

        // different person and only one delivery is null -> different person message displayed -> returns false
        assertNotEquals(formatPerson(ALICE), formatPerson(BENSON));
    }

    @Test
    void formatPerson_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Messages.formatPerson((Person) null));
    }

    @Test
    void formatDeliveryFromPerson_sameDeliveryMessage() {
        // ensures that Alice has delivery and Benson has no delivery
        assertTrue(ALICE.hasDelivery());
        assertFalse(BENSON.hasDelivery());

        // same person and same non-null delivery -> same delivery message displayed -> returns true
        assertEquals(formatDeliveryFromPerson(ALICE), formatDeliveryFromPerson(ALICE));

        // same person and both delivery are null -> same delivery message displayed -> returns true
        assertEquals(formatDeliveryFromPerson(BENSON), formatDeliveryFromPerson(BENSON));
    }

    @Test
    void formatDeliveryFromPerson_differentDeliveryMessage() {
        // ensures that Alice and Carl have delivery and Benson has no delivery
        assertTrue(ALICE.hasDelivery());
        assertFalse(BENSON.hasDelivery());

        // different person and same non-null delivery -> different delivery message displayed -> returns false
        Person editedBenson = new PersonBuilder(BENSON).withDelivery(DELIVERY_ALICE).build();
        assertNotEquals(formatDeliveryFromPerson(ALICE), formatDeliveryFromPerson(editedBenson));

        // different person and both delivery are null -> different delivery message displayed -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withDelivery(null).build();
        assertNotEquals(formatDeliveryFromPerson(BENSON), formatDeliveryFromPerson(editedAlice));

        // different person and different non-null delivery -> different delivery message displayed -> returns false
        editedBenson = new PersonBuilder(BENSON).withDelivery(DELIVERY_CARL).build();
        assertNotEquals(formatDeliveryFromPerson(ALICE), formatDeliveryFromPerson(editedBenson));

        // different person and only one delivery is null -> different delivery message displayed -> returns false
        assertNotEquals(formatDeliveryFromPerson(ALICE), formatDeliveryFromPerson(BENSON));

        // same person and different non-null delivery -> different delivery message displayed -> returns true
        editedAlice = new PersonBuilder(ALICE).withDelivery(DELIVERY_CARL).build();
        assertNotEquals(formatDeliveryFromPerson(ALICE), formatDeliveryFromPerson(editedAlice));

        // same person and only one delivery is null -> different delivery message displayed -> returns true
        editedAlice = new PersonBuilder(ALICE).withDelivery(null).build();
        assertNotEquals(formatDeliveryFromPerson(ALICE), formatDeliveryFromPerson(editedAlice));
    }

    @Test
    void formatDeliveryFromPerson_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Messages.formatDeliveryFromPerson((Person) null));
    }

}

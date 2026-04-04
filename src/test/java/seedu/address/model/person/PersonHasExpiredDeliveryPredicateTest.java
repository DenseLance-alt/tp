package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.delivery.Delivery;
import seedu.address.testutil.DeliveryBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains unit and integration tests for {@code PersonHasExpiredDeliveryPredicate}.
 */
public class PersonHasExpiredDeliveryPredicateTest {

    @Test
    public void test() {
        Delivery delivery = new DeliveryBuilder()
                .withStartDate("2024-03-01")
                .withEndDate("2024-04-01")
                .build();
        Person person = new PersonBuilder()
                .withDelivery(delivery)
                .build();

        // EP: specified date is later than end date of person's delivery -> expired
        LocalDate laterDate = LocalDate.of(2024, 12, 31);
        PersonHasExpiredDeliveryPredicate expiredPredicate = new PersonHasExpiredDeliveryPredicate(laterDate);
        assertTrue(expiredPredicate.test(person));

        // EP: specified date is earlier than end date of person's delivery -> not expired
        LocalDate earlierDate = LocalDate.of(2024, 1, 1);
        PersonHasExpiredDeliveryPredicate notExpiredPredicate = new PersonHasExpiredDeliveryPredicate(earlierDate);
        assertFalse(notExpiredPredicate.test(person));
    }

    @Test
    public void equals() {
        LocalDate firstDate = LocalDate.of(2026, 4, 1);
        LocalDate secondDate = LocalDate.of(2026, 2, 28);

        PersonHasExpiredDeliveryPredicate firstPredicate = new PersonHasExpiredDeliveryPredicate(firstDate);
        PersonHasExpiredDeliveryPredicate secondPredicate = new PersonHasExpiredDeliveryPredicate(secondDate);

        // EP: same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // EP: same values -> returns true
        PersonHasExpiredDeliveryPredicate firstPredicateCopy = new PersonHasExpiredDeliveryPredicate(firstDate);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // EP: different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // EP: null -> returns false
        assertFalse(firstPredicate.equals(null));

        // EP: different date -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void toStringMethod() {
        LocalDate date = LocalDate.of(2026, 4, 1);
        PersonHasExpiredDeliveryPredicate predicate = new PersonHasExpiredDeliveryPredicate(date);

        String expected = PersonHasExpiredDeliveryPredicate.class.getCanonicalName()
                + "{beforeDate=" + date + '}';
        assertEquals(expected, predicate.toString());
    }

}

package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.DeliveryBuilder;
import seedu.address.testutil.PersonBuilder;

public class DeliveryDatePredicateTest {

    @Test
    public void equals() {
        DeliveryDatePredicate firstPredicate = new DeliveryDatePredicate(LocalDate.of(2026, 4, 1));
        DeliveryDatePredicate secondPredicate = new DeliveryDatePredicate(LocalDate.of(2026, 4, 2));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_deliveryMatchesDate_returnsTrue() {
        Delivery delivery = new DeliveryBuilder()
                .withStartDate("2026-04-01")
                .withEndDate("2026-04-30")
                .withDeliveryDays("Wednesday")
                .build();

        Person person = new PersonBuilder()
                .withDelivery(delivery)
                .build();

        DeliveryDatePredicate predicate =
                new DeliveryDatePredicate(LocalDate.of(2026, 4, 1));

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_deliveryDoesNotMatch_returnsFalse() {
        Delivery delivery = new DeliveryBuilder()
                .withStartDate("2026-04-01")
                .withEndDate("2026-04-30")
                .withDeliveryDays("Monday")
                .build();

        Person person = new PersonBuilder()
                .withDelivery(delivery)
                .build();

        DeliveryDatePredicate predicate =
                new DeliveryDatePredicate(LocalDate.of(2026, 4, 1));

        assertFalse(predicate.test(person));
    }

    @Test
    public void test_noDelivery_returnsFalse() {
        Person person = new PersonBuilder().build();

        DeliveryDatePredicate predicate =
                new DeliveryDatePredicate(LocalDate.of(2026, 4, 1));

        assertFalse(predicate.test(person));
    }

    @Test
    public void test_dateBeforeStart_returnsFalse() {
        Delivery delivery = new DeliveryBuilder()
                .withStartDate("2026-04-05")
                .withEndDate("2026-04-30")
                .withDeliveryDays("Wednesday")
                .build();

        Person person = new PersonBuilder()
                .withDelivery(delivery)
                .build();

        DeliveryDatePredicate predicate =
                new DeliveryDatePredicate(LocalDate.of(2026, 4, 1));

        assertFalse(predicate.test(person));
    }

    @Test
    public void test_skippedDate_returnsFalse() {
        Delivery delivery = new DeliveryBuilder()
                .withStartDate("2026-04-01")
                .withEndDate("2026-04-30")
                .withDeliveryDays("Wednesday")
                .withSkippedDates("2026-04-01")
                .build();

        Person person = new PersonBuilder()
                .withDelivery(delivery)
                .build();

        DeliveryDatePredicate predicate =
                new DeliveryDatePredicate(LocalDate.of(2026, 4, 1));

        assertFalse(predicate.test(person));
    }
}

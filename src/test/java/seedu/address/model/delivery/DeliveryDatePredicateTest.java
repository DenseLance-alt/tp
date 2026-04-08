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

        // same single date as equivalent range -> returns true
        DeliveryDatePredicate sameAsRange = new DeliveryDatePredicate(
                LocalDate.of(2026, 4, 1), LocalDate.of(2026, 4, 1));
        assertTrue(firstPredicate.equals(sameAsRange));

        // same range -> returns true
        DeliveryDatePredicate rangePredicate = new DeliveryDatePredicate(
                LocalDate.of(2026, 4, 1), LocalDate.of(2026, 4, 30));
        DeliveryDatePredicate rangePredicateCopy = new DeliveryDatePredicate(
                LocalDate.of(2026, 4, 1), LocalDate.of(2026, 4, 30));
        assertTrue(rangePredicate.equals(rangePredicateCopy));

        // different range -> returns false
        DeliveryDatePredicate differentRange = new DeliveryDatePredicate(
                LocalDate.of(2026, 4, 1), LocalDate.of(2026, 5, 1));
        assertFalse(rangePredicate.equals(differentRange));
    }

    // EP: person has no delivery
    @Test
    public void test_noDelivery_returnsFalse() {
        Person person = new PersonBuilder().build();

        DeliveryDatePredicate predicate =
                new DeliveryDatePredicate(LocalDate.of(2026, 4, 1));

        assertFalse(predicate.test(person));
    }

    // EP: single date overlaps delivery period with a matching delivery day
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

    // EP: single date overlaps delivery period but is not a delivery day
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

    // EP: single date entirely before delivery period
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

    // EP: single date entirely after delivery period
    @Test
    public void test_dateAfterEnd_returnsFalse() {
        Delivery delivery = new DeliveryBuilder()
                .withStartDate("2026-04-01")
                .withEndDate("2026-04-30")
                .withDeliveryDays("Wednesday")
                .build();

        Person person = new PersonBuilder()
                .withDelivery(delivery)
                .build();

        DeliveryDatePredicate predicate =
                new DeliveryDatePredicate(LocalDate.of(2026, 5, 1));

        assertFalse(predicate.test(person));
    }

    // EP: matching single-date query with a matching delivery day
    // Boundary: single date == end date
    @Test
    public void test_dateOnDeliveryEnd_returnsTrue() {
        Delivery delivery = new DeliveryBuilder()
                .withStartDate("2026-04-01")
                .withEndDate("2026-04-30")
                .withDeliveryDays("Thursday")
                .build();

        Person person = new PersonBuilder()
                .withDelivery(delivery)
                .build();

        DeliveryDatePredicate predicate =
                new DeliveryDatePredicate(LocalDate.of(2026, 4, 30));

        assertTrue(predicate.test(person));
    }

    // EP: delivery has multiple delivery days; query date matches only one of them
    @Test
    public void test_multipleDeliveryDays_returnsTrue() {
        Delivery delivery = new DeliveryBuilder()
                .withStartDate("2026-04-01")
                .withEndDate("2026-04-30")
                .withDeliveryDays("Monday", "Wednesday")
                .build();

        Person person = new PersonBuilder()
                .withDelivery(delivery)
                .build();

        DeliveryDatePredicate predicate =
                new DeliveryDatePredicate(LocalDate.of(2026, 4, 6));

        assertTrue(predicate.test(person));
    }

    // EP: query range overlaps delivery period with a matching delivery day
    @Test
    public void test_rangeOverlapsDelivery_returnsTrue() {
        // Delivery: 2026-04-01 to 2026-04-30, Wednesday
        // Query range: 2026-04-05 to 2026-04-15
        // First Wednesday in overlap is 2026-04-08 -> true
        Delivery delivery = new DeliveryBuilder()
                .withStartDate("2026-04-01")
                .withEndDate("2026-04-30")
                .withDeliveryDays("Wednesday")
                .build();

        Person person = new PersonBuilder()
                .withDelivery(delivery)
                .build();

        DeliveryDatePredicate predicate = new DeliveryDatePredicate(
                LocalDate.of(2026, 4, 5), LocalDate.of(2026, 4, 15));

        assertTrue(predicate.test(person));
    }

    // EP: query range entirely outside of delivery period (no overlap)
    @Test
    public void test_rangeNoOverlapWithDelivery_returnsFalse() {
        // Delivery: 2026-04-01 to 2026-04-10, Wednesday
        // Query range: 2026-04-15 to 2026-04-30 -> no overlap -> false
        Delivery delivery = new DeliveryBuilder()
                .withStartDate("2026-04-01")
                .withEndDate("2026-04-10")
                .withDeliveryDays("Wednesday")
                .build();

        Person person = new PersonBuilder()
                .withDelivery(delivery)
                .build();

        DeliveryDatePredicate predicate = new DeliveryDatePredicate(
                LocalDate.of(2026, 4, 15), LocalDate.of(2026, 4, 30));

        assertFalse(predicate.test(person));
    }

    // EP: query range overlaps delivery period but no delivery day falls within the overlap
    @Test
    public void test_rangeOverlapsButNoMatchingDay_returnsFalse() {
        // Delivery: 2026-04-01 to 2026-04-30, Wednesday (2026-04-01 is Wednesday)
        // Query range: 2026-04-02 to 2026-04-07 -> overlap exists but no Wednesday in range -> false
        Delivery delivery = new DeliveryBuilder()
                .withStartDate("2026-04-01")
                .withEndDate("2026-04-30")
                .withDeliveryDays("Wednesday")
                .build();

        Person person = new PersonBuilder()
                .withDelivery(delivery)
                .build();

        DeliveryDatePredicate predicate = new DeliveryDatePredicate(
                LocalDate.of(2026, 4, 2), LocalDate.of(2026, 4, 7));

        assertFalse(predicate.test(person));
    }

}

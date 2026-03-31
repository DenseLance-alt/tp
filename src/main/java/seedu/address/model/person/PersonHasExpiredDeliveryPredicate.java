package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryHasExpiredPredicate;

/**
 * Tests that a {@code Person} has a delivery and it is expired.
 * <p>A delivery is considered expired if its end date is before the specified date.
 */
public class PersonHasExpiredDeliveryPredicate implements Predicate<Person> {

    private final LocalDate beforeDate;

    /**
     * Creates a PersonHasExpiredDeliveryPredicate with the specified date.
     *
     * @param beforeDate Date to compare against. Must not be null.
     */
    public PersonHasExpiredDeliveryPredicate(LocalDate beforeDate) {
        requireNonNull(beforeDate);
        this.beforeDate = beforeDate;
    }

    @Override
    public boolean test(Person person) {
        Delivery delivery = person.getDelivery();

        // Person has no delivery -> person has no expired deliveries
        if (delivery == null) {
            return false;
        }

        return new DeliveryHasExpiredPredicate(beforeDate).test(delivery);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonHasExpiredDeliveryPredicate)) {
            return false;
        }

        PersonHasExpiredDeliveryPredicate otherPredicate = (PersonHasExpiredDeliveryPredicate) other;
        return beforeDate.equals(otherPredicate.beforeDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("beforeDate", beforeDate)
                .toString();
    }

}

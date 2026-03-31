package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Delivery}'s end date is before the specified date.
 */
public class DeliveryHasExpiredPredicate implements Predicate<Delivery> {

    private final LocalDate beforeDate;

    /**
     * Creates a DeliveryHasExpiredPredicate with the specified date.
     *
     * @param beforeDate Date to compare against. Must not be null.
     */
    public DeliveryHasExpiredPredicate(LocalDate beforeDate) {
        requireNonNull(beforeDate);
        this.beforeDate = beforeDate;
    }

    @Override
    public boolean test(Delivery delivery) {
        return delivery.hasExpired(beforeDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliveryHasExpiredPredicate)) {
            return false;
        }

        DeliveryHasExpiredPredicate otherPredicate = (DeliveryHasExpiredPredicate) other;
        return beforeDate.equals(otherPredicate.beforeDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("beforeDate", beforeDate)
                .toString();
    }

}

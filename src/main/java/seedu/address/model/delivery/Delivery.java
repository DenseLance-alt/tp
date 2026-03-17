package seedu.address.model.delivery;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Delivery in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Delivery {

    // Data fields
    private final StartDate startDate;
    private final EndDate endDate;
    private final Set<DeliveryDay> deliveryDays = new HashSet<>();
    private final DeliveryTime deliveryTime;
    private final Set<SkippedDate> skippedDates = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Delivery(StartDate startDate,
                    EndDate endDate,
                    Set<DeliveryDay> deliveryDays,
                    DeliveryTime deliveryTime,
                    Set<SkippedDate> skippedDates) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.deliveryDays.addAll(deliveryDays);
        this.deliveryTime = deliveryTime;
        this.skippedDates.addAll(skippedDates);
    }

    public StartDate getStartDate() {
        return startDate;
    }

    public EndDate getEndDate() {
        return endDate;
    }

    public DeliveryTime getDeliveryTime() {
        return deliveryTime;
    }

    /**
     * Returns an immutable delivery day set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<DeliveryDay> getDeliveryDays() {
        return Collections.unmodifiableSet(deliveryDays);
    }

    /**
     * Returns an immutable skipped date set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<SkippedDate> getSkippedDates() {
        return Collections.unmodifiableSet(skippedDates);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Delivery)) {
            return false;
        }

        Delivery otherDelivery = (Delivery) other;
        return startDate.equals(otherDelivery.startDate)
                && endDate.equals(otherDelivery.endDate)
                && deliveryDays.equals(otherDelivery.deliveryDays)
                && deliveryTime.equals(otherDelivery.deliveryTime)
                && skippedDates.equals(otherDelivery.skippedDates);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(startDate, endDate, deliveryDays, deliveryTime, skippedDates);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("start date", startDate)
                .add("end date", endDate)
                .add("delivery days", deliveryDays)
                .add("delivery time", deliveryTime)
                .add("skipped dates", skippedDates)
                .toString();
    }
}

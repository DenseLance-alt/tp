package seedu.address.model.delivery;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.DateTimeUtil.isValidDeliveryDateRange;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Delivery in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Delivery {
    public static final String MESSAGE_CONSTRAINTS =
            "Start date of the delivery must not be before the end date";

    // Data fields
    private final StartDate startDate;
    private final EndDate endDate;
    private final Set<DeliveryDay> deliveryDays = new LinkedHashSet<>();
    private final DeliveryTime deliveryTime;

    /**
     * Every field must be present and not null.
     */
    public Delivery(StartDate startDate,
                    EndDate endDate,
                    Set<DeliveryDay> deliveryDays,
                    DeliveryTime deliveryTime) {
        requireAllNonNull(startDate, endDate, deliveryDays, deliveryTime);
        checkArgument(isValidDateRange(startDate, endDate), MESSAGE_CONSTRAINTS);
        this.startDate = startDate;
        this.endDate = endDate;
        this.deliveryDays.addAll(deliveryDays);
        this.deliveryTime = deliveryTime;
    }

    /**
     * Returns true if the given date range is valid
     * ({@code startDate} is not after {@code endDate}).
     */
    public static boolean isValidDateRange(StartDate startDate, EndDate endDate) {
        return isValidDeliveryDateRange(startDate.date, endDate.date);
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
     * Returns a formatted string of the delivery schedule for display.
     *
     * @return Formatted string containing the start date, end date, and time of delivery.
     */
    public String getFormattedDeliverySchedule() {
        assert startDate != null;
        assert endDate != null;
        assert deliveryTime != null;

        return startDate + " to " + endDate + "  |  " + deliveryTime;
    }

    /**
     * Returns an immutable set of delivery day names.
     * <p>Example of delivery day names: Monday, Tuesday ...
     *
     * @return A set of delivery day names.
     */
    public Set<String> getDeliveryDayNames() {
        return deliveryDays
                .stream()
                .map(DeliveryDay::toString)
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Checks if delivery's end date is before the specified date.
     *
     * @param beforeDate Date to compare against.
     * @return {@code true} if delivery's end date occurs before the specified date,
     *         {@code false} otherwise.
     */
    public boolean hasExpired(LocalDate beforeDate) {
        assert beforeDate != null;

        return endDate.isBefore(beforeDate);
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
                && deliveryTime.equals(otherDelivery.deliveryTime);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(startDate, endDate, deliveryDays, deliveryTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("startDate", startDate)
                .add("endDate", endDate)
                .add("deliveryDays", deliveryDays)
                .add("deliveryTime", deliveryTime)
                .toString();
    }
}

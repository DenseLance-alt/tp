package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DateTimeUtil.parseDeliveryTime;

import java.time.LocalTime;

import seedu.address.commons.util.DateTimeUtil;

//@@author BenedTj
/**
 * Represents a Delivery's time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeliveryTime(String)}
 */
public class DeliveryTime implements Comparable<DeliveryTime> {
    public static final String MESSAGE_CONSTRAINTS =
            "Time should be of the valid delivery time format HH:mm and between 00:00 and 23:59 inclusive";

    public final LocalTime time;

    /**
     * Constructs a {@code DeliveryTime}.
     *
     * @param time A valid time string in the valid format.
     *             It must not be null.
     */
    public DeliveryTime(String time) {
        requireNonNull(time);
        checkArgument(isValidDeliveryTime(time), MESSAGE_CONSTRAINTS);
        this.time = parseDeliveryTime(time);
    }

    /**
     * Returns {@code true} if a given string is a valid time
     * in the valid format and {@code false} otherwise.
     *
     * @param test The raw string
     */
    public static boolean isValidDeliveryTime(String test) {
        return DateTimeUtil.isValidDeliveryTime(test);
    }

    //@@author elijah-ng
    /**
     * Compares this {@code DeliveryTime} to another {@code DeliveryTime} based on their time.
     * The comparison is based on the time-line position of the local times within a day.
     *
     * @param otherDeliveryTime The other {@code DeliveryTime} to compare to, not null.
     * @return The comparator value, negative if less, zero if equals, positive if greater.
     */
    @Override
    public int compareTo(DeliveryTime otherDeliveryTime) {
        assert otherDeliveryTime != null;

        return time.compareTo(otherDeliveryTime.time);
    }

    //@@author BenedTj
    @Override
    public String toString() {
        return time.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliveryTime)) {
            return false;
        }

        DeliveryTime otherTime = (DeliveryTime) other;
        return time.equals(otherTime.time);
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }
}

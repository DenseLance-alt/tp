package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DateTimeUtil.parseDeliveryTime;

import java.time.LocalTime;

import seedu.address.commons.util.DateTimeUtil;

/**
 * Represents a Delivery's time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeliveryTime(String)}
 */
public class DeliveryTime {
    public static final String MESSAGE_CONSTRAINTS =
            "Time should be of the valid delivery time format";

    public final LocalTime time;

    /**
     * Constructs a {@code DeliveryTime}.
     *
     * @param time A valid time string in the valid format.
     */
    public DeliveryTime(String time) {
        requireNonNull(time);
        checkArgument(isValidDeliveryTime(time), MESSAGE_CONSTRAINTS);
        this.time = parseDeliveryTime(time);
    }

    /**
     * Returns true if a given string is a valid time
     * in the valid format.
     */
    public static boolean isValidDeliveryTime(String test) {
        return DateTimeUtil.isValidDeliveryTime(test);
    }

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

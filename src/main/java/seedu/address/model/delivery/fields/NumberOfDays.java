package seedu.address.model.delivery.fields;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Delivery's number of days in a command field.
 * Guarantees: immutable; is valid as declared in {@link #isValidNumberOfDays(String)}
 */
public class NumberOfDays {
    public static final String MESSAGE_CONSTRAINTS =
            "number of days should be a positive number";

    public final int days;

    /**
     * Constructs a {@code NumberOfDays}
     *
     * @param numberOfDays A valid positive number of days in string.
     */
    public NumberOfDays(String numberOfDays) {
        requireNonNull(numberOfDays);
        checkArgument(isValidNumberOfDays(numberOfDays), MESSAGE_CONSTRAINTS);
        this.days = Integer.parseUnsignedInt(numberOfDays);
    }

    /**
     * Returns true if a given string is a valid
     * positive number of days.
     */
    public static boolean isValidNumberOfDays(String test) {
        if (test == null) {
            throw new NullPointerException();
        }

        try {
            int number = Integer.parseUnsignedInt(test);
            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(days);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NumberOfDays)) {
            return false;
        }

        NumberOfDays otherNumber = (NumberOfDays) other;
        return days == otherNumber.days;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(days);
    }
}

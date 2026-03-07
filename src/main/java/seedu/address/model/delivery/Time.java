package seedu.address.model.delivery;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Delivery's time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {
    public static final String MESSAGE_CONSTRAINTS =
            "Time should be of the valid time format";

    /**
     * The time must follow the format HHmm
     * where HH is the hour value in the 24-hour format
     * and mm is the minute value.
     */
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");

    public final LocalTime time;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid time string in the valid format.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        this.time = LocalTime.parse(time, formatter);
    }

    /**
     * Returns true if a given string is a valid time
     * in the valid format.
     */
    public static boolean isValidTime(String test) {
        try {
            LocalTime.parse(test, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
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
        if (!(other instanceof Time)) {
            return false;
        }

        Time otherTime = (Time) other;
        return time.equals(otherTime.time);
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }
}

package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Represents a Delivery's day in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeliveryDay(String)}
 */
public class DeliveryDay {
    public static final String MESSAGE_CONSTRAINTS =
            "day should be of the valid delivery day format";

    /**
     * The day must follow the format of
     * having the complete day of the week word.
     *
     * Examples of day inputs accepted by the formatter: Monday, Tuesday.
     *
     * Examples of valid input from the user
     * (after capitalization and lowercasing of some letters): monday, TUESDAY, WEDnesDay.
     */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH);

    public final DayOfWeek day;

    /**
     * Constructs a {@code DeliveryDay}
     *
     * @param day A valid day string in the valid format.
     */
    public DeliveryDay(String day) {
        requireNonNull(day);
        checkArgument(isValidDeliveryDay(day), MESSAGE_CONSTRAINTS);
        String dayWithCorrectFormat = day.substring(0, 1).toUpperCase()
                + day.substring(1).toLowerCase();
        this.day = DayOfWeek.from(FORMATTER.parse(dayWithCorrectFormat));
    }

    /**
     * Returns true if a given string is a valid
     * day of the week in the valid format.
     */
    public static boolean isValidDeliveryDay(String test) {
        try {
            // To prevent out of bounds access.
            if (test.isEmpty()) {
                return false;
            }

            String testWithCorrectFormat = test.substring(0, 1).toUpperCase()
                    + test.substring(1).toLowerCase();
            DayOfWeek.from(FORMATTER.parse(testWithCorrectFormat));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return day.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliveryDay)) {
            return false;
        }

        DeliveryDay otherDay = (DeliveryDay) other;
        return day.equals(otherDay.day);
    }

    @Override
    public int hashCode() {
        return day.hashCode();
    }
}

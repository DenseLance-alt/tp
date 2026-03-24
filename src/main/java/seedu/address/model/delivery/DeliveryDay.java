package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DateTimeUtil.isValidDeliveryDayWord;
import static seedu.address.commons.util.DateTimeUtil.parseDeliveryDayWord;

import java.time.DayOfWeek;

/**
 * Represents a Delivery's day in the address book.
 * Guarantees: immutable;
 * is valid as declared in {@link #isValidDeliveryDay(String)}
 */
public enum DeliveryDay {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

    public static final String MESSAGE_CONSTRAINTS =
            "day should be of the valid delivery day format";

    private DayOfWeek day;

    /**
     * Private constructor for enum to store DayOfWeek value.
     */
    DeliveryDay(String day) {
        this.day = parseDeliveryDayWord(day);
    }

    /**
     * Returns a {@code DeliveryDay}
     *
     * @param day A valid day string in the valid format.
     */
    public static DeliveryDay toDeliveryDay(String day) {
        requireNonNull(day);
        checkArgument(isValidDeliveryDay(day), MESSAGE_CONSTRAINTS);
        DayOfWeek dayValue = parseDeliveryDayWord(day);

        for (DeliveryDay deliveryDay : DeliveryDay.values()) {
            if (deliveryDay.day.equals(dayValue)) {
                return deliveryDay;
            }
        }

        String exceptionMessage = "Value " + day
                + " is not one of the DeliveryDay enum values but is valid based on isValidDeliveryDay";

        throw new IllegalArgumentException(exceptionMessage);
    }

    /**
     * Returns the corresponding DayOfWeek object.
     */
    public DayOfWeek getDay() {
        return day;
    }

    /**
     * Returns true if the day passed as the argument has the same value
     * as the day stored in the enum value.
     */
    public boolean isSameDay(DayOfWeek otherDay) {
        return day.equals(otherDay);
    }

    /**
     * Returns true if a given string is a valid
     * day of the week in the valid format.
     */
    public static boolean isValidDeliveryDay(String test) {
        return isValidDeliveryDayWord(test);
    }

    @Override
    public String toString() {
        return day.toString();
    }
}

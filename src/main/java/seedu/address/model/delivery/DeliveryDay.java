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
            "Day should be of the valid delivery day format such as Monday and Tuesday";

    private DayOfWeek day;

    /**
     * Private constructor for enum to store DayOfWeek value.
     *
     * @param day The raw string that represents
     *            the day of delivery and should be parsed.
     *            It must not be null.
     */
    DeliveryDay(String day) {
        this.day = parseDeliveryDayWord(day);
    }

    /**
     * Returns a {@code DeliveryDay}
     *
     * @param day A valid day string in the valid format.
     *            It must not be null.
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
     * Returns {@code true} if the day passed as the argument has the same value
     * as the day stored in the enum value and {@code false} otherwise.
     *
     * @param otherDay The DayOfWeek object that this object
     *                 should be compared to.
     * @return Boolean whether the DayOfWeek object stored
     *         in this object is equal to otherDay.
     */
    public boolean isSameDay(DayOfWeek otherDay) {
        return day.equals(otherDay);
    }

    /**
     * Returns true if a given string is a valid
     * day of the week in the valid format.
     *
     * @param test The raw string to be checked whether
     *             it is a valid delivery day.
     * @return Boolean whether the given string {@code test}
     *         is a valid delivery day.
     */
    public static boolean isValidDeliveryDay(String test) {
        return isValidDeliveryDayWord(test);
    }

    @Override
    public String toString() {
        return day.toString();
    }
}

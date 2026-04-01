package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.Locale;

/**
 * Helper functions for handling dates, days and times.
 */
public class DateTimeUtil {
    // FORMATTER_DATE implementation below written by ChatGPT
    /**
     * The date must follow the format yyyy-MM-dd
     * where yyyy is the 4-digit year, MM is the 2-digit month number,
     * and dd is the 2-digit date number.
     */
    public static final DateTimeFormatter FORMATTER_DATE =
            new DateTimeFormatterBuilder()
                    .appendValue(ChronoField.YEAR, 4)
                    .appendLiteral('-')
                    .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                    .appendLiteral('-')
                    .appendValue(ChronoField.DAY_OF_MONTH, 2)
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);

    /**
     * The day input that uses this formatter must follow the format of
     * having the number representing the day of the week.
     * <p>Examples of day number inputs accepted by the formatter: 1, 2.
     * The formatter will only successfully parse numbers in the range 1-7.
     */
    public static final DateTimeFormatter FORMATTER_DAY_NUMBER =
            DateTimeFormatter.ofPattern("e", Locale.UK).withResolverStyle(ResolverStyle.STRICT);

    /**
     * The day must follow the format of
     * having the complete day of the week word.
     *
     * Examples of day inputs accepted by the formatter: Monday, Tuesday.
     *
     * Examples of valid input from the user
     * (after capitalization and lowercasing of some letters): monday, TUESDAY, WEDnesDay.
     */
    public static final DateTimeFormatter FORMATTER_DAY_WORD =
            DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH).withResolverStyle(ResolverStyle.STRICT);

    /**
     * The time must follow the format HH:mm
     * where HH is the hour value in the 24-hour format
     * and mm is the minute value.
     */
    private static final DateTimeFormatter FORMATTER_TIME =
            DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT);

    /**
     * Returns true if a given string is a valid delivery date
     * in the valid format.
     */
    public static boolean isValidDeliveryDate(String test) {
        try {
            LocalDate.parse(test, FORMATTER_DATE);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Parses {@code date} into a {@code LocalDate} and returns it.
     *
     * @throws NullPointerException If the argument passed is null.
     * @throws DateTimeParseException If the argument passed is an invalid delivery date value.
     */
    public static LocalDate parseDeliveryDate(String date) throws NullPointerException, DateTimeParseException {
        requireNonNull(date, "delivery date must not be null");
        return LocalDate.parse(date, FORMATTER_DATE);
    }

    /**
     * Formats {@code date} into a {@code String}
     * with the format of {@link #FORMATTER_DATE} and returns it.
     */
    public static String formatDeliveryDate(LocalDate date) {
        requireNonNull(date, "delivery date must not be null");
        return FORMATTER_DATE.format(date);
    }

    /**
     * Returns true if a given string is a valid
     * day of the week in the valid format.
     */
    public static boolean isValidDeliveryDayWord(String test) {
        try {
            // To prevent out of bounds access.
            if (test.isEmpty()) {
                return false;
            }

            String testWithCorrectFormat = test.substring(0, 1).toUpperCase()
                    + test.substring(1).toLowerCase();
            DayOfWeek.from(FORMATTER_DAY_WORD.parse(testWithCorrectFormat));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Parses {@code day} into a {@code DayOfWeek} and returns it.
     *
     * @throws NullPointerException If the argument passed is null.
     * @throws DateTimeParseException If the argument passed is an invalid delivery day word value.
     * @throws IllegalArgumentException If an empty string is passed as the argument.
     */
    public static DayOfWeek parseDeliveryDayWord(String day)
            throws NullPointerException, DateTimeParseException, IllegalArgumentException {
        requireNonNull(day, "delivery day word must not be null");
        if (day.isEmpty()) {
            throw new IllegalArgumentException();
        }

        String dayWithCorrectFormat = day.substring(0, 1).toUpperCase()
                + day.substring(1).toLowerCase();
        return DayOfWeek.from(FORMATTER_DAY_WORD.parse(dayWithCorrectFormat));
    }

    /**
     * Returns true if a given string is a valid
     * number representing the day of the week.
     * It should only accept numbers 1-7.
     */
    public static boolean isValidDeliveryDayNumber(String test) {
        try {
            DayOfWeek.from(FORMATTER_DAY_NUMBER.parse(test));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns the word that represents
     * the day of week obtained from the day number.
     * Output examples: Monday, Tuesday, Thursday.
     *
     * @param dayNumber The number representing the day of the week as a string.
     * @return The full word that represents the day of the week.
     * @throws NullPointerException If the argument passed is null.
     * @throws DateTimeParseException If the argument passed is an invalid delivery day number value.
     */
    public static String convertDayNumberToDayWord(String dayNumber)
            throws NullPointerException, DateTimeParseException {
        requireNonNull(dayNumber, "delivery day number must not be null");
        DayOfWeek day = DayOfWeek.from(FORMATTER_DAY_NUMBER.parse(dayNumber));
        return day.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    /**
     * Formats {@code day} into a {@code String}
     * with the format of {@link #FORMATTER_DAY_NUMBER} and returns it.
     */
    public static String formatDayNumber(DayOfWeek day) {
        return FORMATTER_DAY_NUMBER.format(day);
    }

    /**
     * Returns true if a given string is a valid time
     * in the valid format.
     */
    public static boolean isValidDeliveryTime(String test) {
        try {
            LocalTime.parse(test, FORMATTER_TIME);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Parses {@code time} into a {@code LocalTime} and returns it.
     *
     * @throws NullPointerException If the argument passed is null.
     * @throws DateTimeParseException If the argument passed is an invalid delivery time value.
     */
    public static LocalTime parseDeliveryTime(String time) throws NullPointerException, DateTimeParseException {
        requireNonNull(time, "delivery time must not be null");
        return LocalTime.parse(time, FORMATTER_TIME);
    }

    /**
     * Returns true if the date range is valid
     * ({@code startDate} is not after {@code endDate}).
     */
    public static boolean isValidDeliveryDateRange(LocalDate startDate, LocalDate endDate) {
        return !startDate.isAfter(endDate);
    }
}

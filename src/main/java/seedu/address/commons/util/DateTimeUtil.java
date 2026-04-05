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
     * Returns {@code true} if a given string is a valid delivery date
     * in the valid format and {@code false} otherwise.
     *
     * @param test The given string to be verified
     *             whether it is a valid delivery date.
     * @return Boolean signifying whether the string is a valid delivery date.
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
     * @param date The string that is attempted to be parsed into LocalDate.
     *             It should not be null or an invalid delivery date.
     * @return LocalDate object representing the parsed date.
     */
    public static LocalDate parseDeliveryDate(String date) {
        requireNonNull(date, "delivery date must not be null");
        return LocalDate.parse(date, FORMATTER_DATE);
    }

    /**
     * Formats {@code date} into a {@code String}
     * with the format of {@link #FORMATTER_DATE} and returns it.
     *
     * @param date The LocalDate object to be formatted into a string.
     *             It should not be null.
     * @return String that is the formatted version of the date.
     */
    public static String formatDeliveryDate(LocalDate date) {
        requireNonNull(date, "delivery date must not be null");
        return FORMATTER_DATE.format(date);
    }

    /**
     * Returns {@code true} if a given string is a valid
     * day of the week in the valid format and {@code false} otherwise.
     *
     * @param test The given string to be verified
     *             whether it is a valid delivery day word.
     * @return Boolean signifying whether the string
     *         is a valid delivery day word.
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
     * @param day The string to be parsed into DayOfWeek.
     *            It should not be null, an empty string
     *            or an invalid delivery day word.
     * @return DayOfWeek object representing
     *         the parsed day word value.
     */
    public static DayOfWeek parseDeliveryDayWord(String day) {
        requireNonNull(day, "delivery day word must not be null");
        if (day.isEmpty()) {
            throw new IllegalArgumentException();
        }

        String dayWithCorrectFormat = day.substring(0, 1).toUpperCase()
                + day.substring(1).toLowerCase();
        return DayOfWeek.from(FORMATTER_DAY_WORD.parse(dayWithCorrectFormat));
    }

    /**
     * Returns {@code true} if a given string is a valid number
     * representing the day of the week and {@code false} otherwise.
     * It should only accept numbers 1-7.
     *
     * @param test The given string to be verified
     *             whether it is a valid delivery day number.
     * @return Boolean signifying whether the string
     *         is a valid delivery day number.
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
     *                  It should not be null or an invalid delivery day number.
     * @return The full word that represents the day of the week.
     */
    public static String convertDayNumberToDayWord(String dayNumber) {
        requireNonNull(dayNumber, "delivery day number must not be null");
        DayOfWeek day = DayOfWeek.from(FORMATTER_DAY_NUMBER.parse(dayNumber));
        return day.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    /**
     * Formats {@code day} into a {@code String}
     * with the format of {@link #FORMATTER_DAY_NUMBER} and returns it.
     *
     * @param day The DayOfWeek object to be formatted into a string.
     *            It should not be null.
     * @return String that is the formatted version of the day number value.
     */
    public static String formatDayNumber(DayOfWeek day) {
        requireNonNull(day, "delivery day must not be null");
        return FORMATTER_DAY_NUMBER.format(day);
    }

    /**
     * Returns {@code true} if a given string is a valid time
     * in the valid format and {@code false} otherwise.
     *
     * @param test The given string to be verified
     *             whether it is a valid delivery time.
     * @return Boolean signifying whether the string
     *         is a valid delivery time.
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
     * @param time The string attempted to be parsed into LocalTime.
     *             It should not be null or an invalid delivery time.
     * @return LocalTime object representing the parsed time value.
     */
    public static LocalTime parseDeliveryTime(String time) {
        requireNonNull(time, "delivery time must not be null");
        return LocalTime.parse(time, FORMATTER_TIME);
    }

    /**
     * Returns {@code true} if the date range is valid
     * ({@code startDate} is not after {@code endDate})
     * and {@code false} otherwise.
     *
     * @param startDate The LocalDate object representing
     *                  the start date.
     * @param endDate The LocalDate object representing
     *                the end date.
     * @return Boolean representing whether the start date
     *         is not after the end date.
     */
    public static boolean isValidDeliveryDateRange(LocalDate startDate, LocalDate endDate) {
        return !startDate.isAfter(endDate);
    }
}

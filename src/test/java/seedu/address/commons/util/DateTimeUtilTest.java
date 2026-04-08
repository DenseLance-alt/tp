package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

public class DateTimeUtilTest {
    @Test
    public void isValidDeliveryDate() {
        // EP: null delivery date -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> DateTimeUtil.isValidDeliveryDate(null));

        // EP: string does not contain complete date -> returns false
        // Boundary value: empty string
        assertFalse(DateTimeUtil.isValidDeliveryDate(""));
        assertFalse(DateTimeUtil.isValidDeliveryDate(" ")); // spaces only
        assertFalse(DateTimeUtil.isValidDeliveryDate("12")); // only date number
        assertFalse(DateTimeUtil.isValidDeliveryDate("2012")); // only year
        assertFalse(DateTimeUtil.isValidDeliveryDate("01-12")); // does not contain year
        assertFalse(DateTimeUtil.isValidDeliveryDate("2020-01")); // does not contain date number

        // EP: string with date of the wrong number of digits -> returns false
        assertFalse(DateTimeUtil.isValidDeliveryDate("2020-1-01")); // month with only 1 digit
        assertFalse(DateTimeUtil.isValidDeliveryDate("2020-123-01")); // month with more than 2 digits
        assertFalse(DateTimeUtil.isValidDeliveryDate("2020-11-2")); // day with only 1 digit
        assertFalse(DateTimeUtil.isValidDeliveryDate("2020-11-401")); // day with more than 2 digits
        // years with more than 4 digits
        assertFalse(DateTimeUtil.isValidDeliveryDate("+99999999-12-31"));
        assertFalse(DateTimeUtil.isValidDeliveryDate("-99999999-01-01"));

        // EP: string with date of the correct number of digits but incorrect format and invalidity -> returns false
        assertFalse(DateTimeUtil.isValidDeliveryDate("12-01-2026")); // incorrect format
        assertFalse(DateTimeUtil.isValidDeliveryDate("2020-02-31")); // invalid date
        assertFalse(DateTimeUtil.isValidDeliveryDate("2021-02-29")); // invalid date for non-leap year
        assertFalse(DateTimeUtil.isValidDeliveryDate("-1000-12-3")); // negative year
        assertFalse(DateTimeUtil.isValidDeliveryDate("2020-15-01")); // invalid month
        assertFalse(DateTimeUtil.isValidDeliveryDate("2020-11-31")); // invalid day for month
        assertFalse(DateTimeUtil.isValidDeliveryDate("2020-11-63")); // invalid day

        // EP: string with valid date of the correct number of digits -> returns true
        assertTrue(DateTimeUtil.isValidDeliveryDate("2019-10-15")); // correct format
        assertTrue(DateTimeUtil.isValidDeliveryDate("2020-02-29")); // valid date for a leap year
    }

    @Test
    public void parseDeliveryDate() {
        // EP: null delivery date -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> DateTimeUtil.parseDeliveryDate(null));

        // EP: invalid delivery dates -> throws DateTimeParseException
        // Boundary value: empty string
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.parseDeliveryDate(""));
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.parseDeliveryDate(" ")); // spaces only
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.parseDeliveryDate("12")); // only date number
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.parseDeliveryDate("2012")); // only year
        assertThrows(DateTimeParseException.class, () ->
                DateTimeUtil.parseDeliveryDate("01-12")); // does not contain year
        assertThrows(DateTimeParseException.class, () ->
                DateTimeUtil.parseDeliveryDate("2020-01")); // does not contain date number
        assertThrows(DateTimeParseException.class, () ->
                DateTimeUtil.parseDeliveryDate("12-01-2026")); // incorrect format
        assertThrows(DateTimeParseException.class, () ->
                DateTimeUtil.parseDeliveryDate("2020-02-31")); // invalid date

        // EP: string with a valid date -> returns appropriate LocalDate object
        assertEquals(LocalDate.of(2019, 10, 15),
                     DateTimeUtil.parseDeliveryDate("2019-10-15")); // correct format
    }

    @Test
    public void formatDeliveryDate() {
        // EP: null -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> DateTimeUtil.formatDeliveryDate(null));

        // EP: valid LocalDate object -> returns appropriate string with date
        LocalDate date = LocalDate.of(2019, 10, 15);
        assertEquals("2019-10-15", DateTimeUtil.formatDeliveryDate(date));
    }

    @Test
    public void isValidDeliveryDayWord() {
        // EP: null day -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> DateTimeUtil.isValidDeliveryDayWord(null));

        // EP: string without day -> returns false
        // Boundary value: empty string
        assertFalse(DateTimeUtil.isValidDeliveryDayWord(""));
        assertFalse(DateTimeUtil.isValidDeliveryDayWord(" "));

        // EP: string with incorrect day format -> returns false
        assertFalse(DateTimeUtil.isValidDeliveryDayWord("Mon"));

        // EP: string with valid day -> returns true
        assertTrue(DateTimeUtil.isValidDeliveryDayWord("Monday"));
        assertTrue(DateTimeUtil.isValidDeliveryDayWord("thursday"));
        assertTrue(DateTimeUtil.isValidDeliveryDayWord("FRIDAY"));
        assertTrue(DateTimeUtil.isValidDeliveryDayWord("WEDnesDay"));
    }

    @Test
    public void parseDeliveryDayWord() {
        // EP: null day -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> DateTimeUtil.parseDeliveryDayWord(null));


        // EP: string without day -> throws DateTimeParseException
        // Boundary value: empty string
        assertThrows(IllegalArgumentException.class, () -> DateTimeUtil.parseDeliveryDayWord(""));
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.parseDeliveryDayWord(" "));

        // EP: string with incorrect day format -> throws DateTimeParseException
        assertThrows(DateTimeParseException.class, () ->
                DateTimeUtil.parseDeliveryDayWord("Mon")); // only 3-character day

        // EP: string with valid day -> returns appropriate DayOfWeek object
        assertEquals(DayOfWeek.MONDAY, DateTimeUtil.parseDeliveryDayWord("Monday"));
        assertEquals(DayOfWeek.THURSDAY, DateTimeUtil.parseDeliveryDayWord("thursday"));
        assertEquals(DayOfWeek.FRIDAY, DateTimeUtil.parseDeliveryDayWord("FRIDAY"));
        assertEquals(DayOfWeek.WEDNESDAY, DateTimeUtil.parseDeliveryDayWord("WEDnesDay"));
    }

    @Test
    public void isValidDeliveryDayNumber() {
        // EP: null day number -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> DateTimeUtil.isValidDeliveryDayNumber(null));

        // EP: string without day -> returns false
        // Boundary value: empty string
        assertFalse(DateTimeUtil.isValidDeliveryDayNumber(""));
        assertFalse(DateTimeUtil.isValidDeliveryDayNumber(" "));

        // EP: string with no number -> returns false
        assertFalse(DateTimeUtil.isValidDeliveryDayNumber("Mon")); // 3-character day
        // full word representing the day
        assertFalse(DateTimeUtil.isValidDeliveryDayNumber("Monday"));
        assertFalse(DateTimeUtil.isValidDeliveryDayNumber("thursday"));
        assertFalse(DateTimeUtil.isValidDeliveryDayNumber("FRIDAY"));
        assertFalse(DateTimeUtil.isValidDeliveryDayNumber("WEDnesDay"));

        // EP: string with invalid numbers -> returns false
        assertFalse(DateTimeUtil.isValidDeliveryDayNumber("-3")); // negative number
        // Boundary value: 0 as an invalid delivery day number
        assertFalse(DateTimeUtil.isValidDeliveryDayNumber("0")); // zero value
        // Boundary value: 8 as an invalid delivery day number
        assertFalse(DateTimeUtil.isValidDeliveryDayNumber("8")); // number exceeding 7

        // EP: string containing only valid delivery day number -> returns true
        // Boundary value: 1 as the smallest delivery day number possible
        assertTrue(DateTimeUtil.isValidDeliveryDayNumber("1"));
        assertTrue(DateTimeUtil.isValidDeliveryDayNumber("3"));
        assertTrue(DateTimeUtil.isValidDeliveryDayNumber("4"));
        // Boundary value: 7 as the largest delivery day number possible
        assertTrue(DateTimeUtil.isValidDeliveryDayNumber("7"));
    }

    @Test
    public void convertDayNumberToDayWord() {
        // EP: null day number -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> DateTimeUtil.convertDayNumberToDayWord(null));

        // EP: string without day -> throws DateTimeParseException
        // Boundary value: empty string
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.convertDayNumberToDayWord(""));
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.convertDayNumberToDayWord(" "));

        // EP: string with no number -> throws DateTimeParseException
        assertThrows(DateTimeParseException.class, () ->
                DateTimeUtil.convertDayNumberToDayWord("Mon")); // 3-character day
        // full word representing the day
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.convertDayNumberToDayWord("Monday"));
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.convertDayNumberToDayWord("thursday"));
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.convertDayNumberToDayWord("FRIDAY"));
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.convertDayNumberToDayWord("WEDnesDay"));

        // EP: string with invalid numbers -> throws DateTimeParseException
        assertThrows(DateTimeParseException.class, () ->
                DateTimeUtil.convertDayNumberToDayWord("-3")); // negative number
        // Boundary value: 0 as an invalid delivery day number
        assertThrows(DateTimeParseException.class, () ->
                DateTimeUtil.convertDayNumberToDayWord("0")); // zero value
        // Boundary value: 8 as an invalid delivery day number
        assertThrows(DateTimeParseException.class, () ->
                DateTimeUtil.convertDayNumberToDayWord("8")); // number exceeding 7

        // EP: string containing only valid delivery day number -> returns appropriate string with day word
        // Boundary value: 1 as the smallest delivery day number possible
        assertEquals("Monday", DateTimeUtil.convertDayNumberToDayWord("1"));
        assertEquals("Wednesday", DateTimeUtil.convertDayNumberToDayWord("3"));
        assertEquals("Thursday", DateTimeUtil.convertDayNumberToDayWord("4"));
        // Boundary value: 7 as the largest delivery day number possible
        assertEquals("Sunday", DateTimeUtil.convertDayNumberToDayWord("7"));
    }

    @Test
    public void isValidDeliveryTime() {
        // EP: null time -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> DateTimeUtil.isValidDeliveryTime(null));

        // EP: string with no time -> returns false
        // Boundary value: empty string
        assertFalse(DateTimeUtil.isValidDeliveryTime(""));
        assertFalse(DateTimeUtil.isValidDeliveryTime(" ")); // spaces only
        assertFalse(DateTimeUtil.isValidDeliveryTime("12")); // only one number
        assertFalse(DateTimeUtil.isValidDeliveryTime("01-12")); // does not contain year
        assertFalse(DateTimeUtil.isValidDeliveryTime("2020-01")); // does not contain date number
        assertFalse(DateTimeUtil.isValidDeliveryTime("2026-01-12")); // date format

        // EP: string with valid time -> returns true
        assertTrue(DateTimeUtil.isValidDeliveryTime("12:59"));
    }

    @Test
    public void parseDeliveryTime() {
        // EP: null time -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> DateTimeUtil.parseDeliveryTime(null));

        // EP: string with no time -> throws DateTimeParseException
        // Boundary value: empty string
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.parseDeliveryTime(""));
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.parseDeliveryTime(" ")); // spaces only
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.parseDeliveryTime("12")); // only one number
        assertThrows(DateTimeParseException.class, () ->
                DateTimeUtil.parseDeliveryTime("01-12")); // does not contain year
        assertThrows(DateTimeParseException.class, () ->
                DateTimeUtil.parseDeliveryTime("2020-01")); // does not contain date number
        assertThrows(DateTimeParseException.class, () ->
                DateTimeUtil.parseDeliveryTime("2026-01-12")); // date format

        // EP: string with valid time -> returns appropriate LocalTime object
        assertEquals(LocalTime.of(12, 59), DateTimeUtil.parseDeliveryTime("12:59"));
    }

    // EP: start date after end date -> returns false
    @Test
    public void isValidDeliveryDateRange_invalidDateRange_returnsFalse() {
        LocalDate startDate = LocalDate.of(2026, 12, 1);
        LocalDate endDate = startDate.minusDays(5);

        assertFalse(DateTimeUtil.isValidDeliveryDateRange(startDate, endDate));
    }

    // EP: start date not after end date -> returns true
    // Boundary value: same dates
    @Test
    public void isValidDeliveryDateRange_validDateRange_returnsTrue() {
        LocalDate startDate = LocalDate.of(2026, 12, 1);
        LocalDate endDate = startDate.minusDays(0);

        assertTrue(DateTimeUtil.isValidDeliveryDateRange(startDate, endDate));
    }
}

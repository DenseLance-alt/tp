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
        // null delivery date
        assertThrows(NullPointerException.class, () -> DateTimeUtil.isValidDeliveryDate(null));

        // invalid delivery dates
        assertFalse(DateTimeUtil.isValidDeliveryDate("")); // empty string
        assertFalse(DateTimeUtil.isValidDeliveryDate(" ")); // spaces only
        assertFalse(DateTimeUtil.isValidDeliveryDate("12")); // only date number
        assertFalse(DateTimeUtil.isValidDeliveryDate("2012")); // only year
        assertFalse(DateTimeUtil.isValidDeliveryDate("01-12")); // does not contain year
        assertFalse(DateTimeUtil.isValidDeliveryDate("2020-01")); // does not contain date number
        assertFalse(DateTimeUtil.isValidDeliveryDate("12-01-2026")); // incorrect format
        assertFalse(DateTimeUtil.isValidDeliveryDate("2020-02-31")); // invalid date

        // valid delivery date
        assertTrue(DateTimeUtil.isValidDeliveryDate("2019-10-15")); // correct format
    }

    @Test
    public void parseDeliveryDate() {
        // null delivery date
        assertThrows(NullPointerException.class, () -> DateTimeUtil.parseDeliveryDate(null));

        // invalid delivery dates
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.parseDeliveryDate("")); // empty string
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

        // valid delivery date
        assertEquals(LocalDate.of(2019, 10, 15),
                     DateTimeUtil.parseDeliveryDate("2019-10-15")); // correct format
    }

    @Test
    public void isValidDeliveryDayWord() {
        // null day
        assertThrows(NullPointerException.class, () -> DateTimeUtil.isValidDeliveryDayWord(null));

        // invalid days
        assertFalse(DateTimeUtil.isValidDeliveryDayWord("")); // empty string
        assertFalse(DateTimeUtil.isValidDeliveryDayWord(" ")); // spaces only
        assertFalse(DateTimeUtil.isValidDeliveryDayWord("Mon")); // only 3-character day

        // valid days
        assertTrue(DateTimeUtil.isValidDeliveryDayWord("Monday"));
        assertTrue(DateTimeUtil.isValidDeliveryDayWord("thursday"));
        assertTrue(DateTimeUtil.isValidDeliveryDayWord("FRIDAY"));
        assertTrue(DateTimeUtil.isValidDeliveryDayWord("WEDnesDay"));
    }

    @Test
    public void parseDeliveryDayWord() {
        // null day
        assertThrows(NullPointerException.class, () -> DateTimeUtil.parseDeliveryDayWord(null));

        // invalid days
        assertThrows(IllegalArgumentException.class, () -> DateTimeUtil.parseDeliveryDayWord("")); // empty string
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.parseDeliveryDayWord(" ")); // spaces only
        assertThrows(DateTimeParseException.class, () ->
                DateTimeUtil.parseDeliveryDayWord("Mon")); // only 3-character day

        // valid days
        assertEquals(DayOfWeek.MONDAY, DateTimeUtil.parseDeliveryDayWord("Monday"));
        assertEquals(DayOfWeek.THURSDAY, DateTimeUtil.parseDeliveryDayWord("thursday"));
        assertEquals(DayOfWeek.FRIDAY, DateTimeUtil.parseDeliveryDayWord("FRIDAY"));
        assertEquals(DayOfWeek.WEDNESDAY, DateTimeUtil.parseDeliveryDayWord("WEDnesDay"));
    }

    @Test
    public void isValidDeliveryDayNumber() {
        // null day numbers
        assertThrows(NullPointerException.class, () -> DateTimeUtil.isValidDeliveryDayNumber(null));

        // invalid day numbers
        assertFalse(DateTimeUtil.isValidDeliveryDayNumber("")); // empty string
        assertFalse(DateTimeUtil.isValidDeliveryDayNumber(" ")); // spaces only
        assertFalse(DateTimeUtil.isValidDeliveryDayNumber("Mon")); // 3-character day

        // full word representing the day
        assertFalse(DateTimeUtil.isValidDeliveryDayNumber("Monday"));
        assertFalse(DateTimeUtil.isValidDeliveryDayNumber("thursday"));
        assertFalse(DateTimeUtil.isValidDeliveryDayNumber("FRIDAY"));
        assertFalse(DateTimeUtil.isValidDeliveryDayNumber("WEDnesDay"));

        assertFalse(DateTimeUtil.isValidDeliveryDayNumber("-3")); // negative number
        assertFalse(DateTimeUtil.isValidDeliveryDayNumber("0")); // zero value
        assertFalse(DateTimeUtil.isValidDeliveryDayNumber("8")); // number exceeding 7

        // valid day numbers
        assertTrue(DateTimeUtil.isValidDeliveryDayNumber("1"));
        assertTrue(DateTimeUtil.isValidDeliveryDayNumber("3"));
        assertTrue(DateTimeUtil.isValidDeliveryDayNumber("4"));
        assertTrue(DateTimeUtil.isValidDeliveryDayNumber("7"));
    }

    @Test
    public void convertDayNumberToDayWord() {
        // null day numbers
        assertThrows(NullPointerException.class, () -> DateTimeUtil.convertDayNumberToDayWord(null));

        // invalid day numbers
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.convertDayNumberToDayWord("")); // empty string
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.convertDayNumberToDayWord(" ")); // spaces only
        assertThrows(DateTimeParseException.class, () ->
                DateTimeUtil.convertDayNumberToDayWord("Mon")); // 3-character day

        // full word representing the day
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.convertDayNumberToDayWord("Monday"));
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.convertDayNumberToDayWord("thursday"));
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.convertDayNumberToDayWord("FRIDAY"));
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.convertDayNumberToDayWord("WEDnesDay"));

        assertThrows(DateTimeParseException.class, () ->
                DateTimeUtil.convertDayNumberToDayWord("-3")); // negative number
        assertThrows(DateTimeParseException.class, () ->
                DateTimeUtil.convertDayNumberToDayWord("0")); // zero value
        assertThrows(DateTimeParseException.class, () ->
                DateTimeUtil.convertDayNumberToDayWord("8")); // number exceeding 7

        // valid day numbers
        assertEquals("Monday", DateTimeUtil.convertDayNumberToDayWord("1"));
        assertEquals("Wednesday", DateTimeUtil.convertDayNumberToDayWord("3"));
        assertEquals("Thursday", DateTimeUtil.convertDayNumberToDayWord("4"));
        assertEquals("Sunday", DateTimeUtil.convertDayNumberToDayWord("7"));
    }

    @Test
    public void isValidDeliveryTime() {
        // null time
        assertThrows(NullPointerException.class, () -> DateTimeUtil.isValidDeliveryTime(null));

        // invalid times
        assertFalse(DateTimeUtil.isValidDeliveryTime("")); // empty string
        assertFalse(DateTimeUtil.isValidDeliveryTime(" ")); // spaces only
        assertFalse(DateTimeUtil.isValidDeliveryTime("12")); // only one number
        assertFalse(DateTimeUtil.isValidDeliveryTime("01-12")); // does not contain year
        assertFalse(DateTimeUtil.isValidDeliveryTime("2020-01")); // does not contain date number
        assertFalse(DateTimeUtil.isValidDeliveryTime("2026-01-12")); // date format

        // valid time
        assertTrue(DateTimeUtil.isValidDeliveryTime("12:59")); // correct time format
    }

    @Test
    public void parseDeliveryTime() {
        // null time
        assertThrows(NullPointerException.class, () -> DateTimeUtil.parseDeliveryTime(null));

        // invalid times
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.parseDeliveryTime("")); // empty string
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.parseDeliveryTime(" ")); // spaces only
        assertThrows(DateTimeParseException.class, () -> DateTimeUtil.parseDeliveryTime("12")); // only one number
        assertThrows(DateTimeParseException.class, () ->
                DateTimeUtil.parseDeliveryTime("01-12")); // does not contain year
        assertThrows(DateTimeParseException.class, () ->
                DateTimeUtil.parseDeliveryTime("2020-01")); // does not contain date number
        assertThrows(DateTimeParseException.class, () ->
                DateTimeUtil.parseDeliveryTime("2026-01-12")); // date format

        // valid time
        assertEquals(LocalTime.of(12, 59), DateTimeUtil.parseDeliveryTime("12:59"));
    }
}

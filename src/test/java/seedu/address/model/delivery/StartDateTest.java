package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StartDateTest {

    // EP: null string -> throws NullPointerException
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StartDate(null));
    }

    // EP: string with invalid date -> throws IllegalArgumentException
    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        assertThrows(IllegalArgumentException.class, () -> new StartDate(invalidDate));
    }

    @Test
    public void isValidStartDate() {
        // EP: null start date -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> StartDate.isValidStartDate(null));

        // EP: string with no date -> returns false
        // Boundary value: empty string
        assertFalse(StartDate.isValidStartDate(""));
        assertFalse(StartDate.isValidStartDate(" ")); // spaces only
        assertFalse(StartDate.isValidStartDate("12")); // only date number
        assertFalse(StartDate.isValidStartDate("2012")); // only year
        assertFalse(StartDate.isValidStartDate("01-12")); // does not contain year
        assertFalse(StartDate.isValidStartDate("2020-01")); // does not contain date number

        // EP: string with date of the wrong number of digits -> returns false
        assertFalse(StartDate.isValidStartDate("2020-1-01")); // month with only 1 digit
        assertFalse(StartDate.isValidStartDate("2020-123-01")); // month with more than 2 digits
        assertFalse(StartDate.isValidStartDate("2020-11-2")); // day with only 1 digit
        assertFalse(StartDate.isValidStartDate("2020-11-401")); // day with more than 2 digits
        // years with more than 4 digits
        assertFalse(StartDate.isValidStartDate("+99999999-12-31"));
        assertFalse(StartDate.isValidStartDate("-99999999-01-01"));

        // EP: string with date of the correct number of digits but incorrect format and invalidity -> returns false
        assertFalse(StartDate.isValidStartDate("12-01-2026")); // incorrect format
        assertFalse(StartDate.isValidStartDate("2021-02-29")); // invalid date for non-leap year
        assertFalse(StartDate.isValidStartDate("2020-02-31")); // invalid date
        assertFalse(StartDate.isValidStartDate("-1000-12-3")); // negative year
        assertFalse(StartDate.isValidStartDate("2020-15-01")); // invalid month
        assertFalse(StartDate.isValidStartDate("2020-11-31")); // invalid day for month
        assertFalse(StartDate.isValidStartDate("2020-11-63")); // invalid day

        // EP: string with valid date of the correct number of digits -> returns true
        assertTrue(StartDate.isValidStartDate("2019-10-15")); // correct format
        assertTrue(StartDate.isValidStartDate("2020-02-29")); // valid date for a leap year
    }

    @Test
    public void equals() {
        StartDate date = new StartDate("2019-10-15");

        // EP: same values -> returns true
        assertTrue(date.equals(new StartDate("2019-10-15")));

        // EP: same object -> returns true
        assertTrue(date.equals(date));

        // EP: null -> returns false
        assertFalse(date.equals(null));

        // EP: different types -> returns false
        assertFalse(date.equals(5.0f));

        // EP: different values -> returns false
        assertFalse(date.equals(new StartDate("2020-11-12")));
    }
}

package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EndDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EndDate(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        assertThrows(IllegalArgumentException.class, () -> new EndDate(invalidDate));
    }

    @Test
    public void isValidEndDate() {
        // null end date
        assertThrows(NullPointerException.class, () -> EndDate.isValidEndDate(null));

        // invalid end dates
        assertFalse(EndDate.isValidEndDate("")); // empty string
        assertFalse(EndDate.isValidEndDate(" ")); // spaces only
        assertFalse(EndDate.isValidEndDate("12")); // only date number
        assertFalse(EndDate.isValidEndDate("2012")); // only year
        assertFalse(EndDate.isValidEndDate("01-12")); // does not contain year
        assertFalse(EndDate.isValidEndDate("2020-01")); // does not contain date number
        assertFalse(EndDate.isValidEndDate("12-01-2026")); // incorrect format
        assertFalse(EndDate.isValidEndDate("2020-02-31")); // invalid date
        assertFalse(EndDate.isValidEndDate("2021-02-29")); // invalid date for non-leap year
        assertFalse(EndDate.isValidEndDate("-1000-12-3")); // negative year
        assertFalse(EndDate.isValidEndDate("2020-15-01")); // invalid month
        assertFalse(EndDate.isValidEndDate("2020-1-01")); // month with only 1 digit
        assertFalse(EndDate.isValidEndDate("2020-123-01")); // month with more than 2 digits
        assertFalse(EndDate.isValidEndDate("2020-11-31")); // invalid day for month
        assertFalse(EndDate.isValidEndDate("2020-11-63")); // invalid day
        assertFalse(EndDate.isValidEndDate("2020-11-2")); // day with only 1 digit
        assertFalse(EndDate.isValidEndDate("2020-11-401")); // day with more than 2 digits
        // years with more than 4 digits
        assertFalse(EndDate.isValidEndDate("+99999999-12-31"));
        assertFalse(EndDate.isValidEndDate("-99999999-01-01"));

        // valid end date
        assertTrue(EndDate.isValidEndDate("2019-10-15")); // correct format
        assertTrue(EndDate.isValidEndDate("2020-02-29")); // valid date for a leap year
    }

    @Test
    public void equals() {
        EndDate date = new EndDate("2019-10-15");

        // same values -> returns true
        assertTrue(date.equals(new EndDate("2019-10-15")));

        // same object -> returns true
        assertTrue(date.equals(date));

        // null -> returns false
        assertFalse(date.equals(null));

        // different types -> returns false
        assertFalse(date.equals(5.0f));

        // different values -> returns false
        assertFalse(date.equals(new EndDate("2020-11-12")));
    }
}

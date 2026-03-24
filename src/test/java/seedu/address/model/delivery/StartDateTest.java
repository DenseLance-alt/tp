package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.DateTimeUtil;

public class StartDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StartDate(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        assertThrows(IllegalArgumentException.class, () -> new StartDate(invalidDate));
    }

    @Test
    public void isValidStartDate() {
        // null start date
        assertThrows(NullPointerException.class, () -> StartDate.isValidStartDate(null));

        // invalid start dates
        assertFalse(StartDate.isValidStartDate("")); // empty string
        assertFalse(StartDate.isValidStartDate(" ")); // spaces only
        assertFalse(StartDate.isValidStartDate("12")); // only date number
        assertFalse(StartDate.isValidStartDate("2012")); // only year
        assertFalse(StartDate.isValidStartDate("01-12")); // does not contain year
        assertFalse(StartDate.isValidStartDate("2020-01")); // does not contain date number
        assertFalse(StartDate.isValidStartDate("12-01-2026")); // incorrect format
        assertFalse(DateTimeUtil.isValidDeliveryDate("2020-02-31")); // invalid date

        // valid start date
        assertTrue(StartDate.isValidStartDate("2019-10-15")); // correct format
    }

    @Test
    public void equals() {
        StartDate date = new StartDate("2019-10-15");

        // same values -> returns true
        assertTrue(date.equals(new StartDate("2019-10-15")));

        // same object -> returns true
        assertTrue(date.equals(date));

        // null -> returns false
        assertFalse(date.equals(null));

        // different types -> returns false
        assertFalse(date.equals(5.0f));

        // different values -> returns false
        assertFalse(date.equals(new StartDate("2020-11-12")));
    }
}

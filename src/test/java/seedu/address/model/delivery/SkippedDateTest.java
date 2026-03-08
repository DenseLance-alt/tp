package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SkippedDateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SkippedDate(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        assertThrows(IllegalArgumentException.class, () -> new SkippedDate(invalidDate));
    }

    @Test
    public void isValidSkippedDate() {
        // null start date
        assertThrows(NullPointerException.class, () -> SkippedDate.isValidSkippedDate(null));

        // invalid start dates
        assertFalse(SkippedDate.isValidSkippedDate("")); // empty string
        assertFalse(SkippedDate.isValidSkippedDate(" ")); // spaces only
        assertFalse(SkippedDate.isValidSkippedDate("12")); // only date number
        assertFalse(SkippedDate.isValidSkippedDate("2012")); // only year
        assertFalse(SkippedDate.isValidSkippedDate("01-12")); // does not contain year
        assertFalse(SkippedDate.isValidSkippedDate("2020-01")); // does not contain date number
        assertFalse(SkippedDate.isValidSkippedDate("12-01-2026")); // incorrect format

        // valid start dates
        assertTrue(SkippedDate.isValidSkippedDate("2019-10-15")); // correct format
    }

    @Test
    public void equals() {
        SkippedDate date = new SkippedDate("2019-10-15");

        // same values -> returns true
        assertTrue(date.equals(new SkippedDate("2019-10-15")));

        // same object -> returns true
        assertTrue(date.equals(date));

        // null -> returns false
        assertFalse(date.equals(null));

        // different types -> returns false
        assertFalse(date.equals(5.0f));

        // different values -> returns false
        assertFalse(date.equals(new SkippedDate("2020-11-12")));
    }
}

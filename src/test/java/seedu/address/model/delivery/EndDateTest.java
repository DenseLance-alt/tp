package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.DateTimeUtil;

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
        assertFalse(DateTimeUtil.isValidDeliveryDate("2020-02-31")); // invalid date

        // valid end date
        assertTrue(EndDate.isValidEndDate("2019-10-15")); // correct format
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

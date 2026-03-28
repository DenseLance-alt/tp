package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

//@@author BenedTj
public class DeliveryTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeliveryTime(null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String invalidTime = "";
        assertThrows(IllegalArgumentException.class, () -> new DeliveryTime(invalidTime));
    }

    @Test
    public void isValidDeliveryTime() {
        // null time
        assertThrows(NullPointerException.class, () -> DeliveryTime.isValidDeliveryTime(null));

        // invalid times
        assertFalse(DeliveryTime.isValidDeliveryTime("")); // empty string
        assertFalse(DeliveryTime.isValidDeliveryTime(" ")); // spaces only
        assertFalse(DeliveryTime.isValidDeliveryTime("12")); // only one number
        assertFalse(DeliveryTime.isValidDeliveryTime("01-12")); // does not contain year
        assertFalse(DeliveryTime.isValidDeliveryTime("2020-01")); // does not contain date number
        assertFalse(DeliveryTime.isValidDeliveryTime("2026-01-12")); // date format

        // valid time
        assertTrue(DeliveryTime.isValidDeliveryTime("12:59")); // correct time format
    }

    //@@author elijah-ng
    // EP: Smaller time
    @Test
    public void compareTo_deliveryTimeSmaller_returnsNegative() {
        DeliveryTime earlierDeliveryTime = new DeliveryTime("00:00"); // Boundary value
        DeliveryTime laterDeliveryTime = new DeliveryTime("23:59"); // Boundary value
        assertTrue(earlierDeliveryTime.compareTo(laterDeliveryTime) < 0);
    }

    // EP: Same time
    @Test
    public void compareTo_deliveryTimeEquals_returnsZero() {
        DeliveryTime firstDeliveryTime = new DeliveryTime("12:00");
        DeliveryTime secondDeliveryTime = new DeliveryTime("12:00");
        assertEquals(0, firstDeliveryTime.compareTo(secondDeliveryTime));
    }

    // EP: Larger time
    @Test
    public void compareTo_deliveryTimeLarger_returnsPositive() {
        DeliveryTime earlierDeliveryTime = new DeliveryTime("00:00"); // Boundary value
        DeliveryTime laterDeliveryTime = new DeliveryTime("23:59"); // Boundary value
        assertTrue(laterDeliveryTime.compareTo(earlierDeliveryTime) > 0);
    }

    //@@author BenedTj
    @Test
    public void equals() {
        DeliveryTime time = new DeliveryTime("12:59");

        // same values -> returns true
        assertTrue(time.equals(new DeliveryTime("12:59")));

        // same object -> returns true
        assertTrue(time.equals(time));

        // null -> returns false
        assertFalse(time.equals(null));

        // different types -> returns false
        assertFalse(time.equals(5.0f));

        // different values -> returns false
        assertFalse(time.equals(new DeliveryTime("13:43")));
    }
}

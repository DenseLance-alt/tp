package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class DeliveryDayTest {

    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeliveryDay(null));
    }

    @Test
    public void constructor_invalidDay_throwsIllegalArgumentException() {
        String invalidDay = "";
        assertThrows(IllegalArgumentException.class, () -> new DeliveryDay(invalidDay));
    }

    @Test
    public void isValidDeliveryDay() {
        // null start date
        assertThrows(NullPointerException.class, () -> DeliveryDay.isValidDeliveryDay(null));

        // invalid start dates
        assertFalse(DeliveryDay.isValidDeliveryDay("")); // empty string
        assertFalse(DeliveryDay.isValidDeliveryDay(" ")); // spaces only
        assertFalse(DeliveryDay.isValidDeliveryDay("Mon")); // only 3-character day

        // valid start dates
        assertTrue(DeliveryDay.isValidDeliveryDay("Monday"));
        assertTrue(DeliveryDay.isValidDeliveryDay("thursday"));
        assertTrue(DeliveryDay.isValidDeliveryDay("FRIDAY"));
    }

    @Test
    public void equals() {
        DeliveryDay day = new DeliveryDay("Tuesday");

        // same values -> returns true
        assertTrue(day.equals(new DeliveryDay("Tuesday")));

        // same object -> returns true
        assertTrue(day.equals(day));

        // null -> returns false
        assertFalse(day.equals(null));

        // different types -> returns false
        assertFalse(day.equals(5.0f));

        // different values -> returns false
        assertFalse(day.equals(new DeliveryDay("Thursday")));
    }
}

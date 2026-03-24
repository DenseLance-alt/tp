package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.delivery.DeliveryDay.toDeliveryDay;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class DeliveryDayTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> toDeliveryDay(null));
    }

    @Test
    public void constructor_invalidDay_throwsIllegalArgumentException() {
        String invalidDay = "";
        assertThrows(IllegalArgumentException.class, () -> toDeliveryDay(invalidDay));
    }

    @Test
    public void isValidDeliveryDay() {
        // null day
        assertThrows(NullPointerException.class, () -> DeliveryDay.isValidDeliveryDay(null));

        // invalid days
        assertFalse(DeliveryDay.isValidDeliveryDay("")); // empty string
        assertFalse(DeliveryDay.isValidDeliveryDay(" ")); // spaces only
        assertFalse(DeliveryDay.isValidDeliveryDay("Mon")); // only 3-character day

        // valid days
        assertTrue(DeliveryDay.isValidDeliveryDay("Monday"));
        assertTrue(DeliveryDay.isValidDeliveryDay("thursday"));
        assertTrue(DeliveryDay.isValidDeliveryDay("FRIDAY"));
        assertTrue(DeliveryDay.isValidDeliveryDay("WEDnesDay"));
    }

    @Test
    public void equals() {
        DeliveryDay day = toDeliveryDay("Tuesday");

        // same values -> returns true
        assertTrue(day.equals(toDeliveryDay("Tuesday")));

        // same object -> returns true
        assertTrue(day.equals(day));

        // null -> returns false
        assertFalse(day.equals(null));

        // different types -> returns false
        assertFalse(day.equals(5.0f));

        // different values -> returns false
        assertFalse(day.equals(toDeliveryDay("Thursday")));
    }
}

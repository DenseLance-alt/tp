package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.delivery.DeliveryDay.toDeliveryDay;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class DeliveryDayTest {

    // EP: null string -> throws NullPointerException
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> toDeliveryDay(null));
    }

    // EP: string with invalid day -> throws IllegalArgumentException
    @Test
    public void constructor_invalidDay_throwsIllegalArgumentException() {
        String invalidDay = "";
        assertThrows(IllegalArgumentException.class, () -> toDeliveryDay(invalidDay));
    }

    @Test
    public void isValidDeliveryDay() {
        // EP: null day -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> DeliveryDay.isValidDeliveryDay(null));

        // EP: string with no day -> returns false
        // Boundary value: empty string
        assertFalse(DeliveryDay.isValidDeliveryDay(""));
        assertFalse(DeliveryDay.isValidDeliveryDay(" "));

        // EP: string with incorrect day format -> returns false
        assertFalse(DeliveryDay.isValidDeliveryDay("Mon")); // only 3-character day

        // EP: string with valid day in correct format -> returns true
        assertTrue(DeliveryDay.isValidDeliveryDay("Monday"));
        assertTrue(DeliveryDay.isValidDeliveryDay("thursday"));
        assertTrue(DeliveryDay.isValidDeliveryDay("FRIDAY"));
        assertTrue(DeliveryDay.isValidDeliveryDay("WEDnesDay"));
    }

    @Test
    public void equals() {
        DeliveryDay day = toDeliveryDay("Tuesday");

        // EP: same values -> returns true
        assertTrue(day.equals(toDeliveryDay("Tuesday")));

        // EP: same object -> returns true
        assertTrue(day.equals(day));

        // EP: null -> returns false
        assertFalse(day.equals(null));

        // EP: different types -> returns false
        assertFalse(day.equals(5.0f));

        // EP: different values -> returns false
        assertFalse(day.equals(toDeliveryDay("Thursday")));
    }
}

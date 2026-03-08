package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DAY_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_TIME_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKIPPED_DATE_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKIPPED_DATE_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_TWO;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDeliveries.DELIVERY_ONE;
import static seedu.address.testutil.TypicalDeliveries.DELIVERY_TWO;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DeliveryBuilder;

public class DeliveryTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Delivery delivery = new DeliveryBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> delivery.getDeliveryDays().remove(0));
    }

    @Test
    public void isSameDelivery() {
        // same object -> returns true
        assertTrue(DELIVERY_ONE.isSameDelivery(DELIVERY_ONE));

        // null -> returns false
        assertFalse(DELIVERY_ONE.isSameDelivery(null));

        // new overlapping start and end date, all other attributes same -> returns true
        Delivery editedDeliveryOne = new DeliveryBuilder(DELIVERY_ONE)
                .withStartDate(VALID_START_DATE_TWO).withEndDate(VALID_END_DATE_TWO).build();
        assertTrue(DELIVERY_ONE.isSameDelivery(editedDeliveryOne));

        // new overlapping start and date, all other attributes different -> returns true
        Delivery editedDeliveryTwo = new DeliveryBuilder(DELIVERY_TWO).withStartDate(VALID_START_DATE_TWO)
                .withEndDate(VALID_START_DATE_TWO).withDeliveryDays(VALID_DELIVERY_DAY_FIRST)
                .withDeliveryTime(VALID_DELIVERY_TIME_ONE).withSkippedDates(VALID_SKIPPED_DATE_FIRST).build();
        assertTrue(DELIVERY_TWO.isSameDelivery(editedDeliveryTwo));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Delivery deliveryOneCopy = new DeliveryBuilder(DELIVERY_ONE).build();
        assertTrue(DELIVERY_ONE.equals(deliveryOneCopy));

        // same object -> returns true
        assertTrue(DELIVERY_ONE.equals(DELIVERY_ONE));

        // null -> returns false
        assertFalse(DELIVERY_ONE.equals(null));

        // different type -> returns false
        assertFalse(DELIVERY_ONE.equals(5));

        // different delivery -> returns false
        assertFalse(DELIVERY_ONE.equals(DELIVERY_TWO));

        // different start date -> returns false
        Delivery editedDeliveryOne = new DeliveryBuilder(DELIVERY_ONE).withStartDate(VALID_START_DATE_TWO).build();
        assertFalse(DELIVERY_ONE.equals(editedDeliveryOne));

        // different end date -> returns false
        Delivery editedDeliveryTwo = new DeliveryBuilder(DELIVERY_TWO).withEndDate(VALID_END_DATE_ONE).build();
        assertFalse(DELIVERY_TWO.equals(editedDeliveryTwo));

        // different delivery time -> returns false
        editedDeliveryTwo = new DeliveryBuilder(DELIVERY_TWO).withDeliveryTime(VALID_DELIVERY_TIME_ONE).build();
        assertFalse(DELIVERY_TWO.equals(editedDeliveryTwo));

        // different delivery days -> returns false
        editedDeliveryOne = new DeliveryBuilder(DELIVERY_ONE).withDeliveryDays(VALID_DELIVERY_DAY_FIRST).build();
        assertFalse(DELIVERY_ONE.equals(editedDeliveryOne));

        // different skipped date -> returns false
        editedDeliveryOne = new DeliveryBuilder(DELIVERY_ONE).withSkippedDates(VALID_SKIPPED_DATE_SECOND).build();
        assertFalse(DELIVERY_ONE.equals(editedDeliveryOne));
    }

    @Test
    public void toStringMethod() {
        String expected = Delivery.class.getCanonicalName() + "{start date=" + DELIVERY_ONE.getStartDate()
                + ", end date=" + DELIVERY_ONE.getEndDate() + ", delivery days=" + DELIVERY_ONE.getDeliveryDays()
                + ", delivery time=" + DELIVERY_ONE.getDeliveryTime()
                + ", skipped dates=" + DELIVERY_ONE.getSkippedDates() + "}";
        assertEquals(expected, DELIVERY_ONE.toString());
    }
}

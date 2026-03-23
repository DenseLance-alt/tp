package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_TIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKIPPED_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_AMY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDeliveries.DELIVERY_ALICE;
import static seedu.address.testutil.TypicalDeliveries.DELIVERY_CARL;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DeliveryBuilder;

public class DeliveryTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Delivery delivery = new DeliveryBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> delivery.getDeliveryDays().remove(0));
    }

    @Test
    public void getFormattedDeliverySchedule_validDelivery_returnsDeliverySchedule() {
        assertEquals(DELIVERY_ALICE.getStartDate()
                        + " to " + DELIVERY_ALICE.getEndDate()
                        + "  |  " + DELIVERY_ALICE.getDeliveryTime(),
                DELIVERY_ALICE.getFormattedDeliverySchedule());
    }

    @Test
    public void equals() {
        // same values -> returns true
        Delivery deliveryOneCopy = new DeliveryBuilder(DELIVERY_ALICE).build();
        assertTrue(DELIVERY_ALICE.equals(deliveryOneCopy));

        // same object -> returns true
        assertTrue(DELIVERY_ALICE.equals(DELIVERY_ALICE));

        // null -> returns false
        assertFalse(DELIVERY_ALICE.equals(null));

        // different type -> returns false
        assertFalse(DELIVERY_ALICE.equals(5));

        // different delivery -> returns false
        assertFalse(DELIVERY_ALICE.equals(DELIVERY_CARL));

        // different start date -> returns false
        Delivery editedDeliveryOne = new DeliveryBuilder(DELIVERY_ALICE).withStartDate(VALID_START_DATE_AMY).build();
        assertFalse(DELIVERY_ALICE.equals(editedDeliveryOne));

        // different end date -> returns false
        Delivery editedDeliveryTwo = new DeliveryBuilder(DELIVERY_CARL).withEndDate(VALID_END_DATE_AMY).build();
        assertFalse(DELIVERY_CARL.equals(editedDeliveryTwo));

        // different delivery time -> returns false
        editedDeliveryTwo = new DeliveryBuilder(DELIVERY_CARL).withDeliveryTime(VALID_DELIVERY_TIME_AMY).build();
        assertFalse(DELIVERY_CARL.equals(editedDeliveryTwo));

        // different delivery days -> returns false
        editedDeliveryOne = new DeliveryBuilder(DELIVERY_ALICE).withDeliveryDays(VALID_DELIVERY_DAY).build();
        assertFalse(DELIVERY_ALICE.equals(editedDeliveryOne));

        // different skipped date -> returns false
        editedDeliveryOne = new DeliveryBuilder(DELIVERY_ALICE).withSkippedDates(VALID_SKIPPED_DATE).build();
        assertFalse(DELIVERY_ALICE.equals(editedDeliveryOne));
    }

    @Test
    public void toStringMethod() {
        String expected = Delivery.class.getCanonicalName() + "{start date=" + DELIVERY_ALICE.getStartDate()
                + ", end date=" + DELIVERY_ALICE.getEndDate() + ", delivery days=" + DELIVERY_ALICE.getDeliveryDays()
                + ", delivery time=" + DELIVERY_ALICE.getDeliveryTime()
                + ", skipped dates=" + DELIVERY_ALICE.getSkippedDates() + "}";
        assertEquals(expected, DELIVERY_ALICE.toString());
    }
}

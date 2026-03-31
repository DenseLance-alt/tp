package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_TIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_TIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_BOB;
import static seedu.address.model.delivery.Delivery.isValidDateRange;
import static seedu.address.model.delivery.DeliveryDay.toDeliveryDay;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.DeliveryUtil.generateEndDate;
import static seedu.address.testutil.TypicalDeliveries.DELIVERY_ALICE;
import static seedu.address.testutil.TypicalDeliveries.DELIVERY_CARL;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DeliveryBuilder;

public class DeliveryTest {
    @Test
    public void constructor_invalidDateRange_throwsIllegalArgumentException() {
        StartDate startDate = new StartDate(VALID_START_DATE_BOB);
        EndDate endDate = generateEndDate(startDate, -5);
        DeliveryTime deliveryTime = new DeliveryTime(VALID_DELIVERY_TIME_BOB);
        Set<DeliveryDay> deliveryDaySet = new HashSet<>();
        deliveryDaySet.add(toDeliveryDay(VALID_DELIVERY_DAY));
        assertThrows(IllegalArgumentException.class, () ->
                new Delivery(startDate, endDate, deliveryDaySet, deliveryTime));
    }

    @Test
    public void isValidDateRange_invalidDateRange_returnsFalse() {
        StartDate startDate = new StartDate(VALID_START_DATE_BOB);
        EndDate endDate = generateEndDate(startDate, -5);

        assertFalse(isValidDateRange(startDate, endDate));
    }

    @Test
    public void isValidDateRange_validDateRange_returnsTrue() {
        StartDate startDate = new StartDate(VALID_START_DATE_BOB);
        EndDate endDate = generateEndDate(startDate, 0);

        assertTrue(isValidDateRange(startDate, endDate));
    }

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
    }

    @Test
    public void toStringMethod() {
        String expected = Delivery.class.getCanonicalName() + "{startDate=" + DELIVERY_ALICE.getStartDate()
                + ", endDate=" + DELIVERY_ALICE.getEndDate() + ", deliveryDays=" + DELIVERY_ALICE.getDeliveryDays()
                + ", deliveryTime=" + DELIVERY_ALICE.getDeliveryTime() + "}";
        assertEquals(expected, DELIVERY_ALICE.toString());
    }
}

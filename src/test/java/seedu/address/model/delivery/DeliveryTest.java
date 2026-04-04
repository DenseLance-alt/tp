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

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DeliveryBuilder;

public class DeliveryTest {
    @Test
    public void constructor_invalidDateRange_throwsIllegalArgumentException() {
        StartDate startDate = new StartDate(VALID_START_DATE_BOB);
        EndDate endDate = generateEndDate(startDate, -5);
        DeliveryTime deliveryTime = new DeliveryTime(VALID_DELIVERY_TIME_BOB);
        Set<DeliveryDay> deliveryDaySet = new LinkedHashSet<>();
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
    public void getFormattedDeliverySchedule_returnsDeliverySchedule() {
        assertEquals(DELIVERY_ALICE.getStartDate()
                        + " to " + DELIVERY_ALICE.getEndDate()
                        + "  |  " + DELIVERY_ALICE.getDeliveryTime(),
                DELIVERY_ALICE.getFormattedDeliverySchedule());
    }

    @Test
    public void hasExpired_deliveryExpired_returnsTrue() {
        Delivery delivery = new DeliveryBuilder()
                .withStartDate("2024-03-01")
                .withEndDate("2024-04-01")
                .build();

        // Boundary value: delivery expired one day ago
        LocalDate beforeDate = LocalDate.of(2024, 4, 2);
        assertTrue(delivery.hasExpired(beforeDate));

        // Equivalence partition for expired delivery
        beforeDate = LocalDate.of(2026, 3, 27);
        assertTrue(delivery.hasExpired(beforeDate));
    }

    @Test
    public void hasExpired_deliveryNotExpired_returnsFalse() {
        Delivery delivery = new DeliveryBuilder()
                .withStartDate("2023-03-01")
                .withEndDate("2023-04-01")
                .build();

        // Boundary value: delivery expires today
        LocalDate beforeDate = LocalDate.of(2023, 4, 1);
        assertFalse(delivery.hasExpired(beforeDate));

        // Near boundary value: delivery expires in two days
        beforeDate = LocalDate.of(2023, 3, 31);
        assertFalse(delivery.hasExpired(beforeDate));

        // Equivalence partition where specified date is between start and end dates of delivery
        // Specified date is still before the end date of delivery
        beforeDate = LocalDate.of(2023, 3, 27);
        assertFalse(delivery.hasExpired(beforeDate));

        // Equivalence partition where specified date is before delivery's start date
        beforeDate = LocalDate.of(2023, 2, 27);
        assertFalse(delivery.hasExpired(beforeDate));
    }

    //@@author BenedTj
    @Test
    public void equals() {
        // EP: same values -> returns true
        Delivery deliveryOneCopy = new DeliveryBuilder(DELIVERY_ALICE).build();
        assertTrue(DELIVERY_ALICE.equals(deliveryOneCopy));

        // EP: same object -> returns true
        assertTrue(DELIVERY_ALICE.equals(DELIVERY_ALICE));

        // EP: null -> returns false
        assertFalse(DELIVERY_ALICE.equals(null));

        // EP: different type -> returns false
        assertFalse(DELIVERY_ALICE.equals(5));

        // EP: different delivery -> returns false
        assertFalse(DELIVERY_ALICE.equals(DELIVERY_CARL));

        // EP: different start date -> returns false
        Delivery editedDeliveryOne = new DeliveryBuilder(DELIVERY_ALICE).withStartDate(VALID_START_DATE_AMY).build();
        assertFalse(DELIVERY_ALICE.equals(editedDeliveryOne));

        // EP: different end date -> returns false
        Delivery editedDeliveryTwo = new DeliveryBuilder(DELIVERY_CARL).withEndDate(VALID_END_DATE_AMY).build();
        assertFalse(DELIVERY_CARL.equals(editedDeliveryTwo));

        // EP: different delivery time -> returns false
        editedDeliveryTwo = new DeliveryBuilder(DELIVERY_CARL).withDeliveryTime(VALID_DELIVERY_TIME_AMY).build();
        assertFalse(DELIVERY_CARL.equals(editedDeliveryTwo));

        // EP: different delivery days -> returns false
        editedDeliveryOne = new DeliveryBuilder(DELIVERY_ALICE).withDeliveryDays(VALID_DELIVERY_DAY).build();
        assertFalse(DELIVERY_ALICE.equals(editedDeliveryOne));
    }
    //@@author

    @Test
    public void toStringMethod() {
        String expected = Delivery.class.getCanonicalName() + "{startDate=" + DELIVERY_ALICE.getStartDate()
                + ", endDate=" + DELIVERY_ALICE.getEndDate() + ", deliveryDays=" + DELIVERY_ALICE.getDeliveryDays()
                + ", deliveryTime=" + DELIVERY_ALICE.getDeliveryTime() + "}";
        assertEquals(expected, DELIVERY_ALICE.toString());
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY_RESCHEDULE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB_RESCHEDULE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_TIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RescheduleCommand.RescheduleDeliveryDescriptor;
import seedu.address.testutil.RescheduleDeliveryDescriptorBuilder;

/**
 * Contains unit tests for RescheduleDeliveryDescriptor.
 */
public class RescheduleDeliveryDescriptorTest {

    @Test
    public void equals() {
        // EP: same values -> returns true
        RescheduleDeliveryDescriptor descriptorWithSameValues = new RescheduleDeliveryDescriptor(DESC_AMY_RESCHEDULE);
        assertTrue(DESC_AMY_RESCHEDULE.equals(descriptorWithSameValues));

        // EP: same object -> returns true
        assertTrue(DESC_AMY_RESCHEDULE.equals(DESC_AMY_RESCHEDULE));

        // EP: null -> returns false
        assertFalse(DESC_AMY_RESCHEDULE.equals(null));

        // EP: different types -> returns false
        assertFalse(DESC_AMY_RESCHEDULE.equals(5));

        // EP: different values -> returns false
        assertFalse(DESC_AMY_RESCHEDULE.equals(DESC_BOB_RESCHEDULE));

        // EP: different start date -> returns false
        RescheduleDeliveryDescriptor editedAmy = new RescheduleDeliveryDescriptorBuilder(DESC_AMY_RESCHEDULE)
                .withStartDate(VALID_START_DATE_BOB).build();
        assertFalse(DESC_AMY_RESCHEDULE.equals(editedAmy));

        // EP: different end date -> returns false
        editedAmy = new RescheduleDeliveryDescriptorBuilder(DESC_AMY_RESCHEDULE)
                .withEndDate(VALID_END_DATE_BOB).build();
        assertFalse(DESC_AMY_RESCHEDULE.equals(editedAmy));

        // EP: different delivery days -> returns false
        editedAmy = new RescheduleDeliveryDescriptorBuilder(DESC_AMY_RESCHEDULE)
                .withDeliveryDays(VALID_DELIVERY_DAY_BOB).build();
        assertFalse(DESC_AMY_RESCHEDULE.equals(editedAmy));

        // EP: different delivery time -> returns false
        editedAmy = new RescheduleDeliveryDescriptorBuilder(DESC_AMY_RESCHEDULE)
                .withDeliveryTime(VALID_DELIVERY_TIME_BOB).build();
        assertFalse(DESC_AMY_RESCHEDULE.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        RescheduleDeliveryDescriptor rescheduleDeliveryDescriptor = new RescheduleDeliveryDescriptor();
        String expected = RescheduleDeliveryDescriptor.class.getCanonicalName() + "{startDate="
                + rescheduleDeliveryDescriptor.getStartDate().orElse(null) + ", endDate="
                + rescheduleDeliveryDescriptor.getEndDate().orElse(null) + ", deliveryDays="
                + rescheduleDeliveryDescriptor.getDeliveryDays().orElse(null) + ", deliveryTime="
                + rescheduleDeliveryDescriptor.getDeliveryTime().orElse(null) + "}";
        assertEquals(expected, rescheduleDeliveryDescriptor.toString());
    }

}

package seedu.address.testutil;

import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.UniqueDeliveryList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypicalDeliveries {
    public static final Delivery DELIVERY_ONE = new DeliveryBuilder().withStartDate("2025-10-19")
            .withEndDate("2025-10-30").withDeliveryDays("Monday", "Tuesday")
            .withDeliveryTime("12:00")
            .withSkippedDates("2025-10-21", "2025-10-22").build();
    public static final Delivery DELIVERY_TWO = new DeliveryBuilder().withStartDate("2025-10-19")
            .withEndDate("2025-10-30").withDeliveryDays("Monday", "Tuesday", "Thursday")
            .withDeliveryTime("14:14")
            .withSkippedDates("2025-10-21", "2025-10-24").build();
    public static final Delivery DELIVERY_THREE = new DeliveryBuilder().withStartDate("2024-10-21")
            .withEndDate("2024-10-29").withDeliveryDays("Tuesday", "Wednesday", "Thursday")
            .withDeliveryTime("15:15")
            .withSkippedDates("2024-10-24").build();
    public static final Delivery DELIVERY_FOUR = new DeliveryBuilder().withStartDate("2019-03-19")
            .withEndDate("2019-03-27").withDeliveryDays("Wednesday", "Friday")
            .withDeliveryTime("12:00")
            .withSkippedDates().build();
    public static final Delivery DELIVERY_FIVE = new DeliveryBuilder().withStartDate("2019-05-21")
            .withEndDate("2019-06-29").withDeliveryDays("Monday", "Thursday")
            .withDeliveryTime("11:11")
            .withSkippedDates("2019-05-28").build();

    private TypicalDeliveries() {} // prevents instantiation

    /**
     * Returns an {@code UniqueDeliveryList} with all the typical deliveries.
     */
    public static UniqueDeliveryList getTypicalDeliveryList() {
        UniqueDeliveryList dl = new UniqueDeliveryList();
        dl.setDeliveries(getTypicalDeliveries());
        return dl;
    }

    public static List<Delivery> getTypicalDeliveries() {
        return new ArrayList<>(Arrays.asList(DELIVERY_ONE, DELIVERY_TWO, DELIVERY_THREE, DELIVERY_FOUR, DELIVERY_FIVE));
    }
}

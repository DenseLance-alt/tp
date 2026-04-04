package seedu.address.testutil;

import static seedu.address.model.delivery.DeliveryDay.toDeliveryDay;

import java.util.LinkedHashSet;
import java.util.Set;

import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryDay;
import seedu.address.model.delivery.DeliveryTime;
import seedu.address.model.delivery.EndDate;
import seedu.address.model.delivery.StartDate;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Delivery objects
 */
public class DeliveryBuilder {

    public static final String DEFAULT_START_DATE = "2019-10-15";
    public static final String DEFAULT_END_DATE = "2019-10-20";
    public static final String DEFAULT_TIME = "12:59";
    public static final String DEFAULT_DAY = "Monday";

    private StartDate startDate;
    private EndDate endDate;
    private Set<DeliveryDay> deliveryDays;
    private DeliveryTime deliveryTime;

    /**
     * Creates a {@code DeliveryBuilder} with the default details.
     */
    public DeliveryBuilder() {
        startDate = new StartDate(DEFAULT_START_DATE);
        endDate = new EndDate(DEFAULT_END_DATE);
        deliveryDays = new LinkedHashSet<>();
        deliveryDays.add(toDeliveryDay(DEFAULT_DAY));
        deliveryTime = new DeliveryTime(DEFAULT_TIME);
    }

    /**
     * Initializes the DeliveryBuilder with the data of {@code deliveryToCopy}.
     */
    public DeliveryBuilder(Delivery deliveryToCopy) {
        startDate = deliveryToCopy.getStartDate();
        endDate = deliveryToCopy.getEndDate();
        deliveryDays = new LinkedHashSet<>(deliveryToCopy.getDeliveryDays());
        deliveryTime = deliveryToCopy.getDeliveryTime();
    }

    /**
     * Sets the {@code StartDate} of the {@code Delivery} that we are building.
     */
    public DeliveryBuilder withStartDate(String date) {
        this.startDate = new StartDate(date);
        return this;
    }

    /**
     * Sets the {@code EndDate} of the {@code Delivery} that we are building.
     */
    public DeliveryBuilder withEndDate(String date) {
        this.endDate = new EndDate(date);
        return this;
    }

    /**
     * Parses the {@code deliveryDays} into a {@code Set<DeliveryDay>} and set it to
     * the {@code Delivery} that we are building.
     */
    public DeliveryBuilder withDeliveryDays(String ... days) {
        this.deliveryDays = SampleDataUtil.getDeliveryDaySet(days);
        return this;
    }

    /**
     * Sets the {@code DeliveryTime} of the {@code Delivery} that we are building.
     */
    public DeliveryBuilder withDeliveryTime(String time) {
        this.deliveryTime = new DeliveryTime(time);
        return this;
    }

    public Delivery build() {
        return new Delivery(startDate, endDate, deliveryDays, deliveryTime);
    }
}

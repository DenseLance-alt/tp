package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.RescheduleCommand.RescheduleDeliveryDescriptor;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryDay;
import seedu.address.model.delivery.DeliveryTime;
import seedu.address.model.delivery.EndDate;
import seedu.address.model.delivery.StartDate;

/**
 * A utility class to help with building RescheduleDeliveryDescriptor objects.
 */
public class RescheduleDeliveryDescriptorBuilder {

    private RescheduleDeliveryDescriptor descriptor;

    public RescheduleDeliveryDescriptorBuilder() {
        descriptor = new RescheduleDeliveryDescriptor();
    }

    public RescheduleDeliveryDescriptorBuilder(RescheduleDeliveryDescriptor descriptor) {
        this.descriptor = new RescheduleDeliveryDescriptor(descriptor);
    }

    /**
     * Returns an {@code RescheduleDeliveryDescriptor} with fields containing {@code delivery}'s details.
     *
     * @param delivery Delivery whose details will be used.
     */
    public RescheduleDeliveryDescriptorBuilder(Delivery delivery) {
        descriptor = new RescheduleDeliveryDescriptor();
        descriptor.setStartDate(delivery.getStartDate());
        descriptor.setEndDate(delivery.getEndDate());
        descriptor.setDeliveryDays(delivery.getDeliveryDays());
        descriptor.setDeliveryTime(delivery.getDeliveryTime());
    }

    /**
     * Sets the {@code StartDate} of the {@code RescheduleDeliveryDescriptor} that we are building.
     *
     * @param startDate Start date of the delivery.
     * @return {@code RescheduleDeliveryDescriptorBuilder} with the start date set.
     */
    public RescheduleDeliveryDescriptorBuilder withStartDate(String startDate) {
        descriptor.setStartDate(new StartDate(startDate));
        return this;
    }

    /**
     * Sets the {@code EndDate} of the {@code RescheduleDeliveryDescriptor} that we are building.
     *
     * @param endDate End date of the delivery.
     * @return {@code RescheduleDeliveryDescriptorBuilder} with the end date set.
     */
    public RescheduleDeliveryDescriptorBuilder withEndDate(String endDate) {
        descriptor.setEndDate(new EndDate(endDate));
        return this;
    }

    /**
     * Parses the {@code deliveryDays} into a {@code Set<DeliveryDay>} and set it
     * to the {@code RescheduleDeliveryDescriptor}.
     *
     * @param deliveryDays Delivery days of the delivery.
     * @return {@code RescheduleDeliveryDescriptorBuilder} with the delivery days set.
     */
    public RescheduleDeliveryDescriptorBuilder withDeliveryDays(String... deliveryDays) {
        Set<DeliveryDay> deliveryDaysSet = Stream.of(deliveryDays)
                .map(DeliveryDay::toDeliveryDay)
                .collect(Collectors.toSet());
        descriptor.setDeliveryDays(deliveryDaysSet);
        return this;
    }

    /**
     * Sets the {@code DeliveryTime} of the {@code RescheduleDeliveryDescriptor} that we are building.
     *
     * @param time Delivery time of the delivery.
     * @return {@code RescheduleDeliveryDescriptorBuilder} with the delivery time set.
     */
    public RescheduleDeliveryDescriptorBuilder withDeliveryTime(String time) {
        descriptor.setDeliveryTime(new DeliveryTime(time));
        return this;
    }

    public RescheduleDeliveryDescriptor build() {
        return descriptor;
    }
}

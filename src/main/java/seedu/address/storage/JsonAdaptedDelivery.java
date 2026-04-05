package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryDay;
import seedu.address.model.delivery.DeliveryTime;
import seedu.address.model.delivery.EndDate;
import seedu.address.model.delivery.StartDate;

/**
 * Jacson-friendly version of {@link Delivery}
 */
public class JsonAdaptedDelivery {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Delivery's %s field is missing!";

    private final String startDate;
    private final String endDate;
    private final List<JsonAdaptedDeliveryDay> deliveryDays = new ArrayList<>();
    private final String deliveryTime;

    /**
     * Constructs a {@code JsonAdaptedDelivery} with the given delivery details.
     *
     * @param startDate The start date of the delivery.
     * @param endDate The end date of the delivery.
     * @param deliveryDays The list of delivery days.
     * @param deliveryTime The delivery time, expected to be non-null.
     */
    @JsonCreator
    public JsonAdaptedDelivery(@JsonProperty("startDate") String startDate, @JsonProperty("endDate") String endDate,
                               @JsonProperty("deliveryDays") List<JsonAdaptedDeliveryDay> deliveryDays,
                               @JsonProperty("deliveryTime") String deliveryTime) {
        this.startDate = startDate;
        this.endDate = endDate;
        if (deliveryDays != null) {
            this.deliveryDays.addAll(deliveryDays);
        }
        this.deliveryTime = deliveryTime;
    }

    /**
     * Converts a given {@code Delivery} into this class for Jackson use.
     *
     * @param source The delivery to be converted must not be null.
     */
    public JsonAdaptedDelivery(Delivery source) {
        requireNonNull(source);

        startDate = source.getStartDate().toString();
        endDate = source.getEndDate().toString();
        deliveryDays.addAll(source.getDeliveryDays().stream()
                .map(JsonAdaptedDeliveryDay::new)
                .collect(Collectors.toList()));
        deliveryTime = source.getDeliveryTime().toString();
    }

    /**
     * Converts this Jackson-friendly adapted delivery object into the model's {@code Delivery} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted delivery.
     */
    public Delivery toModelType() throws IllegalValueException {
        final List<DeliveryDay> deliveryDays = new ArrayList<>();
        for (JsonAdaptedDeliveryDay deliveryDay : this.deliveryDays) {
            deliveryDays.add(deliveryDay.toModelType());
        }

        if (startDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StartDate.class.getSimpleName()));
        }

        if (!StartDate.isValidStartDate(startDate)) {
            throw new IllegalValueException(StartDate.MESSAGE_CONSTRAINTS);
        }

        final StartDate modelStartDate = new StartDate(startDate);

        if (endDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, EndDate.class.getSimpleName()));
        }

        if (!EndDate.isValidEndDate(endDate)) {
            throw new IllegalValueException(EndDate.MESSAGE_CONSTRAINTS);
        }

        final EndDate modelEndDate = new EndDate(endDate);

        if (deliveryDays.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DeliveryDay.class.getSimpleName()));
        }

        Collections.sort(deliveryDays);
        final Set<DeliveryDay> modelDeliveryDays = new LinkedHashSet<>(deliveryDays);

        if (deliveryTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DeliveryTime.class.getSimpleName()));
        }

        if (!DeliveryTime.isValidDeliveryTime(deliveryTime)) {
            throw new IllegalValueException(DeliveryTime.MESSAGE_CONSTRAINTS);
        }

        final DeliveryTime modelDeliveryTime = new DeliveryTime(deliveryTime);

        return new Delivery(modelStartDate, modelEndDate, modelDeliveryDays, modelDeliveryTime);
    }
}

package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
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
import seedu.address.model.delivery.SkippedDate;
import seedu.address.model.delivery.StartDate;


/**
 * Jacson-friendly version of {@link Delivery}
 */
public class JsonAdaptedDelivery {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Delivery's %s field is missing!";

    private final String startDate;
    private final String endDate;
    private final List<JsonAdaptedDeliveryDay> deliveryDays;
    private final String deliveryTime;
    private final List<JsonAdaptedSkippedDate> skippedDates;

    /**
     * Constructs a {@code JsonAdaptedDelivery} with the given delivery details.
     */
    @JsonCreator
    public JsonAdaptedDelivery(@JsonProperty("startDate") String startDate, @JsonProperty("endDate") String endDate,
                               @JsonProperty("deliveryDays") List<JsonAdaptedDeliveryDay> deliveryDays,
                               @JsonProperty("deliveryTime") String deliveryTime,
                               @JsonProperty("skippedDates") List<JsonAdaptedSkippedDate> skippedDates) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.deliveryDays = deliveryDays;
        this.deliveryTime = deliveryTime;
        this.skippedDates = skippedDates;
    }

    /**
     * Converts a given {@code Delivery} into this class for Jackson use.
     */
    public JsonAdaptedDelivery(Delivery source) {
        startDate = source.getStartDate().toString();
        endDate = source.getEndDate().toString();
        deliveryDays = source.getDeliveryDays().stream()
                .map(JsonAdaptedDeliveryDay::new)
                .collect(Collectors.toList());
        deliveryTime = source.getDeliveryTime().toString();
        skippedDates = source.getSkippedDates().stream()
                .map(JsonAdaptedSkippedDate::new)
                .collect(Collectors.toList());
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

        final List<SkippedDate> skippedDates = new ArrayList<>();
        for (JsonAdaptedSkippedDate skippedDate : this.skippedDates) {
            skippedDates.add(skippedDate.toModelType());
        }

        if (startDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "start date"));
        }

        if (!StartDate.isValidStartDate(startDate)) {
            throw new IllegalValueException(StartDate.MESSAGE_CONSTRAINTS);
        }

        final StartDate modelStartDate = new StartDate(startDate);

        if (endDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "end date"));
        }

        if (!StartDate.isValidStartDate(endDate)) {
            throw new IllegalValueException(StartDate.MESSAGE_CONSTRAINTS);
        }

        final EndDate modelEndDate = new EndDate(endDate);

        final Set<DeliveryDay> modelDeliveryDays = new HashSet<>(deliveryDays);

        if (deliveryTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "delivery time"));
        }

        if (!DeliveryTime.isValidDeliveryTime(deliveryTime)) {
            throw new IllegalValueException(DeliveryTime.MESSAGE_CONSTRAINTS);
        }

        final DeliveryTime modelDeliveryTime = new DeliveryTime(deliveryTime);

        final Set<SkippedDate> modelSkippedDates = new HashSet<>(skippedDates);
        return new Delivery(modelStartDate, modelEndDate, modelDeliveryDays, modelDeliveryTime, modelSkippedDates);
    }
}

package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.delivery.DeliveryDay;

/**
 * Jackson-friendly version of {@link DeliveryDay}
 */
public class JsonAdaptedDeliveryDay {

    private final String dayOfWeek;

    /**
     * Constructs a {@code JsonAdaptedDeliveryDay} with the given {@code dayOfWeek}.
     */
    @JsonCreator
    public JsonAdaptedDeliveryDay(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Converts a given {@code DeliveryDay} into this class for Jackson use.
     */
    public JsonAdaptedDeliveryDay(DeliveryDay source) {
        dayOfWeek = source.toString();
    }

    @JsonValue
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Converts this Jackson-friendly adapted delivery day object into the model's {@code DeliveryDay} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted delivery day.
     */
    public DeliveryDay toModelType() throws IllegalValueException {
        if (!DeliveryDay.isValidDeliveryDay(dayOfWeek)) {
            throw new IllegalValueException(DeliveryDay.MESSAGE_CONSTRAINTS);
        }
        return DeliveryDay.toDeliveryDay(dayOfWeek);
    }
}

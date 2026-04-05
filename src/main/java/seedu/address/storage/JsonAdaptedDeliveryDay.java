package seedu.address.storage;

import static java.util.Objects.requireNonNull;

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
     *
     * @param dayOfWeek The day of the week for the delivery day must not be null.
     */
    @JsonCreator
    public JsonAdaptedDeliveryDay(String dayOfWeek) {
        requireNonNull(dayOfWeek);

        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Converts a given {@code DeliveryDay} into this class for Jackson use.
     *
     * @param source The delivery day to be converted, expected to be non-null.
     */
    public JsonAdaptedDeliveryDay(DeliveryDay source) {
        assert source != null;

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

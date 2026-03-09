package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.delivery.SkippedDate;

/**
 * Jackson-friendly version of {@link SkippedDate}
 */
public class JsonAdaptedSkippedDate {

    private final String date;

    /**
     * Constructs a {@code JsonAdaptedSkippedDate} with the given {@code date}
     */
    @JsonCreator
    public JsonAdaptedSkippedDate(String date) {
        this.date = date;
    }

    /**
     * Converts a given {@code SkippedDate} into this class for Jackson use.
     */
    public JsonAdaptedSkippedDate(SkippedDate source) {
        date = source.toString();
    }

    @JsonValue
    public String getDate() {
        return date;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code SkippedDate} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public SkippedDate toModelType() throws IllegalValueException {
        if (!SkippedDate.isValidSkippedDate(date)) {
            throw new IllegalValueException(SkippedDate.MESSAGE_CONSTRAINTS);
        }
        return new SkippedDate(date);
    }
}

package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedDelivery.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDeliveries.DELIVERY_ALICE;
import static seedu.address.testutil.TypicalDeliveries.DELIVERY_ELLE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.delivery.DeliveryDay;
import seedu.address.model.delivery.DeliveryTime;
import seedu.address.model.delivery.EndDate;
import seedu.address.model.delivery.StartDate;

public class JsonAdaptedDeliveryTest {
    private static final String INVALID_START_DATE = "2019/10/15";
    private static final String INVALID_END_DATE = "2019/10/20";
    private static final String INVALID_DELIVERY_DAY = "sunnyDay";
    private static final String INVALID_DELIVERY_TIME = "20.20";

    private static final String VALID_START_DATE = DELIVERY_ALICE.getStartDate().toString();
    private static final String VALID_END_DATE = DELIVERY_ALICE.getEndDate().toString();
    private static final List<JsonAdaptedDeliveryDay> VALID_DELIVERY_DAYS = DELIVERY_ALICE.getDeliveryDays().stream()
            .map(JsonAdaptedDeliveryDay::new)
            .collect(Collectors.toList());
    private static final String VALID_DELIVERY_TIME = DELIVERY_ALICE.getDeliveryTime().toString();

    @Test
    public void toModelType_validDeliveryDetails_returnsDelivery() throws Exception {
        JsonAdaptedDelivery delivery = new JsonAdaptedDelivery(DELIVERY_ELLE);
        assertEquals(DELIVERY_ELLE, delivery.toModelType());
    }

    @Test
    public void toModelType_invalidStartDate_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(INVALID_START_DATE, VALID_END_DATE,
                        VALID_DELIVERY_DAYS, VALID_DELIVERY_TIME);
        String expectedMessage = StartDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_nullStartDate_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery = new JsonAdaptedDelivery(null, VALID_END_DATE,
                VALID_DELIVERY_DAYS, VALID_DELIVERY_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StartDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_invalidEndDate_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_START_DATE, INVALID_END_DATE,
                        VALID_DELIVERY_DAYS, VALID_DELIVERY_TIME);
        String expectedMessage = EndDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_nullEndDate_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery = new JsonAdaptedDelivery(VALID_START_DATE, null,
                VALID_DELIVERY_DAYS, VALID_DELIVERY_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EndDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_invalidDeliveryDay_throwsIllegalValueException() {
        List<JsonAdaptedDeliveryDay> invalidDeliveryDays = new ArrayList<>(VALID_DELIVERY_DAYS);
        invalidDeliveryDays.add(new JsonAdaptedDeliveryDay(INVALID_DELIVERY_DAY));
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_START_DATE, VALID_END_DATE, invalidDeliveryDays,
                        VALID_DELIVERY_TIME);
        String expectedMessage = DeliveryDay.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_nullDeliveryDays_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery = new JsonAdaptedDelivery(VALID_START_DATE, VALID_END_DATE, null,
                VALID_DELIVERY_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DeliveryDay.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_invalidDeliveryTime_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_START_DATE, VALID_END_DATE, VALID_DELIVERY_DAYS,
                        INVALID_DELIVERY_TIME);
        String expectedMessage = DeliveryTime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_nullDeliveryTime_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery = new JsonAdaptedDelivery(VALID_START_DATE, VALID_END_DATE,
                VALID_DELIVERY_DAYS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DeliveryTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }
}

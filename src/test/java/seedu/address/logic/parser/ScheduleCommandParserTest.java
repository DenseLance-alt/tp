package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DAYS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DAYS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NUMBER_OF_DAYS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NUMBER_OF_DAYS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAYS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_TIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUMBER_OF_DAYS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_OF_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryDay;
import seedu.address.model.delivery.DeliveryTime;
import seedu.address.model.delivery.StartDate;
import seedu.address.model.delivery.fields.NumberOfDays;
import seedu.address.testutil.DeliveryBuilder;

public class ScheduleCommandParserTest {
    private ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Index index = INDEX_FIRST_PERSON;
        Delivery delivery = new DeliveryBuilder().withStartDate(VALID_START_DATE_BOB)
                .withEndDate(VALID_END_DATE_BOB).withDeliveryDays(VALID_DELIVERY_DAY_BOB)
                .withDeliveryTime(VALID_DELIVERY_TIME_BOB).build();

        assertParseSuccess(parser,
                           INDEX_FIRST_PERSON.getOneBased() + START_DATE_DESC_BOB
                                   + NUMBER_OF_DAYS_DESC_BOB + TIME_DESC_BOB + DAYS_DESC_BOB,
                           new ScheduleCommand(INDEX_FIRST_PERSON, delivery));
    }

    @Test
    public void parse_repeatedCompulsoryField_failure() {
        String validExpectedDeliveryString = INDEX_FIRST_PERSON.getOneBased() + START_DATE_DESC_BOB
                + NUMBER_OF_DAYS_DESC_BOB + TIME_DESC_BOB + DAYS_DESC_BOB;

        // multiple start dates
        assertParseFailure(parser, validExpectedDeliveryString + START_DATE_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_START_DATE));

        // multiple number of days
        assertParseFailure(parser, validExpectedDeliveryString + NUMBER_OF_DAYS_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NUMBER_OF_DAYS));

        // multiple delivery times
        assertParseFailure(parser, validExpectedDeliveryString + TIME_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TIME));

        // multiple delivery days
        assertParseFailure(parser, validExpectedDeliveryString + DAYS_DESC_BOB,
                           Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DAYS));

        // valid value followed by invalid value

        // invalid start date
        assertParseFailure(parser, validExpectedDeliveryString + INVALID_START_DATE_DESC,
                           Messages.getErrorMessageForDuplicatePrefixes(PREFIX_START_DATE));

        // invalid number of day
        assertParseFailure(parser, validExpectedDeliveryString + INVALID_NUMBER_OF_DAYS_DESC,
                           Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NUMBER_OF_DAYS));

        // invalid delivery time
        assertParseFailure(parser, validExpectedDeliveryString + INVALID_TIME_DESC,
                           Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TIME));

        // invalid delivery days
        assertParseFailure(parser, validExpectedDeliveryString + INVALID_DAYS_DESC,
                           Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DAYS));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE);

        // missing start date prefix
        assertParseFailure(parser,
                           INDEX_FIRST_PERSON.getOneBased() + VALID_START_DATE_BOB
                                   + NUMBER_OF_DAYS_DESC_BOB + TIME_DESC_BOB + DAYS_DESC_BOB,
                           expectedMessage);

        // missing number of days prefix
        assertParseFailure(parser,
                           INDEX_FIRST_PERSON.getOneBased() + START_DATE_DESC_BOB
                                   + VALID_NUMBER_OF_DAYS_BOB + TIME_DESC_BOB + DAYS_DESC_BOB,
                           expectedMessage);

        // missing delivery time prefix
        assertParseFailure(parser,
                           INDEX_FIRST_PERSON.getOneBased() + START_DATE_DESC_BOB
                                   + NUMBER_OF_DAYS_DESC_BOB + VALID_DELIVERY_TIME_BOB + DAYS_DESC_BOB,
                           expectedMessage);

        // missing delivery days prefix
        assertParseFailure(parser,
                           INDEX_FIRST_PERSON.getOneBased() + START_DATE_DESC_BOB
                                   + NUMBER_OF_DAYS_DESC_BOB + TIME_DESC_BOB + VALID_DAYS_BOB,
                           expectedMessage);

        // all prefixes missing
        assertParseFailure(parser,
                           INDEX_FIRST_PERSON.getOneBased() + VALID_START_DATE_BOB
                                   + VALID_NUMBER_OF_DAYS_BOB + VALID_DELIVERY_TIME_BOB + VALID_DAYS_BOB,
                           expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid start date
        assertParseFailure(parser,
                           INDEX_FIRST_PERSON.getOneBased() + INVALID_START_DATE_DESC
                                   + NUMBER_OF_DAYS_DESC_BOB + TIME_DESC_BOB + DAYS_DESC_BOB,
                           StartDate.MESSAGE_CONSTRAINTS);

        // invalid number of days
        assertParseFailure(parser,
                           INDEX_FIRST_PERSON.getOneBased() + START_DATE_DESC_BOB
                                   + INVALID_NUMBER_OF_DAYS_DESC + TIME_DESC_BOB + DAYS_DESC_BOB,
                           NumberOfDays.MESSAGE_CONSTRAINTS);

        // invalid delivery time
        assertParseFailure(parser,
                           INDEX_FIRST_PERSON.getOneBased() + START_DATE_DESC_BOB
                                   + NUMBER_OF_DAYS_DESC_BOB + INVALID_TIME_DESC + DAYS_DESC_BOB,
                           DeliveryTime.MESSAGE_CONSTRAINTS);

        // invalid delivery days
        assertParseFailure(parser,
                           INDEX_FIRST_PERSON.getOneBased() + START_DATE_DESC_BOB
                                   + NUMBER_OF_DAYS_DESC_BOB + TIME_DESC_BOB + INVALID_DAYS_DESC,
                           DeliveryDay.MESSAGE_CONSTRAINTS);
    }
}

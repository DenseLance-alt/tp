package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DAYS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DAYS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.END_DATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.END_DATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DAYS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAYS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_TIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_DAY_NUMBER;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.RescheduleCommand;
import seedu.address.logic.commands.RescheduleCommand.RescheduleDeliveryDescriptor;
import seedu.address.model.delivery.DeliveryTime;
import seedu.address.model.delivery.EndDate;
import seedu.address.model.delivery.StartDate;
import seedu.address.testutil.RescheduleDeliveryDescriptorBuilder;

/**
 * Contains tests for RescheduleCommandParser.
 */
public class RescheduleCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RescheduleCommand.MESSAGE_USAGE);

    private RescheduleCommandParser parser = new RescheduleCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // EP: no index specified
        assertParseFailure(parser, VALID_START_DATE_AMY, MESSAGE_INVALID_FORMAT);

        // EP: no field specified
        assertParseFailure(parser, "1", RescheduleCommand.MESSAGE_NOT_EDITED);

        // EP: no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // EP: negative index
        assertParseFailure(parser, "-5" + START_DATE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // EP: zero index
        assertParseFailure(parser, "0" + START_DATE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // EP: invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // EP: invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // EP: invalid start date
        assertParseFailure(parser, "1" + INVALID_START_DATE_DESC, StartDate.MESSAGE_CONSTRAINTS);

        // EP: invalid end date
        assertParseFailure(parser, "1" + INVALID_END_DATE_DESC, EndDate.MESSAGE_CONSTRAINTS);

        // EP: invalid delivery time
        assertParseFailure(parser, "1" + INVALID_TIME_DESC, DeliveryTime.MESSAGE_CONSTRAINTS);

        // EP: invalid delivery days
        assertParseFailure(parser, "1" + INVALID_DAYS_DESC, MESSAGE_INVALID_DAY_NUMBER);

        // EP: invalid start date followed by valid end date
        assertParseFailure(parser, "1" + INVALID_START_DATE_DESC + VALID_END_DATE_AMY,
                StartDate.MESSAGE_CONSTRAINTS);

        // EP: multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_START_DATE_DESC + INVALID_END_DATE_DESC
                + VALID_DAYS_AMY + VALID_DELIVERY_TIME_AMY, StartDate.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + START_DATE_DESC_AMY + END_DATE_DESC_AMY
                + DAYS_DESC_AMY + TIME_DESC_AMY;
        // EP: all fields specified with valid values
        RescheduleDeliveryDescriptor descriptor = new RescheduleDeliveryDescriptorBuilder()
                .withStartDate(VALID_START_DATE_AMY)
                .withEndDate(VALID_END_DATE_AMY)
                .withDeliveryDays(VALID_DELIVERY_DAY_AMY)
                .withDeliveryTime(VALID_DELIVERY_TIME_AMY)
                .build();
        RescheduleCommand expectedCommand = new RescheduleCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + START_DATE_DESC_AMY + END_DATE_DESC_AMY;
        // EP: some fields specified with valid values
        RescheduleDeliveryDescriptor descriptor = new RescheduleDeliveryDescriptorBuilder()
                .withStartDate(VALID_START_DATE_AMY)
                .withEndDate(VALID_END_DATE_AMY)
                .build();
        RescheduleCommand expectedCommand = new RescheduleCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // EP: start date specified
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + START_DATE_DESC_AMY;
        RescheduleDeliveryDescriptor descriptor = new RescheduleDeliveryDescriptorBuilder()
                .withStartDate(VALID_START_DATE_AMY).build();
        RescheduleCommand expectedCommand = new RescheduleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: end date specified
        userInput = targetIndex.getOneBased() + END_DATE_DESC_AMY;
        descriptor = new RescheduleDeliveryDescriptorBuilder()
                .withEndDate(VALID_END_DATE_AMY).build();
        expectedCommand = new RescheduleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: delivery days specified
        userInput = targetIndex.getOneBased() + DAYS_DESC_AMY;
        descriptor = new RescheduleDeliveryDescriptorBuilder()
                .withDeliveryDays(VALID_DELIVERY_DAY_AMY).build();
        expectedCommand = new RescheduleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: delivery time specified
        userInput = targetIndex.getOneBased() + TIME_DESC_AMY;
        descriptor = new RescheduleDeliveryDescriptorBuilder()
                .withDeliveryTime(VALID_DELIVERY_TIME_AMY).build();
        expectedCommand = new RescheduleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        // EP: valid input followed by invalid input
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + START_DATE_DESC_AMY + INVALID_START_DATE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_START_DATE));

        // EP: invalid input followed by valid input
        userInput = targetIndex.getOneBased() + INVALID_START_DATE_DESC + START_DATE_DESC_AMY;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_START_DATE));

        // EP: multiple valid fields repeated
        userInput = targetIndex.getOneBased() + START_DATE_DESC_AMY + END_DATE_DESC_AMY
                + DAYS_DESC_AMY + TIME_DESC_AMY + START_DATE_DESC_BOB + END_DATE_DESC_BOB
                + DAYS_DESC_BOB + TIME_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(
                PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_DAYS, PREFIX_TIME));

        // EP: multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_START_DATE_DESC + INVALID_END_DATE_DESC
                + INVALID_DAYS_DESC + INVALID_TIME_DESC + INVALID_START_DATE_DESC + INVALID_END_DATE_DESC
                + INVALID_DAYS_DESC + INVALID_TIME_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(
                PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_DAYS, PREFIX_TIME));
    }
}

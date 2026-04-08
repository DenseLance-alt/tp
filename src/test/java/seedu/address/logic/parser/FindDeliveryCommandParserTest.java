package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.FindDeliveryCommandParser.MESSAGE_START_AFTER_END;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_DATE;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindDeliveryCommand;
import seedu.address.model.delivery.DeliveryDatePredicate;

public class FindDeliveryCommandParserTest {

    private FindDeliveryCommandParser parser = new FindDeliveryCommandParser();

    // EP: Empty/blank input — no prefix, no date
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDeliveryCommand.MESSAGE_USAGE));
    }

    // EP: Non-empty preamble — raw date with no prefix becomes preamble;
    //     preamble present alongside a valid prefix is also rejected
    @Test
    public void parse_invalidPreamble_throwsParseException() {
        // raw date with no prefix
        assertParseFailure(parser, "2026-04-01",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDeliveryCommand.MESSAGE_USAGE));

        // non-empty preamble even when a valid prefix follows
        assertParseFailure(parser, " some text dt/2026-04-01",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDeliveryCommand.MESSAGE_USAGE));
    }

    // EP: Valid single date with dt/ prefix
    @Test
    public void parse_validSingleDate_returnsFindDeliveryCommand() {
        LocalDate date = LocalDate.parse("2026-04-01");
        FindDeliveryCommand expectedCommand =
                new FindDeliveryCommand(new DeliveryDatePredicate(date));

        // exact input
        assertParseSuccess(parser, " dt/2026-04-01", expectedCommand);

        // extra whitespace around date value
        assertParseSuccess(parser, " dt/ 2026-04-01 ", expectedCommand);
    }

    // EP: Valid date range with st/ and ed/ prefixes, start < end
    @Test
    public void parse_validDateRange_returnsFindDeliveryCommand() {
        LocalDate startDate = LocalDate.parse("2026-04-01");
        LocalDate endDate = LocalDate.parse("2026-04-30");
        FindDeliveryCommand expectedCommand =
                new FindDeliveryCommand(new DeliveryDatePredicate(startDate, endDate));

        assertParseSuccess(parser, " st/2026-04-01 ed/2026-04-30", expectedCommand);
    }

    // EP: Valid date range with st/ and ed/ on the same date
    // Boundary: start date == end date
    @Test
    public void parse_sameDateRange_returnsFindDeliveryCommand() {
        LocalDate date = LocalDate.parse("2026-04-01");
        FindDeliveryCommand expectedCommand =
                new FindDeliveryCommand(new DeliveryDatePredicate(date));

        assertParseSuccess(parser, " st/2026-04-01 ed/2026-04-01", expectedCommand);
    }

    // EP: Invalid date format
    @Test
    public void parse_invalidSingleDate_throwsParseException() {
        assertParseFailure(parser, " dt/invalid-date", MESSAGE_INVALID_DATE);
    }

    @Test
    public void parse_invalidStartDate_throwsParseException() {
        assertParseFailure(parser, " st/invalid-date ed/2026-04-30", MESSAGE_INVALID_DATE);
    }

    @Test
    public void parse_invalidEndDate_throwsParseException() {
        assertParseFailure(parser, " st/2026-04-01 ed/invalid-date", MESSAGE_INVALID_DATE);
    }

    // EP: Start date after end date
    @Test
    public void parse_startAfterEnd_throwsParseException() {
        assertParseFailure(parser, " st/2026-04-30 ed/2026-04-01", MESSAGE_START_AFTER_END);
    }

    // EP: Only st/ is provided
    @Test
    public void parse_missingEndDate_throwsParseException() {
        assertParseFailure(parser, " st/2026-04-01",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDeliveryCommand.MESSAGE_USAGE));
    }

    // EP: Only ed/ is provided
    @Test
    public void parse_missingStartDate_throwsParseException() {
        assertParseFailure(parser, " ed/2026-04-30",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDeliveryCommand.MESSAGE_USAGE));
    }

    // EP: dt/ prefix mixed with st/ or ed/
    @Test
    public void parse_mixedPrefixes_throwsParseException() {
        assertParseFailure(parser, " dt/2026-04-01 st/2026-04-01",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDeliveryCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " dt/2026-04-01 ed/2026-04-30",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDeliveryCommand.MESSAGE_USAGE));
    }
}

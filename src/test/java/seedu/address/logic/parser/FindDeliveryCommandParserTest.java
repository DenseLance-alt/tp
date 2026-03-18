package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_DATE;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindDeliveryCommand;
import seedu.address.model.delivery.DeliveryDatePredicate;

public class FindDeliveryCommandParserTest {

    private FindDeliveryCommandParser parser = new FindDeliveryCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDeliveryCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindDeliveryCommand() {

        LocalDate date = LocalDate.parse("2026-04-01");

        FindDeliveryCommand expectedCommand =
                new FindDeliveryCommand(new DeliveryDatePredicate(date));

        assertParseSuccess(parser, "2026-04-01", expectedCommand);

        assertParseSuccess(parser, " \n 2026-04-01 \t", expectedCommand);
    }

    @Test
    public void parse_invalidDate_throwsParseException() {
        assertParseFailure(parser, "invalid-date", MESSAGE_INVALID_DATE);
    }
}

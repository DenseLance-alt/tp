package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIND_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.time.LocalDate;

import seedu.address.logic.commands.FindDeliveryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.DeliveryDatePredicate;

/**
 * Parses input arguments and creates a new FindDeliveryCommand object.
 */
public class FindDeliveryCommandParser implements Parser<FindDeliveryCommand> {

    public static final String MESSAGE_START_AFTER_END =
            "Start date must not be after end date.";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * FindDeliveryCommand and returns a FindDeliveryCommand object for execution.
     *
     * @param args The arguments string to parse.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindDeliveryCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FIND_DATE, PREFIX_START_DATE, PREFIX_END_DATE);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDeliveryCommand.MESSAGE_USAGE));
        }

        boolean hasExactDate = argMultimap.getValue(PREFIX_FIND_DATE).isPresent();
        boolean hasStartDate = argMultimap.getValue(PREFIX_START_DATE).isPresent();
        boolean hasEndDate = argMultimap.getValue(PREFIX_END_DATE).isPresent();

        if (hasExactDate && (hasStartDate || hasEndDate)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDeliveryCommand.MESSAGE_USAGE));
        }

        if (hasExactDate) {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_FIND_DATE);
            LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_FIND_DATE).get());
            return new FindDeliveryCommand(new DeliveryDatePredicate(date));
        }

        if (hasStartDate && hasEndDate) {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_START_DATE, PREFIX_END_DATE);
            LocalDate startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_START_DATE).get());
            LocalDate endDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_END_DATE).get());
            if (startDate.isAfter(endDate)) {
                throw new ParseException(MESSAGE_START_AFTER_END);
            }
            return new FindDeliveryCommand(new DeliveryDatePredicate(startDate, endDate));
        }

        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDeliveryCommand.MESSAGE_USAGE));
    }
}

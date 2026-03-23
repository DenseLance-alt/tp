package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RescheduleCommand;
import seedu.address.logic.commands.RescheduleCommand.RescheduleDeliveryDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RescheduleCommand object
 */
public class RescheduleCommandParser implements Parser<RescheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RescheduleCommand
     * and returns an RescheduleCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public RescheduleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_START_DATE,
                        PREFIX_END_DATE,
                        PREFIX_TIME,
                        PREFIX_DAYS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RescheduleCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_TIME, PREFIX_DAYS);

        RescheduleDeliveryDescriptor rescheduleDeliveryDescriptor = new RescheduleDeliveryDescriptor();

        if (argMultimap.getValue(PREFIX_START_DATE).isPresent()) {
            rescheduleDeliveryDescriptor.setStartDate(ParserUtil.parseStartDate(argMultimap
                    .getValue(PREFIX_START_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_END_DATE).isPresent()) {
            rescheduleDeliveryDescriptor.setEndDate(ParserUtil.parseEndDate(argMultimap
                    .getValue(PREFIX_END_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
            rescheduleDeliveryDescriptor.setDeliveryTime(ParserUtil.parseDeliveryTime(argMultimap
                    .getValue(PREFIX_TIME).get()));
        }
        if (argMultimap.getValue(PREFIX_DAYS).isPresent()) {
            rescheduleDeliveryDescriptor.setDeliveryDays(ParserUtil.parseDeliveryDays(argMultimap
                    .getValue(PREFIX_DAYS).get()));
        }

        if (!rescheduleDeliveryDescriptor.isAnyFieldEdited()) {
            throw new ParseException(RescheduleCommand.MESSAGE_NOT_EDITED);
        }

        return new RescheduleCommand(index, rescheduleDeliveryDescriptor);
    }

}

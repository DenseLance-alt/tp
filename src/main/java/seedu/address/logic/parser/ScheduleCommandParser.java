package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_OF_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.*;
import seedu.address.model.delivery.fields.NumberOfDays;

/**
 * Parses input arguments and creates a new ScheduleCommand object
 */
public class ScheduleCommandParser implements Parser<ScheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleCommand
     * and returns a ScheduleCommand object for execution.
     * @throws ParseException if the user input does not confirm the expected format.
     */
    public ScheduleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                                           PREFIX_START_DATE,
                                           PREFIX_NUMBER_OF_DAYS,
                                           PREFIX_TIME,
                                           PREFIX_DAYS);

        if (!arePrefixesPresent(argMultimap, PREFIX_START_DATE, PREFIX_NUMBER_OF_DAYS, PREFIX_TIME, PREFIX_DAYS)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_START_DATE, PREFIX_NUMBER_OF_DAYS, PREFIX_TIME, PREFIX_DAYS);
        StartDate startDate = ParserUtil.parseStartDate(argMultimap.getValue(PREFIX_START_DATE).get());
        NumberOfDays numberOfDays = ParserUtil.parseNumberOfDays(argMultimap.getValue(PREFIX_NUMBER_OF_DAYS).get());
        DeliveryTime deliveryTime = ParserUtil.parseDeliveryTime(argMultimap.getValue(PREFIX_TIME).get());
        Set<DeliveryDay> deliveryDayList = ParserUtil.parseDeliveryDays(argMultimap.getValue(PREFIX_DAYS).get());

        EndDate endDate = ParserUtil.getEndDate(startDate, numberOfDays);

        // TODO: Remove Set<SkippedDate> from constructor after removing SkippedDate.
        Delivery delivery = new Delivery(startDate,
                                         endDate,
                                         deliveryDayList,
                                         deliveryTime,
                                         new HashSet<>());

        return new ScheduleCommand(index, delivery);
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEFORE_DATE;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;
import static seedu.address.logic.parser.ParserUtil.parseDate;

import java.time.LocalDate;

import seedu.address.logic.commands.ExpiredCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonHasExpiredDeliveryPredicate;

/**
 * Parses input arguments and creates a new ExpiredCommand object.
 */
public class ExpiredCommandParser implements Parser<ExpiredCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExpiredCommand
     * and returns an ExpiredCommand object for execution.
     *
     * @param args Arguments specified after the command word.
     * @return The constructed {@code ExpiredCommand} based on the arguments specified.
     * @throws ParseException If the user input does not conform the expected format.
     */
    public ExpiredCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_BEFORE_DATE);

        // Check that required prefixes are present and there is no text before the first prefix
        if (!arePrefixesPresent(argMultimap, PREFIX_BEFORE_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpiredCommand.MESSAGE_USAGE));
        }

        // Check that there are no duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_BEFORE_DATE);

        String dateString = argMultimap.getValue(PREFIX_BEFORE_DATE).get();
        LocalDate date = parseDate(dateString);
        PersonHasExpiredDeliveryPredicate predicate = new PersonHasExpiredDeliveryPredicate(date);
        return new ExpiredCommand(predicate);
    }
}

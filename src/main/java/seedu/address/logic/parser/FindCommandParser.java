package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonMatchesFilterPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @param args Arguments specified after the find command word.
     * @return The constructed {@code FindCommand} based on the arguments specified.
     * @throws ParseException If the user input does not conform the expected format.
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_TAG);

        // If no prefixes specified or preamble contains unrecognised characters.
        if (!areAnyPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Disallow duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_TAG);

        // Extract list of name, address and tag keywords for filtering.
        // Throws ParseException if name, address or tag keywords are invalid.
        List<String> nameKeywords = parseKeywords(argMultimap, PREFIX_NAME);
        List<String> addressKeywords = parseKeywords(argMultimap, PREFIX_ADDRESS);
        List<String> tagKeywords = parseKeywords(argMultimap, PREFIX_TAG);

        // isEmpty() == true, when no prefix specified or no keyword specified after prefix.
        if (nameKeywords.isEmpty() && addressKeywords.isEmpty() && tagKeywords.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // At least 1 filter with both prefix and keyword specified
        return new FindCommand(new PersonMatchesFilterPredicate(nameKeywords, addressKeywords, tagKeywords));
    }

    /**
     * Returns true if at least 1 of the prefixes contains a non-empty {@code Optional} value in the given
     * {@code ArgumentMultimap}.
     *
     * @param argumentMultimap {@code ArgumentMultimap} to check for values of the specified prefixes.
     * @param prefixes Variable number of {@code Prefix} to check for their values.
     * @return {@code true} if at least 1 of the prefixes contains a non-empty {@code Optional} value.
     *         {@code false} otherwise.
     */
    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        // anyMatch checks if any of the prefixes is present (short-circuiting)
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns a list of keywords by splitting prefix's value by (1 or more) whitespaces.
     * Otherwise, returns an empty list if there are no keywords (specified after the prefix).
     */
    private static List<String> parseKeywords(ArgumentMultimap argumentMultimap, Prefix prefix)
            throws ParseException {

        // if keyword unspecified, value = "", filter removes empty string
        List <String> keywordsList = argumentMultimap.getValue(prefix).stream()
                .flatMap(keywordsString -> Arrays.stream(keywordsString.trim().split("\\s+")))
                .filter(keyword -> !keyword.isEmpty())
                .toList();

        if (prefix.equals(PREFIX_NAME)) {
            validateNameKeywords(keywordsList);

        } else if (prefix.equals(PREFIX_ADDRESS)) {
            validateAddressKeywords(keywordsList);

        } else if (prefix.equals(PREFIX_TAG)) {
            validateTagKeywords(keywordsList);

        } else { // Should never reach else
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_INVALID_PREFIX));
        }

        return keywordsList;
    }

    /**
     * Throws ParseException if any name keywords are invalid.
     */
    private static void validateNameKeywords(List<String> nameKeywords) throws ParseException {
        for (String nameKeyword : nameKeywords) { // handles empty list
            try {
                ParserUtil.parseName(nameKeyword);

            } catch (ParseException pe) {
                throw new ParseException(FindCommand.MESSAGE_INVALID_NAME_KEYWORD);
            }
        }
    }

    /**
     * Throws ParseException if any address keywords are invalid.
     */
    private static void validateAddressKeywords(List<String> addressKeywords) throws ParseException {
        for (String addressKeyword : addressKeywords) { // handles empty list
            try {
                ParserUtil.parseAddress(addressKeyword);

            } catch (ParseException pe) {
                // Address is only invalid if first character is space. But trim and split removes it.
                // Defensive programming. If valid address format/regex changes, still validates correctly.
                throw new ParseException(FindCommand.MESSAGE_INVALID_ADDRESS_KEYWORD);
            }
        }
    }

    /**
     * Throws ParseException if any tag keywords are invalid.
     */
    private static void validateTagKeywords(List<String> tagKeywords) throws ParseException {
        for (String tagKeyword : tagKeywords) { // handles empty list
            try {
                ParserUtil.parseTag(tagKeyword);

            } catch (ParseException pe) {
                throw new ParseException(FindCommand.MESSAGE_INVALID_TAG_KEYWORD);
            }
        }
    }
}

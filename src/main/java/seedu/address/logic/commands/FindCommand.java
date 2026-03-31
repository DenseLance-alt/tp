package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.PersonMatchesFilterPredicate;

/**
 * Finds and lists all persons in address book whose attributes (fields) match all the filters.
 * Keyword matching is case-insensitive. Full word match is required.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all customers whose attributes (name, address, tag) match all filter(s) specified, "
            + "and displays them as a list with index numbers.\n"
            + "A customer matches a filter when at least 1 of the keywords (case-insensitive) "
            + "in the filter matches.\n"
            + "At least 1 filter with a keyword must be specified. "
            + "Filters: "
            + "[" + PREFIX_NAME + "NAME_KEYWORDS...] "
            + "[" + PREFIX_ADDRESS + "ADDRESS_KEYWORDS...] "
            + "[" + PREFIX_TAG + "TAG_KEYWORDS...]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Alex Bernice "
            + PREFIX_ADDRESS + "Jurong "
            + PREFIX_TAG + "Vegetarian";

    public static final String MESSAGE_INVALID_NAME_KEYWORD =
            "Name keywords should only contain alphanumeric characters.";
    public static final String MESSAGE_INVALID_ADDRESS_KEYWORD =
            "Address keywords should not contain a space as the first character.";
    public static final String MESSAGE_INVALID_TAG_KEYWORD =
            "Tag keywords should only contain alphanumeric characters.";
    public static final String MESSAGE_INVALID_PREFIX = "Invalid prefix provided. Only supports: "
            + PREFIX_NAME + ", " + PREFIX_ADDRESS + ", " + PREFIX_TAG + ".";

    private static final Logger logger = LogsCenter.getLogger(FindCommand.class);

    private final PersonMatchesFilterPredicate predicate;

    /**
     * Creates a FindCommand to filter based on the specified {@code PersonMatchesFilterPredicate}.
     *
     * @param predicate The {@code PersonMatchesFilterPredicate} used to filter, not null.
     */
    public FindCommand(PersonMatchesFilterPredicate predicate) {
        requireNonNull(predicate); // defensive programming

        logger.fine("Initializing with predicate: " + predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

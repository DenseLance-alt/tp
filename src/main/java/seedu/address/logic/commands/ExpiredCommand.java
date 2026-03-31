package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEFORE_DATE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.PersonHasExpiredDeliveryPredicate;

/**
 * Finds and lists all persons with expired deliveries.
 * <p>A delivery is considered expired if its end date is before the specified date.
 */
public class ExpiredCommand extends Command {

    public static final String COMMAND_WORD = "expired";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all customers whose deliveries have ended before the specified date "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: "
            + PREFIX_BEFORE_DATE + "DATE (yyyy-MM-dd)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_BEFORE_DATE + "2026-04-01";

    private final PersonHasExpiredDeliveryPredicate predicate;

    /**
     * Creates an ExpiredCommand to filter based on the specified {@code PersonHasExpiredDeliveryPredicate}.
     *
     * @param predicate Predicate used to filter persons with expired deliveries. Must not be null.
     */
    public ExpiredCommand(PersonHasExpiredDeliveryPredicate predicate) {
        requireNonNull(predicate);
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
        if (!(other instanceof ExpiredCommand)) {
            return false;
        }

        ExpiredCommand otherExpiredCommand = (ExpiredCommand) other;
        return predicate.equals(otherExpiredCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }

}

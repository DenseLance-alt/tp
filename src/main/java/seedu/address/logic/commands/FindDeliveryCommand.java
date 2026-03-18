package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.delivery.DeliveryDatePredicate;

/**
 * Finds and lists all persons who require delivery on the specified date.
 */
public class FindDeliveryCommand extends Command {

    public static final String COMMAND_WORD = "find-delivery";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all persons who require delivery on the specified date.\n"
            + "Parameters: DATE (yyyy-MM-dd)\n"
            + "Example: " + COMMAND_WORD + " 2026-04-01";

    private final DeliveryDatePredicate predicate;

    /**
     * Creates a FindDeliveryCommand to find persons whose delivery falls on the date
     * specified by the given {@code predicate}.
     */
    public FindDeliveryCommand(DeliveryDatePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                        model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindDeliveryCommand)) {
            return false;
        }

        FindDeliveryCommand otherFindDeliveryCommand = (FindDeliveryCommand) other;
        return predicate.equals(otherFindDeliveryCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

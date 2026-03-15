package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Unschedules a person's delivery identified using their
 * displayed index from the address book.
 */
public class UnscheduleCommand extends Command {

    public static final String COMMAND_WORD = "unschedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person's delivery identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_DELIVERY_SUCCESS = "Deleted Delivery for Person: %1$s";
    public static final String MESSAGE_MISSING_DELIVERY =
            "This person does not have an existing delivery in the address book.";

    private final Index targetIndex;

    public UnscheduleCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUnschedule = lastShownList.get(targetIndex.getZeroBased());

        if (!personToUnschedule.hasDelivery()) {
            throw new CommandException(MESSAGE_MISSING_DELIVERY);
        }

        Person personWithNoDelivery = personToUnschedule.withoutDelivery();
        model.setPerson(personToUnschedule, personWithNoDelivery);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_DELIVERY_SUCCESS,
                Messages.formatDeliveryFromPerson(personToUnschedule)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnscheduleCommand)) {
            return false;
        }

        UnscheduleCommand otherUnscheduleCommand = (UnscheduleCommand) other;
        return targetIndex.equals(otherUnscheduleCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}

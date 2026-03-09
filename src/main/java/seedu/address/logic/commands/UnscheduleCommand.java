package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

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
        model.setPerson(personToUnschedule, removeDelivery(personToUnschedule));
        return new CommandResult(String.format(MESSAGE_DELETE_DELIVERY_SUCCESS,
                Messages.formatDelivery(personToUnschedule)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToUnschedule}
     * but has delivery removed.
     */
    private static Person removeDelivery(Person personToUnschedule) {
        assert personToUnschedule != null;

        Name name = personToUnschedule.getName();
        Phone phone = personToUnschedule.getPhone();
        Email email = personToUnschedule.getEmail();
        Address address = personToUnschedule.getAddress();
        Set<Tag> tags = personToUnschedule.getTags();

        return new Person(name, phone, email, address, tags, null);
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

        UnscheduleCommand otherDeleteCommand = (UnscheduleCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}

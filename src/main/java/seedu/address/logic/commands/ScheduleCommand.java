package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Schedules a delivery for a person, who is identified using their
 * displayed index from the address book.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a delivery to the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_START_DATE + "START DATE "
            + PREFIX_END_DATE + "END DATE "
            + PREFIX_TIME + "DELIVERY TIME "
            + PREFIX_DAYS + "DELIVERY DAYS...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_START_DATE + "2026-10-15 "
            + PREFIX_END_DATE + "2026-10-20 "
            + PREFIX_TIME + "12:59 "
            + PREFIX_DAYS + "124";

    public static final String MESSAGE_SCHEDULE_DELIVERY_SUCCESS = "Scheduled Delivery for Person: %1$s";
    public static final String MESSAGE_PERSON_HAS_SCHEDULE = "Person already has Delivery: %1$s";

    private final Index targetIndex;
    private final Delivery toSchedule;

    /**
     * Creates a ScheduleCommand to add the specified {@code Delivery}
     * to the Person object found at the specified index of the address book.
     */
    public ScheduleCommand(Index targetIndex, Delivery delivery) {
        requireNonNull(delivery);
        this.targetIndex = targetIndex;
        this.toSchedule = delivery;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToSchedule = lastShownList.get(targetIndex.getZeroBased());

        if (personToSchedule.hasDelivery()) {
            throw new CommandException(String.format(MESSAGE_PERSON_HAS_SCHEDULE,
                    Messages.formatDeliveryFromPerson(personToSchedule)));
        }

        Person personWithDelivery = addDeliveryToPerson(personToSchedule, toSchedule);

        assert personWithDelivery.hasDelivery();

        model.setPerson(personToSchedule, personWithDelivery);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SCHEDULE_DELIVERY_SUCCESS,
                Messages.formatDeliveryFromPerson(personWithDelivery)));
    }

    /**
     * Creates and returns a new {@code Person} with the details of
     * {@code personToSchedule} but having {@code toSchedule} as their delivery.
     */
    private static Person addDeliveryToPerson(Person personToSchedule, Delivery toSchedule) {
        assert personToSchedule != null;

        Name name = personToSchedule.getName();
        Phone phone = personToSchedule.getPhone();
        Email email = personToSchedule.getEmail();
        Address address = personToSchedule.getAddress();
        Set<Tag> tags = personToSchedule.getTags();

        return new Person(name, phone, email, address, tags, toSchedule);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ScheduleCommand)) {
            return false;
        }

        ScheduleCommand otherScheduleCommand = (ScheduleCommand) other;
        return targetIndex.equals(otherScheduleCommand.targetIndex)
                && toSchedule.equals(otherScheduleCommand.toSchedule);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("toSchedule", toSchedule)
                .toString();
    }
}

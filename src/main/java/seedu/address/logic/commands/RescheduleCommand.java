package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryDay;
import seedu.address.model.delivery.DeliveryTime;
import seedu.address.model.delivery.EndDate;
import seedu.address.model.delivery.StartDate;
import seedu.address.model.person.Person;

/**
 * Edits the details of an existing delivery assigned to a particular person in the address book.
 */
public class RescheduleCommand extends Command {

    public static final String COMMAND_WORD = "reschedule";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of a delivery assigned to "
            + "a particular person identified "
            + "by the index number used in the displayed person list."
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer)"
            + "[" + PREFIX_START_DATE + "START DATE] "
            + "[" + PREFIX_END_DATE + "END DATE] "
            + "[" + PREFIX_TIME + "DELIVERY TIME] "
            + "[" + PREFIX_DAYS + "DELIVERY DAYS]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_END_DATE + "2026-12-30 "
            + PREFIX_TIME + "14:00 "
            + PREFIX_DAYS + "35";

    public static final String MESSAGE_EDIT_DELIVERY_SUCCESS = "Edited Delivery: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_NON_EXISTENT_DELIVERY = "This person does not have an existing delivery";

    private final Index targetIndex;
    private final RescheduleDeliveryDescriptor rescheduleDeliveryDescriptor;

    /**
     * Creates a RescheduleCommand to edit the details of the delivery assigned to the specified person.
     *
     * @param targetIndex Index of the person whose delivery is to be edited.
     * @param rescheduleDeliveryDescriptor Details to edit the delivery with.
     */
    public RescheduleCommand(Index targetIndex, RescheduleDeliveryDescriptor rescheduleDeliveryDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(rescheduleDeliveryDescriptor);

        this.targetIndex = targetIndex;
        this.rescheduleDeliveryDescriptor = rescheduleDeliveryDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToReschedule = lastShownList.get(targetIndex.getZeroBased());
        if (!personToReschedule.hasDelivery()) {
            throw new CommandException(MESSAGE_NON_EXISTENT_DELIVERY);
        }
        Person rescheduledPerson = createRescheduledPerson(personToReschedule, rescheduleDeliveryDescriptor);

        model.setPerson(personToReschedule, rescheduledPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_DELIVERY_SUCCESS,
                Messages.formatDeliveryFromPerson(rescheduledPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToReschedule} but with the delivery
     * details edited according to  {@code rescheduleDeliveryDescriptor}
     *
     * @param personToReschedule The person whose delivery details are to be edited.
     * @param rescheduleDeliveryDescriptor The details to edit the delivery with.
     * @return A new {@code Person} with the edited delivery details.
     */
    private Person createRescheduledPerson(Person personToReschedule,
                                           RescheduleDeliveryDescriptor rescheduleDeliveryDescriptor) {
        assert personToReschedule != null;

        Delivery rescheduledDelivery = createRescheduledDelivery(personToReschedule, rescheduleDeliveryDescriptor);
        return new Person(personToReschedule.getName(), personToReschedule.getPhone(), personToReschedule.getEmail(),
                personToReschedule.getAddress(), personToReschedule.getTags(), rescheduledDelivery);
    }

    private Delivery createRescheduledDelivery(Person personToReschedule,
                                               RescheduleDeliveryDescriptor rescheduleDeliveryDescriptor) {
        StartDate startDate = rescheduleDeliveryDescriptor.getStartDate()
                .orElse(personToReschedule.getDeliveryStartDate());
        EndDate endDate = rescheduleDeliveryDescriptor.getEndDate()
                .orElse(personToReschedule.getDeliveryEndDate());
        Set<DeliveryDay> deliveryDays = rescheduleDeliveryDescriptor.getDeliveryDays()
                .orElse(personToReschedule.getDeliveryDays());
        DeliveryTime deliveryTime = rescheduleDeliveryDescriptor.getDeliveryTime()
                .orElse(personToReschedule.getDeliveryTime());

        Delivery rescheduledDelivery = new Delivery(startDate, endDate, deliveryDays, deliveryTime, new HashSet<>());
        return rescheduledDelivery;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RescheduleCommand)) {
            return false;
        }

        RescheduleCommand otherRescheduleCommand = (RescheduleCommand) other;
        return targetIndex.equals(otherRescheduleCommand.targetIndex)
                && rescheduleDeliveryDescriptor.equals(otherRescheduleCommand.rescheduleDeliveryDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("rescheduleDeliveryDescriptor", rescheduleDeliveryDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the delivery with. Each non-empty field value will replace the
     * corresponding field value of the delivery.
     */
    public static class RescheduleDeliveryDescriptor {
        private StartDate startDate;
        private EndDate endDate;
        private Set<DeliveryDay> deliveryDays;
        private DeliveryTime deliveryTime;

        /**
         * Creates a RescheduleDeliveryDescriptor with empty fields.
         */
        public RescheduleDeliveryDescriptor() {
        }

        /**
         * Creates a {@code RescheduleDeliveryDescriptor} by copying the fields of {@code toCopy}.
         *
         * @param toCopy Another RescheduleDeliveryDescriptor whose fields value will be copied to this object's
         *               fields value.
         */
        public RescheduleDeliveryDescriptor(RescheduleDeliveryDescriptor toCopy) {
            setStartDate(toCopy.startDate);
            setEndDate(toCopy.endDate);
            setDeliveryDays(toCopy.deliveryDays);
            setDeliveryTime(toCopy.deliveryTime);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(startDate, endDate, deliveryDays, deliveryTime);
        }

        public void setStartDate(StartDate startDate) {
            this.startDate = startDate;
        }

        public Optional<StartDate> getStartDate() {
            return Optional.ofNullable(startDate);
        }

        public void setEndDate(EndDate endDate) {
            this.endDate = endDate;
        }

        public Optional<EndDate> getEndDate() {
            return Optional.ofNullable(endDate);
        }

        /**
         * Sets {@code deliveryDays} to this object's {@code deliveryDays}.
         *
         * @param deliveryDays A set of delivery days to be set.
         */
        public void setDeliveryDays(Set<DeliveryDay> deliveryDays) {
            this.deliveryDays = (deliveryDays != null) ? new HashSet<>(deliveryDays) : null;
        }

        /**
         * Returns an unmodifiable delivery day set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         *
         * @return An unmodifiable delivery day set or {@code Optional#empty()} if {@code deliveryDays} is null.
         */
        public Optional<Set<DeliveryDay>> getDeliveryDays() {
            return (deliveryDays != null) ? Optional.of(Collections.unmodifiableSet(deliveryDays)) : Optional.empty();
        }

        public void setDeliveryTime(DeliveryTime deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public Optional<DeliveryTime> getDeliveryTime() {
            return Optional.ofNullable(deliveryTime);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof RescheduleDeliveryDescriptor)) {
                return false;
            }

            RescheduleDeliveryDescriptor otherRescheduleDeliveryDescriptor = (RescheduleDeliveryDescriptor) other;
            return Objects.equals(startDate, otherRescheduleDeliveryDescriptor.startDate)
                    && Objects.equals(endDate, otherRescheduleDeliveryDescriptor.endDate)
                    && Objects.equals(deliveryDays, otherRescheduleDeliveryDescriptor.deliveryDays)
                    && Objects.equals(deliveryTime, otherRescheduleDeliveryDescriptor.deliveryTime);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("startDate", startDate)
                    .add("endDate", endDate)
                    .add("deliveryDays", deliveryDays)
                    .add("deliveryTime", deliveryTime)
                    .toString();
        }
    }
}

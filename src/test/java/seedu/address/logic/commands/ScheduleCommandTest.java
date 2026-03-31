package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.person.Person;
import seedu.address.testutil.DeliveryBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ScheduleCommand}.
 */
public class ScheduleCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_nullDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ScheduleCommand(INDEX_FIRST_PERSON, null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Delivery delivery = new DeliveryBuilder().build();
        Person personToSchedule = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        // ensures that person does not have delivery
        assertFalse(personToSchedule.hasDelivery());

        Person personWithDelivery = new PersonBuilder(personToSchedule).withDelivery(delivery).build();
        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_SECOND_PERSON, delivery);

        String expectedMessage = String.format(ScheduleCommand.MESSAGE_SCHEDULE_DELIVERY_SUCCESS,
                Messages.formatDeliveryFromPerson(personWithDelivery));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // ensures that personToSchedule is in the correct index
        assertTrue(model.getFilteredPersonList().get(1).equals(personToSchedule));

        expectedModel.setPerson(model.getFilteredPersonList().get(1), personWithDelivery);

        assertCommandSuccess(scheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Delivery delivery = new DeliveryBuilder().build();
        ScheduleCommand scheduleCommand = new ScheduleCommand(outOfBoundIndex, delivery);

        assertCommandFailure(scheduleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_personAlreadyWithDeliveryUnfilteredList_failure() {
        Delivery delivery = new DeliveryBuilder().build();
        Person personToSchedule = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // ensures that person does have delivery
        assertTrue(personToSchedule.hasDelivery());

        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_PERSON, delivery);

        String expectedMessage = String.format(ScheduleCommand.MESSAGE_PERSON_HAS_SCHEDULE,
                Messages.formatDeliveryFromPerson(personToSchedule));

        // ensures that personToSchedule is in the correct index
        assertTrue(model.getFilteredPersonList().get(0).equals(personToSchedule));

        assertCommandFailure(scheduleCommand, model, expectedMessage);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);

        Delivery delivery = new DeliveryBuilder().build();
        Person personToSchedule = model.getFilteredPersonList().get(0);

        // ensures that person does not have delivery
        assertFalse(personToSchedule.hasDelivery());

        Person personWithDelivery = new PersonBuilder(personToSchedule).withDelivery(delivery).build();
        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_PERSON, delivery);

        String expectedMessage = String.format(ScheduleCommand.MESSAGE_SCHEDULE_DELIVERY_SUCCESS,
                                               Messages.formatDeliveryFromPerson(personWithDelivery));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // ensures that personToSchedule is in the correct index
        assertTrue(model.getFilteredPersonList().get(0).equals(personToSchedule));

        expectedModel.setPerson(model.getFilteredPersonList().get(0), personWithDelivery);

        assertCommandSuccess(scheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_personAlreadyWithDeliveryFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Delivery delivery = new DeliveryBuilder().build();
        Person personToSchedule = model.getFilteredPersonList().get(0);

        // ensures that person does have delivery
        assertTrue(personToSchedule.hasDelivery());

        ScheduleCommand scheduleCommand = new ScheduleCommand(INDEX_FIRST_PERSON, delivery);

        String expectedMessage = String.format(ScheduleCommand.MESSAGE_PERSON_HAS_SCHEDULE,
                                               Messages.formatDeliveryFromPerson(personToSchedule));

        // ensures that personToSchedule is in the correct index
        assertTrue(model.getFilteredPersonList().get(0).equals(personToSchedule));

        assertCommandFailure(scheduleCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        Delivery delivery = new DeliveryBuilder().build();
        ScheduleCommand scheduleCommand = new ScheduleCommand(outOfBoundIndex, delivery);

        assertCommandFailure(scheduleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Delivery firstDelivery = new DeliveryBuilder().build();
        Delivery secondDelivery = new DeliveryBuilder().withDeliveryTime("16:19").build();

        assertFalse(firstDelivery.equals(secondDelivery));

        ScheduleCommand scheduleFirstCommand = new ScheduleCommand(INDEX_FIRST_PERSON, firstDelivery);
        ScheduleCommand scheduleSecondCommand = new ScheduleCommand(INDEX_SECOND_PERSON, secondDelivery);
        ScheduleCommand scheduleThirdCommand = new ScheduleCommand(INDEX_SECOND_PERSON, firstDelivery);
        ScheduleCommand scheduleFourthCommand = new ScheduleCommand(INDEX_FIRST_PERSON, secondDelivery);

        // same object -> returns true
        assertTrue(scheduleFirstCommand.equals(scheduleFirstCommand));

        // same values -> returns true
        ScheduleCommand scheduleFirstCommandCopy = new ScheduleCommand(INDEX_FIRST_PERSON, firstDelivery);
        assertTrue(scheduleFirstCommand.equals(scheduleFirstCommandCopy));

        // different types -> returns false
        assertFalse(scheduleFirstCommand.equals(1));

        // null -> returns false
        assertFalse(scheduleFirstCommand.equals(null));

        // different objects with completely different values -> returns false
        assertFalse(scheduleFirstCommand.equals(scheduleSecondCommand));

        // different objects with the same index but different delivery -> returns false
        assertFalse(scheduleFirstCommand.equals(scheduleThirdCommand));

        // different objects with the same delivery but different index -> returns false
        assertFalse(scheduleFirstCommand.equals(scheduleFourthCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        Delivery delivery = new DeliveryBuilder().build();
        ScheduleCommand scheduleCommand = new ScheduleCommand(targetIndex, delivery);
        String expected = ScheduleCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex
                + ", toSchedule=" + delivery + "}";
        assertEquals(expected, scheduleCommand.toString());
    }
}

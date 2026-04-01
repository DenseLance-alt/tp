package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code UnscheduleCommand}.
 */
public class UnscheduleCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToUnschedule = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personWithNoDelivery = new PersonBuilder(personToUnschedule).withDelivery(null).build();
        UnscheduleCommand unscheduleCommand = new UnscheduleCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnscheduleCommand.MESSAGE_DELETE_DELIVERY_SUCCESS,
                Messages.formatDeliveryFromPerson(personToUnschedule));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), personWithNoDelivery);

        assertCommandSuccess(unscheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnscheduleCommand unscheduleCommand = new UnscheduleCommand(outOfBoundIndex);

        assertCommandFailure(unscheduleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_personWithNoDeliveryUnfilteredList_throwsCommandException() {
        Person personToUnschedule = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        assert !personToUnschedule.hasDelivery();
        UnscheduleCommand unscheduleCommand = new UnscheduleCommand(INDEX_SECOND_PERSON);

        assertCommandFailure(unscheduleCommand, model, UnscheduleCommand.MESSAGE_MISSING_DELIVERY);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToUnschedule = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personWithNoDelivery = new PersonBuilder(personToUnschedule).withDelivery(null).build();
        UnscheduleCommand unscheduleCommand = new UnscheduleCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnscheduleCommand.MESSAGE_DELETE_DELIVERY_SUCCESS,
                Messages.formatDeliveryFromPerson(personToUnschedule));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), personWithNoDelivery);

        assertCommandSuccess(unscheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnscheduleCommand unscheduleCommand = new UnscheduleCommand(outOfBoundIndex);

        assertCommandFailure(unscheduleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnscheduleCommand unscheduleFirstCommand = new UnscheduleCommand(INDEX_FIRST_PERSON);
        UnscheduleCommand unscheduleSecondCommand = new UnscheduleCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(unscheduleFirstCommand.equals(unscheduleFirstCommand));

        // same values -> returns true
        UnscheduleCommand unscheduleFirstCommandCopy = new UnscheduleCommand(INDEX_FIRST_PERSON);
        assertTrue(unscheduleFirstCommand.equals(unscheduleFirstCommandCopy));

        // different types -> returns false
        assertFalse(unscheduleFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unscheduleFirstCommand.equals(null));

        // different objects -> returns false
        assertFalse(unscheduleFirstCommand.equals(unscheduleSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnscheduleCommand unscheduleCommand = new UnscheduleCommand(targetIndex);
        String expected = UnscheduleCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, unscheduleCommand.toString());
    }
}

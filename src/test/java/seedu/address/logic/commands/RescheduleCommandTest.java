package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY_RESCHEDULE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB_RESCHEDULE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_TIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.RescheduleCommand.RescheduleDeliveryDescriptor;
import static seedu.address.testutil.TypicalDeliveries.DELIVERY_ELLE;
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
import seedu.address.testutil.RescheduleDeliveryDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RescheduleCommand.
 */
public class RescheduleCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personWithEditedDelivery = new PersonBuilder(firstPerson)
                .withDelivery(DELIVERY_ELLE)
                .build();

        RescheduleDeliveryDescriptor descriptor = new RescheduleDeliveryDescriptorBuilder(DELIVERY_ELLE).build();
        RescheduleCommand rescheduleCommand = new RescheduleCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(
                RescheduleCommand.MESSAGE_EDIT_DELIVERY_SUCCESS,
                Messages.formatDeliveryFromPerson(personWithEditedDelivery));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(firstPerson, personWithEditedDelivery);

        assertCommandSuccess(rescheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Delivery firstPersonsDelivery = firstPerson.getDelivery();

        // A hotfix before skipped date is removed from Delivery
        Delivery editedDelivery = new DeliveryBuilder()
                .withStartDate(firstPersonsDelivery.getStartDate().toString())
                .withEndDate(firstPersonsDelivery.getEndDate().toString())
                .withDeliveryDays(firstPersonsDelivery.getDeliveryDays().stream()
                        .map(day -> day.toString()).toArray(String[]::new))
                .withDeliveryTime(VALID_DELIVERY_TIME_AMY)
                .build();
        Person personWithEditedDelivery = new PersonBuilder(firstPerson).withDelivery(editedDelivery).build();

        RescheduleDeliveryDescriptor descriptor = new RescheduleDeliveryDescriptorBuilder()
                .withDeliveryTime(VALID_DELIVERY_TIME_AMY).build();
        RescheduleCommand rescheduleCommand = new RescheduleCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(
                RescheduleCommand.MESSAGE_EDIT_DELIVERY_SUCCESS,
                Messages.formatDeliveryFromPerson(personWithEditedDelivery));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(firstPerson, personWithEditedDelivery);

        assertCommandSuccess(rescheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        RescheduleCommand rescheduleCommand = new RescheduleCommand(INDEX_FIRST_PERSON,
                new RescheduleDeliveryDescriptor());
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Delivery firstPersonsDelivery = firstPerson.getDelivery();

        // A hotfix before skipped date is removed from Delivery
        Delivery editedDelivery = new DeliveryBuilder()
                .withStartDate(firstPersonsDelivery.getStartDate().toString())
                .withEndDate(firstPersonsDelivery.getEndDate().toString())
                .withDeliveryDays(firstPersonsDelivery.getDeliveryDays().stream()
                        .map(day -> day.toString()).toArray(String[]::new))
                .withDeliveryTime(firstPersonsDelivery.getDeliveryTime().toString())
                .build();
        Person firstPersonHotFix = new PersonBuilder(firstPerson).withDelivery(editedDelivery).build();

        String expectedMessage = String.format(
                RescheduleCommand.MESSAGE_EDIT_DELIVERY_SUCCESS, Messages.formatDeliveryFromPerson(firstPersonHotFix));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(firstPerson, firstPersonHotFix);

        assertCommandSuccess(rescheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personWithEditedDelivery = new PersonBuilder(personInFilteredList)
                .withDelivery(DELIVERY_ELLE)
                .build();
        RescheduleCommand rescheduleCommand = new RescheduleCommand(INDEX_FIRST_PERSON,
                new RescheduleDeliveryDescriptorBuilder(DELIVERY_ELLE).build());

        String expectedMessage = String.format(
                RescheduleCommand.MESSAGE_EDIT_DELIVERY_SUCCESS,
                Messages.formatDeliveryFromPerson(personWithEditedDelivery));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), personWithEditedDelivery);

        assertCommandSuccess(rescheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RescheduleDeliveryDescriptor descriptor = new RescheduleDeliveryDescriptorBuilder()
                .withDeliveryTime(VALID_DELIVERY_TIME_AMY).build();
        RescheduleCommand rescheduleCommand = new RescheduleCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(rescheduleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit delivery in a filtered list where index is larger than size of filtered list,
     * but smaller than size of address book.
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RescheduleCommand rescheduleCommand = new RescheduleCommand(outOfBoundIndex,
                new RescheduleDeliveryDescriptorBuilder()
                .withDeliveryTime(VALID_DELIVERY_TIME_AMY).build());

        assertCommandFailure(rescheduleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final RescheduleCommand standardCommand = new RescheduleCommand(INDEX_FIRST_PERSON,
                DESC_AMY_RESCHEDULE);

        // same values -> returns true
        RescheduleDeliveryDescriptor copyDescriptor = new RescheduleDeliveryDescriptor(DESC_AMY_RESCHEDULE);
        RescheduleCommand commandWithSameValues = new RescheduleCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RescheduleCommand(INDEX_SECOND_PERSON, DESC_AMY_RESCHEDULE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new RescheduleCommand(INDEX_FIRST_PERSON, DESC_BOB_RESCHEDULE)));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        RescheduleDeliveryDescriptor rescheduleDeliveryDescriptor = new RescheduleDeliveryDescriptor();
        RescheduleCommand rescheduleCommand = new RescheduleCommand(targetIndex, rescheduleDeliveryDescriptor);
        String expected = RescheduleCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex
                + ", rescheduleDeliveryDescriptor=" + rescheduleDeliveryDescriptor + "}";
        assertEquals(expected, rescheduleCommand.toString());
    }
}

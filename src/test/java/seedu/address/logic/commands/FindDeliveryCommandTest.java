package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.delivery.DeliveryDatePredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindDeliveryCommand}.
 */
public class FindDeliveryCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {

        DeliveryDatePredicate firstPredicate =
                new DeliveryDatePredicate(LocalDate.parse("2026-04-01"));
        DeliveryDatePredicate secondPredicate =
                new DeliveryDatePredicate(LocalDate.parse("2026-05-01"));

        FindDeliveryCommand findFirstCommand = new FindDeliveryCommand(firstPredicate);
        FindDeliveryCommand findSecondCommand = new FindDeliveryCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindDeliveryCommand findFirstCommandCopy = new FindDeliveryCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different delivery date -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_noPersonFound() {

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);

        DeliveryDatePredicate predicate =
                new DeliveryDatePredicate(LocalDate.parse("2026-04-01"));

        FindDeliveryCommand command = new FindDeliveryCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {

        DeliveryDatePredicate predicate =
                new DeliveryDatePredicate(LocalDate.parse("2026-04-01"));

        FindDeliveryCommand command = new FindDeliveryCommand(predicate);

        String expected = FindDeliveryCommand.class.getCanonicalName()
                + "{predicate=" + predicate + "}";

        assertEquals(expected, command.toString());
    }
}

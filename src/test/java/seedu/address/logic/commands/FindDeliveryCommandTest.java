package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.util.Arrays;
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

        // EP: same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // EP: same values -> returns true
        FindDeliveryCommand findFirstCommandCopy = new FindDeliveryCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // EP: different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // EP: null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // EP: different delivery date -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    // EP: single date that matches no person's delivery
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

    // EP: single date that matches multiple persons' deliveries
    @Test
    public void execute_singleDateMatchesPersons_personsFound() {
        DeliveryDatePredicate predicate =
                new DeliveryDatePredicate(LocalDate.parse("2025-10-20"));

        FindDeliveryCommand command = new FindDeliveryCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        assertEquals(Arrays.asList(ALICE, CARL), model.getFilteredPersonList());
    }

    // EP: date range that matches multiple persons' deliveries
    @Test
    public void execute_dateRangeMatchesPersons_personsFound() {
        DeliveryDatePredicate predicate =
                new DeliveryDatePredicate(LocalDate.parse("2025-10-19"), LocalDate.parse("2025-10-30"));

        FindDeliveryCommand command = new FindDeliveryCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        assertEquals(Arrays.asList(ALICE, CARL), model.getFilteredPersonList());
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

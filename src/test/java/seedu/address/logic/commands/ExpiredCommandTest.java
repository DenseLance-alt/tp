package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonHasExpiredDeliveryPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ExpiredCommand}.
 */
public class ExpiredCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validDate_multiplePersonsWithExpiredDeliveryFound() {
        LocalDate beforeDate = LocalDate.of(2024, 4, 1);
        PersonHasExpiredDeliveryPredicate predicate = new PersonHasExpiredDeliveryPredicate(beforeDate);
        ExpiredCommand command = new ExpiredCommand(predicate);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_validDate_personWithExpiredDeliveryFound() {
        // Elle's delivery ends on 2019-03-27 -> expires one day before specified date
        LocalDate beforeDate = LocalDate.of(2019, 3, 28);
        PersonHasExpiredDeliveryPredicate predicate = new PersonHasExpiredDeliveryPredicate(beforeDate);
        ExpiredCommand command = new ExpiredCommand(predicate);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(ELLE), model.getFilteredPersonList());
    }

    @Test
    public void execute_furthestPastDate_noPersonFound() {
        // No deliveries should have expired before the specified (furthest past) date
        PersonHasExpiredDeliveryPredicate predicate = new PersonHasExpiredDeliveryPredicate(LocalDate.MIN);
        ExpiredCommand command = new ExpiredCommand(predicate);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_furthestFutureDate_allPersonsWithDeliveryFound() {
        // All deliveries should have expired before the specified (furthest future) date
        PersonHasExpiredDeliveryPredicate predicate = new PersonHasExpiredDeliveryPredicate(LocalDate.MAX);
        ExpiredCommand command = new ExpiredCommand(predicate);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARL, DANIEL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void equals() {
        LocalDate firstDate = LocalDate.of(2026, 4, 1);
        LocalDate secondDate = LocalDate.of(2026, 2, 28);

        PersonHasExpiredDeliveryPredicate firstPredicate = new PersonHasExpiredDeliveryPredicate(firstDate);
        PersonHasExpiredDeliveryPredicate secondPredicate = new PersonHasExpiredDeliveryPredicate(secondDate);

        ExpiredCommand firstCommand = new ExpiredCommand(firstPredicate);
        ExpiredCommand secondCommand = new ExpiredCommand(secondPredicate);

        // EP: same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // EP: same values -> returns true
        ExpiredCommand firstCommandCopy = new ExpiredCommand(firstPredicate);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // EP: different types -> returns false
        assertFalse(firstCommand.equals(1));

        // EP: null -> returns false
        assertFalse(firstCommand.equals(null));

        // EP: different expired command -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void toStringMethod() {
        LocalDate firstDate = LocalDate.of(2026, 4, 1);
        PersonHasExpiredDeliveryPredicate predicate = new PersonHasExpiredDeliveryPredicate(firstDate);
        ExpiredCommand expiredCommand = new ExpiredCommand(predicate);

        String expected = ExpiredCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, expiredCommand.toString());
    }

}

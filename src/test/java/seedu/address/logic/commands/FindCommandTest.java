package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonMatchesFilterPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PersonMatchesFilterPredicate firstPredicate =
                new PersonMatchesFilterPredicate(Collections.singletonList("first"),
                        Collections.singletonList("first"), Collections.singletonList("first"));
        PersonMatchesFilterPredicate secondPredicate =
                new PersonMatchesFilterPredicate(Collections.singletonList("second"),
                        Collections.singletonList("second"), Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different find command -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_allPersonsFound() {
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        FindCommand command = new FindCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(getTypicalPersons(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleNameKeywords_multiplePersonsFound() {
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                Arrays.asList("Kurz", "Elle", "Kunz"), Collections.emptyList(), Collections.emptyList());
        FindCommand command = new FindCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleAddressKeywords_multiplePersonsFound() {
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                Collections.emptyList(), Arrays.asList("street", "nonexistent"), Collections.emptyList());
        FindCommand command = new FindCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, DANIEL, GEORGE), model.getFilteredPersonList());
    }

    //@@author elijah-ng
    @Test
    public void execute_multipleTagKeywords_multiplePersonsFound() {
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                Collections.emptyList(), Collections.emptyList(), Arrays.asList("West", "Halal"));
        FindCommand command = new FindCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredPersonList());
    }
    //@@author

    @Test
    public void execute_multipleFiltersAndKeywords_personFound() {
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                Arrays.asList("Kurz", "Elle", "Kunz"), Arrays.asList("street"), Collections.emptyList());
        FindCommand command = new FindCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL), model.getFilteredPersonList());
    }

    //@@author elijah-ng
    @Test
    public void execute_allFiltersAndMixedCaseKeywords_personFound() {
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                Arrays.asList("dAnieL"), Arrays.asList("strEEt"), Arrays.asList("hAlAL"));
        FindCommand command = new FindCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL), model.getFilteredPersonList());
    }
    //@@author

    @Test
    public void toStringMethod() {
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(Arrays.asList("alice"),
                Arrays.asList("Blk 30 Clementi"), Arrays.asList("West"));
        FindCommand findCommand = new FindCommand(predicate);

        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

}

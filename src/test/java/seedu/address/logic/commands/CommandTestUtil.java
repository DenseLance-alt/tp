package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.RescheduleDeliveryDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HALAL = "Halal";
    public static final String VALID_TAG_VEGETARIAN = "Vegetarian";

    public static final String VALID_START_DATE_AMY = "2019-11-12";
    public static final String VALID_START_DATE_BOB = "2025-10-29";
    public static final String VALID_END_DATE_AMY = "2026-01-11";
    public static final String VALID_END_DATE_BOB = "2025-11-11";
    public static final String VALID_DELIVERY_TIME_AMY = "20:20";
    public static final String VALID_DELIVERY_TIME_BOB = "12:13";
    public static final String VALID_DELIVERY_DAY = "Monday";
    public static final String VALID_DELIVERY_DAY_AMY = "Monday";
    public static final String VALID_DELIVERY_DAY_BOB = "Wednesday";

    public static final String UNSORTED_DAYS = "4231";
    public static final String[] UNSORTED_DAYS_WORDS = {"THURSDAY", "TUESDAY", "WEDNESDAY", "MONDAY"};

    public static final String VALID_DAYS_AMY = "1";
    public static final String VALID_DAYS_BOB = "3";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_VEGETARIAN = " " + PREFIX_TAG + VALID_TAG_VEGETARIAN;
    public static final String TAG_DESC_HALAL = " " + PREFIX_TAG + VALID_TAG_HALAL;

    public static final String START_DATE_DESC_AMY = " " + PREFIX_START_DATE + VALID_START_DATE_AMY;
    public static final String START_DATE_DESC_BOB = " " + PREFIX_START_DATE + VALID_START_DATE_BOB;
    public static final String END_DATE_DESC_AMY = " " + PREFIX_END_DATE + VALID_END_DATE_AMY;
    public static final String END_DATE_DESC_BOB = " " + PREFIX_END_DATE + VALID_END_DATE_BOB;
    public static final String TIME_DESC_AMY = " " + PREFIX_TIME + VALID_DELIVERY_TIME_AMY;
    public static final String TIME_DESC_BOB = " " + PREFIX_TIME + VALID_DELIVERY_TIME_BOB;
    public static final String DAYS_DESC_AMY = " " + PREFIX_DAYS + VALID_DAYS_AMY;
    public static final String DAYS_DESC_BOB = " " + PREFIX_DAYS + VALID_DAYS_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "halal*"; // '*' not allowed in tags

    public static final String INVALID_START_DATE_DESC =
            " " + PREFIX_START_DATE + "2020*20*20"; // '*' not allowed in start dates
    public static final String INVALID_END_DATE_DESC =
            " " + PREFIX_END_DATE + "2020*20*20"; // '*' not allowed in end dates
    public static final String INVALID_TIME_DESC = " " + PREFIX_TIME + "1112"; // missing ':' symbol
    public static final String INVALID_DAYS_DESC = " " + PREFIX_DAYS; // empty string not allowed for delivery days

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final RescheduleCommand.RescheduleDeliveryDescriptor DESC_AMY_RESCHEDULE;
    public static final RescheduleCommand.RescheduleDeliveryDescriptor DESC_BOB_RESCHEDULE;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_VEGETARIAN).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HALAL, VALID_TAG_VEGETARIAN).build();

        DESC_AMY_RESCHEDULE = new RescheduleDeliveryDescriptorBuilder().withStartDate(VALID_START_DATE_AMY)
                .withEndDate(VALID_END_DATE_AMY)
                .withDeliveryTime(VALID_DELIVERY_TIME_AMY)
                .withDeliveryDays(VALID_DELIVERY_DAY_AMY).build();
        DESC_BOB_RESCHEDULE = new RescheduleDeliveryDescriptorBuilder().withStartDate(VALID_START_DATE_BOB)
                .withEndDate(VALID_END_DATE_BOB)
                .withDeliveryTime(VALID_DELIVERY_TIME_BOB)
                .withDeliveryDays(VALID_DELIVERY_DAY_BOB).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}

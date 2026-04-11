package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    public static final String ALICE = "alice";
    public static final String BOB = "bob";
    public static final String CHARLIE = "charlie";
    public static final String TAN = "tan";
    public static final String LACK = "lack";
    public static final String ONG = "ong";
    public static final String LIN = "lin";
    public static final String ALICE_TAN = "Alice Tan";
    public static final String ALICE_BOB = "Alice Bob";
    public static final String ALICE_CAROL = "Alice Carol";
    public static final String PASCAL_CASE_ALICE = "Alice";
    public static final String PASCAL_CASE_BOB = "Bob";
    public static final String PASCAL_CASE_CAROL = "Carol";

    public static final String CLEMENTI = "clementi";
    public static final String JURONG = "jurong";
    public static final String TAMPINES = "tampines";
    public static final String ORCHARD = "orchard";
    public static final String AVE = "ave";
    public static final String STREET = "Street";
    public static final String PASCAL_CASE_YISHUN = "Yishun";
    public static final String PASCAL_CASE_ORCHARD = "Orchard";
    public static final String CLEMENTI_AVE = "311, Clementi Ave 2, #02-25";

    public static final String NORTH = "north";
    public static final String EAST = "east";
    public static final String WEST = "west";
    public static final String CENTRAL = "central";
    public static final String PASCAL_CASE_WEST = "West";
    public static final String PASCAL_CASE_NORTH = "North";
    public static final String PASCAL_CASE_CENTRAL = "Central";
    public static final String VEGETARIAN = "vegetarian";

    public static final String ALICE_EMAIL = "alice@email.com";
    public static final String DUMMY_PHONE = "12345";

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}

package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command.\n"
            + "Type 'help' to view the user guide for a list of available commands.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The customer index provided is invalid!";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d customers listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the given {@code person} for display to the user.
     * The person's delivery, if any, is not returned.
     * <p>Assumes that the person to format is not null.
     *
     * @param person The person to format. Must not be null.
     * @return String representation of the person's information.
     */
    public static String formatPerson(Person person) {
        requireNonNull(person);

        StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code person}'s delivery for display to the user.
     * If the person has no delivery, only the person's name is returned.
     * Otherwise, both the person's name and delivery are returned.
     * <p>Assumes that the person whose delivery is to be formatted
     * is not null.
     *
     * @param person The person whose delivery is to be formatted. Must not be null.
     * @return String representation of the person's name and, if
     *         present, information about their delivery.
     */
    public static String formatDeliveryFromPerson(Person person) {
        requireNonNull(person);

        StringBuilder builder = new StringBuilder();
        builder.append(person.getName());

        if (!person.hasDelivery()) {
            return builder.toString();
        }

        return builder.append("; Start Date: ")
                .append(person.getDeliveryStartDate())
                .append("; End Date: ")
                .append(person.getDeliveryEndDate())
                .append("; Delivery Days: ")
                .append(person.getDeliveryDays())
                .append("; Delivery Time: ")
                .append(person.getDeliveryTime())
                .toString();
    }

}

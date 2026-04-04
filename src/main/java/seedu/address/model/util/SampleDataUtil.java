package seedu.address.model.util;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryDay;
import seedu.address.model.delivery.DeliveryTime;
import seedu.address.model.delivery.EndDate;
import seedu.address.model.delivery.StartDate;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    /** Sample delivery objects */
    private static final Delivery SAMPLE_DELIVERY_ALEX = new Delivery(
            new StartDate("2026-09-20"), new EndDate("2026-12-20"),
            getDeliveryDaySet("Monday", "Tuesday", "Wednesday", "Friday", "Sunday"),
            new DeliveryTime("16:00")
    );
    private static final Delivery SAMPLE_DELIVERY_BERNICE = new Delivery(
            new StartDate("2026-08-09"), new EndDate("2027-02-09"),
            getDeliveryDaySet("Monday", "Thursday", "Friday", "Saturday"),
            new DeliveryTime("16:30")
    );
    private static final Delivery SAMPLE_DELIVERY_IRFAN = new Delivery(
            new StartDate("2025-01-01"), new EndDate("2100-01-01"),
            getDeliveryDaySet("Monday", "Tuesday", "Wednesday", "Thursday", "Friday"),
            new DeliveryTime("16:45")
    );
    private static final Delivery SAMPLE_DELIVERY_ROY = new Delivery(
            new StartDate("2025-12-25"), new EndDate("2100-12-25"),
            getDeliveryDaySet("Saturday", "Sunday"),
            new DeliveryTime("16:15")
    );

    /**
     * Returns an array of {@code Person} objects containing sample data.
     *
     * @return An array of {@code Person} objects containing sample data.
     */
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("Vegetarian"),
                SAMPLE_DELIVERY_ALEX),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("NoSeafood", "PeanutAllergy"),
                SAMPLE_DELIVERY_BERNICE),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet()),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("LactoseIntolerant")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("Halal"),
                SAMPLE_DELIVERY_IRFAN),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("Vegetarian"),
                SAMPLE_DELIVERY_ROY)
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a deliveryDay set containing the list of strings given.
     */
    public static Set<DeliveryDay> getDeliveryDaySet(String... strings) {
        return Arrays.stream(strings)
                .map(DeliveryDay::toDeliveryDay)
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}

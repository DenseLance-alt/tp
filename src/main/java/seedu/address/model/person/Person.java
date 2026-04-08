package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryDay;
import seedu.address.model.delivery.DeliveryTime;
import seedu.address.model.delivery.EndDate;
import seedu.address.model.delivery.StartDate;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Delivery delivery;

    /**
     * Constructs a {@code Person} with the given name, phone, email, address and tags.
     * The {@code delivery} field is initialized to {@code null}.
     *
     * @param name Name of the person. Must not be null.
     * @param phone Phone number of the person. Must not be null.
     * @param email Email of the person. Must not be null.
     * @param address Address of the person. Must not be null.
     * @param tags Set of tags associated with the person. Must not be null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        this(name, phone, email, address, tags, null);
    }

    //@@author BenedTj
    /**
     * Every field must be present and not null.
     * This constructor should be used when delivery is not null.
     *
     * @param name Name of the person. Must not be null.
     * @param phone Phone number of the person. Must not be null.
     * @param email Email of the person. Must not be null.
     * @param address Address of the person. Must not be null.
     * @param tags Set of tags associated with the person. Must not be null.
     * @param delivery Delivery of the person. Can be null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Delivery delivery) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.delivery = delivery;
    }
    //@@author

    /**
     * Returns a new {@code Person} without any delivery assigned.
     *
     * @return A copy of this person with their delivery removed.
     */
    public Person withoutDelivery() {
        return new Person(name, phone, email, address, new HashSet<>(tags));
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public StartDate getDeliveryStartDate() {
        return delivery.getStartDate();
    }

    public EndDate getDeliveryEndDate() {
        return delivery.getEndDate();
    }

    public Set<DeliveryDay> getDeliveryDays() {
        return delivery.getDeliveryDays();
    }

    public DeliveryTime getDeliveryTime() {
        return delivery.getDeliveryTime();
    }

    /**
     * Checks whether the person has a {@code delivery} assigned.
     *
     * @return {@code true} if a delivery is assigned to this person,
     *         {@code false} otherwise.
     */
    public boolean hasDelivery() {
        return delivery != null;
    }

    /**
     * Checks whether the person's delivery ends before the specified date.
     * <p>Assumes that the specified date is not null.
     *
     * @param beforeDate Date to compare against.
     * @return {@code true} if the person has a delivery assigned and the delivery's
     *                       end date occurs before the specified date,
     *         {@code false} otherwise.
     */
    public boolean hasExpiredDelivery(LocalDate beforeDate) {
        assert beforeDate != null;

        return hasDelivery() && delivery.hasExpired(beforeDate); // uses short circuit evaluation
    }

    //@@author BenedTj
    /**
     * Returns the Delivery object of the person, which could be null.
     */
    public Delivery getDelivery() {
        return delivery;
    }
    //@@author

    /**
     * Returns a formatted string of the person's delivery schedule for display,
     * or an empty string if person does not have a delivery assigned.
     *
     * @return Formatted string containing the start date, end date, and time of
     *         the person's delivery.
     */
    public String getFormattedDeliverySchedule() {
        if (!hasDelivery()) {
            return "";
        }
        return delivery.getFormattedDeliverySchedule();
    }

    /**
     * Returns an immutable set of delivery day names.
     * <p>If the person does not have a delivery assigned or has a delivery with
     * no delivery days, an empty set is returned.
     * <p>Example of delivery day names: Monday, Tuesday ...
     *
     * @return Set of delivery day names.
     */
    public Set<String> getDeliveryDayNames() {
        if (!hasDelivery()) {
            return Collections.emptySet();
        }
        return delivery.getDeliveryDayNames();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        //@@author BenedTj
        Person otherPerson = (Person) other;

        boolean isNonNullableFieldsEqual = name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);

        if (delivery == null) {
            return isNonNullableFieldsEqual && otherPerson.delivery == null;
        } else {
            return isNonNullableFieldsEqual && delivery.equals(otherPerson.delivery);
        }
        //@@author
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, delivery);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("delivery", delivery)
                .toString();
    }

}

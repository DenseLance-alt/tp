package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.delivery.Delivery;
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
     * Constructs a {@code Person} with the given name, phone, email, address and tag.
     * <p>
     * The {@code delivery} field is set to {@code null} by default.
     * @param name Name of the person, which must not be null.
     * @param phone Phone number of the person, which must not be null.
     * @param email Email of the person, which must not be null.
     * @param address Address of the person, which must not be null.
     * @param tags Set of tags associated with the person, which must not be null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        this(name, phone, email, address, tags, null);
    }

    //@author BenedTj
    /**
     * Every field must be present and not null.
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
     * @return A copy of this person with their delivery removed.
     */
    public Person withoutDelivery() {
        return new Person(name, phone, email, address, getTags());
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

    /**
     * Returns true if the person has a {@code delivery}.
     */
    public boolean hasDelivery() {
        return delivery != null;
    }

    /**
     * Returns the Delivery object of the person, which could be null.
     */
    public Delivery getDelivery() {
        return delivery;
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

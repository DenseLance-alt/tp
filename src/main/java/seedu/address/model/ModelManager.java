package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.delivery.DeliveryDatePredicate;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final LocalDate todayDate;

    private final FilteredList<Person> filteredPersons;
    private final SortedList<Person> sortedPersonWithTodayDeliveryList;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.todayDate = LocalDate.now();

        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());

        // Matches all persons with delivery scheduled today
        DeliveryDatePredicate todayDeliveryPredicate = new DeliveryDatePredicate(todayDate);

        // A new FilteredList is created,
        // so that personWithTodayDeliveryList is unaffected by filtering done on filteredPersons list.
        FilteredList<Person> personWithTodayDeliveryList = new FilteredList<>(
                this.addressBook.getPersonList(), todayDeliveryPredicate);

        // Defensive programming:
        // Assertion checks todayPredicate filters correctly, each person in the list must have a delivery.
        assert personWithTodayDeliveryList.stream().allMatch(person -> person.getDelivery() != null);

        // Solution below inspired by Claude AI
        // Sort based on ascending delivery time (from earliest to latest).
        this.sortedPersonWithTodayDeliveryList = new SortedList<>(personWithTodayDeliveryList,
                Comparator.comparing(person -> person.getDelivery().getDeliveryTime().time));
        // TODO: Resolve violation of LoD: getDeliverTime().time.
        // TODO: Refactor to use Sort class in future, if sorting feature implemented.
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Person with Delivery List Accessors =============================================================

    @Override
    public ObservableList<Person> getSortedPersonWithTodayDeliveryList() {
        return sortedPersonWithTodayDeliveryList;
    }

    //=========== Current Local Date Accessors =============================================================
    @Override
    public LocalDate getTodayDate() {
        return todayDate;
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && todayDate.equals(otherModelManager.todayDate)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && sortedPersonWithTodayDeliveryList.equals(otherModelManager.sortedPersonWithTodayDeliveryList);
    }

}

package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

/**
 * Tests that a {@code Person}'s attributes matches all filters specified (AND search).
 * Each attribute matches its corresponding filter, if the attribute matches at least 1 keyword (OR search).
 * Each filter is optional, the corresponding predicate is only tested if there are keywords.
 */
public class PersonMatchesFilterPredicate implements Predicate<Person> {
    private final List<String> keywordsForName;
    private final List<String> keywordsForAddress;
    private final List<String> keywordsForTag;

    /**
     * Constructs a {@code PersonMatchesFilterPredicate} where every list of keywords,
     * can be non-empty or empty, but not null.
     */
    public PersonMatchesFilterPredicate(List<String> keywordsForName,
                                        List<String> keywordsForAddress,
                                        List<String> keywordsForTag) {
        // Lists should never be null. If no keywords, list should be empty.
        requireNonNull(keywordsForName);
        requireNonNull(keywordsForAddress);
        requireNonNull(keywordsForTag);

        this.keywordsForName = keywordsForName;
        this.keywordsForAddress = keywordsForAddress;
        this.keywordsForTag = keywordsForTag;
    }

    @Override
    public boolean test(Person person) {
        // Short-circuit evaluations. If empty, match without new predicate.
        // Match if: Either no keywords for "name" field or has at least 1 keyword in name.
        boolean hasMatchedName = keywordsForName.isEmpty()
                || new NameContainsKeywordsPredicate(keywordsForName).test(person);

        // Match if: Either no keywords for "address" field or has at least 1 keyword in address.
        boolean hasMatchedAddress = keywordsForAddress.isEmpty()
                || new AddressContainsKeywordsPredicate(keywordsForAddress).test(person);

        // Match if: Either no keywords for "tag" or has at least 1 keyword matching at least 1 tag.
        boolean hasMatchedTag = keywordsForTag.isEmpty()
                || new TagContainsKeywordsPredicate(keywordsForTag).test(person);

        return hasMatchedName && hasMatchedAddress && hasMatchedTag;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonMatchesFilterPredicate)) {
            return false;
        }

        PersonMatchesFilterPredicate otherPersonMatchesFilterPredicate = (PersonMatchesFilterPredicate) other;
        boolean isSameNameFilter = keywordsForName.equals(otherPersonMatchesFilterPredicate.keywordsForName);
        boolean isSameAddressFilter = keywordsForAddress.equals(otherPersonMatchesFilterPredicate.keywordsForAddress);
        boolean isSameTagFilter = keywordsForTag.equals(otherPersonMatchesFilterPredicate.keywordsForTag);

        return isSameNameFilter && isSameAddressFilter && isSameTagFilter;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywordsForName", keywordsForName)
                .add("keywordsForAddress", keywordsForAddress)
                .add("keywordsForTag", keywordsForTag)
                .toString();
    }
}

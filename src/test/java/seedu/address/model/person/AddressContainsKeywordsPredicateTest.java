package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class AddressContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("clementi");
        List<String> secondPredicateKeywordList = Arrays.asList("clementi", "jurong");

        AddressContainsKeywordsPredicate firstPredicate = new AddressContainsKeywordsPredicate(
                firstPredicateKeywordList);
        AddressContainsKeywordsPredicate secondPredicate = new AddressContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsKeywordsPredicate firstPredicateCopy = new AddressContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    // EP: At least one matching keyword
    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        // Boundary case: One keyword
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(
                Collections.singletonList("clementi"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("311, Clementi Ave 2, #02-25").build()));

        // Boundary case: Multiple keywords, all match.
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("clementi", "ave"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("311, Clementi Ave 2, #02-25").build()));

        // Duplicate keywords, all match.
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("clementi", "clementi"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("311, Clementi Ave 2, #02-25").build()));

        // Multiple keywords, only one matching keyword.
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("clementi", "jurong"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("311, Clementi Ave 2, #02-25").build()));

        // Mixed-case keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("ClEmenTI", "jUroNG"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("311, Clementi Ave 2, #02-25").build()));
    }

    // EP: No matching keyword
    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Boundary value: Zero keywords
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("311, Clementi Ave 2, #02-25").build()));

        // Non-matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("jurong"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("311, Clementi Ave 2, #02-25").build()));

        // Keywords match phone, email and name, but does not match address.
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Alice", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("311, Clementi Ave 2, #02-25").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(keywords);

        String expected = AddressContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}

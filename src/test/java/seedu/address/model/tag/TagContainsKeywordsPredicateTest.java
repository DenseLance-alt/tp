package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("central");
        List<String> secondPredicateKeywordList = Arrays.asList("central", "north");

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.singletonList("central"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Central").build()));

        // Multiple keywords match
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("central", "central"));
        assertTrue(predicate.test(new PersonBuilder().withTags("central").build()));

        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("central", "north"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Central").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("CenTraL", "NOrtH"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Central").build()));

        // Test Multiple Tags
        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Central", "east"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Central", "North").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("CenTraL", "NOrtH"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Central", "North").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("Central").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("north"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Central").build()));

        // No keywords match entire tag
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("central", "north"));
        assertFalse(predicate.test(new PersonBuilder().withTags("centralnorth").build()));

        // Keywords match phone, email, name and address, but does not match tag
        predicate = new TagContainsKeywordsPredicate(
                Arrays.asList("12345", "alice@email.com", "Alice", "Orchard", "North"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("238 Orchard Boulevard, Singapore 237973")
                .withTags("Central").build()));

        // Test Multiple Tags
        // No matching keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("west", "east"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Central", "North").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(keywords);

        String expected = TagContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}

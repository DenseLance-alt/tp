package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.PersonUtil.ALICE_EMAIL;
import static seedu.address.testutil.PersonUtil.CENTRAL;
import static seedu.address.testutil.PersonUtil.DUMMY_PHONE;
import static seedu.address.testutil.PersonUtil.EAST;
import static seedu.address.testutil.PersonUtil.NORTH;
import static seedu.address.testutil.PersonUtil.PASCAL_CASE_ALICE;
import static seedu.address.testutil.PersonUtil.PASCAL_CASE_CENTRAL;
import static seedu.address.testutil.PersonUtil.PASCAL_CASE_NORTH;
import static seedu.address.testutil.PersonUtil.PASCAL_CASE_ORCHARD;
import static seedu.address.testutil.PersonUtil.VEGETARIAN;
import static seedu.address.testutil.PersonUtil.WEST;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList(CENTRAL);
        List<String> secondPredicateKeywordList = Arrays.asList(CENTRAL, NORTH);

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

    // EP: At least one matching keyword
    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // Boundary case: One keyword
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.singletonList(CENTRAL));
        assertTrue(predicate.test(new PersonBuilder().withTags(PASCAL_CASE_CENTRAL).build()));

        // Duplicate keywords, all match.
        predicate = new TagContainsKeywordsPredicate(Arrays.asList(CENTRAL, CENTRAL));
        assertTrue(predicate.test(new PersonBuilder().withTags(CENTRAL).build()));

        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList(CENTRAL, NORTH));
        assertTrue(predicate.test(new PersonBuilder().withTags(PASCAL_CASE_CENTRAL).build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("CenTraL", "NOrtH"));
        assertTrue(predicate.test(new PersonBuilder().withTags(PASCAL_CASE_CENTRAL).build()));

        // Person with multiple tags
        // Boundary case: Multiple keywords, all match.
        predicate = new TagContainsKeywordsPredicate(Arrays.asList(CENTRAL, VEGETARIAN));
        assertTrue(predicate.test(new PersonBuilder().withTags(CENTRAL, VEGETARIAN).build()));

        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList(PASCAL_CASE_CENTRAL, EAST));
        assertTrue(predicate.test(new PersonBuilder().withTags(PASCAL_CASE_CENTRAL, PASCAL_CASE_NORTH).build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("CenTraL", "NOrtH"));
        assertTrue(predicate.test(new PersonBuilder().withTags(PASCAL_CASE_CENTRAL, PASCAL_CASE_NORTH).build()));
    }

    // EP: No matching keyword
    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Boundary value: Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags(PASCAL_CASE_CENTRAL).build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList(NORTH));
        assertFalse(predicate.test(new PersonBuilder().withTags(PASCAL_CASE_CENTRAL).build()));

        // No keywords match entire tag
        predicate = new TagContainsKeywordsPredicate(Arrays.asList(CENTRAL, NORTH));
        assertFalse(predicate.test(new PersonBuilder().withTags("centralnorth").build()));

        // Keywords match phone, email, name and address, but does not match tag.
        predicate = new TagContainsKeywordsPredicate(
                Arrays.asList(DUMMY_PHONE, ALICE_EMAIL, PASCAL_CASE_ALICE, PASCAL_CASE_ORCHARD, PASCAL_CASE_NORTH));
        assertFalse(predicate.test(new PersonBuilder().withName(PASCAL_CASE_ALICE).withPhone(DUMMY_PHONE)
                .withEmail(ALICE_EMAIL).withAddress("238 Orchard Boulevard, Singapore 237973")
                .withTags(PASCAL_CASE_CENTRAL).build()));

        // Person with multiple tags, no matching keyword.
        // Test inputs causing predicate.test() to return false individually, before combining them.
        predicate = new TagContainsKeywordsPredicate(Arrays.asList(WEST));
        assertFalse(predicate.test(new PersonBuilder().withTags(PASCAL_CASE_CENTRAL, PASCAL_CASE_NORTH).build()));

        predicate = new TagContainsKeywordsPredicate(Arrays.asList(EAST));
        assertFalse(predicate.test(new PersonBuilder().withTags(PASCAL_CASE_CENTRAL, PASCAL_CASE_NORTH).build()));

        predicate = new TagContainsKeywordsPredicate(Arrays.asList(WEST, EAST));
        assertFalse(predicate.test(new PersonBuilder().withTags(PASCAL_CASE_CENTRAL, PASCAL_CASE_NORTH).build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(keywords);

        String expected = TagContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}

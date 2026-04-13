package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.PersonUtil.ALICE_BOB;
import static seedu.address.testutil.PersonUtil.ALICE_CAROL;
import static seedu.address.testutil.PersonUtil.ALICE_EMAIL;
import static seedu.address.testutil.PersonUtil.DUMMY_PHONE;
import static seedu.address.testutil.PersonUtil.PASCAL_CASE_ALICE;
import static seedu.address.testutil.PersonUtil.PASCAL_CASE_BOB;
import static seedu.address.testutil.PersonUtil.PASCAL_CASE_CAROL;
import static seedu.address.testutil.PersonUtil.STREET;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    // EP: At least one matching keyword
    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // Boundary case: One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(
                Collections.singletonList(PASCAL_CASE_ALICE));
        assertTrue(predicate.test(new PersonBuilder().withName(ALICE_BOB).build()));

        // Boundary case: Multiple keywords, all match.
        predicate = new NameContainsKeywordsPredicate(Arrays.asList(PASCAL_CASE_ALICE, PASCAL_CASE_BOB));
        assertTrue(predicate.test(new PersonBuilder().withName(ALICE_BOB).build()));

        // Duplicate keywords, all match.
        predicate = new NameContainsKeywordsPredicate(Arrays.asList(PASCAL_CASE_ALICE, PASCAL_CASE_ALICE));
        assertTrue(predicate.test(new PersonBuilder().withName(ALICE_BOB).build()));

        // Multiple keywords, only one matching keyword.
        predicate = new NameContainsKeywordsPredicate(Arrays.asList(PASCAL_CASE_BOB, PASCAL_CASE_CAROL));
        assertTrue(predicate.test(new PersonBuilder().withName(ALICE_CAROL).build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName(ALICE_BOB).build()));
    }

    // EP: No matching keyword
    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Boundary value: Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName(PASCAL_CASE_ALICE).build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList(PASCAL_CASE_CAROL));
        assertFalse(predicate.test(new PersonBuilder().withName(ALICE_BOB).build()));

        // Keywords match phone, email and address, but does not match name.
        predicate = new NameContainsKeywordsPredicate(Arrays.asList(DUMMY_PHONE, ALICE_EMAIL, "Main", STREET));
        assertFalse(predicate.test(new PersonBuilder().withName(PASCAL_CASE_ALICE).withPhone(DUMMY_PHONE)
                .withEmail(ALICE_EMAIL).withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(keywords);

        String expected = NameContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}

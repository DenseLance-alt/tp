package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.PersonUtil.ALICE_EMAIL;
import static seedu.address.testutil.PersonUtil.CLEMENTI;
import static seedu.address.testutil.PersonUtil.CLEMENTI_AVE;
import static seedu.address.testutil.PersonUtil.DUMMY_PHONE;
import static seedu.address.testutil.PersonUtil.JURONG;
import static seedu.address.testutil.PersonUtil.PASCAL_CASE_ALICE;
import static seedu.address.testutil.PersonUtil.STREET;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class AddressContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList(CLEMENTI);
        List<String> secondPredicateKeywordList = Arrays.asList(CLEMENTI, JURONG);

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
                Collections.singletonList(CLEMENTI));
        assertTrue(predicate.test(new PersonBuilder().withAddress(CLEMENTI_AVE).build()));

        // Boundary case: Multiple keywords, all match.
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList(CLEMENTI, "ave"));
        assertTrue(predicate.test(new PersonBuilder().withAddress(CLEMENTI_AVE).build()));

        // Duplicate keywords, all match.
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList(CLEMENTI, CLEMENTI));
        assertTrue(predicate.test(new PersonBuilder().withAddress(CLEMENTI_AVE).build()));

        // Multiple keywords, only one matching keyword.
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList(CLEMENTI, JURONG));
        assertTrue(predicate.test(new PersonBuilder().withAddress(CLEMENTI_AVE).build()));

        // Mixed-case keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("ClEmenTI", "jUroNG"));
        assertTrue(predicate.test(new PersonBuilder().withAddress(CLEMENTI_AVE).build()));
    }

    // EP: No matching keyword
    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Boundary value: Zero keywords
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress(CLEMENTI_AVE).build()));

        // Non-matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList(JURONG));
        assertFalse(predicate.test(new PersonBuilder().withAddress(CLEMENTI_AVE).build()));

        // Keywords match phone, email and name, but does not match address.
        predicate = new AddressContainsKeywordsPredicate(
                Arrays.asList(DUMMY_PHONE, ALICE_EMAIL, PASCAL_CASE_ALICE, STREET));
        assertFalse(predicate.test(new PersonBuilder().withName(PASCAL_CASE_ALICE).withPhone(DUMMY_PHONE)
                .withEmail(ALICE_EMAIL).withAddress(CLEMENTI_AVE).build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(keywords);

        String expected = AddressContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}

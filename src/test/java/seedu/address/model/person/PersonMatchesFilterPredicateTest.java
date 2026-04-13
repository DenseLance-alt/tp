package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.PersonUtil.ALICE;
import static seedu.address.testutil.PersonUtil.ALICE_TAN;
import static seedu.address.testutil.PersonUtil.AVE;
import static seedu.address.testutil.PersonUtil.BOB;
import static seedu.address.testutil.PersonUtil.CENTRAL;
import static seedu.address.testutil.PersonUtil.CHARLIE;
import static seedu.address.testutil.PersonUtil.CLEMENTI;
import static seedu.address.testutil.PersonUtil.CLEMENTI_AVE;
import static seedu.address.testutil.PersonUtil.EAST;
import static seedu.address.testutil.PersonUtil.JURONG;
import static seedu.address.testutil.PersonUtil.LACK;
import static seedu.address.testutil.PersonUtil.LIN;
import static seedu.address.testutil.PersonUtil.ONG;
import static seedu.address.testutil.PersonUtil.ORCHARD;
import static seedu.address.testutil.PersonUtil.PASCAL_CASE_NORTH;
import static seedu.address.testutil.PersonUtil.PASCAL_CASE_WEST;
import static seedu.address.testutil.PersonUtil.PASCAL_CASE_YISHUN;
import static seedu.address.testutil.PersonUtil.TAMPINES;
import static seedu.address.testutil.PersonUtil.TAN;
import static seedu.address.testutil.PersonUtil.WEST;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonMatchesFilterPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateNameKeywordList = Collections.singletonList(ALICE);
        List<String> firstPredicateAddressKeywordList = Collections.singletonList(CLEMENTI);
        List<String> firstPredicateTagKeywordList = Collections.singletonList(WEST);

        List<String> secondPredicateNameKeywordList = Arrays.asList(BOB, CHARLIE);
        List<String> secondPredicateAddressKeywordList = Arrays.asList(CLEMENTI, JURONG);
        List<String> secondPredicateTagKeywordList = Collections.singletonList(WEST);

        List<String> thirdPredicateNameKeywordList = Arrays.asList(ALICE);
        List<String> thirdPredicateAddressKeywordList = Arrays.asList(CLEMENTI, JURONG);
        List<String> thirdPredicateTagKeywordList = Collections.singletonList(WEST);

        List<String> fourthPredicateNameKeywordList = Arrays.asList(ALICE);
        List<String> fourthPredicateAddressKeywordList = Arrays.asList(CLEMENTI);
        List<String> fourthPredicateTagKeywordList = Collections.singletonList(CENTRAL);

        PersonMatchesFilterPredicate firstPredicate = new PersonMatchesFilterPredicate(
                firstPredicateNameKeywordList, firstPredicateAddressKeywordList, firstPredicateTagKeywordList);

        PersonMatchesFilterPredicate secondPredicate = new PersonMatchesFilterPredicate(
                secondPredicateNameKeywordList, secondPredicateAddressKeywordList, secondPredicateTagKeywordList);

        PersonMatchesFilterPredicate thirdPredicate = new PersonMatchesFilterPredicate(
                thirdPredicateNameKeywordList, thirdPredicateAddressKeywordList, thirdPredicateTagKeywordList);

        PersonMatchesFilterPredicate fourthPredicate = new PersonMatchesFilterPredicate(
                fourthPredicateNameKeywordList, fourthPredicateAddressKeywordList, fourthPredicateTagKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonMatchesFilterPredicate firstPredicateCopy = new PersonMatchesFilterPredicate(
                firstPredicateNameKeywordList, firstPredicateAddressKeywordList, firstPredicateTagKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
        assertFalse(firstPredicate.equals(thirdPredicate));
        assertFalse(firstPredicate.equals(fourthPredicate));
    }

    // EP: No attribute filters
    @Test
    public void test_personMatchesWithNoFilter_returnsTrue() {
        // No keyword, any person will match
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withAddress(CLEMENTI_AVE).build()));
    }

    // Each valid input (name, address and tag filter) at least once in a positive test case

    // EP: One attribute filter, with a matching keyword.
    @Test
    public void test_personMatchesWithOneFilter_returnsTrue() {
        // One name filter. For each filter, keyword match.
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(Arrays.asList(TAN),
                Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        // One address filter. For each filter, keyword match.
        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Arrays.asList(CLEMENTI),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        // One tag filter. For each filter, keyword match.
        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Collections.emptyList(),
                Arrays.asList(WEST));
        assertTrue(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        // Boundary cases: If tag filter not specified, person with no tags will still match.
        predicate = new PersonMatchesFilterPredicate(Arrays.asList(ALICE), Collections.emptyList(),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE).build()));

        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Arrays.asList(CLEMENTI),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE).build()));

        // One filter. For each filter, at least 1 keyword match.
        predicate = new PersonMatchesFilterPredicate(Arrays.asList(TAN, LIN), Collections.emptyList(),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));
    }

    // EP: Two attribute filters, each filter has a matching keyword.
    @Test
    public void test_personMatchesWithTwoFilters_returnsTrue() {
        // Two filters. For each filter, keyword match.
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(Arrays.asList(TAN),
                Arrays.asList(CLEMENTI), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList(TAN), Collections.emptyList(),
                Arrays.asList(WEST));
        assertTrue(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Arrays.asList(CLEMENTI),
                Arrays.asList(WEST));
        assertTrue(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        // Boundary case: If tag filter not specified, person with no tags will still match.
        predicate = new PersonMatchesFilterPredicate(Arrays.asList(ALICE), Arrays.asList(CLEMENTI),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE).build()));

        // Two filters. For each filter, at least 1 keyword match.
        predicate = new PersonMatchesFilterPredicate(Arrays.asList(TAN, LIN), Arrays.asList(CLEMENTI, ORCHARD),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));
    }

    // EP: Three attribute filters, each filter has a matching keyword.
    @Test
    public void test_personMatchesWithThreeFilters_returnsTrue() {
        // Three filters. For each filter, keyword match.
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(Arrays.asList(TAN),
                Arrays.asList(CLEMENTI), Arrays.asList(WEST));
        assertTrue(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        // Three filters. For each filter, at least 1 keyword match.
        predicate = new PersonMatchesFilterPredicate(Arrays.asList(TAN, LIN), Arrays.asList(CLEMENTI, ORCHARD),
                Arrays.asList(WEST, CENTRAL));
        assertTrue(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));
    }

    // EP: One attribute filter, with no matching keyword.
    @Test
    public void test_personDoesNotMatchWithOneFilter_returnsFalse() {
        // One name filter. For each filter, no keyword match.
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(Arrays.asList(LACK),
                Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList(LACK, ONG), Collections.emptyList(),
                Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        // One address filter. For each filter, no keyword match.
        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Arrays.asList(JURONG),
                Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Arrays.asList(JURONG, TAMPINES),
                Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        // One tag filter. For each filter, no keyword match.
        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Collections.emptyList(),
                Arrays.asList(CENTRAL));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Collections.emptyList(),
                Arrays.asList(CENTRAL, EAST));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));
    }

    // EP: Two attribute filters, at least one filter has no matching keyword.
    // Test inputs causing predicate.test() to return false individually, before combining them.
    @Test
    public void test_personDoesNotMatchOneOfTwoFilters_returnsFalse() {
        // Two filters. Does not match one filter (no keyword in that filter match)
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(Arrays.asList(LACK),
                Arrays.asList(CLEMENTI), Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList(LACK, ONG), Arrays.asList(CLEMENTI),
                Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList(TAN), Collections.emptyList(),
                Arrays.asList(CENTRAL));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList(TAN), Collections.emptyList(),
                Arrays.asList(CENTRAL, EAST));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Arrays.asList(JURONG),
                Arrays.asList(WEST));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Arrays.asList(JURONG, ORCHARD),
                Arrays.asList(WEST));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        // Boundary cases: If tag filter specified, person with no tags does not match.
        predicate = new PersonMatchesFilterPredicate(Arrays.asList(ALICE),
                Collections.emptyList(), Arrays.asList(WEST));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE).build()));

        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Arrays.asList(CLEMENTI),
                Arrays.asList(WEST));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE).build()));
    }

    @Test
    public void test_personDoesNotMatchAllTwoFilters_returnsFalse() {
        // Two filters. Does not match both filters
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(Arrays.asList(LACK),
                Arrays.asList(JURONG), Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList(LACK, ONG), Arrays.asList(JURONG, TAMPINES),
                Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList(LACK), Collections.emptyList(),
                Arrays.asList(CENTRAL));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList(LACK, ONG), Collections.emptyList(),
                Arrays.asList(CENTRAL, EAST));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Arrays.asList(JURONG),
                Arrays.asList(CENTRAL));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Arrays.asList(JURONG, TAMPINES),
                Arrays.asList(CENTRAL, EAST));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));
    }

    // EP: Three attribute filters, at least one filter has no matching keyword.
    // Test inputs causing predicate.test() to return false individually, before combining them.
    @Test
    public void test_personDoesNotMatchOneOfThreeFilters_returnsFalse() {
        // Three filters. Does not match 1 filter
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(Arrays.asList(LACK),
                Arrays.asList(CLEMENTI), Arrays.asList(WEST));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList(LACK, ONG), Arrays.asList(CLEMENTI, AVE),
                Arrays.asList(WEST));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList(TAN), Arrays.asList(JURONG),
                Arrays.asList(WEST));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList(TAN, ALICE), Arrays.asList(JURONG, ORCHARD),
                Arrays.asList(WEST));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList(TAN), Arrays.asList(CLEMENTI),
                Arrays.asList(CENTRAL));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList(TAN), Arrays.asList(CLEMENTI),
                Arrays.asList(CENTRAL, EAST));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        // Boundary case: If tag filter specified, person with no tags does not match.
        predicate = new PersonMatchesFilterPredicate(Arrays.asList(ALICE), Arrays.asList(CLEMENTI),
                Arrays.asList(WEST));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE).build()));
    }

    @Test
    public void test_personDoesNotMatchTwoOfThreeFilters_returnsFalse() {
        // Three filters. Does not match 2 filters
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(Arrays.asList(LACK),
                Arrays.asList(JURONG), Arrays.asList(WEST));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList(LACK, ONG), Arrays.asList(JURONG, TAMPINES),
                Arrays.asList(WEST));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList(LACK), Arrays.asList(CLEMENTI),
                Arrays.asList(CENTRAL));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList(LACK, ONG), Arrays.asList(CLEMENTI),
                Arrays.asList(CENTRAL, EAST));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList(TAN), Arrays.asList(JURONG),
                Arrays.asList(CENTRAL));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList(TAN), Arrays.asList(JURONG, TAMPINES),
                Arrays.asList(CENTRAL, EAST));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));
    }

    @Test
    public void test_personDoesNotMatchAllThreeFilters_returnsFalse() {
        // Three filters. Does not match all filters.
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(Arrays.asList(LACK),
                Arrays.asList(JURONG), Arrays.asList(CENTRAL));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList(LACK, ONG), Arrays.asList(JURONG, ORCHARD),
                Arrays.asList(CENTRAL, EAST));
        assertFalse(predicate.test(new PersonBuilder()
                .withName(ALICE_TAN)
                .withAddress(CLEMENTI_AVE)
                .withTags(PASCAL_CASE_WEST).build()));
    }

    @Test
    public void toStringMethod() {
        List<String> nameKeywords = List.of(ALICE, BOB);
        List<String> addressKeywords = List.of(PASCAL_CASE_YISHUN);
        List<String> tagKeywords = List.of(PASCAL_CASE_NORTH);

        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                nameKeywords, addressKeywords, tagKeywords);

        String expected = PersonMatchesFilterPredicate.class.getCanonicalName()
                + "{keywordsForName=" + nameKeywords
                + ", keywordsForAddress=" + addressKeywords
                + ", keywordsForTag=" + tagKeywords + '}';
        assertEquals(expected, predicate.toString());
    }
}

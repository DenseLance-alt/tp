package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonMatchesFilterPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateNameKeywordList = Collections.singletonList("alice");
        List<String> firstPredicateAddressKeywordList = Collections.singletonList("clementi");
        List<String> firstPredicateTagKeywordList = Collections.singletonList("west");

        List<String> secondPredicateNameKeywordList = Arrays.asList("bob", "charlie");
        List<String> secondPredicateAddressKeywordList = Arrays.asList("clementi", "jurong");
        List<String> secondPredicateTagKeywordList = Collections.singletonList("west");

        List<String> thirdPredicateNameKeywordList = Arrays.asList("alice");
        List<String> thirdPredicateAddressKeywordList = Arrays.asList("clementi", "jurong");
        List<String> thirdPredicateTagKeywordList = Collections.singletonList("west");

        List<String> fourthPredicateNameKeywordList = Arrays.asList("alice");
        List<String> fourthPredicateAddressKeywordList = Arrays.asList("clementi");
        List<String> fourthPredicateTagKeywordList = Collections.singletonList("central");

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

    @Test
    public void test_personMatchesFilters_returnsTrue() {
        // No keyword, any person will match
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withAddress("311, Clementi Ave 2, #02-25").build()));

        // If tag filter not specified, person with no tags will still match.
        predicate = new PersonMatchesFilterPredicate(Arrays.asList("alice"), Collections.emptyList(),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25").build()));

        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Arrays.asList("clementi"),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25").build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList("alice"), Arrays.asList("clementi"),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25").build()));

        // One name filter. For each filter, keyword match.
        predicate = new PersonMatchesFilterPredicate(Arrays.asList("tan"), Collections.emptyList(),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        // One address filter. For each filter, keyword match.
        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Arrays.asList("clementi"),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        // One tag filter. For each filter, keyword match.
        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Collections.emptyList(),
                Arrays.asList("west"));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        // Two filters. For each filter, keyword match.
        predicate = new PersonMatchesFilterPredicate(Arrays.asList("tan"), Arrays.asList("clementi"),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList("tan"), Collections.emptyList(),
                Arrays.asList("west"));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Arrays.asList("clementi"),
                Arrays.asList("west"));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        // Three filters. For each filter, keyword match.
        predicate = new PersonMatchesFilterPredicate(Arrays.asList("tan"), Arrays.asList("clementi"),
                Arrays.asList("west"));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        // One filter. For each filter, at least 1 keyword match.
        predicate = new PersonMatchesFilterPredicate(Arrays.asList("tan", "lin"), Collections.emptyList(),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        // Two filters. For each filter, at least 1 keyword match.
        predicate = new PersonMatchesFilterPredicate(Arrays.asList("tan", "lin"), Arrays.asList("clementi", "orchard"),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        // Three filters. For each filter, at least 1 keyword match.
        predicate = new PersonMatchesFilterPredicate(Arrays.asList("tan", "lin"), Arrays.asList("clementi", "orchard"),
                Arrays.asList("west", "central"));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));
    }

    @Test
    public void test_personDoesNotMatchFilters_returnsFalse() {
        // If tag filter specified, person with no tags does not match.
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(Arrays.asList("alice"),
                Collections.emptyList(), Arrays.asList("west"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25").build()));

        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Arrays.asList("clementi"),
                Arrays.asList("west"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25").build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList("alice"), Arrays.asList("clementi"),
                Arrays.asList("west"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25").build()));

        // One name filter. For each filter, no keyword match.
        predicate = new PersonMatchesFilterPredicate(Arrays.asList("lack"), Collections.emptyList(),
                Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList("lack", "ong"), Collections.emptyList(),
                Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        // One address filter. For each filter, no keyword match.
        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Arrays.asList("jurong"),
                Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Arrays.asList("jurong", "tampines"),
                Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        // One tag filter. For each filter, no keyword match.
        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Collections.emptyList(),
                Arrays.asList("central"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Collections.emptyList(),
                Arrays.asList("central", "east"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        // Two filters. Does not match one filter (no keyword in that filter match)
        predicate = new PersonMatchesFilterPredicate(Arrays.asList("lack"), Arrays.asList("clementi"),
                Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList("lack", "ong"), Arrays.asList("clementi"),
                Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList("tan"), Collections.emptyList(),
                Arrays.asList("central"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList("tan"), Collections.emptyList(),
                Arrays.asList("central", "east"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Arrays.asList("jurong"),
                Arrays.asList("west"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Arrays.asList("jurong", "orchard"),
                Arrays.asList("west"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        // Two filters. Does not match both filters
        predicate = new PersonMatchesFilterPredicate(Arrays.asList("lack"), Arrays.asList("jurong"),
                Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList("lack", "ong"), Arrays.asList("jurong", "tampines"),
                Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList("lack"), Collections.emptyList(),
                Arrays.asList("central"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList("lack", "ong"), Collections.emptyList(),
                Arrays.asList("central", "east"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Arrays.asList("jurong"),
                Arrays.asList("central"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Collections.emptyList(), Arrays.asList("jurong", "tampines"),
                Arrays.asList("central", "east"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        // Three filters. Does not match 1 filter
        predicate = new PersonMatchesFilterPredicate(Arrays.asList("lack"), Arrays.asList("clementi"),
                Arrays.asList("west"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList("lack", "ong"), Arrays.asList("clementi", "ave"),
                Arrays.asList("west"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList("tan"), Arrays.asList("jurong"),
                Arrays.asList("west"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList("tan", "alice"), Arrays.asList("jurong", "orchard"),
                Arrays.asList("west"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList("tan"), Arrays.asList("clementi"),
                Arrays.asList("central"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList("tan"), Arrays.asList("clementi"),
                Arrays.asList("central", "east"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        // Three filters. Does not match 2 filters
        predicate = new PersonMatchesFilterPredicate(Arrays.asList("lack"), Arrays.asList("jurong"),
                Arrays.asList("west"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList("lack", "ong"), Arrays.asList("jurong", "tampines"),
                Arrays.asList("west"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList("lack"), Arrays.asList("clementi"),
                Arrays.asList("central"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList("lack", "ong"), Arrays.asList("clementi"),
                Arrays.asList("central", "east"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList("tan"), Arrays.asList("jurong"),
                Arrays.asList("central"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList("tan"), Arrays.asList("jurong", "tampines"),
                Arrays.asList("central", "east"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        // Three filters. Does not match all filters.
        predicate = new PersonMatchesFilterPredicate(Arrays.asList("lack"), Arrays.asList("jurong"),
                Arrays.asList("central"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));

        predicate = new PersonMatchesFilterPredicate(Arrays.asList("lack", "ong"), Arrays.asList("jurong", "orchard"),
                Arrays.asList("central", "east"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Tan")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withTags("West").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> nameKeywords = List.of("alice", "bob");
        List<String> addressKeywords = List.of("Yishun");
        List<String> tagKeywords = List.of("North");

        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                nameKeywords, addressKeywords, tagKeywords);

        String expected = PersonMatchesFilterPredicate.class.getCanonicalName()
                + "{keywordsForName=" + nameKeywords
                + ", keywordsForAddress=" + addressKeywords
                + ", keywordsForTag=" + tagKeywords + '}';
        assertEquals(expected, predicate.toString());
    }
}

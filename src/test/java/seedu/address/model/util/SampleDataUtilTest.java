package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.model.util.SampleDataUtil.getTagSet;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

/**
 * Contains tests for {@code SampleDataUtilTest}.
 */
public class SampleDataUtilTest {

    @Test
    void getTagSet_validTags_hasSameTags() {
        // non-empty set
        assertEquals(getTagSet(TAG_DESC_FRIEND, TAG_DESC_HUSBAND),
                Set.of(TAG_DESC_FRIEND, TAG_DESC_HUSBAND));

        // empty set
        assertEquals(getTagSet(), Set.<Tag>of());
    }

    @Test
    public void getTagSet_invalidTags_throwsIllegalArgumentException() {
        // empty tag
        String emptyTagName = "";
        assertThrows(IllegalArgumentException.class, () -> getTagSet(emptyTagName));

        // non alphanumeric tag
        assertThrows(IllegalArgumentException.class, () -> getTagSet(INVALID_TAG_DESC));

        // one invalid tag with many valid tags
        assertThrows(IllegalArgumentException.class, () -> getTagSet(
                TAG_DESC_FRIEND, TAG_DESC_HUSBAND, INVALID_TAG_DESC));
    }

}

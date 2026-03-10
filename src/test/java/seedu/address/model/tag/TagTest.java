package seedu.address.model.tag;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        // empty tag
        String emptyTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(emptyTagName));

        // non alphanumeric tag
        assertThrows(IllegalArgumentException.class, () -> new Tag(INVALID_TAG_DESC));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

}

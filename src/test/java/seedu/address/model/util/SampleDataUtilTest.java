package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.UNSORTED_DAYS_WORDS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HALAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_VEGETARIAN;
import static seedu.address.model.util.SampleDataUtil.getDeliveryDaySet;
import static seedu.address.model.util.SampleDataUtil.getTagSet;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.delivery.DeliveryDay;
import seedu.address.model.tag.Tag;

/**
 * Contains tests for {@code SampleDataUtilTest}.
 */
public class SampleDataUtilTest {

    @Test
    void getTagSet_validTags_hasSameTags() {
        // EP: non-empty set
        Tag vegetarianTag = new Tag(VALID_TAG_VEGETARIAN);
        Tag halalTag = new Tag(VALID_TAG_HALAL);
        assertEquals(Set.of(vegetarianTag, halalTag),
                getTagSet(VALID_TAG_VEGETARIAN, VALID_TAG_HALAL));

        // EP: duplicate items are not included in set
        assertEquals(Set.of(vegetarianTag),
                getTagSet(VALID_TAG_VEGETARIAN, VALID_TAG_VEGETARIAN));

        // EP: empty set
        assertEquals(Set.<Tag>of(), getTagSet());
    }

    @Test
    public void getTagSet_invalidTags_throwsIllegalArgumentException() {
        // EP: empty tag
        String emptyTagName = "";
        assertThrows(IllegalArgumentException.class, () -> getTagSet(emptyTagName));

        // EP: non-alphanumeric tag
        String nonAlphanumericTagName = "(Halal)";
        assertThrows(IllegalArgumentException.class, () -> getTagSet(nonAlphanumericTagName));

        // EP: mix of valid and invalid tags
        assertThrows(IllegalArgumentException.class, () -> getTagSet(
                VALID_TAG_VEGETARIAN, VALID_TAG_HALAL, nonAlphanumericTagName));
    }

    @Test
    void getDeliveryDaySet_validDeliveryDay_hasSameDeliveryDays() {
        // EP: non-empty set
        String mondayString = "Monday";
        String tuesdayString = "TueSDAy";
        DeliveryDay monday = DeliveryDay.MONDAY;
        DeliveryDay tuesday = DeliveryDay.TUESDAY;
        assertEquals(Set.of(monday, tuesday),
                getDeliveryDaySet(mondayString, tuesdayString));

        // EP: duplicate items are not included in set
        assertEquals(Set.of(monday),
                getDeliveryDaySet(mondayString, mondayString));

        // EP: empty set
        assertEquals(Set.<DeliveryDay>of(), getDeliveryDaySet());
    }

    @Test
    public void getDeliveryDaySet_invalidDeliveryDay_throwsIllegalArgumentException() {
        // EP: empty delivery day
        String emptyDeliveryDayString = "";
        assertThrows(IllegalArgumentException.class, () -> getDeliveryDaySet(emptyDeliveryDayString));

        // EP: wrong format for delivery day
        String invalidDeliveryDayString = "Mon";
        assertThrows(IllegalArgumentException.class, () -> getDeliveryDaySet(invalidDeliveryDayString));

        // EP: one invalid delivery day with many valid delivery days
        String mondayString = "Monday";
        String tuesdayString = "TueSDAy";
        assertThrows(IllegalArgumentException.class, () -> getDeliveryDaySet(
                mondayString, tuesdayString, invalidDeliveryDayString));
    }

    @Test
    public void getDeliveryDaySet_unsortedDeliveryDays_returnsSortedDeliveryDaySet() throws IllegalValueException {
        Set<DeliveryDay> actualDeliveryDays = getDeliveryDaySet(UNSORTED_DAYS_WORDS);
        DeliveryDay[] expectedDeliveryDays = {DeliveryDay.MONDAY, DeliveryDay.TUESDAY,
                                              DeliveryDay.WEDNESDAY, DeliveryDay.THURSDAY};
        Set<DeliveryDay> expectedDeliveryDaySet = new LinkedHashSet<>(Arrays.asList(expectedDeliveryDays));

        assertArrayEquals(expectedDeliveryDaySet.toArray(), actualDeliveryDays.toArray());
    }
}

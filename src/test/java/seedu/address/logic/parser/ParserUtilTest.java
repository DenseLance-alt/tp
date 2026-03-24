package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.DateTimeUtil.convertDayNumberToDayWord;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.model.delivery.DeliveryDay.toDeliveryDay;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.DateTimeUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.DeliveryDay;
import seedu.address.model.delivery.DeliveryTime;
import seedu.address.model.delivery.EndDate;
import seedu.address.model.delivery.StartDate;
import seedu.address.model.delivery.fields.NumberOfDays;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#Vegan";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "Vegan";
    private static final String VALID_TAG_2 = "South";

    private static final String INVALID_START_DATE = "12-11-2012";
    private static final String INVALID_NUMBER_OF_DAYS = "-12";
    private static final String INVALID_END_DATE = "12-13-2012";
    private static final String INVALID_TIME = "25:67";
    private static final String INVALID_DAY_NUMBER = "0";
    private static final String INVALID_DAYS = "579";

    private static final String VALID_START_DATE = "2021-12-11";
    private static final String VALID_NUMBER_OF_DAYS = "2";
    private static final String VALID_END_DATE = "2021-12-13";
    private static final String VALID_TIME = "23:23";
    private static final String VALID_DAY_NUMBER_1 = "1";
    private static final String VALID_DAY_NUMBER_2 = "4";
    private static final String VALID_DAY_NUMBER_3 = "6";
    private static final String VALID_DAY_NUMBER_4 = "7";

    // The resulting end date of adding VALID_NUMBER_OF_DAYS days to VALID_START_DATE
    private static final String RESULTING_END_DATE = "2021-12-12";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseStartDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStartDate((String) null));
    }

    @Test
    public void parseStartDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStartDate(INVALID_START_DATE));
    }

    @Test
    public void parseStartDate_validValueWithoutWhitespace_returnsStartDate() throws Exception {
        StartDate expectedStartDate = new StartDate(VALID_START_DATE);
        assertEquals(expectedStartDate, ParserUtil.parseStartDate(VALID_START_DATE));
    }

    @Test
    public void parseStartDate_validValueWithWhitespace_returnsTrimmedStartDate() throws Exception {
        String startDateWithWhitespace = WHITESPACE + VALID_START_DATE + WHITESPACE;
        StartDate expectedStartDate = new StartDate(VALID_START_DATE);
        assertEquals(expectedStartDate, ParserUtil.parseStartDate(startDateWithWhitespace));
    }

    @Test
    public void parseEndDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEndDate((String) null));
    }

    @Test
    public void parseEndDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEndDate(INVALID_END_DATE));
    }

    @Test
    public void parseEndDate_validValueWithoutWhitespace_returnsEndDate() throws Exception {
        EndDate expectedStartDate = new EndDate(VALID_END_DATE);
        assertEquals(expectedStartDate, ParserUtil.parseEndDate(VALID_END_DATE));
    }

    @Test
    public void parseEndDate_validValueWithWhitespace_returnsTrimmedEndDate() throws Exception {
        String endDateWithWhitespace = WHITESPACE + VALID_END_DATE + WHITESPACE;
        EndDate expectedEndDate = new EndDate(VALID_END_DATE);
        assertEquals(expectedEndDate, ParserUtil.parseEndDate(endDateWithWhitespace));
    }

    @Test
    public void parseNumberOfDays_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNumberOfDays((String) null));
    }

    @Test
    public void parseNumberOfDays_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNumberOfDays(INVALID_NUMBER_OF_DAYS));
    }

    @Test
    public void parseNumberOfDays_validValueWithoutWhitespace_returnsNumberOfDays() throws ParseException {
        NumberOfDays expectedNumberOfDays = new NumberOfDays(VALID_NUMBER_OF_DAYS);
        assertEquals(expectedNumberOfDays, ParserUtil.parseNumberOfDays(VALID_NUMBER_OF_DAYS));
    }

    @Test
    public void parseNumberOfDays_validValueWithWhitespace_returnsTrimmedNumberOfDays() throws ParseException {
        String numberOfDaysWithWhitespace = WHITESPACE + VALID_NUMBER_OF_DAYS + WHITESPACE;
        NumberOfDays expectedNumberOfDays = new NumberOfDays(VALID_NUMBER_OF_DAYS);
        assertEquals(expectedNumberOfDays, ParserUtil.parseNumberOfDays(numberOfDaysWithWhitespace));
    }

    @Test
    public void getEndDate_bothNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.getEndDate(null, null));
    }

    @Test
    public void getEndDate_nullStartDate_throwsNullPointerException() {
        NumberOfDays numberOfDays = new NumberOfDays(VALID_NUMBER_OF_DAYS);
        assertThrows(NullPointerException.class, () -> ParserUtil.getEndDate(null, numberOfDays));
    }

    @Test
    public void getEndDate_nullNumberOfDays_throwsNullPointerException() {
        StartDate startDate = new StartDate(VALID_START_DATE);
        assertThrows(NullPointerException.class, () -> ParserUtil.getEndDate(startDate, null));
    }

    @Test
    public void getEndDate_validValues_returnsEndDate() {
        StartDate startDate = new StartDate(VALID_START_DATE);
        NumberOfDays numberOfDays = new NumberOfDays(VALID_NUMBER_OF_DAYS);
        EndDate expectedEndDate = new EndDate(RESULTING_END_DATE);
        assertEquals(expectedEndDate, ParserUtil.getEndDate(startDate, numberOfDays));
    }

    @Test
    public void parseDeliveryTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDeliveryTime((String) null));
    }

    @Test
    public void parseDeliveryTime_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDeliveryTime(INVALID_TIME));
    }

    @Test
    public void parseDeliveryTime_validValueWithoutWhitespace_returnsDeliveryTime() throws Exception {
        DeliveryTime expectedDeliveryTime = new DeliveryTime(VALID_TIME);
        assertEquals(expectedDeliveryTime, ParserUtil.parseDeliveryTime(VALID_TIME));
    }

    @Test
    public void parseDeliveryTime_validValueWithWhitespace_returnsDeliveryTime() throws Exception {
        String deliveryTimeWithWhitespace = WHITESPACE + VALID_TIME + WHITESPACE;
        DeliveryTime expectedDeliveryTime = new DeliveryTime(VALID_TIME);
        assertEquals(expectedDeliveryTime, ParserUtil.parseDeliveryTime(deliveryTimeWithWhitespace));
    }

    @Test
    public void parseDeliveryDayNumber_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDeliveryDayNumber((String) null));
    }

    @Test
    public void parseDeliveryDayNumber_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDeliveryDayNumber(INVALID_DAY_NUMBER));
    }

    @Test
    public void parseDeliveryDayNumber_validValue_returnsDeliveryDay() throws ParseException {
        String dayWord = convertDayNumberToDayWord(VALID_DAY_NUMBER_1);
        DeliveryDay expectedDeliveryDay = toDeliveryDay(dayWord);
        assertEquals(expectedDeliveryDay, ParserUtil.parseDeliveryDayNumber(VALID_DAY_NUMBER_1));
    }

    @Test
    public void parseDeliveryDays_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDeliveryDays((String) null));
    }

    @Test
    public void parseDeliveryDays_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDeliveryDays(INVALID_DAYS));
    }

    @Test
    public void parseDeliveryDays_validValueWithoutWhitespace_returnsDeliveryDaySet() throws Exception {
        String[] deliveryDayNumbers = {VALID_DAY_NUMBER_1, VALID_DAY_NUMBER_2, VALID_DAY_NUMBER_3, VALID_DAY_NUMBER_4};
        Set<DeliveryDay> actualDeliveryDaySet =
                ParserUtil.parseDeliveryDays(String.join("", deliveryDayNumbers));
        Set<DeliveryDay> expectedDeliverySet = Arrays.stream(deliveryDayNumbers)
                .map(DateTimeUtil::convertDayNumberToDayWord)
                .map(DeliveryDay::toDeliveryDay)
                .collect(Collectors.toSet());

        assertEquals(expectedDeliverySet, actualDeliveryDaySet);
    }

    @Test
    public void parseDeliveryDays_validValueWithWhiteSpace_returnsTrimmedDeliveryDaySet() throws Exception {
        String[] deliveryDayNumbers = {VALID_DAY_NUMBER_1, VALID_DAY_NUMBER_2, VALID_DAY_NUMBER_3, VALID_DAY_NUMBER_4};
        String deliveryDayNumbersWithWhitespace = WHITESPACE + String.join("", deliveryDayNumbers) + WHITESPACE;
        Set<DeliveryDay> expectedDeliverySet = Arrays.stream(deliveryDayNumbers)
                .map(DateTimeUtil::convertDayNumberToDayWord)
                .map(DeliveryDay::toDeliveryDay)
                .collect(Collectors.toSet());

        assertEquals(expectedDeliverySet, ParserUtil.parseDeliveryDays(deliveryDayNumbersWithWhitespace));
    }
}

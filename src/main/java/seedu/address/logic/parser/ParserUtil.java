package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.DateTimeUtil.convertDayNumberToDayWord;
import static seedu.address.commons.util.DateTimeUtil.isValidDeliveryDate;
import static seedu.address.commons.util.DateTimeUtil.isValidDeliveryDayNumber;
import static seedu.address.commons.util.DateTimeUtil.parseDeliveryDate;
import static seedu.address.model.delivery.DeliveryDay.toDeliveryDay;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
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

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_DATE = "Date is not valid. It should be in the format yyyy-MM-dd.";
    public static final String MESSAGE_INVALID_DAY_NUMBER =
            "Day number is not valid. It can only be a whole number within 1 to 7 inclusive.";

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses a {@code String date} into a {@code LocalDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is not in yyyy-MM-dd format.
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!isValidDeliveryDate(trimmedDate)) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }

        return parseDeliveryDate(trimmedDate);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code String startDate} into a {@code StartDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code startDate} is invalid.
     */
    public static StartDate parseStartDate(String startDate) throws ParseException {
        requireNonNull(startDate);
        String trimmedStartDate = startDate.trim();
        if (!StartDate.isValidStartDate(trimmedStartDate)) {
            throw new ParseException(StartDate.MESSAGE_CONSTRAINTS);
        }
        return new StartDate(trimmedStartDate);
    }

    /**
     * Parses {@code String endDate} into a {@code EndDate}.
     *
     * @param endDate The end date to be parsed.
     * @return The EndDate object representing the end date.
     * @throws ParseException if the given {@code endDate} is invalid.
     */
    public static EndDate parseEndDate(String endDate) throws ParseException {
        requireNonNull(endDate);
        String trimmedEndDate = endDate.trim();
        if (!EndDate.isValidEndDate(trimmedEndDate)) {
            throw new ParseException(EndDate.MESSAGE_CONSTRAINTS);
        }
        return new EndDate(trimmedEndDate);
    }

    /**
     * Parses {@code String numberOfDays} into a {@code NumberOfDays}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code numberOfDays} is invalid.
     */
    public static NumberOfDays parseNumberOfDays(String numberOfDays) throws ParseException {
        requireNonNull(numberOfDays);
        String trimmedNumberOfDays = numberOfDays.trim();
        if (!NumberOfDays.isValidNumberOfDays(trimmedNumberOfDays)) {
            throw new ParseException(NumberOfDays.MESSAGE_CONSTRAINTS);
        }
        return new NumberOfDays(trimmedNumberOfDays);
    }

    /**
     * Returns the {@code EndDate} object when
     * given the {@code startDate} and {@code numberOfDays}.
     *
     * @param startDate The StartDate object representing the start date.
     * @param numberOfDays The NumberOfDays object representing the number of days
     *                     that should be added to the startDate.
     * @return The EndDate object which represents the end date computed.
     */
    public static EndDate getEndDate(StartDate startDate, NumberOfDays numberOfDays) {
        requireNonNull(startDate);
        requireNonNull(numberOfDays);
        LocalDate endDate = startDate.date.plusDays(numberOfDays.days - 1);
        return new EndDate(endDate.format(EndDate.FORMATTER));
    }

    /**
     * Returns the {@code String deliveryTime} into a {@code DeliveryTime}
     * Leading and trailing whitespaces are trimmed.
     *
     * @throws ParseException if the given {@code deliveryTime} is invalid.
     */
    public static DeliveryTime parseDeliveryTime(String deliveryTime) throws ParseException {
        requireNonNull(deliveryTime);
        String trimmedDeliveryTime = deliveryTime.trim();
        if (!DeliveryTime.isValidDeliveryTime(trimmedDeliveryTime)) {
            throw new ParseException(DeliveryTime.MESSAGE_CONSTRAINTS);
        }
        return new DeliveryTime(trimmedDeliveryTime);
    }

    /**
     * Parses a {@code String deliveryDayNumber} into a {@code DeliveryDay}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code deliveryDayNumber} is invalid.
     */
    public static DeliveryDay parseDeliveryDayNumber(String deliveryDayNumber) throws ParseException {
        requireNonNull(deliveryDayNumber);
        if (!isValidDeliveryDayNumber(deliveryDayNumber)) {
            throw new ParseException(MESSAGE_INVALID_DAY_NUMBER);
        }

        String deliveryDayWord = convertDayNumberToDayWord(deliveryDayNumber);
        return toDeliveryDay(deliveryDayWord);
    }

    /**
     * Parses {@code Collection<String> deliveryDays} into {@code Set<DeliveryDay>}.
     */
    public static Set<DeliveryDay> parseDeliveryDays(String deliveryDays) throws ParseException {
        requireNonNull(deliveryDays);

        String trimmedDeliveryDays = deliveryDays.trim();
        String[] listOfDeliveryDays = trimmedDeliveryDays.split("");
        Set<DeliveryDay> deliveryDaySet = new HashSet<>();
        for (String deliveryDayNumber : listOfDeliveryDays) {
            deliveryDaySet.add(parseDeliveryDayNumber(deliveryDayNumber));
        }
        return deliveryDaySet;
    }
}

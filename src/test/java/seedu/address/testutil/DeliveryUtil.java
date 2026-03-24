package seedu.address.testutil;

import static seedu.address.commons.util.DateTimeUtil.DATE_FORMATTER;
import static seedu.address.commons.util.DateTimeUtil.DAY_NUMBER_FORMATTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryDay;
import seedu.address.model.delivery.EndDate;
import seedu.address.model.delivery.StartDate;

/**
 * A utility class for Delivery.
 */
public class DeliveryUtil {
    /**
     * Returns the part of command string for the given {@code delivery}'s details.
     */
    public static String getDeliveryDetails(Delivery delivery) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_START_DATE + delivery.getStartDate().date.toString() + " ");
        sb.append(PREFIX_END_DATE + delivery.getEndDate().date.toString() + " ");
        sb.append(PREFIX_TIME + delivery.getDeliveryTime().time.toString() + " ");
        sb.append(PREFIX_DAYS + getDeliveryDaysArgument(delivery.getDeliveryDays()) + " ");
        return sb.toString();
    }

    /**
     * Returns the command argument found after /days
     * given a certain set of DeliveryDay {@code deliveryDaysSet}.
     *
     * @param deliveryDaysSet The set of DeliveryDay.
     * @return The argument that can be put after /days in commands.
     */
    private static String getDeliveryDaysArgument(Set<DeliveryDay> deliveryDaysSet) {
        return deliveryDaysSet.stream()
                .map(deliveryDay -> DAY_NUMBER_FORMATTER.format(deliveryDay.getDay()))
                .collect(Collectors.joining(""));
    }

    /**
     * Returns an EndDate object encapsulating
     * the date of {@code startDate} added by {@code numberOfDays}.
     *
     * @param startDate The StartDate object that will be added upon.
     * @param numberOfDays The number of days to be added,
     *                     which can be a negative number.
     * @return The EndDate object corresponding to
     *         the start date added by the number of days.
     */
    public static EndDate generateEndDate(StartDate startDate, int numberOfDays) {
        LocalDate endDate = startDate.date.plusDays(numberOfDays);
        return new EndDate(DATE_FORMATTER.format(endDate));
    }
}

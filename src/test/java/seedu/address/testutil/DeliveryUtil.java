package seedu.address.testutil;

import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryDay;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_OF_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.delivery.DeliveryDay.NUMBER_FORMATTER;

public class DeliveryUtil {
    /**
     * Returns the part of command string for the given {@code delivery}'s details.
     */
    public static String getDeliveryDetails(Delivery delivery) {
        LocalDate startDate = delivery.getStartDate().date;
        LocalDate endDate = delivery.getEndDate().date;
        int numberOfDays = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;

        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_START_DATE + delivery.getStartDate().date.toString() + " ");
        sb.append(PREFIX_NUMBER_OF_DAYS + String.valueOf(numberOfDays) + " ");
        sb.append(PREFIX_TIME + delivery.getDeliveryTime().time.toString() + " ");
        sb.append(PREFIX_DAYS + getDeliveryDaysArgument(delivery.getDeliveryDays()) + " ");
        return sb.toString();
    }

    private static String getDeliveryDaysArgument(Set<DeliveryDay> deliveryDaysSet) {
        return deliveryDaysSet.stream()
                .map(deliveryDay -> NUMBER_FORMATTER.format(deliveryDay.day))
                .collect(Collectors.joining(""));
    }
}

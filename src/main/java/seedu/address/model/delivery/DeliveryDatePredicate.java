package seedu.address.model.delivery;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests whether a {@code Person} has a delivery scheduled within the given date range.
 */
public class DeliveryDatePredicate implements Predicate<Person> {

    private final LocalDate startDate;
    private final LocalDate endDate;

    /**
     * Creates a DeliveryDatePredicate that tests whether a person has a delivery on {@code date}.
     *
     * @param date The date to check for deliveries.
     */
    public DeliveryDatePredicate(LocalDate date) {
        this(date, date);
    }

    /**
     * Creates a DeliveryDatePredicate that tests whether a person has a delivery
     * within the range from {@code startDate} to {@code endDate} (inclusive).
     *
     * @param startDate The start of the date range to check for deliveries (inclusive).
     * @param endDate The end of the date range to check for deliveries (inclusive).
     */
    public DeliveryDatePredicate(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Tests whether the given {@code Person} has a delivery scheduled within the stored date range,
     * inclusive of both start and end dates.
     *
     * @param person The person to test.
     * @return {@code true} if the person has a delivery that falls within the date range, {@code false} otherwise.
     */
    @Override
    public boolean test(Person person) {

        Delivery delivery = person.getDelivery();

        if (delivery == null) {
            return false;
        }

        LocalDate deliveryStart = delivery.getStartDate().date;
        LocalDate deliveryEnd = delivery.getEndDate().date;

        LocalDate overlapStart = startDate.isAfter(deliveryStart) ? startDate : deliveryStart;
        LocalDate overlapEnd = endDate.isBefore(deliveryEnd) ? endDate : deliveryEnd;

        if (overlapStart.isAfter(overlapEnd)) {
            return false;
        }

        // For each delivery day of the week, find its first occurrence on or after overlapStart.
        // If that date falls within the overlap period, a valid delivery day exists in the range.
        if (delivery.getDeliveryDays().stream()
                .noneMatch(deliveryDay -> {
                    DayOfWeek dow = deliveryDay.getDay();
                    LocalDate firstOccurrence = overlapStart.with(TemporalAdjusters.nextOrSame(dow));
                    return !firstOccurrence.isAfter(overlapEnd);
                })) {
            return false;
        }

        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeliveryDatePredicate)) {
            return false;
        }

        DeliveryDatePredicate otherPredicate = (DeliveryDatePredicate) other;
        return startDate.equals(otherPredicate.startDate)
                && endDate.equals(otherPredicate.endDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("startDate", startDate)
                .add("endDate", endDate)
                .toString();
    }
}

package seedu.address.model.delivery;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests whether a {@code Person} has a delivery on the given date.
 */
public class DeliveryDatePredicate implements Predicate<Person> {

    private final LocalDate targetDate;

    /**
     * Creates a DeliveryDatePredicate that tests whether a person has a delivery on {@code targetDate}.
     */
    public DeliveryDatePredicate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    @Override
    public boolean test(Person person) {

        Delivery delivery = person.getDelivery();

        if (delivery == null) {
            return false;
        }

        LocalDate startDate = delivery.getStartDate().date;
        LocalDate endDate = delivery.getEndDate().date;

        if (targetDate.isBefore(startDate) || targetDate.isAfter(endDate)) {
            return false;
        }

        DayOfWeek targetDay = targetDate.getDayOfWeek();

        if (delivery.getDeliveryDays().stream()
                .noneMatch(deliveryDay -> deliveryDay.day.equals(targetDay))) {
            return false;
        }

        return delivery.getSkippedDates().stream()
                .noneMatch(skipped -> skipped.date.equals(targetDate));
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
        return targetDate.equals(otherPredicate.targetDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("targetDate", targetDate).toString();
    }
}

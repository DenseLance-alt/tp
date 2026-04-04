package seedu.address.ui;

import java.util.Comparator;
import java.util.Set;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.delivery.DeliveryTime;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * A UI component that displays selected information of a {@code Person} with delivery scheduled today.
 */
public class TodayDeliveryCard extends UiPart<Region> {
    private static final String FXML = "TodayDeliveryCard.fxml";
    private static final Logger logger = LogsCenter.getLogger(TodayDeliveryCard.class);

    public final Person person;

    @FXML
    private HBox todayCardPane;
    @FXML
    private Label todayCardName;
    @FXML
    private Label todayCardTime;
    @FXML
    private Label todayCardAddress;
    @FXML
    private FlowPane todayCardTags;

    /**
     * Creates a {@code TodayDeliveryCard} for the given person and display index.
     *
     * @param person The {@code Person} to create a card for, not null.
     *               {@code Person} name, delivery, address and tags must not be null.
     */
    public TodayDeliveryCard(Person person) {
        super(FXML);
        this.person = person;

        Name personName = person.getName();
        DeliveryTime personDeliveryTime = person.getDeliveryTime();
        Address personAddress = person.getAddress();
        Set<Tag> personTags = person.getTags();

        logger.fine("Initializing with: " + personName + ", " + personDeliveryTime + ", "
                + personAddress + ", " + personTags);

        displayTodayCardName(personName);
        displayTodayCardTime(personDeliveryTime);
        displayTodayCardAddress(personAddress);
        displayTodayCardTags(personTags);
    }

    // Design Decision: use AB3's current implementation idioms to access data in {@code Person} fields,
    // As costs outweigh benefits of refactoring AB3's implementation and updating all parts of the codebase
    // to use the new getter methods given available resources.

    private void displayTodayCardName(Name name) {
        assert name != null;

        todayCardName.setText(name.toString());
    }

    private void displayTodayCardTime(DeliveryTime deliveryTime) {
        assert deliveryTime != null;

        todayCardTime.setText(deliveryTime.toString());
    }

    private void displayTodayCardAddress(Address address) {
        assert address != null;

        todayCardAddress.setText(address.toString());
    }

    private void displayTodayCardTags(Set<Tag> tags) {
        assert tags != null;

        tags.stream()
            .sorted(Comparator.comparing(tag -> tag.tagName))
            .forEach(tag -> {
                Label tagLabel = new Label(tag.tagName);
                tagLabel.setMaxWidth(300);
                tagLabel.setTextOverrun(OverrunStyle.ELLIPSIS);
                todayCardTags.getChildren().add(tagLabel);
            });
    }
}

package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.person.Person;

/**
 * A UI component that displays selected information of a {@code Person} with delivery scheduled today.
 */
public class TodayDeliveryCard extends UiPart<Region> {
    private static final String FXML = "TodayDeliveryCard.fxml";

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
     */
    public TodayDeliveryCard(Person person) {
        super(FXML);
        this.person = person;

        todayCardName.setText(person.getName().fullName);
        setTodayCardTime((person.getDelivery()));
        todayCardAddress.setText(person.getAddress().value); // TODO: fix LoD violation

        person.getTags().stream() // TODO: fix LoD violation tag.tagName
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> todayCardTags.getChildren().add(new Label(tag.tagName)));
    }

    private void setTodayCardTime(Delivery delivery) {
        todayCardTime.setText(delivery.getDeliveryTime().toString()); // TODO: fix LoD violation
    }

}

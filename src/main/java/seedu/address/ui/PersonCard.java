package seedu.address.ui;

import static javafx.geometry.Pos.CENTER;

import java.time.DayOfWeek;
import java.util.Comparator;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    public static final String STYLE_DELIVERY_DAY_PRESENT = "-fx-background-color: #3CB043; "
            + "-fx-text-fill: white; "
            + "-fx-font-weight: bold; "
            + "-fx-font-size: 11px; "
            + "-fx-background-radius: 5; "
            + "-fx-border-radius: 5; "
            + "-fx-border-color: #028A0F; "
            + "-fx-border-width: 1;";
    public static final String STYLE_DELIVERY_DAY_ABSENT = "-fx-background-color: #E0E0E0; "
            + "-fx-text-fill: #9E9E9E; "
            + "-fx-font-size: 11px; "
            + "-fx-background-radius: 5; "
            + "-fx-border-radius: 5; "
            + "-fx-border-color: #BDBDBD; "
            + "-fx-border-width: 1;";

    public static final int DAY_BOX_WIDTH = 38;
    public static final int DAY_BOX_HEIGHT = 20;

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox deliverySection;
    @FXML
    private HBox deliveryKeyInfo;
    @FXML
    private Label deliverySchedule;
    @FXML
    private HBox deliveryDaysContainer;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        showDeliveryInfo();
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        // Solution below inspired by GitHub Copilot
        name.setWrapText(true);
        phone.setWrapText(true);
        address.setWrapText(true);
        email.setWrapText(true);
    }

    //Solution below inspired by
    // https://github.com/AY2526S1-CS2103T-W11-1/tp/blob/master/src/main/java/seedu/address/ui/PersonCard.java
    private void showDeliveryInfo() {
        // no delivery -> do not show delivery section
        if (!person.hasDelivery()) {
            showDeliverySection(false);
            return;
        }

        displayDeliverySchedule(person);
        displayDeliveryDays(person);
        showDeliverySection(true);
    }

    /**
     * Shows delivery details if {@code true}, otherwise it is not shown.
     */
    private void showDeliverySection(boolean shouldShow) {
        deliverySection.setVisible(shouldShow);
        deliverySection.setManaged(shouldShow);
    }

    /**
     * Displays delivery schedule for a person.
     *
     * @param person Person whose delivery schedule should be displayed.
     */
    private void displayDeliverySchedule(Person person) {
        String formattedDeliverySchedule = person.getFormattedDeliverySchedule();
        deliverySchedule.setText(formattedDeliverySchedule);
    }

    /**
     * Displays delivery days for a person.
     *
     * @param person Person whose delivery days should be displayed.
     */
    private void displayDeliveryDays(Person person) {
        deliveryDaysContainer.getChildren().clear();
        Set<String> deliveryDayNames = person.getDeliveryDayNames();
        for (DayOfWeek day : DayOfWeek.values()) {
            Label dayBox = createDayBox(day);
            colourDayBox(dayBox, day, deliveryDayNames);
            deliveryDaysContainer.getChildren().add(dayBox);
        }
    }

    /**
     * Creates a day box for a particular day-of-week.
     *
     * @param day The day-of-week to create a day box for.
     * @return Box with a label denoting the day-of-week.
     */
    private Label createDayBox(DayOfWeek day) {
        String dayString = day.toString();
        String shortenedDayString = dayString.substring(0, 3); // take first 3 letters only
        Label dayBox = new Label(shortenedDayString);
        dayBox.setMinWidth(DAY_BOX_WIDTH);
        dayBox.setMaxWidth(DAY_BOX_WIDTH);
        dayBox.setMinHeight(DAY_BOX_HEIGHT);
        dayBox.setMaxHeight(DAY_BOX_HEIGHT);
        dayBox.setAlignment(CENTER);
        return dayBox;
    }

    /**
     * Colours a day box given the day-of-week and set of delivery day names.
     *
     * @param dayBox The day box to colour.
     */
    private void colourDayBox(Label dayBox, DayOfWeek day, Set<String> deliveryDayNames) {
        String dayName = day.toString();
        String style = deliveryDayNames.contains(dayName)
            ? STYLE_DELIVERY_DAY_PRESENT
            : STYLE_DELIVERY_DAY_ABSENT;
        dayBox.setStyle(style);
    }

}

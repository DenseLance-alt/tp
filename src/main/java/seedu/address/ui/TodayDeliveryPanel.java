package seedu.address.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons with delivery scheduled today.
 */
public class TodayDeliveryPanel extends UiPart<Region> {
    // Date Format: Abbreviated day of week, day with no leading 0, abbreviated month name, 4-digit year
    private static final DateTimeFormatter HEADER_DATE_FORMAT =
            DateTimeFormatter.ofPattern("EEE, d MMM yyyy");

    private static final String FXML = "TodayDeliveryPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TodayDeliveryPanel.class);

    @FXML private Label todayPanelTitle;
    @FXML private Label todayPanelCount;
    @FXML private ListView<Person> todayDeliveryListView;

    /**
     * Creates a {@code TodayDeliveryPanel} with the given {@code ObservableList}.
     *
     * @param sortedPersonWithTodayDeliveryList List of {@code Person} with delivery scheduled today,
     *                                          sorted by ascending order of delivery time.
     * @param today The current local date.
     */
    public TodayDeliveryPanel(ObservableList<Person> sortedPersonWithTodayDeliveryList, LocalDate today) {
        super(FXML);

        // Solution below inspired by Claude AI
        todayPanelTitle.setText("Today's Deliveries - " + today.format(HEADER_DATE_FORMAT));

        // Displays the number of deliveries by binding to list size,
        // updates automatically with appropriate singular/plural description when list size changes.
        todayPanelCount.textProperty().bind(
                Bindings.createStringBinding(() -> sortedPersonWithTodayDeliveryList.size() == 1 ? "1 delivery"
                                : sortedPersonWithTodayDeliveryList.size() + " deliveries",
                        sortedPersonWithTodayDeliveryList
                )
        );

        todayDeliveryListView.setItems(sortedPersonWithTodayDeliveryList);
        todayDeliveryListView.setCellFactory(listView -> new TodayDeliveryListViewCell());

        logger.info("TodayDeliveryPanel initialised for date: " + today);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code TodayDeliveryCard}.
     */
    class TodayDeliveryListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TodayDeliveryCard(person).getRoot());
            }
        }
    }

}

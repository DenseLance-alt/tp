---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# ServeMate Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Acknowledgements

### Original Source
* This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

### Other Sources
* Displaying of delivery information under each person card was inspired by [SoCTAssist](https://github.com/AY2526S1-CS2103T-W11-1/tp).

### Libraries Used
* [JavaFX](https://openjfx.io/) for GUI rendering.
* [Jackson](https://github.com/FasterXML/jackson) for JSON processing.
* [JUnit5](https://github.com/junit-team/junit5) for unit and integration testing.
* [PlantUML](https://plantuml.com/stdlib) for generating UML diagrams.

### AI Generated Work
* Gemini was used to generate the ServeMate icon for the application and GUI window.

--------------------------------------------------------------------------------------------------------------------

## Getting started

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## Design

<box type="info" light>

**Note:** All references of a person in this design section represent a customer.
</box>

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<box type="info" light>

**Note:** Due to a limitation of PlantUML, there is an overlap in the dependency arrowhead and inheritance triangle originating from `TodayDeliveryCard` to `Model` and `UiPart` respectively. The arrowheads and inheritance triangle should not overlap.
</box>

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component" />

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `TodayDeliveryPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<box type="info" light>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object) and the `Delivery` objects contained within these `Person` objects.
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" light>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` and `Delivery` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects. Similarly, only one `Delivery` object would be required per unique delivery, instead of each `Person` needing their own `Delivery` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

Note on parsing address book data:
* Although commands from the user accept days of the week in the form of numbers (i.e. `123`), they are stored as capitalized alphabet-only strings (i.e. "MONDAY", "TUESDAY", etc.) in the JSON file. This is to enable better readability by advanced users who would like to edit the JSON file since speed of inputting is not a concern for this use case.

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## Implementation

This section describes some noteworthy details on how certain features are implemented.

<br>

### Today's deliveries panel

**Objective:** Allow administrative staff to view deliveries scheduled for the current day.

#### Implementation details
The following sequence diagram illustrates the interactions between the `Ui`, `Logic` and `Model` components, to create the `TodayDeliveryPanel` when the application is launched.

<puml src="diagrams/TodayDeliveryPanelSequenceDiagram.puml" alt="Interactions between the `Ui`, `Logic` and `Model` components, to create the `TodayDeliveryPanel` when the application is launched" />

**Execution flow:**
1. When `MainWindow` is called upon to fill its inner parts, it gets the list of persons with deliveries scheduled for the current day from the `LogicManager`.
2. The `LogicManager` in turn calls `ModelManager`, which retrieves and returns the sorted list of today's deliveries. This list is sorted in ascending order of delivery time.
3. `MainWindow` then gets the current date from the `LogicManager`, which in turn calls `ModelManager` to retrieve and return the current date.
4. Next, `MainWindow` instantiates a `TodayDeliveryPanel` object using the sorted list of today's deliveries and the current date.<br>
   Note that although `fillInnerParts()` is shown only instantiating a `TodayDeliveryPanel` object in the diagram above (for simplicity), in the code `fillInnerParts()` also instantiates other parts of the `Ui` (e.g. `PersonListPanel`).
5. Finally, the newly created `TodayDeliveryPanel` object is used by `MainWindow` to fill the panel's placeholder, displaying the current date and the sorted list of today's deliveries.<br>
   Note that this step is omitted in the diagram above (for simplicity).

#### Design considerations

1. Whether deliveries displayed on the `TodayDeliveryPanel` should be updated when filtering commands are executed (e.g. `find`, `find-delivery`).
    * **Chosen:** `TodayDeliveryPanel` will not be updated when filtering commands are executed.
        * Pros: Allows the user to consistently view all deliveries scheduled for the current day at a glance. Keeps the responsibilities of the `PersonListPanel` and `TodayDeliveryPanel` distinct.
        * Cons: Users may not be able to directly see how filtered results relate to deliveries scheduled for the current day on the `TodayDeliveryPanel`.
    * **Alternative:** `TodayDeliveryPanel` will be updated when filtering commands are executed.
        * Pros: Allows the user flexibility to filter the deliveries displayed on the `TodayDeliveryPanel`.
        * Cons: May confuse users when results on both the `PersonListPanel` and `TodayDeliveryPanel` change. Reduces the usefulness of the `TodayDeliveryPanel` as a stable, quick overview of deliveries scheduled for the current day.

<br>

### Find delivery by date

**Objective:** Allows administrative staff to filter the customer list to show only customers with deliveries scheduled on a specified date, or within a specified date range.

#### Implementation details
The following sequence diagram illustrates the interactions within the `Logic` component for finding deliveries by date:

<box type="info" light>

**Note:** The lifeline for `FindDeliveryCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram. Additionally, another limitation of PlantUML is that a dotted line cannot be shown from the UML note.
</box>

<puml src="diagrams/FindDeliverySequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `find-delivery dt/2026-04-01` Command" />

**Execution flow:**
1. The user enters the `find-delivery` command with either a single date (`dt/`) or a date range (`st/` and `ed/`).
2. `LogicManager` receives the input string and passes it to `AddressBookParser`.
3. `AddressBookParser` creates a `FindDeliveryCommandParser` to parse the command arguments.
4. `FindDeliveryCommandParser` parses the date prefix(es) and constructs a `DeliveryDatePredicate` that encapsulates the search criteria.
5. `FindDeliveryCommandParser` creates a `FindDeliveryCommand` with the predicate and returns it.
6. `LogicManager` executes the `FindDeliveryCommand`.
7. `FindDeliveryCommand` calls `Model#updateFilteredPersonList(predicate)` to filter the customer list.
8. `FindDeliveryCommand` completes and returns a `CommandResult` indicating how many customers were listed.

#### Design considerations

1. How `find-delivery` accepts date input.
    * **Chosen:** Support both a single date (`dt/`) and a date range (`st/` and `ed/`), but not both at the same time.
        * Pros: Flexible; covers the common case of checking a single day as well as planning for a longer window.
        * Cons: Parser must validate that the two modes are mutually exclusive, adding some complexity.
    * **Alternative:** Accept only a single date.
        * Pros: Simpler parsing logic.
        * Cons: Less useful for staff who need to view deliveries over a multi-day period.

<br>

### Find customers with expired delivery

**Objective:** Allows administrative staff to track subscriptions that have ended and facilitate renewals.

#### Implementation Details
The following sequence diagram illustrates the interactions within the `Logic` component for finding customers with an expired delivery:

<box type="info" light>

**Note:** The lifeline for `ExpiredCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of the diagram.
</box>

<puml src="diagrams/ExpiredSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `expired bf/2026-02-01` Command">

**Execution flow:**
1. The user enters the `expired` command as an input string.
2. `LogicManager` receives the input string and passes it to `AddressBookParser`.
3. `AddressBookParser` creates an `ExpiredCommandParser` to parse the command arguments.
4. `ExpiredCommandParser` parses the arguments and creates a `PersonHasExpiredDeliveryPredicate` object.
5. `ExpiredCommandParser` uses the `PersonHasExpiredDeliveryPredicate` to create an `ExpiredCommand` object.
6. `LogicManager` executes the `ExpiredCommand` object.
7. `ExpiredCommand` requests `Model` to filter the customer list based on the given `PersonHasExpiredDeliveryPredicate`.
8. `ExpiredCommand` completes and returns the result of the `expired` command.

#### Design Considerations

1. Functionality of the `expired` command.
    * **Chosen:** Find all customers with deliveries that have ended before a user-specified date.
      * Pros: Flexible, as it allows users to find deliveries that have expired today or are about to expire.
      * Cons: Requires extra effort to decide on the correct date to key in.
        * Note that users can find today's date easily by referring to the deliveries panel. 
    * **Alternative:** Find all customers with deliveries that have ended before today.
      * Pros: Simple and fast way to view recently expired deliveries.
      * Cons: Reduces testability, as test cases have to be created with respect to the system's date time.
2. How the prefix for the date field is named.
    * **Chosen:** Name the prefix as `bf/` to represent "before".
      * Pros: User-friendly and intuitive, as the prefix aligns with the intent of the command.
      * Cons: `bf/` as an abbreviation for "before" might not be obvious to new users initially.
    * **Alternative:** Name the prefix as `ed/` to represent "end date".
      * Pros: Consistent with the format used for `find-delivery`.
      * Cons: Ambiguous, since `expired ed/DATE` could refer to finding deliveries that end on that exact date.

<br>

### Schedule delivery

**Objective:** Allows administrative staff to add a delivery to be associated with the specified customer.

#### Implementation details
The following sequence diagram illustrates the interactions within the `Logic` component for scheduling a delivery:

<box type="info" light>

**Note:** The lifeline for `ScheduleCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of the diagram. Additionally, another limitation of PlantUML is that a dotted line cannot be shown from the UML note.
</box>

<puml src="diagrams/ScheduleSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `schedule 1 st/2026-01-01 ed/2026-02-01 tm/14:00 d/123` Command" />

**Execution flow:**
1. The user enters the `schedule` command as an input string.
2. `LogicManager` receives the input string and passes it to `AddressBookParser`.
3. `AddressBookParser` creates a `ScheduleCommandParser` to parse the command arguments.
4. `ScheduleCommandParser` parses the index and arguments, and creates both a `Delivery` object and `ScheduleCommand` object.
5. `LogicManager` executes `ScheduleCommand` object.
6. `ScheduleCommand` checks whether the specified customer exists and the customer already has a delivery.
7. If the customer does not already have a delivery, `ScheduleCommand` creates a new `Person` object with the delivery.
8. `ScheduleCommand` requests `Model` to replace the old entry in the address book with the newly created `Person` object.
9. `ScheduleCommand` completes and returns the result of the `schedule` command.

#### Design considerations

1. How the `schedule` adds the delivery for a person.
   * **Chosen:** Implement a dedicated `schedule` command.
        * Pros: One-shot command that enables users to easily add a new delivery to the specified person.
        * Cons: Requires implementing a new command class.
   * **Alternative 1:** Extend `add` to support adding delivery details alongside person details.
        * Pros: Fewer commands to learn.
        * Cons: Complicates the syntax of `add`, increases parser and validation complexity and weakens separation between customer-date edits and delivery-scheduling operations.
   * **Alternative 2:** Extend `edit` to support adding delivery details to person without deliveries.
        * Pros: Fewer commands to learn.
        * Cons: Potential confusion between unintuitive command name (`edit`) for the intended effect (adding a new command), increases parser and validation complexity and weakens separation between customer-date edits and delivery-scheduling operations.

<br>

### Reschedule delivery

**Objective:** Allows administrative staff to edit a delivery that is associated with the specified customer.

#### Implementation details
The following sequence diagram illustrates the interactions within the `Logic` component for rescheduling a delivery:

<box type="info" light>

**Note:** The lifeline for `RescheduleCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram. Additionally, another limitation of PlantUML is that a dotted line cannot be shown from the UML note.
</box>

<puml src="diagrams/RescheduleSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `reschedule 1 tm/14:00` Command" />

**Execution flow:**
1. The user enters the `reschedule` command as an input string.
2. `LogicManager` receives the input string and passes it to `AddressBookParser`.
3. `AddressBookParser` creates an `RescheduleCommandParser` to parse the command arguments.
4. `RescheduleCommandParser` parses the index and creates an `RescheduleCommand` object.
5. `LogicManager` executes the `RescheduleCommand` object.
6. `RescheduleCommand` checks whether the specified customer and their delivery exist.
7. If the customer has a delivery, `RescheduleCommand` creates a new `Person` object with an updated delivery.
8. `RescheduleCommand` requests `Model` to replace the old entry in the address book with the newly created `Person` object.
9. `RescheduleCommand` completes and returns the result of the `reschedule` command.

#### Design considerations

1. How `reschedule` edits the delivery of a person.
    * **Chosen:** Implement a dedicated `reschedule` command.
        * Pros: One-shot command that enables users to easily edit the delivery of the specified person.
        * Cons: Requires implementing a new command class.
    * **Alternative 1:** Instruct the user to add a new delivery with updated details to the person, overwriting the old delivery.
        * Pros: Reuses existing `schedule` commands without needing additional implementation effort.
        * Cons: More error-prone, as the user must manually re-enter all delivery details.
    * **Alternative 2:** Extend `edit` to support editing of delivery details alongside customer details.
        * Pros: Fewer commands to learn.
        * Cons: Increases parser and validation complexity and weakens separation between customer-data edits and delivery-scheduling operations.

<br>

### Unschedule delivery

**Objective:** Allows administrative staff to delete a delivery that is associated with the specified customer, without deleting the customer record.

#### Implementation details
The following sequence diagram illustrates the interactions within the `Logic` component for unscheduling a delivery:

<box type="info" light>

**Note:** The lifeline for `UnscheduleCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

<puml src="diagrams/UnscheduleSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `unschedule 2` Command" />

**Execution flow:**
1. The user enters the `unschedule` command as an input string.
2. `LogicManager` receives the input string and passes it to `AddressBookParser`.
3. `AddressBookParser` creates an `UnscheduleCommandParser` to parse the command arguments.
4. `UnscheduleCommandParser` parses the index and creates an `UnscheduleCommand` object.
5. `LogicManager` executes the `UnscheduleCommand` object.
6. `UnscheduleCommand` checks whether the specified customer and their delivery exist.
7. If the customer has a delivery, `UnscheduleCommand` creates a new `Person` object with all existing fields intact except the delivery.
8. `UnscheduleCommand` requests `Model` to replace the old entry in the address book with the newly created `Person` object.
9. `UnscheduleCommand` requests `Model` to refresh the displayed customer list to show all customers.
10. `UnscheduleCommand` completes and returns the result of the `unschedule` command.

#### Design considerations

1. How the command is named.
    * **Chosen:** Name the command as `unschedule`.
      * Pros: User-friendly and intuitive, as it maintains consistency with delivery-related commands (`schedule`, `reschedule`).
      * Cons: Not commonly used in standard English, which may cause first-time users to find it unusual.
    * **Alternative:** Name the command as `cancel`.
      * Pros: Familiar word that users are unlikely to mistype.
      * Cons: Ambiguous, since `cancel` could refer to cancelling of other actions beyond deliveries; breaks consistency with `schedule` and `reschedule`.
2. How `unschedule` removes the delivery from a customer.
    * **Chosen:** Implement a dedicated `unschedule` command.
      * Pros: One-shot command that enables users to easily remove the delivery of the specified customer.
      * Cons: Requires implementing a new command class.
    * **Alternative:** Instruct the user to delete the customer and add them back without their delivery details.
      * Pros: Reuses existing `delete` and `add` commands without needing additional implementation effort.
      * Cons: More error-prone, as the user must manually re-enter the customer's details.

--------------------------------------------------------------------------------------------------------------------

## Documentation, logging, testing, configuration, dev-ops

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## Appendix: Requirements

### Product scope

#### Target user profile

* handle the **planning and administration of deliveries** for administrative staff of a Tingkat caterer
* has a need to **manage a significant number** of customers
* prefer **desktop apps** over other types
* can **type fast**
* **prefers typing** to mouse interactions
* is **reasonably comfortable using** CLI apps

#### Value proposition
* Provides fast and organised solution for tingkat caterers to manage their customer information for delivery planning


<!-- @@author MrMarshall12 -->
### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​       | I want to …​                                         | So that I can…​                                                                                                |
|----------|---------------|------------------------------------------------------|----------------------------------------------------------------------------------------------------------------|
| `* * *`  | beginner user | add a customer                                       | store their contact information                                                                                |
| `* * *`  | beginner user | view a list of all customers                         | get a complete overview of my contact base                                                                     |
| `* * *`  | beginner user | view deliveries for all customers                    | ensure that food is delivered on the correct days                                                              |
| `* * *`  | beginner user | exit from the app easily                             | avoid cluttering my desktop screen once I have finished using the app                                          |
| `* * *`  | beginner user | delete a customer                                    | get rid of customer records that I no longer need to track                                                     |
| `* *`    | beginner user | see a message explaining how to access the help page | learn what each operation does                                                                                 |
| `* *`    | user          | edit customer's data                                 | correct any mistakes or changes to customer data to keep information accuracy                                  |
| `* *`    | user          | schedule a delivery                                  | track deliveries that need to be made                                                                          |
| `* *`    | user          | reschedule a delivery                                | correct any mistakes or changes to delivery data belongs to a particular customer to keep information accuracy |
| `* *`    | user          | unschedule a delivery                                | remove an inactive delivery                                                                                    |
| `* *`    | familiar user | display all upcoming deliveries for the day          | prepare the food and plan for the deliveries                                                                   |
| `* *`    | familiar user | find customers with expired subscriptions            | identify and follow up with customers to renew their subscription                                              |
| `* *`    | familiar user | tag each customer by their food preference           | inform the cooks to prepare food that aligns with the customers' food preference                               |
| `*`      | familiar user | create a delivery route                              | inform delivery drivers on their delivery route                                                                |
| `*`      | busy user     | search for a customer by name, address or tags       | quickly locate customer details                                                                                |
| `*`      | expert user   | reorder stops within a delivery route                | ensures deliveries follow an efficient sequence                                                                |
| `*`      | expert user   | import customer data in bulk                         | conveniently transition into the app                                                                           |
| `*`      | expert user   | set estimated time of delivery for a customer        | ensure all customers have their food delivered on time                                                         |
| `*`      | expert user   | set delivery status for a customer                   | keep track of deliveries that have been made and cancelled                                                     |
| `*`      | expert user   | track customers' subscription payment                | know when I received their payments                                                                            |
| `*`      | expert user   | mass copy emails and contact numbers to clipboard    | mass email and message customer about upcoming promotions                                                      |
| `*`      | expert user   | view free time slots                                 | schedule new deliveries for new customers                                                                      |
| `*`      | expert user   | track the total revenue from a customer              | know how much I have earned from a customer                                                                    |
| `*`      | expert user   | track number of days subscribed by a customer so far | know who are my loyal customers                                                                                |
| `*`      | expert user   | back up customer and route data                      | ensure that delivery operations are not disrupted by data loss                                                 |
| `*`      | expert user   | archive customers data                               | see only the relevant data for currently subscribed customers                                                  |
<!-- @@author -->



### Use cases

(For all use cases below, the **System** is the `ServeMate` and the **Actor** is the `user`, unless specified otherwise)

<br>

**Use case 1: Add a customer**

**MSS**

1. User requests to add a customer contact with required fields.
2. ServeMate adds the customer into the customer list.
3. ServeMate shows a success message with the added customer’s details.

    Use case ends.

**Extensions**

* 1a. ServeMate detects that a required field is missing.

    * 1a1. ServeMate shows an error message describing the correct command format.

      Use case resumes at step 1.

* 1b. ServeMate detects an invalid field value.

    * 1b1. ServeMate shows an error message describing the violated constraint.

      Use case resumes at step 1.

* 1c. ServeMate detects that a customer with the same name already exists.

    * 1c1. ServeMate shows an error message indicating that the customer already exists.

      Use case resumes at step 1.

<br>

**Use case 2: Delete a customer**

**MSS**

1. User requests to list customers.
2. ServeMate shows a list of customers.
3. User requests to delete a customer in the list.
4. ServeMate deletes the customer.
5. ServeMate shows a success message with the deleted customer’s details.

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

* 3a. ServeMate detects that the given index is not a positive integer.

    * 3a1. ServeMate shows an error message describing the correct command format.

      Use case resumes at step 3.

* 3b. ServeMate detects that the given index is out of range.

    * 3b1. ServeMate shows an error message indicating that the provided index is invalid.

      Use case resumes at step 3.

<br>

**Use case 3: Edit customer record**

**MSS**

1. User requests to list customers.
2. ServeMate shows a list of customers.
3. User requests to edit a customer in the list.
4. ServeMate updates the customer record.
5. ServeMate shows a success message with the updated customer’s details.

   Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

* 3a. ServeMate detects that the given index is not a positive integer.

    * 3a1. ServeMate shows an error message describing the correct command format.

      Use case resumes at step 3.

* 3b. ServeMate detects that the given index is out of range.

    * 3b1. ServeMate shows an error message indicating that the provided index is invalid.

      Use case resumes at step 3.

* 3c. ServeMate detects that no fields are specified for editing.

    * 3c1. ServeMate shows an error message indicating that at least one field must be provided for editing.

      Use case resumes at step 3.

* 3d. ServeMate detects that an invalid field is provided.

    * 3d1. ServeMate shows an error message describing the violated constraint.

      Use case resumes at step 3.

* 3e. ServeMate detects that editing the name causes a duplicate with an existing customer.

    * 3e1. ServeMate shows an error message indicating that the customer already exists.

      Use case resumes at step 3.

<br>

**Use case 4: Filter customers by attributes**

**MSS**
1. User requests to list customers.
2. ServeMate displays a list of customers.
3. User requests to filter customers by attributes and provides one or more filters, each containing one or more keywords.
4. ServeMate displays the list of customers that match the given filter(s).

   Use case ends.

**Extensions**

* 3a. ServeMate detects an error in the command format.

    * 3a1. ServeMate displays an error message describing the correct command format.

      Use case resumes at step 3.

* 3b. ServeMate detects a violation of keyword format constraints in a filter.

    * 3b1. ServeMate displays an error message describing the violated constraint.

      Use case resumes at step 3.

* 3c. No customers match the specified filters.

    * 3c1. ServeMate displays an empty result list.

      Use case ends.

<br>

**Use case 5: View all upcoming deliveries for the day**

**MSS**

1. User launches application to view all upcoming deliveries for the day.
2. ServeMate displays a list of all upcoming deliveries for the day, ordered from earliest to latest delivery time.

   Use case ends.

**Extensions**

* 1a. ServeMate detects there are no deliveries scheduled for the day.

    * 1a1. ServeMate displays there are no upcoming deliveries for the day.

      Use case ends.

<br>

**Use case 6: Add upcoming delivery for a customer**

**MSS**

1. User requests to list customers.
2. ServeMate shows a list of customers.
3. User requests to add a new upcoming delivery for a customer with required fields.
4. ServeMate adds the upcoming delivery to the customer's details.
5. ServeMate shows a success message with the added upcoming delivery's details.

   Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

* 3a. ServeMate detects that the given index is not a positive integer.

    * 3a1. ServeMate shows an error message describing the correct command format.

      Use case resumes at step 3.

* 3b. ServeMate detects that the given index is out of range.

    * 3b1. ServeMate shows an error message indicating that the provided index is invalid.

      Use case resumes at step 3.

* 3c. ServeMate detects that there is a required field is missing.

    * 3c1. ServeMate shows an error message describing the correct command format.

      Use case resumes from step 3.

* 3d. ServeMate detects that a parameter value is invalid.

    * 3d1. ServeMate shows an error message describing the violated constraint.

      Use case resumes from step 3.

* 3e. ServeMate detects that a delivery to the same customer already exists.

    * 3e1. ServeMate shows an error message describing that the customer already has a delivery scheduled.

      Use case resumes from step 3.

<br>

**Use case 7: Edit delivery details belonging to a customer**

**MSS**

1. User requests to list customers.
2. ServeMate shows a list of customers.
3. User requests to edit the delivery details of a customer in the list.
4. ServeMate updates the customer's delivery details.
5. ServeMate shows a success message with the updated customer’s delivery details.

   Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case ends.

* 3a. The given index is not a positive integer.

    * 3a1. ServeMate shows an error message describing the correct command format.

      Use case resumes at step 3.

* 3b. The given index is out of range.

    * 3b1. ServeMate shows an error message indicating that the provided index is invalid.

      Use case resumes at step 3.

* 3c. No fields are specified for editing.

    * 3c1. ServeMate shows an error message indicating that at least one field must be provided for editing.

      Use case resumes at step 3.

* 3d. Any provided field value is invalid.

    * 3d1. ServeMate shows an error message describing the violated constraint.

      Use case resumes at step 3.

<br>

**Use case 8: Delete a delivery associated with a customer**

**MSS**

1. User requests to list customers.
2. ServeMate shows a list of customers.
3. User requests to delete a customer's delivery.
4. ServeMate deletes the delivery associated with the specified customer.
5. ServeMate shows a success message that includes the customer's name and details of the deleted delivery.
   Use case ends.

**Extensions**

* 1a. The list of upcoming deliveries is empty.

  Use case ends.

* 3a. ServeMate detects that the given index is not a positive integer.

  * 3a1. ServeMate shows an error message describing the correct command format.

    Use case resumes at step 3.

* 3b. ServeMate detects that the given index is out of range.

  * 3b1. ServeMate shows an error message describing that the index provided is invalid.

    Use case resumes at step 3.

* 3c. ServeMate detects that the customer at the given index does not have a delivery.

  * 3c1. ServeMate shows an error message describing that the specified customer does not have an existing delivery.

    Use case ends.

<br>

**Use case 9: Tag customer with delivery note**

**MSS**

1. User requests to list customers.
2. ServeMate shows a list of customers.
3. User requests to tag a customer in the list with a delivery note.
4. ServeMate tags the customer in the customer record with the delivery note.
5. ServeMate shows a success message with the updated customer's details including the delivery note.

**Extensions**

* 1a. The list of customers is empty.

  Use case ends.

* 3a. ServeMate detects that the given index is not a positive integer.

    * 3a1. ServeMate shows an error message describing the correct command format.

      Use case resumes at step 3.

* 3b. ServeMate detects that the given index is out of range.

    * 3b1. ServeMate shows an error message describing that the index value given is invalid.


* 3c. ServeMate detects that the given delivery note is empty.

    * 3c1. ServeMate shows an error message describing that the delivery note is missing.
  
      Use case resumes at step 3.

<br>

**Use case 10: Find expired deliveries**

**MSS**

1. User requests to find all customers whose deliveries have ended before a specific date.
2. ServeMate displays the list of all matching customers.

   Use case ends.

**Extensions**

* 1a. ServeMate detects an error in the command format.

    * 1a1. ServeMate displays an error message describing the correct command format.

      Use case resumes at step 1.

* 1b. ServeMate detects that the provided date is invalid. 

    * 1b1. ServeMate displays an error message describing that the date given is invalid.

      Use case resumes at step 1.

* 1c. No customers have deliveries which end before the specified date.

    * 1c1. ServeMate displays an empty result list.

      Use case ends.

<br>

### Non-Functional Requirements

#### ⚙️ Technical
1. The application should be implemented primarily using the Object-Oriented Paradigm (OOP), where core logic are encapsulated within classes. A small mix of other styles (e.g. lambda expressions from the Functional Programming Paradigm) are allowed if justifiable (e.g. if it improves conciseness or readability within a method).

#### 🚧 Operational Constraints
1. It should be operated as a **single-user** application. It should not support shared usage nor share access to data amongst multiple users.
2. The application should not depend on its own remote server.

#### 💻 Portability
1. The application should support any _mainstream OS_ with Java `17` or higher.
2. The application should be packaged into a single JAR file or a single zip file, with a maximum size of 100MB.
3. The application should function as a standalone product that does not require additional user installations.

#### ⌨️ Usability
1. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
2. The application's GUI should not cause any resolution-related inconvenience to the user for standard screen resolutions 1920x1080 and higher, and for screen scales 100% and 125%.
3. The application's GUI should be usable (i.e. allow all application functions to be usable, even if the user experience is not optimal) for resolutions 1280x720 and higher, and for screen scale 150%.

#### 🚀 Performance
1. The application should remain responsive even when managing 1000 customer records.

#### 💾 Data Persistence
1. The application should store data locally in a human editable text file (e.g. JSON format). It should not use a Database Management System (DBMS) to store data.

#### 📖 Documentation
1. The Developer Guide and User Guide should be PDF-friendly (e.g. no expandable panels, embedded videos, animated GIFs, etc.).
2. The Developer Guide and User Guide should have a maximum size of 15MB each, when downloaded as PDF files.

<br>

### Glossary

* **Customer**: A person who subscribes to the Tingkat delivery service.
* **Delivery Rider/Driver**: A person who delivers meals to customers.
* **Tingkat Administrative Staff**: A person who manages the Tingkat delivery service.
* **Delivery Route**: A sequence of stops planned for delivering meals to customers.
* **Mainstream OS**: Windows, Linux, MacOS
* **Tingkat Delivery**: Subscription-based home-cooked meal delivery service commonly found in Singapore.
* **Tingkat Package**: The food catering package, ordered from a predefined start date to an end date.
* **Command Line Interface (CLI)**: A text-based user interface used to interact with software by typing commands.
* **Graphical User Interface (GUI)**: A visual interface that allows users to interact with the application through graphical elements like buttons, windows, and icons.
* **Subscription**: A predefined plan for meal delivery within a period from start date to end date.
* **Command**: A user input that triggers a specific action in the application (e.g., `add`, `delete`, `list`).
* **JavaScript Object Notation (JSON)**: A file format for storing and transmitting data as human-readable text.
* **Java Archive (JAR)**: A file format that can be used to compress and bundle multiple files associated with a Java application into a single file for ease of distribution and execution.

--------------------------------------------------------------------------------------------------------------------

## Appendix: Instructions for manual testing

Given below are instructions to test the app manually.

<box type="info" light>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a customer

1. Deleting a customer while all customers are being shown

   1. Prerequisites: List all customers using the `list` command. Multiple customers in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No customer is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_

--------------------------------------------------------------------------------------------------------------------

## Appendix: Planned enhancements
Team size: 5
1. **Refresh today's deliveries panel:** The current delivery panel does not update automatically when a new day starts. Users must restart the application to view the current day's deliveries. We plan to add a command (e.g. `today`) to refresh the delivery panel so that it reflects the new current date.
2. **Combine existing find commands:** The current separation of the `find-delivery` and `find` commands does not allow filtering by both delivery information (e.g. date) and customer information (e.g. address). We plan to combine both commands into a single `find` command, so that filtering by both delivery information and customer information is possible (e.g. `find n/John dt/2026-04-01`).
3. **Allow special characters for customer's name**: The current name field for a customer does not allow names containing special characters, which may be in the customer's legal name. We plan to allow special characters in names (e.g. `s/o`).
4. **Allow alphabets, special characters and spaces for customer's phone number**: The current phone number field for a customer does not allow phone numbers containing alphabets, special characters and spaces. We plan to allow entering phone numbers with alphabets, special characters and spaces (e.g. `+65 9876 5432 (HP) 6560-6060 (Office)` when a customer has multiple phone numbers).
5. **Handle tags longer than 45 characters:** Currently, tags are restricted to 45 characters. We plan to allow tags with more than 45 characters, but the 46th character onwards will be clipped. The user can view the full tag by clicking on the clipped tag, which will show the full tag in a tooltip or a pop-up window.

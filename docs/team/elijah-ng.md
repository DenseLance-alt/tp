---
  layout: default.md
  title: "Elijah Ng Ding Jie's Project Portfolio Page"
---

### Project: ServeMate

ServeMate is a desktop application created for **administrative staff** of tingkat catering businesses to manage their customer contacts and schedule deliveries.

Given below are my contributions to the project.

* **Code contributed**: [RepoSense Dashboard](https://nus-cs2103-ay2526-s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=elijah-ng&tabRepo=AY2526S2-CS2103T-W14-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Enhancements implemented**:
  * **Enhanced `find` command**
    * What it does: Add the ability to find customers whose attributes (name, address, tag) match at least 1 of the keywords given in each filter (n/, a/, t/) specified.
    * Justification: Find customers use flexible multi-attribute filtering, enabling users to search for a customer matching at least 1 keyword, across all filters. Usage of filters is highly configurable and intuitive: At least 1 filter with a keyword must be specified. Any combination of the filters are allowed. Within each filter, multiple keywords can be provided, performing "OR" match.
    * Highlights: The ability to specify multiple filters, where each filter will provide more fine-grained searching. At the same time, allowing users to specify multiple keywords per filter to enhance usability. This combination of both "AND" and "OR" matching was a major design decision after considerable deliberation on what would be most useful for users. Significant time was spent on writing automated tests to ensure the `find` logic worked correctly.

  * **Today's deliveries panel**
    * What it does: Add the ability to view deliveries scheduled for the current day, sorted from earliest to latest delivery time, to meet the operational needs of the target users.
    * Justification: Key functionality for users to be able to quickly view deliveries scheduled for the current day, to aid in operational planning of deliveries. Acts as one of the unique selling points of ServeMate, for enhanced visibility into daily operations.
    * Highlights: The current date and number of deliveries is shown on the delivery panel. Information for each delivery on the delivery panel was specifically chosen to be the most important and relevant as a quick reference. The delivery panel is made adjustable together with the customer panel to enhance usability. A design decision to add a horizontal scrollbar, instead of a simple text wrap, was made to facilitate quick scanning. Significant time was spent on manual GUI testing on different screen resolutions and scales, to ensure usability when the user chooses to resize the ServeMate application window or the customer and delivery panels.

* **Documentation**:
  * **Contributions to User Guide:**
    * Add description for dashboard layout and update UI screenshot with dual panel dashboard.
    * Update screenshot for `help` command.
    * Update example in `delete` command description.
    * Update description, examples and screenshots for the enhanced `find` command.
    * Add relevant frequently asked questions (FAQs) and update AB3's FAQ for transferring data.
    * Update warning description for known issue related to `preferences.json`.
    * Add relevant coming soon features.

  * **Contributions to Developer Guide:**
    * Update `UiClassDiagram.puml` and relevant descriptions for the Ui component section.
    * Add implementation description for the today's deliveries panel section and created `TodayDeliveryPanelSequenceDiagram.puml`. Including objective, implementation details, execution flow and design considerations.
    * Add line breaks for implementation section and use cases section for ease of reading by developers.
    * Update user stories and use cases relating to `find` command and the delivery panel. Help to correct spelling mistakes in user stories.
    * Add relevant non-functional requirements based on project constraints.
    * Add relevant glossary terms.
    * Add planned enhancement section with relevant planned enhancements.

* **Contribution to team-based tasks**:
  * Assist in verifying and setting up Codecov for GitHub repository.
  * Help to draft release notes and publish GitHub release for MVP v1.3. Contribute to release notes for new features in v1.4 and v1.5. Add relevant screenshots for the enhanced `find` command, today's deliveries panel and GIF for customer and delivery panel adjustment.
  * Plan agenda for some weekly team meetings, update action items and next steps after the meeting. For some meetings, helped to facilitate and record down subsequent meeting arrangements.
  * Review tP requirements before meetings to help keep the team aligned of expected tasks to be completed in the following week and deliverables to be submitted (e.g. format, deadline and location to submit).

* **Review/mentoring contributions:**: [Link to PRs Reviewed](https://github.com/AY2526S2-CS2103T-W14-2/tp/pulls?q=is%3Apr+is%3Aclosed+reviewed-by%3A%40elijah-ng)
  * Reviewed PRs, identify areas which could be improved and provide suggestions. Where appropriate, provide links to relevant excerpt in the textbook or course recommendations for ease of reference.
  * During team meetings, discussed on issues and solutions affecting the codebase across multiple features (e.g. law/principle violations).

**Contributions beyond the project team:**
  * Contribute to the forum through questions, reporting bugs and responding to peer posts.
  * Help to smoke tests on peers' iPs shared on the forum.

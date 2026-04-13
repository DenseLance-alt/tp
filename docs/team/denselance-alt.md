---
  layout: default.md
  title: "Lance's Project Portfolio Page"
---

### Project: ServeMate

ServeMate is a desktop application created for **administrative staff** of tingkat catering businesses to manage their customer contacts and schedule deliveries.

Given below are my contributions to the project.

* **New Feature**: `unschedule` command ([\#80](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/80)).
    * What it does: Allow the user to unschedule deliveries tied to a particular customer.
    * Justification: This feature is important as it allows Tingkat administrators to help customers cancel their Tingkat subscription plans, while keeping their records intact.
    * Highlights: This was the first command implemented that explicitly interacts with a customer's delivery. As such, I had to ensure that future commands interact correctly with the `Delivery` and `Person` classes.

* **New Feature**: `expired` command ([\#163](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/163), [\#254](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/254))
    * What it does: Allows administrative staff to track subscriptions that have ended.
    * Justification: This feature is important as it allows Tingkat administrators to follow up with customers and ask them to renew their Tingkat plans.
    * Original Design: `PersonHasExpiredDeliveryPredicate.test(person)` $\to$ `person.getDelivery()` + `DeliveryHasExpiredPredicate.test(delivery)` $\to$ `delivery.hasExpired(beforeDate)`.
      * The original design was implemented when `Person` did not have the `getDeliveryAttribute` methods.
      * This results in high coupling, since `PersonHasExpiredDeliveryPredicate` needs to know the internal structure of `Person`.
    * New Design: `PersonHasExpiredDeliveryPredicate.test(person)` $\to$ `person.hasExpiredDelivery(beforeDate)` $\to$ `delivery.hasExpired(beforeDate)`.
      * Simple design, which reduced my functional and test code by a total of approximately 200 LoC.

* **New Feature**: Update UI to include delivery information for each person ([\#110](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/110)).
  * What it does: Allows administrative staff to view delivery information for each person on the customer panel.
  * Justification: This feature is important so that users know that they have scheduled the correct delivery details for a customer.

* **Enhancements to Existing Features**:
  * Erase all traces of AB3 and replace them with ServeMate ([\#13](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/13), [\#69](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/69), [\#94](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/94)).
  * Add sample delivery data ([\#80](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/80), [\#147](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/147), [\#168](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/168), [\#241](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/241)).

* **Contributions to Documentation**:
  * Update `index.md` ([blame](https://github.com/AY2526S2-CS2103T-W14-2/tp/blame/master/docs/index.md)) and `README.md` ([blame](https://github.com/AY2526S2-CS2103T-W14-2/tp/blame/master/README.md)).
  * User Guide:
    * Create introduction ([\#69](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/69), [\#280](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/280)).
    * Design an interesting storyline and provide an easy-to-follow demo for the step-by-step tutorial ([\#280](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/280)).
    * Craft an easy-to-understand quick start section for less technical users ([\#280](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/280)).
    * Add documentation for `unschedule` and `unschedule` command ([\#80](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/80), [\#168](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/168)).
    * Maintain and update command summary table ([\#168](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/168)).
  * Developer Guide:
    * Add UML sequence diagrams, execution flow and design considerations for `unschedule` and `expired` commands in the Implementation section ([\#152](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/152), [\#262](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/262)).
      * This is referenced and used as a template by the rest of the team.
    * Add user stories and use cases ([\#152](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/152), [\#247](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/247)).
    * Add glossary and NFRs ([\#45](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/45), [\#56](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/56), [\#61](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/61)).

* **Other Contributions to Project**:
  * Update site-wide settings ([\#2](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/2), [\#38](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/38), [\#134](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/134), [\#168](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/168)).
  * Enable assertions ([\#138](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/138)).
  * Create logo for ServeMate ([\#134](https://github.com/AY2526S2-CS2103T-W14-2/tp/pull/134)).
  * Write additional tests for existing and new features to increase test coverage.
  * Submit release [Alpha Version v1.4](https://github.com/AY2526S2-CS2103T-W14-2/tp/releases/tag/v1.4), with contributions from other team members.

* **Issues Submitted**: [GitHub Submitted Issues](https://github.com/AY2526S2-CS2103T-W14-2/tp/issues?q=is%3Aissue%20author%3A%40denselance-alt)

* **Issues Commented**: [GitHub Commented Issues](https://github.com/AY2526S2-CS2103T-W14-2/tp/issues?q=is%3Aissue%20commenter%3A%40denselance-alt)

* **PRs Submitted**: [GitHub Submitted PRs](https://github.com/AY2526S2-CS2103T-W14-2/tp/pulls?q=author%3ADenseLance-alt)

* **PRs Reviewed**: [GitHub Reviewed PRs](https://github.com/AY2526S2-CS2103T-W14-2/tp/pulls?q=reviewed-by%3ADenseLance-alt)
  * I have provided comments for approximately half of the team's PRs.

* **Code Contributed**: [RepoSense Link](https://nus-cs2103-ay2526-s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=DenseLance-alt&tabRepo=AY2526S2-CS2103T-W14-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&chartGroupIndex=85&chartIndex=3&authorshipIsIgnoredFilesChecked=false)

* **Forum Contributions**: [GitHub Forum Link](https://github.com/NUS-CS2103-AY2526-S2/forum/issues?q=commenter%3A%40DenseLance-alt)

* **Administrative Work**:
  * Set up and maintain project's Google Drive and its documents.
  * Kickstart and add tags for bug triaging after PE-D.
  * Organise team meetings. Describe objectives and initial plans for some team meetings.
  * Track and remind team of deadlines. Close milestones once complete. Incorporate explicit buffers into milestones to absorb unforeseen delays.
  * Help to submit team tasks on Canvas and TEAMMATES.
  * Identify major and subtle bugs, and propose suggestions to fix them in PR reviews and team group chats.
  * Point out issues with current design decisions and suggest ways to improve them.

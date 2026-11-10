# CS2114Group32 Group 32 – Got Next

A console app that organizes pickup basketball games at a gym. Teams sign up, see who is waiting, and play in the order they registered. After each game, both teams leave the court and can sign up again at the back of the line.

## Requirements

- Java Development Kit (JDK) 8 or newer
- To check that it's installed, run `java -version` and `javac -version`

## Files

Model (state and logic, no input/output)

- `LinkedQueue.java` - Generic FIFO queue with head and tail references (`Node` is a private class inside it)
- `Team.java` - One team's name and its five player names
- `Match.java` - The two teams currently playing on the court
- `Result.java` - The outcome of one action: success flag and message
- `CourtManager.java` - Got Next rules: waiting queue, current match, registration, start, finish

View

`ConsoleView.java` - Prints the menu, waiting teams, current match, and messages; reads raw text

Controller

`GotNextController.java` - Menu loop and menu-input checking; calls `CourtManager` and passes results to `ConsoleView`
`Main.java` - Program entry point; creates the model, view, and controller

Tests

`LinkedQueueTest.java`, `TeamTest.java`, `MatchTest.java`, `ResultTest.java`, `CourtManagerTest.java`

## Compiling

Open a terminal in the `project3` folder and run:

```
javac Main.java
```

This also compiles `CourtManager`, `LinkedQueue`, and the other classes that `Main` uses.

## Running the thin view

From the same folder:

```
java Main
```

## Using the program

Type the number of a menu option and press Enter.

1 - Register team (team name, then five player names)
2 - Start match (the first two waiting teams go on the court)
3 - Finish match (both teams leave the court; they can register again)
4 - View status (shows the waiting line and the current match)
5 - Quit

## Running the model without the view

The model is tested with JUnit only. The tests create `CourtManager`, `LinkedQueue`, `Team`, `Match`, and `Result` directly and never create `ConsoleView` or `GotNextController`.

The tests extend `student.TestCase`, so the course test library and JUnit must be on the classpath. Replace the jar paths below with the ones on your machine:

```
javac -cp ".:path/to/student.jar:path/to/junit.jar" *.java
java  -cp ".:path/to/student.jar:path/to/junit.jar" org.junit.runner.JUnitCore LinkedQueueTest CourtManagerTest
```

On Windows, use `;` instead of `:` to separate classpath entries.

If you use an IDE (Eclipse, IntelliJ, VS Code), add the same jars to the project's build path and run test files as a JUnit test.

## Reflection

What changed from your specification? Classes, method signatures, where validation lives, test cases — what did the spec say, what did you build instead, and why?

- **Classes and model signatures:** All eight spec classes were built, and every public model method matches the spec.
- **`TEAM_SIZE`:** Moved from `CourtManager` to `Team`, since the `Team` constructor is what checks roster size. `TEAMS_PER_MATCH` stayed in `CourtManager`.
- **View methods:** The spec didn't list them. We built `showMenu`, `readLine`, `showResult`, `showState`, and `showMessage`. `showMessage` handles invalid menu choices, which the controller rejects before the model is called, so there's no `Result` to show.
- **Scanner:** `ConsoleView` takes its `Scanner` in the constructor, so `Main` passes `System.in` and a test could pass scripted input.
- **Menu:** "View waiting list" became "View status", which shows the line and the court together.
- **Validation:** Matches the spec. The controller checks menu syntax, constructors reject bad data, `CourtManager` converts those errors into failed `Result`s and enforces game rules, and `LinkedQueue` rejects `null` and empty removals.
- **Tests:** These follow the plan, with related cases grouped into one method (e.g. `testStartMatch`). We added `assertRejectedRegistration`, which checks that state is unchanged after a bad registration. The planned "change the original name string" case wasn't testable because Strings are immutable, so we tested trimming and capitalization instead.

Did the model really stand alone? Was there anything you had to move across the model/view line to make it run, or anything in the model that still knows about the view?

- **Yes.** Model classes only import `java.util` and never print, read input, or reference the view or controller. Every test builds model objects directly, with no view.
- **Nothing had to move across the line.** The view reads `Team`, `Match`, and `Result` through getters, as planned, and that dependency only goes one way.
- **One leak:** The controller hard-codes the roster size (five player prompts) because `Team.TEAM_SIZE` is private. Changing the roster size would mean editing both classes.

What happened to your unknowns? Of the things you listed in your scope as not knowing how to do, which did you figure out, and which are still open?

- **Inspecting the queue without changing it:** Solved with `toList()`, which returns a copy in FIFO order. The display and the duplicate check both use it.
- **Console-to-model communication:** Solved. The controller calls `CourtManager`, gets a `Result`, and passes it and the current state to the view.
- **Testing that rejections change nothing:** Solved. Since `Team`, `Match`, and `Result` are immutable, tests compare `getWaitingTeams()` and `getCurrentMatch()` before and after each rejected action.
- **Still open:** None.

What doesn't work yet? MVP features, bad-input cases, or tests that are missing or failing.

- **MVP features and bad input:** All MVP features work, every bad-input case from our scope is rejected without changing state, and all JUnit tests pass.
- **Untested controller and view:** There are no automated tests for `GotNextController` or `ConsoleView`. Menu parsing and end-of-input were only checked by hand.
- **Late registration errors:** The console asks for all five players before validating, so a blank or duplicate team name is only reported after the whole roster is typed.
- **Player on two teams:** A player can still be on two teams at once. Cross-team checking is still a stretch goal.

## Next Steps

## Next Steps

What is left to finish the MVP? The remaining features and fixes, in the order you will do them.

- All MVP features from the scope work, so only documentation is left:
  1. **Using the program:** Replace the TODO with the five menu options (Register team, Start match, Finish match, View status, Quit) and what each one does.
  2. **`@author` tags:** Add the missing tags to `ConsoleView`, `GotNextController`, `Main`, and `CourtManagerTest`.
  3. **Attribution:** Fill in the empty `Main` row and the blank Contribution cells, confirm each file is credited to the right person, and complete the GenAI note.

Your change feature. Name it, then predict which classes (and methods) it will touch and which ones your design says should not need to change.

- **Winner stays on:** After a match, the winning team can choose to stay on the court, and only the next waiting team comes on to play them (scope stretch goal).
- **Will change:**
  - `CourtManager`: `finishMatch()` needs to know which team won and whether it stays. A new field will hold the staying winner between matches, which adds a fourth piece of changing state to the three listed in the spec's design note. `startMatch()` will pair the winner with one dequeued team instead of two, so its "at least two waiting" check depends on whether a winner is staying. The private `isTaken` helper must also check the staying winner so its name can't be re-registered.
  - `GotNextController`: The finish-match handler will ask which team won and whether it stays, and reject invalid answers at the menu-syntax level, the same way it handles menu choices now.
  - `ConsoleView`: `showState` will display a staying winner, and the view will print the new prompts.
- **Should not change:** `LinkedQueue` doesn't need to change, because the winner is held in `CourtManager` and never goes back in the queue. `Team`, `Match`, and `Result` are immutable and should be reusable as they are. A new match is still two teams, with the winner as the home team. `Main` only wires objects together.

Which stretch goals will you attempt? The ones from your scope you are committing to, and the ones you are dropping.

- **Committing to:** Winner stays on, which is also our change feature.
- **Dropping:** Leaving the line (including rejoining) and player conflict checking.

What new tests will you need? The methods and bad-input cases the added work must cover.

- Following the spec's test-first plan for the model, `CourtManagerTest` will cover:
  - **`finishMatch` with a winner who stays:** The winner is held and the losing team leaves. The next `startMatch()` dequeues only one team and puts the winner on as home team.
  - **`finishMatch` with a winner who leaves:** Both teams leave, same as the current behavior.
  - **Bad input:** A winner who isn't in the current match, or a null/blank winner, returns a failed `Result` with state unchanged. Finishing with no match still fails.
  - **`startMatch` with a winner held:** It fails with no teams waiting and succeeds with one. Both cases leave state unchanged on failure.
  - **`registerTeam`:** Registering the staying winner's name, in any capitalization, is rejected.
  - **Existing tests:** Update the current `finishMatch` and `startMatch` tests if their signatures change.
- **Controller:** Check invalid winner and stay/leave answers by hand, as with the menu now, and run the full program after each step, per the spec's integration plan.

Who does what? Which team member owns each remaining item.

- **Model:** Logan and Yabsira own the `CourtManager` changes and the new `CourtManagerTest` cases.
- **View:** Yabsira owns the `ConsoleView` changes for showing the staying winner and the new prompts, plus the "Using the program" section.
- **Controller:** Shalin owns the finish-match flow in `GotNextController`.
- **Whole team:** Each member adds their own `@author` tags and attribution rows. The whole team writes the GenAI note.

## Attribution

| File                | Team member(s) | Contribution (how)           |
| ------------------- | -------------- | ---------------------------- |
| LinkedQueue         | Logan          | GenAI drafted w/ supervision |
| Team                | Logan          | GenAI drafted w/ supervision |
| Match               | Logan          | GenAI drafted w/ supervision |
| Result              | Logan          | GenAI drafted w/ supervision |
| CourtManager        | Logan          | GenAI drafted w/ supervision |
| ConsoleView         | Yabsira        | GenAI drafted w/ supervision |
| GotNextController   | Yabsira        | GenAI drafted w/ supervision |
| Main                |                |                              |
| LinkedQueueTest     | Shalin         |                              |
| TeamTest            | Shalin         |                              |
| MatchTest           | Shalin         |                              |
| ResultTest          | Shalin         |                              |
| CourtManagerTest    | Yabsira        | GenAI drafted w/ supervision |
| README - Reflection | Jonathan       | GenAI drafted w/ supervision |
| README - Next Steps | Jonathan       | GenAI drafted w/ supervision |
| README              | _ALL_          | GenAI assisted               |

GenAI note: TODO

## Authors

Group 32

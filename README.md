# CS2114Group32 Group 32 – Got Next

A console app that organizes pickup basketball games at a gym. Teams sign up, see who is waiting, and play in the order they registered. After each game, both teams leave the court and can sign up again at the back of the line.

## Requirements

- Java Development Kit (JDK) 8 or newer
- To check that it's installed, run `java -version` and `javac -version`

## Files

Model (state and logic, no input/output)

`LinkedQueue.java` - Generic FIFO queue with head and tail references (`Node` is a private class inside it)
`Team.java` - One team's name and its five player names
`Match.java` - The two teams currently playing on the court
`Result.java` - The outcome of one action: success flag and message
`CourtManager.java` - Got Next rules: waiting queue, current match, registration, start, finish

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

TODO: list the menu options once GotNextController is done.

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

- TODO

Did the model really stand alone? Was there anything you had to move across the model/view line to make it run, or anything in the model that still knows about the view?

- TODO

What happened to your unknowns? Of the things you listed in your scope as not knowing how to do, which did you figure out, and which are still open?

- TODO

What doesn't work yet? MVP features, bad-input cases, or tests that are missing or failing.

- TODO

## Next Steps

What is left to finish the MVP? The remaining features and fixes, in the order you will do them.

- TODO

Your change feature. Name it, then predict which classes (and methods) it will touch and which ones your design says should not need to change.

- TODO

Which stretch goals will you attempt? The ones from your scope you are committing to, and the ones you are dropping.

- TODO

What new tests will you need? The methods and bad-input cases the added work must cover.

- TODO

Who does what? Which team member owns each remaining item.

- TODO

## Attribution

| File              | Team member(s) | Contribution (how) |
| ----------------- | -------------- | ------------------ |
| LinkedQueue       |                |                    |
| Team              |                |                    |
| Match             |                |                    |
| Result            |                |                    |
| CourtManager      |                |                    |
| ConsoleView       |                |                    |
| GotNextController |                |                    |
| Main              |                |                    |
| LinkedQueueTest   |                |                    |
| TeamTest          |                |                    |
| MatchTest         |                |                    |
| ResultTest        |                |                    |
| CourtManagerTest  |                |                    |
| README            |                |                    |

GenAI note: TODO

## Authors

Group 32

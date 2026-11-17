# CS2114Group32 Group 32 – Got Next

A console app that organizes pickup basketball games at a gym. Teams sign up, see who is waiting, and play in the order they registered. After each game, the winner can stay on the court to play the next waiting team, or both teams leave and can sign up again at the back of the line.

## Requirements

- Java Development Kit (JDK) 8 or newer
- To check that it's installed, run `java -version` and `javac -version`

## Files

Model (state and logic, no input/output)

- `LinkedQueue.java` - Generic FIFO queue with head and tail references (`Node` is a private class inside it)
- `Team.java` - One team's name and its five player names
- `Match.java` - The two teams currently playing on the court
- `Result.java` - The outcome of one action: success flag and message
- `CourtManager.java` - Got Next rules: waiting queue, current match, staying winner, registration, start, finish

View

`ConsoleView.java` - Prints the menu, waiting teams, current match, staying winner, and messages; reads raw text

Controller

`GotNextController.java` - Menu loop and input-syntax checking (menu numbers, y/n); calls `CourtManager` and passes results to `ConsoleView`
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
2 - Start match (the first two waiting teams play, or the staying winner plays the first waiting team)
3 - Finish match (type the winning team, then `y` if the winner stays or `n` if both teams leave)
4 - View status (shows the waiting line, the current match, and the staying winner)
5 - Quit

## Running the model without the view

The model is tested with JUnit only. The tests create `CourtManager`, `LinkedQueue`, `Team`, `Match`, and `Result` directly and never create `ConsoleView` or `GotNextController`.

The tests extend `student.TestCase`, so the course test library and JUnit must be on the classpath. Replace the jar paths below with the ones on your machine:

```
javac -cp ".:path/to/student.jar:path/to/junit.jar" *.java
java  -cp ".:path/to/student.jar:path/to/junit.jar" org.junit.runner.JUnitCore LinkedQueueTest TeamTest MatchTest ResultTest CourtManagerTest
```

On Windows, use `;` instead of `:` to separate classpath entries.

If you use an IDE (Eclipse, IntelliJ, VS Code), add the same jars to the project's build path and run test files as a JUnit test.

## Reflection

How did your design absorb the change? List the classes your feature actually touched. Compare that with the prediction in your D3 Next Steps: what matched, what surprised you?

- **Feature:** Winner stays on, the change feature we named in D3.
- **Classes touched:** `CourtManager`, `GotNextController`, `ConsoleView`, plus the tests in `CourtManagerTest` and one new test in `LinkedQueueTest`.
- **What matched:** This is the same list D3 predicted. `LinkedQueue`, `Team`, `Match`, `Result`, and `Main` did not change at all. The winner is held in a new `stayingTeam` field in `CourtManager` and never goes back into the queue, and a new match is still a plain `Match` with the winner as the home team. `isTaken()` needed one more check for the staying winner, as predicted.
- **What surprised us:**
  - We did not have to change any existing method signature. We added an overload, `finishMatch(String winnerName, boolean winnerStays)`, and kept the old `finishMatch()`, so the old tests still pass without edits.
  - The controller has to know whether a match is in progress before it asks "who won?". Otherwise the user would type a winner just to be told there is no match. It reads `getCurrentMatch()` and, if there is none, calls the old `finishMatch()` so the model still writes the error message.
  - The rule "a match and a staying winner never exist at the same time" made the code simpler. `startMatch()` clears `stayingTeam` as soon as the winner goes into the new match.

Did the change cross the model/view line? Did the view have to learn anything new about the model? If a change rippled through layers it shouldn't have, where and why?

- **It did not cross the line.** All winner rules are in `CourtManager`: the winner must be one of the two teams, the name is trimmed and case-insensitive, and the start needs one waiting team instead of two. A failed action changes nothing. The model still imports only `java.util`.
- **What the view learned:** `showState()` got a third parameter, the staying `Team` (or `null`), and prints "Winner staying: X". The controller gets it from the new `getStayingTeam()` and passes it in. The view still keeps no copy of model state.
- **What the controller learned:** It asks two new questions and checks only syntax. `y`/`yes`/`n`/`no` are accepted in any case, anything else is rejected before the model is called. Whether the winner name is valid is the model's job.
- **Ripple:** The `showState()` signature change touched both the view and the controller. This was expected, because the controller is the only caller.

What changed from your D3 plan? Features, signatures, validation, or tests that differ from what you said you'd do, and why.

- **Signatures:** D3 said we would update the existing `finishMatch` and `startMatch` tests if their signatures changed. We used an overload instead, so `startMatch()` kept its signature (only its behavior changed) and no old test needed edits.
- **New public methods:** `finishMatch(String, boolean)` and `getStayingTeam()`. D3 only described the field, but the view needs a getter to show the staying winner.
- **Messages:** The duplicate-name message changed from "is already waiting or playing" to "is already waiting or on the court", because a staying winner is on the court but not playing.
- **Tests:** Jonathan wrote the cases D3 listed: winner stays, winner leaves, bad winner names (null, blank, not in the match, no match), start with a staying winner and an empty or non-empty line, and registering the staying winner's name. He added the `assertRejectedFinish` helper and made `assertRejectedRegistration` also check the staying winner, so every rejection test checks that all three pieces of state are unchanged. Logan added one case D3 did not list: a staying winner who loses the next match does not stay, and the old `finishMatch()` with no match keeps the staying winner. `LinkedQueueTest` got an interleaved enqueue/dequeue order test.
- **Work split:** D3 gave the `CourtManager` tests to Logan and Yabsira and the controller to Shalin. In the end Logan wrote the model, controller, and view changes, and Jonathan wrote the new `CourtManager` tests.

What doesn't work yet? MVP features, bad-input cases, or tests that are missing or failing.

- **Everything in scope works.** All MVP features and the change feature work, every bad-input case from our scope and from the new feature is rejected without changing state, and all 42 JUnit tests pass.
- **A staying winner cannot leave early.** Once a winner stays, they are on the court until the next match starts. There is no menu option for "the winner leaves".
- **No limit on wins.** A team can stay on forever if it keeps winning.
- **Untested controller and view:** There are still no automated tests for `GotNextController` or `ConsoleView`. Menu, y/n parsing, and end-of-input were checked by hand.
- **Late errors:** The console asks for all five players before checking the team name, and asks y/n before checking the winner name, so some errors are only reported at the end.
- **Player on two teams:** A player can still be on two teams at once.

What design change would make the next feature cheaper?

- **A public roster size.** `Team.TEAM_SIZE` is private, so the controller hard-codes five player prompts. Making it public (or adding a getter) would mean a roster-size change touches only `Team`.
- **A `remove` method in `LinkedQueue`.** Our dropped stretch goal, leaving the line, needs to take a team out of the middle of the queue. Adding `remove(T item)` once to `LinkedQueue` would make that feature mostly a new `CourtManager` method and a menu option.

## Next Steps

What is left for the final build? Remaining features and fixes, in order.

1. **Staying winner can leave:** Add a `CourtManager` method that clears `stayingTeam` with a failed `Result` if no winner is staying, plus a menu option and tests.
2. **Earlier error messages:** Check the team name before asking for players, and the winner name before asking y/n. The model would expose a check (for example `isNameAvailable(String)`) so the rule stays in `CourtManager`.
3. **Public roster size:** Make `Team.TEAM_SIZE` public so the controller stops hard-coding five.
4. **Final README and demo practice.**

Which stretch goals are you committing to, and which are you dropping?

- **Done:** Winner stays on, which was also our change feature.
- **Committing to:** Letting a staying winner leave (item 1 above). It is small and finishes the feature.
- **Dropping:** Leaving the line (including rejoining) and player conflict checking. Both need bigger changes to `LinkedQueue` or `Team` than we have time for.

What will your 3-minute demo show? The path you'll walk through, including the new feature.

| Time      | What we show                                                                                                  |
| --------- | ------------------------------------------------------------------------------------------------------------- |
| 0:00–0:20 | Start the program, pick option 4 to show an empty line and court                                              |
| 0:20–1:00 | Register Lakers, Bulls, and Celtics, then try registering " lakers " again to show the duplicate rejection    |
| 1:00–1:20 | Start match: Lakers vs Bulls, Celtics still waiting                                                           |
| 1:20–1:50 | Finish match with bad input: a team not in the match, a blank name, and `maybe` for y/n; state does not change |
| 1:50–2:20 | Finish with "Bulls" and `y`; status shows "Winner staying: Bulls", then start Bulls vs Celtics                 |
| 2:20–2:40 | Finish with "Celtics" and `n`; court is empty, then try starting with no teams to show the failure            |
| 2:40–3:00 | Run the JUnit tests and show all 42 passing                                                                   |

Who does what? Which team member owns each remaining item.

- **Logan:** Item 1 (staying winner can leave) in `CourtManager` and its tests.
- **Shalin:** Item 2 (earlier error messages) in `GotNextController`, and the menu option for item 1.
- **Yabsira:** Item 3 (public roster size) and the controller/view by-hand test checklist.
- **Jonathan:** Final README pass and running the demo script.
- **Whole team:** Practice the demo once together before the final build is due.

## Attribution

| File                | Team member(s)  | Contribution (how)                                       |
| ------------------- | --------------- | -------------------------------------------------------- |
| LinkedQueue         | Logan           | GenAI drafted w/ supervision                             |
| Team                | Logan           | GenAI drafted w/ supervision                             |
| Match               | Logan           | GenAI drafted w/ supervision                             |
| Result              | Logan           | GenAI drafted w/ supervision                             |
| CourtManager        | Logan           | GenAI drafted w/ supervision; D4 feature GenAI-assisted  |
| ConsoleView         | Yabsira, Logan  | Yabsira wrote; Logan revised for D4 (GenAI-assisted)     |
| GotNextController   | Yabsira, Logan  | Yabsira wrote; Logan revised for D4 (GenAI-assisted)     |
| Main                | Logan           | GenAI drafted w/ supervision                             |
| LinkedQueueTest     | Shalin, Logan   | Shalin wrote; Logan added interleaved test for D4        |
| TeamTest            | Shalin          | GenAI drafted w/ supervision                             |
| MatchTest           | Shalin          | GenAI drafted w/ supervision                             |
| ResultTest          | Shalin          | GenAI drafted w/ supervision                             |
| CourtManagerTest    | Yabsira, Jonathan, Logan | Yabsira: original tests, GenAI drafted w/ supervision. Jonathan: winner-stays-on tests, GenAI drafted w/ supervision. Logan: one extra test |
| README - Reflection | Jonathan, Logan | GenAI drafted w/ supervision                             |
| README - Next Steps | Jonathan, Logan | GenAI drafted w/ supervision                             |
| README              | _ALL_           | GenAI assisted                                           |

GenAI note: used ChatGPT to build the project skeleton and write the five model classes from our Deliverable 2 method signatures, and later asked it to compare the code with our spec and explain some methods so I could understand them myself. I kept the code after checking it against the spec and running it with normal and bad input, and I also kept two changes it made on its own (moving `TEAM_SIZE` into `Team` and adding a private `isTaken()` helper) because they fit our design. It helped me write the code quickly in our old project's style, but it did not help with team decisions, and its design changes meant I had to track what changed for the Reflection.

## Authors

Group 32

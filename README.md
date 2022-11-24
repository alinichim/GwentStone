# GwentStone

This game is a combination between Gwent and HeartStone card games that have multiple
features and their own play-style.

## Implementation

The implementation follows a very basic paradigm. The game takes JSON objects as input
and parses them into it's structures, building the game environment. *InitGame* creates the
*BattleGround* and the *Players* with their decks. Each game is running through the *GameEngine*,
that will finally return the resulted JSON data. The *GameEngine* takes every action and
sends it to the *Controller*, which executes them and updates the JSON data with the
appropriate messages. At this stage, the *Controller* uses the classes in the **gamedev**
package that illustrate every object in the game.

### The BattleGround

The BattleGround contains the players, the table (with 4 rows) and some metadata about the
current match.

### The Players

Each player possesses the decks that they created at game initialization. The player HUD
is only important during a match, because it contains the data about the state of the player
like the cards in their deck, the cards in their hand, the mana left, the hero.

### The Cards

The cards are structured in such a way that every game character is perceived as a card.
What makes them different from each other is the abilities that they might have,
the ability to attack, the ability of being placed on the table or even their name.

## Conclusions

GwentStone is a very simple game, with the potential of being extended to a more complex
design, as it uses a very common type of I/O communication through JSON.

---

**Author: Alin Ichim**
**Group: 323CA**

*Copyright (C) 2022 Alin Ichim*

*All Rights Reserved*

*The package* **gamedev** *and the package* **main** *except Main.java and
Test.java are protected by copyright.*

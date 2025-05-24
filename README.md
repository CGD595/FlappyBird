# Flappy Bird - LibGDX (Java) Android Studio Project

## Overview

This is a Flappy Bird clone developed in **Java** using the **LibGDX framework** within **Android Studio**. It leverages multiple **design patterns** (State, Command, Singleton, Strategy, etc.) to maintain clean architecture, modular code, and scalable gameplay features.

---

## Project Structure

```
Flappy_bird_JAVA/
├── core/                # Core game logic and assets
│   └── com.flappy.game/
│       ├── FlappyDemo.java          # Game entry point
│       ├── GameFacade.java          # Handles rendering logic
│       ├── SoundManager.java        # Manages sound effects (Singleton)
│       ├── InputAdapter.java        # Command-based input handling
│       └── states/                  # Game state management
│           ├── GameStateManager.java
│           ├── State.java
│           ├── MenuState.java
│           ├── PlayState.java
│           ├── CreditState.java
│           ├── LeaderboardState.java
│           └── commands/            # JumpCommand, RestartCommand, etc.
│       └── sprites/                 # Game objects
│           ├── Bird.java
│           ├── Tube.java
│           ├── PowerUp.java
│           ├── BirdDecorator.java
│           └── decorators/         # SpeedBoostDecorator, etc.
├── desktop/             # Desktop-specific launcher for testing
│   └── DesktopLauncher.java
├── build.gradle         # Gradle build config
└── settings.gradle      # Gradle project settings
```

---

## Features

* **Multiple Game States**: Menu, Play, Credit, Leaderboard
* **Command Pattern** for input abstraction
* **Power-Ups**: Speed boost, invincibility, and score multiplier
* **Dynamic Obstacles** with tube recycling
* **Leaderboard** using SharedPreferences
* **State Transitions** managed with a custom `GameStateManager`
* **Smooth rendering** with a `GameFacade` managing SpriteBatch lifecycle

---

## Key Classes & Patterns

### Game Logic

* `FlappyDemo.java`: Initializes `GameStateManager`, pushes MenuState, manages global game setup.
* `GameFacade.java`: Contains the SpriteBatch and ensures a single begin-end block.
* `SoundManager.java`: Singleton for all sounds (flap, score, game over).

### States (State Pattern + Template Method)

* `MenuState.java`: Lets users start game or view leaderboard.
* `PlayState.java`: Core game logic, power-ups, scoring, input.
* `CreditState.java`: Shows final score and high score.
* `LeaderboardState.java`: Displays top scores.

### Commands (Command Pattern)

* `JumpCommand.java`: Makes bird jump.
* `RestartCommand.java`: Restarts the game.
* `ResetHighScoreCommand.java`: Clears saved high score.
* `SaveScoreCommand.java`: Updates saved high score and leaderboard.

### Entities

* `Bird.java`: Main character, supports animation and movement.
* `Tube.java`: Obstacles with repositioning logic.
* `PowerUp.java`: Pickups with different effects.

### Decorators (Decorator Pattern)

* `BirdDecorator.java`: Abstract class to wrap a Bird.
* `SpeedBoostDecorator`, `InvincibilityDecorator`, `ScoreMultiplierDecorator`: Add behavior dynamically.

---

## Assets

All assets go in `core/assets/`:

### Textures

* `bg.png`
* `ground.png`
* `playbtn.png`
* `bird.png`
* `birdanimation.png`
* `toptube.png`
* `bottomtube.png`
* `gameover.png`
* `powerup.png`

### Fonts

* `FlappyBirdRegular-9Pq0.ttf`

### Sounds

* `sfx_wing.ogg`
* `sfx_point.ogg`
* `sfx_hit.ogg`
* `sfx_powerup.ogg`
* `music.mp3`
* `phudosa.mp3`

Make sure all these assets are present in the `core/assets/` directory to avoid runtime errors.

---

## How to Build & Run (Android Studio)

1. Open Android Studio → `Open an Existing Project`
2. Select `Flappy_bird_JAVA`
3. Let Gradle sync and download dependencies
4. To test on Desktop:

   ```bash
   ./gradlew desktop:run
   ```

Make sure `Java 8+` is installed and your Android Studio project SDK is set.

---

## Important Notes

* Do **not** use `SpriteBatch.begin()` or `end()` in game states — it's handled in `GameFacade`.
* Use design patterns to keep components isolated and testable.

---

## Design Patterns Summary

| Pattern             | Used In                        |
| ------------------- | ------------------------------ |
| State               | GameStateManager + States      |
| Singleton           | SoundManager                   |
| Command             | Jump, Restart, SaveScore, etc. |
| Strategy            | Bird movement behaviors        |
| Decorator           | Bird power-ups                 |
| Template Method     | State base class               |
| Flyweight (Partial) | Shared tube textures           |

---


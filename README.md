# Flappy Bird - LibGDX (Java) Android Studio Project

## Overview

This is a Flappy Bird clone developed in **Java** using the **LibGDX framework** within **Android Studio**. It integrates multiple **object-oriented design patterns** to create a clean, extensible, and maintainable architecture. The project is structured into multiple states (menu, gameplay, credits, leaderboard) and supports power-ups, score tracking, and user input abstraction.

---

## Project Structure

```
Flappy_bird_JAVA/
├── core/                # Core game logic and assets
│   └── com.flappy.game/
│       ├── FlappyDemo.java
│       ├── GameFacade.java
│       ├── SoundManager.java
│       ├── InputAdapter.java
│       └── states/
│           ├── GameStateManager.java
│           ├── State.java
│           ├── MenuState.java
│           ├── PlayState.java
│           ├── CreditState.java
│           ├── LeaderboardState.java
│           └── commands/
│       └── sprites/
│           ├── Bird.java
│           ├── Tube.java
│           ├── PowerUp.java
│           ├── BirdDecorator.java
│           └── decorators/
├── desktop/
│   └── DesktopLauncher.java
├── build.gradle
└── settings.gradle
```

---

## Design Patterns Used

### 📋 Summary Table

| No.                                                                                                                | Design Pattern        | Applied In                              | Purpose                                              |
| ------------------------------------------------------------------------------------------------------------------ | --------------------- | --------------------------------------- | ---------------------------------------------------- |
| 1                                                                                                                  | State                 | GameStateManager, State subclasses      | Manage transitions between different game states     |
| 2                                                                                                                  | Template Method       | State.java                              | Define common game loop structure for all states     |
| 3                                                                                                                  | Singleton             | SoundManager.java                       | Single global instance for sound management          |
| 4                                                                                                                  | Command               | JumpCommand, RestartCommand, etc.       | Decouple input handling from game actions            |
| 5                                                                                                                  | Strategy              | Bird movement logic                     | Switchable behavior for bird (normal vs powered-up)  |
| 6                                                                                                                  | Decorator             | BirdDecorator + Power-up wrappers       | Dynamically enhance bird behavior with power-ups     |
| 7                                                                                                                  | Factory               | GameStateFactory.java                   | Centralized, flexible creation of game states        |
| 8                                                                                                                  | Observer (implicit)   | Score updates, game state notifications | Trigger reactions in UI and other components         |
| 9                                                                                                                  | Object Pool (partial) | Tube recycling logic                    | Efficient reuse of tubes to minimize object creation |
| 10                                                                                                                 | Flyweight (partial)   | Shared tube textures                    | Reduce memory use by reusing shared image data       |
| The project uses the following **10 design patterns**, each improving specific aspects of the game's architecture: |                       |                                         |                                                      |

### ✅ 1. **State Pattern**

* **Used in**: `GameStateManager` and `State` subclasses (`MenuState`, `PlayState`, `CreditState`, `LeaderboardState`)
* **Purpose**: Manages different game screens and their transitions.

### ✅ 2. **Template Method Pattern**

* **Used in**: `State.java` abstract class
* **Purpose**: Defines the game loop template (`handleInput`, `update`, `render`) that each state must implement.

### ✅ 3. **Singleton Pattern**

* **Used in**: `SoundManager.java`
* **Purpose**: Ensures only one instance of the sound manager controls all audio.

### ✅ 4. **Command Pattern**

* **Used in**: `JumpCommand`, `RestartCommand`, `SaveScoreCommand`, `ResetHighScoreCommand`
* **Purpose**: Decouples input handling from game logic.

### ✅ 5. **Strategy Pattern**

* **Used in**: Bird movement logic (strategy can vary for power-ups)
* **Purpose**: Enables interchangeable bird movement behaviors.

### ✅ 6. **Decorator Pattern**

* **Used in**: `BirdDecorator` and its subclasses (`SpeedBoostDecorator`, `InvincibilityDecorator`, `ScoreMultiplierDecorator`)
* **Purpose**: Dynamically adds power-up behaviors to the bird without modifying the base class.

### ✅ 7. **Factory Pattern**

* **Used in**: `GameStateFactory.java`
* **Purpose**: Creates game states without exposing instantiation logic.

### ✅ 8. **Observer Pattern** (manual/implicit)

* **Used in**: Score updating and UI notifications
* **Purpose**: Notifies different parts of the game when score or state changes.

### ✅ 9. **Object Pool Pattern** (recommended / partially implemented)

* **Used in**: Tube recycling logic
* **Purpose**: Reuses tube objects to reduce memory allocation.

### ✅ 10. **Flyweight Pattern** (partial)

* **Used in**: `Tube.java`
* **Purpose**: Shares the same texture instance across multiple tubes to save memory.

---

## Features

* **Modular game states**
* **Power-up system** using decorators
* **Score & leaderboard tracking**
* **Sound control** via singleton
* **Command-driven input abstraction**
* **Clean rendering lifecycle** via `GameFacade`
* **Flexible state creation** via factory pattern

---

## How to Build & Run (Android Studio)

1. Open Android Studio → `Open an Existing Project`
2. Select `Flappy_bird_JAVA`
3. Let Gradle sync and download dependencies
4. To run the desktop version:

   ```bash
   ./gradlew desktop:run
   ```

Ensure that Java 8+ is installed and configured in your IDE.

---

## Assets (core/assets/)

### Textures

* `bg.png`, `ground.png`, `bird.png`, `birdanimation.png`, `toptube.png`, `bottomtube.png`, `playbtn.png`, `powerup.png`, `gameover.png`

### Fonts

* `FlappyBirdRegular-9Pq0.ttf`

### Sounds

* `sfx_wing.ogg`, `sfx_point.ogg`, `sfx_hit.ogg`, `sfx_powerup.ogg`, `music.mp3`, `phudosa.mp3`

---

## Important Notes

* **Avoid using `SpriteBatch.begin()`/`end()`** in game states — use the centralized `GameFacade` instead.
* Design patterns help separate concerns and facilitate scaling new features.

---


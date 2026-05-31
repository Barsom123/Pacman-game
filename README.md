# Pacman Game (JavaFX & MySQL)

A 2D Pacman game built using JavaFX for the graphical interface and MySQL for storing user accounts and high scores. 

The project includes basic game mechanics such as movement on a grid-based map, enemy ghosts, scoring, and a login system for users. It was developed as a university project to practice object-oriented programming, JavaFX, and database integration.

---

## Key Features

- **User Authentication System:** Login and Registration pages connected to a MySQL database for user authentication.
- **Grid-Based Movement:** Pacman moves on a tile-based map and can change direction at intersections while staying aligned with the maze.
- **Pacman Animation:** Pacman's mouth animation changes while moving and stops when the player is not moving or blocked by a wall.
- **Score and High Score Tracking:** Players earn points by collecting food and bonus items. The highest score is saved and loaded from the database.
- **Game States:** The game displays Game Over and You Win screens when the player loses or completes the level.
- **Restart Functionality:** Players can restart the game by pressing the **R** key after winning or losing.

---

##  Prerequisites & Requirements

To compile and run this project, you will need:
1.  **Java Development Kit (JDK):** Version 21 or higher.
2.  **JavaFX SDK:** Version 21+ (matching your JDK environment).
3.  **MySQL Server:** Running locally or remotely to store user credentials.
4.  **MySQL Connector/J:** The JDBC driver jar file (e.g., `mysql-connector-j-9.7.0.jar`).

---

## Key File Structure 

```bash
├── Main.java              # Game loop orchestrator (AnimationTimer) & Input wrapper
├── Loginpage.java         # UI view for secure user entry 
├── Registerpage.java      # UI view for registering unique accounts
├── DatabaseHelper.java    # JDBC bridge executing database CRUD queries
├── GameMap.java           # Board tile layout generation and entity container
├── Pacman.java            # Player controls, collision triggers, & vector rendering
├── Character.java         # Shared foundational mobile entities logic
├── GameObject.java        # Core baseline entity template
├── Movable.java           # Procedural movement contract interface
├── Score.java             # Scoring logic wrapper mapping live states to database
├── Lives.java             # Tracks and modifies player life counts
├── Wall.java              # Solid grid obstacle components mapping tile layouts
├── Food.java              # Standard collectible dots yielding basic points
├── Cherry.java            # Special consumable that triggers scared states in ghosts
├── Assets.java            # Resource binder loading game visual elements
├── Ghost.java             # Base abstract archetype for enemy logic
├── RedGhost.java          # Aggressive tracking routines
├── PinkGhost.java         # Randomized wander behaviors
├── OrangeGhost.java       # Randomized wander behaviors
└── BlueGhost.java         # Randomized wander behaviors
```

## Controls

- **`UP` Arrow** / **`DOWN` Arrow** / **`LEFT` Arrow** / **`RIGHT` Arrow** — Seamless directional steering controls.
- **`R` Key** — Instant reload/restart command when viewing the game-over or win screen layout overlays.

##  Compilation & Execution

**Important Path Note:** The commands below use default paths pointing directly to the **`C:`** drive. You have two ways to run this:

- **Option 1 (Easiest):** Simply paste your downloaded `javafx-sdk-21.0.10` folder and your `mysql` connector folder directly inside your `C:\` drive so the paths match the commands perfectly.
- **Option 2:** If your JavaFX SDK or MySQL connector are located somewhere else (like your Downloads or Desktop folder), change the paths inside the quotation marks (`"C:\..."`) to match their exact location on your computer.

### 1. Compile the Project

Execute this from your root source folder. Ensure paths match your local file structure variables:

```bash
javac --module-path "C:\javafx-sdk-21.0.10\lib" --add-modules javafx.controls -cp ".;C:\mysql\mysql-connector-j-9.7.0\mysql-connector-j-9.7.0.jar"  *.java
```

### 2. Run the Game

Execute the compiled bytecodes passing structural module references along with classpaths pointing directly to your binary output folder (`out`):

```bash
 java --module-path "C:\javafx-sdk-21.0.10\lib" --add-modules javafx.controls -cp ".;out;C:\mysql\mysql-connector-j-9.7.0\mysql-connector-j-9.7.0.jar" Main
```

##  Database Setup

Ensure your local relational schema mirrors the initialization parameters expected by `DatabaseHelper`. Create a database named `pacman_game` with a `users` schema structured as follows:

```sql
-- Create Database
CREATE DATABASE IF NOT EXISTS pacman_game;

-- Use Database
USE pacman_game;

-- Create Users Table
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    high_score INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

```

>  **CRITICAL STEP:** Before launching the game, you must update the connection parameters inside your **`DatabaseHelper.java`** file to match your local MySQL configuration settings.

Open `DatabaseHelper.java` and modify the credential constants with your actual local MySQL `username` and `password`

```java
// Inside DatabaseHelper.java
private static final String URL = "jdbc:mysql://localhost:3306/pacman_game";
private static final String USERNAME = "your_local_mysql_username"; // e.g., "root"
private static final String PASSWORD = "your_local_mysql_password"; // Your MySQL password
```
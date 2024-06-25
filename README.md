# Tank Game

This project is an Tank and maze game implemented in Java. It includes various classes for managing game objects, events, and game logic.

## Project Structure

- **FindPos.java**: This class represents a position on the game map with x and y coordinates. It includes getter and setter methods for these coordinates.

- **GameManager.java**: This class manages the game objects. It provides methods to add and remove objects, as well as update and draw them.

- **GameObject.java**: This class serves as the base class for all objects in the game. It should be extended by specific game object classes like characters and bullets.

- **Bullet.java**: This class represents a bullet in the game. It extends `GameObject` and includes specific behaviors and properties of a bullet.

- **Character.java**: This class represents a character in the game. It extends `GameObject` and includes specific behaviors and properties of a character.

- **COMP603_Assesment_RPGgame.java**: The main class for running the RPG game. It initializes the game and manages the game loop.

- **DBManager.java**: This class handles database interactions, such as saving and loading game data.

- **DrawGameMap.java**: This class is responsible for drawing the game map on the screen.

- **KeyboardEventListener.java**: This class listens for keyboard events and handles player inputs.

- **MapPoint.java**: This class represents a point on the game map.

- **Player.java**: This class represents the player in the game. It extends `Character` and includes player-specific behaviors and properties.

- **Wall.java**: This class represents a wall in the game. It extends `GameObject` and includes properties and behaviors of a wall.

- **WinningCheck.java**: This class checks the conditions for winning the game.

- **GamePanel.java**: This class represents the panel where the game is rendered. It handles drawing the game objects and the game map.


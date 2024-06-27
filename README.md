# Klondike Card Game
This repository contains Java implementations for a simplified version of the Klondike card game, including models and textual views.

## Project Overview
The project is structured into several packages, each containing different components of the Klondike card game implementation.

### Models
This package contains the foundational components of the Klondike card game model.

#### Card Interface
The Card interface defines the basic behavior of a playing card. It includes methods to get the card's value, suit, and visibility state.



# Klondike Solitaire Game

This project implements different versions of the Klondike Solitaire game using Java. It includes the basic game, a version with limited redraws, and a version following specific rules known as Whitehead Klondike.

## Table of Contents
- [Project Structure](#project-structure)
- [Classes and Interfaces](#classes-and-interfaces)
- [Usage](#usage)
- [Examples](#examples)
- [Contributing](#contributing)
- [License](#license)

## Project Structure
The project is organized into the following packages:

1. `model.02` - Contains the basic model and card classes.
2. `model.04` - Contains the classes for different game variations.
3. `view` - Contains classes for rendering the game view.

## Classes and Interfaces

### `model.02`

#### `Card`
An interface that represents a playing card. Implementations of this interface must provide methods to:
- Render the card as a string.
- Change the visibility of the card.
- Get the visibility status of the card.
- Get the value of the card.
- Get the suit of the card.

#### `RegularCard`
An implementation of the `Card` interface representing a regular playing card with a suit and value.

#### `Suits`
An enumeration representing the four suits of a deck of cards: Clubs, Spades, Hearts, and Diamonds.

#### `KlondikeModel`
An interface representing the primary model for playing a game of Klondike Solitaire. It defines methods for:
- Getting a deck of cards.
- Starting a game.
- Moving cards between piles and to foundations.
- Managing draw cards.
- Checking the game state and score.

### `model.04`

#### `AbstractKlondike`
An abstract class that provides common functionality for various Klondike game implementations.

#### `LimitedDrawKlondike`
A class that extends `AbstractKlondike` and implements a version of Klondike with a limited number of redraws for the draw pile.

#### `WhiteheadKlondike`
A class that extends `AbstractKlondike` and implements a specific version of Klondike following Whitehead rules.

#### `KlondikeCreator`
A class with methods to create instances of different versions of the Klondike game.

### `view`

#### `TextualView`
An interface for rendering the game in different views.

#### `KlondikeTextualView`
A class that implements `TextualView` and provides a textual representation of the game state.

## Usage

To use this project, follow these steps:

1. **Clone the repository**:
    ```sh
    git clone https://github.com/your-username/klondike-solitaire.git
    cd klondike-solitaire
    ```

2. **Compile the project**:
    ```sh
    javac -d out -sourcepath src src/cs3500/klondike/*.java
    ```

3. **Run the game**:
    ```sh
    java -cp out klondike.Main
    ```

## Examples

### Creating a Basic Klondike Game
```java
KlondikeModel game = KlondikeCreator.createGame(KlondikeCreator.GameType.BASIC);

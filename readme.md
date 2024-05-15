# Minesweeper Game
## (Being updated)

## Introduction
This Minesweeper game is a classic single-player puzzle game where the player must uncover all non-bomb cells without detonating any bombs. It's a fun and challenging game that tests your logical thinking and strategy skills.

## Features
- Customizable grid size and number of bombs.
- Revealing cells and marking potential bomb locations.
- Visual indication of bomb cells and nearby counts.
- Game logic to handle winning and losing conditions.
- New game option to reset the grid and start fresh.

## Instructions
1. **Starting a New Game**
   - Run the `MinesweeperGUI` class to start the game.
   - Customize the grid size and number of bombs if desired.
   - Click "New Game" to reset the grid and start a new game.

2. **Gameplay**
   - Left-click on a cell to reveal its content.
   - Right-click to mark a cell as a potential bomb (flag).
   - Use logic and deduction to uncover all non-bomb cells without detonating any bombs.
   - The game ends when all non-bomb cells are revealed (win) or when a bomb is detonated (loss).

3. **Scoring**
   - The game doesn't have a scoring system. The objective is to win by uncovering all safe cells.

4. **Notes**
   - Cells may contain numbers indicating the number of bombs in adjacent cells.
   - Use the count information to deduce safe cells and avoid bombs.

## Development Details
- The game is developed in Java using Swing for the graphical user interface.
- The `Grid` class handles the game logic, including bomb placement, cell counts, and grid management.
- The `MinesweeperGUI` class sets up the game GUI and handles user interactions.

## Credits
- Developed by XJHCODEX
- Inspired by the classic Minesweeper game.

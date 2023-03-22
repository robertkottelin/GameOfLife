package se.peaccounting;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GameOfLifeApp extends GameApplication {
    // Define the size of a single cell and the grid dimensions
    private static final int CELL_SIZE = 10;
    private static final int GRID_WIDTH = 80;
    private static final int GRID_HEIGHT = 60;

    // Create a list to store all cell entities
    private List<Entity> cells = new ArrayList<>();

    // Initialize game settings
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(GRID_WIDTH * CELL_SIZE);
        settings.setHeight(GRID_HEIGHT * CELL_SIZE);
        settings.setTitle("Conway's Game of Life");
    }

    // Initialize the game by creating and attaching cell entities
    @Override
    protected void initGame() {
        // Iterate through the grid and create cell entities
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                Entity cell = FXGL.entityBuilder()
                        .at(x * CELL_SIZE, y * CELL_SIZE)
                        .view(new Rectangle(CELL_SIZE, CELL_SIZE, Color.BLACK))
                        .with("alive", Math.random() < 0.5)
                        .buildAndAttach();

                cells.add(cell);
            }
        }

        // Schedule the update of the game state using the FXGL library
        FXGL.run(() -> {
            // List to store the next generation state of the cells
            List<Boolean> nextGeneration = new ArrayList<>();

            // Iterate through cells and determine their next state
            for (Entity cell : cells) {
                int x = (int) (cell.getX() / CELL_SIZE);
                int y = (int) (cell.getY() / CELL_SIZE);

                int aliveNeighbors = countAliveNeighbors(x, y);

                boolean alive = cell.getBoolean("alive");
                boolean nextAlive = (alive && (aliveNeighbors == 2 || aliveNeighbors == 3)) || (!alive && aliveNeighbors == 3);

                nextGeneration.add(nextAlive);
            }

            // Update the cells based on their next state
            for (int i = 0; i < cells.size(); i++) {
                Entity cell = cells.get(i);
                boolean nextAlive = nextGeneration.get(i);

                cell.setProperty("alive", nextAlive);
                cell.getViewComponent().getChildren().forEach(node -> {
                    Rectangle rect = (Rectangle) node;
                    rect.setFill(nextAlive ? Color.LIGHTGREEN : Color.BLACK);
                });
            }
        }, Duration.seconds(0.1));
    }

    // Count the number of alive neighbors for a given cell
    private int countAliveNeighbors(int x, int y) {
        int aliveCount = 0;

        // Iterate through the neighboring cells
        for (int yOffset = -1; yOffset <= 1; yOffset++) {
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                if (xOffset == 0 && yOffset == 0) {
                    continue;
                }

                // Calculate the neighbor's coordinates, wrapping around the grid edges
                int neighborX = (x + xOffset + GRID_WIDTH) % GRID_WIDTH;
                int neighborY = (y + yOffset + GRID_HEIGHT) % GRID_HEIGHT;

                // Get the neighbor's index and entity
                int neighborIndex = neighborY * GRID_WIDTH + neighborX;
                Entity neighbor = cells.get(neighborIndex);

                // Increment the alive neighbors count if the neighbor is alive
                if (neighbor.getBoolean("alive")) {
                    aliveCount++;
                }
            }
        }

        return aliveCount;
    }

    // Main method to launch with args
    public static void main(String[] args) {
        launch(args);
    }
}
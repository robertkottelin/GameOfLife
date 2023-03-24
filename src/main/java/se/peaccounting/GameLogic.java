package se.peaccounting;

import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods for managing and updating the game logic.
 */
public class GameLogic {

    /**
     * Updates the given cells based on the next generation data.
     *
     * @param cells         the list of cell entities
     * @param nextGeneration the list of booleans representing the next generation of cells
     * @param cellSize      the size of each cell
     */
    public static void updateCells(List<Entity> cells, List<Boolean> nextGeneration, int cellSize) {
        for (int i = 0; i < cells.size(); i++) {
            Entity cell = cells.get(i);
            boolean nextAlive = nextGeneration.get(i);

            cell.setProperty("alive", nextAlive);
            cell.getViewComponent().getChildren().forEach(node -> {
                Rectangle rect = (Rectangle) node;
                rect.setFill(nextAlive ? Color.LIGHTGREEN : Color.BLACK);
            });
        }
    }

    /**
     * Counts the number of alive neighbors for a given cell.
     *
     * @param x         the x-coordinate of the cell
     * @param y         the y-coordinate of the cell
     * @param cells     the list of cell entities
     * @param gridWidth the width of the grid
     * @param gridHeight the height of the grid
     * @return the count of alive neighbors
     */
    public static int countAliveNeighbors(int x, int y, List<Entity> cells, int gridWidth, int gridHeight) {
        int aliveCount = 0;

        for (int yOffset = -1; yOffset <= 1; yOffset++) {
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                if (xOffset == 0 && yOffset == 0) {
                    continue;
                }

                int neighborX = (x + xOffset + gridWidth) % gridWidth;
                int neighborY = (y + yOffset + gridHeight) % gridHeight;

                int neighborIndex = neighborY * gridWidth + neighborX;
                Entity neighbor = cells.get(neighborIndex);

                if (neighbor.getBoolean("alive")) {
                    aliveCount++;
                }
            }
        }

        return aliveCount;
    }

    /**
     * Computes the next generation of cells based on the current generation.
     *
     * @param cells      the list of cell entities
     * @param cellSize   the size of each cell
     * @param gridWidth  the width of the grid
     * @param gridHeight the height of the grid
     * @return the list of booleans representing the next generation of cells
     */
    public static List<Boolean> computeNextGeneration(List<Entity> cells, int cellSize, int gridWidth, int gridHeight) {
        List<Boolean> nextGeneration = new ArrayList<>();

        for (Entity cell : cells) {
            int x = (int) (cell.getX() / cellSize);
            int y = (int) (cell.getY() / cellSize);

            int aliveNeighbors = countAliveNeighbors(x, y, cells, gridWidth, gridHeight);

            boolean alive = cell.getBoolean("alive");
            boolean nextAlive = (alive && (aliveNeighbors == 2 || aliveNeighbors == 3)) || (!alive && aliveNeighbors == 3);

            nextGeneration.add(nextAlive);
        }

        return nextGeneration;
    }
}

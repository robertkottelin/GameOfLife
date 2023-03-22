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

    private static final int CELL_SIZE = 10;
    private static final int GRID_WIDTH = 80;
    private static final int GRID_HEIGHT = 60;

    private List<Entity> cells = new ArrayList<>();

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(GRID_WIDTH * CELL_SIZE);
        settings.setHeight(GRID_HEIGHT * CELL_SIZE);
        settings.setTitle("Conway's Game of Life");
    }

    @Override
    protected void initGame() {
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

        FXGL.run(() -> {
            List<Boolean> nextGeneration = new ArrayList<>();

            for (Entity cell : cells) {
                int x = (int) (cell.getX() / CELL_SIZE);
                int y = (int) (cell.getY() / CELL_SIZE);

                int aliveNeighbors = countAliveNeighbors(x, y);

                boolean alive = cell.getBoolean("alive");
                boolean nextAlive = (alive && (aliveNeighbors == 2 || aliveNeighbors == 3)) || (!alive && aliveNeighbors == 3);

                nextGeneration.add(nextAlive);
            }

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

    private int countAliveNeighbors(int x, int y) {
        int aliveCount = 0;

        for (int yOffset = -1; yOffset <= 1; yOffset++) {
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                if (xOffset == 0 && yOffset == 0) {
                    continue;
                }

                int neighborX = (x + xOffset + GRID_WIDTH) % GRID_WIDTH;
                int neighborY = (y + yOffset + GRID_HEIGHT) % GRID_HEIGHT;

                int neighborIndex = neighborY * GRID_WIDTH + neighborX;
                Entity neighbor = cells.get(neighborIndex);

                if (neighbor.getBoolean("alive")) {
                    aliveCount++;
                }
            }
        }

        return aliveCount;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
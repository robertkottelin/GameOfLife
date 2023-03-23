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

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static se.peaccounting.GameOfLifeConfig.*;

public class GameOfLifeApp extends GameApplication {

    protected List<Entity> cells = new ArrayList<>();

    @Override
    protected void initSettings(GameSettings settings) {
        configureSettings(settings);
    }

    @Override
    protected void initGame() {
        createAndAttachCells();
        scheduleGameStateUpdates();
    }

    protected void configureSettings(GameSettings settings) {
        settings.setWidth(GRID_WIDTH);
        settings.setHeight(GRID_HEIGHT);
        settings.setTitle("Conway's Game of Life");
    }

    protected void createAndAttachCells() {
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                GameOfLifeCell cell = new GameOfLifeCell(x, y, CELL_SIZE);
                cells.add(cell);
                getGameWorld().addEntity(cell);
            }
        }
    }

    protected void scheduleGameStateUpdates() {
        FXGL.run(() -> {
            List<Boolean> nextGeneration = computeNextGeneration();

            updateCells(nextGeneration);
        }, Duration.seconds(UPDATE_STATE_INTERVAL));
    }

    protected List<Boolean> computeNextGeneration() {
        List<Boolean> nextGeneration = new ArrayList<>();

        for (Entity cell : cells) {
            int x = (int) (cell.getX() / CELL_SIZE);
            int y = (int) (cell.getY() / CELL_SIZE);

            int aliveNeighbors = countAliveNeighbors(x, y);

            boolean alive = cell.getBoolean("alive");
            boolean nextAlive = (alive && (aliveNeighbors == 2 || aliveNeighbors == 3)) || (!alive && aliveNeighbors == 3);

            nextGeneration.add(nextAlive);
        }

        return nextGeneration;
    }

    protected void updateCells(List<Boolean> nextGeneration) {
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

    protected int countAliveNeighbors(int x, int y) {
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

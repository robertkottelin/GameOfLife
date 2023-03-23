package se.peaccounting;

import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameOfLifeCell extends Entity {
    public GameOfLifeCell(int x, int y, int cellSize) {
        setPosition(x * cellSize, y * cellSize);

        Rectangle view = new Rectangle(cellSize, cellSize, Color.BLACK);
        getViewComponent().addChild(view);

        setProperty("alive", Math.random() < 0.5);
    }
}

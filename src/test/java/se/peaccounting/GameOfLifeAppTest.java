package se.peaccounting;

import com.almasb.fxgl.entity.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeAppTest {
    private GameOfLifeApp game;

    @BeforeEach
    void setUp() {
        game = new GameOfLifeApp();
    }

    // Your test cases will go here
    @Test
    void test() {
        int x = 2;
        int y = 2;
        assertEquals(x, y, "x and y should match");
    }

    @Test
    void countAliveNeighbors() {
        // Set up a simple 3x3 grid with a single alive cell in the middle
        game.GRID_WIDTH = 3;
        game.GRID_HEIGHT = 3;
        game.cells.clear();
        for (int i = 0; i < 9; i++) {
            Entity cell = new Entity();
            cell.setProperty("alive", i == 4);
            game.cells.add(cell);
        }

        assertEquals(1, game.countAliveNeighbors(0, 0));
        assertEquals(1, game.countAliveNeighbors(1, 0));
        assertEquals(1, game.countAliveNeighbors(0, 1));
        assertEquals(0, game.countAliveNeighbors(1, 1));
    }

    @Test
    void computeNextGeneration() {
        // Set up a simple 3x3 grid with a "block" pattern of alive cells in the top-left corner
        game.GRID_WIDTH = 3;
        game.GRID_HEIGHT = 3;
        game.cells.clear();
        for (int i = 0; i < 9; i++) {
            Entity cell = new Entity();
            cell.setProperty("alive", i == 0 || i == 1 || i == 3 || i == 4);
            game.cells.add(cell);
        }

        List<Boolean> nextGeneration = game.computeNextGeneration();

        assertTrue(nextGeneration.get(0));
        assertTrue(nextGeneration.get(1));
        assertTrue(nextGeneration.get(2));
        assertTrue(nextGeneration.get(3));
        assertTrue(nextGeneration.get(4));
    }

}

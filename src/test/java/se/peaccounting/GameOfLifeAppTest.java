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
    void testthetest() {
        int x = 2;
        int y = 2;
        assertEquals(x, y, "x and y should match");
    }

    @Test
    void countAliveNeighbors() {
        assertEquals(0, 0);
    }

    @Test
    void computeNextGeneration() {
        assertEquals(0, 0);
    }

}

// package se.peaccounting;

// import com.almasb.fxgl.entity.Entity;
// import javafx.scene.shape.Rectangle;
// import org.junit.jupiter.api.Test;

// import java.util.ArrayList;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// /**
//  * Test class for the GameLogic.
//  */
// public class GameLogicTest {

//     /**
//      * Creates a mock entity with the specified position and alive state.
//      *
//      * @param x     the x-coordinate of the entity
//      * @param y     the y-coordinate of the entity
//      * @param alive the alive state of the entity
//      * @return the created mock entity
//      */
//     private Entity createMockEntity(double x, double y, boolean alive) {
//         Entity entity = new Entity();
//         entity.setPosition(x, y);
//         entity.setProperty("alive", alive);
//         entity.getViewComponent().addChild(new Rectangle());
//         return entity;
//     }

//     /**
//      * Creates a list of mock entities with the specified grid dimensions and initial alive state.
//      *
//      * @param gridWidth     the width of the grid
//      * @param gridHeight    the height of the grid
//      * @param initialState  the initial alive state for all entities
//      * @return the list of created mock entities
//      */
//     private List<Entity> createMockEntities(int gridWidth, int gridHeight, boolean initialState) {
//         List<Entity> cells = new ArrayList<>();
//         for (int y = 0; y < gridHeight; y++) {
//             for (int x = 0; x < gridWidth; x++) {
//                 cells.add(createMockEntity(x * 10, y * 10, initialState));
//             }
//         }
//         return cells;
//     }

//     @Test
//     public void countAliveNeighborsTest() {
//         int gridWidth = 3;
//         int gridHeight = 3;
//         List<Entity> cells = createMockEntities(gridWidth, gridHeight, false);

//         // Set the center cell to be alive
//         cells.get(4).setProperty("alive", true);

//         int aliveNeighbors = GameLogic.countAliveNeighbors(0, 0, cells, gridWidth, gridHeight);

//         assertEquals(1, aliveNeighbors);
//     }

//     @Test
//     public void updateCellsTest() {
//         int gridWidth = 3;
//         int gridHeight = 3;
//         List<Entity> cells = createMockEntities(gridWidth, gridHeight, false);
//         List<Boolean> nextGeneration = new ArrayList<>();

//         // Set the next generation to be all alive
//         for (int i = 0; i < cells.size(); i++) {
//             nextGeneration.add(true);
//         }

//         GameLogic.updateCells(cells, nextGeneration, 10);

//         // Check if all cells are updated to be alive
//         for (Entity cell : cells) {
//             assertTrue(cell.getBoolean("alive"));
//         }
//     }

//     @Test
//     public void computeNextGenerationTest() {
//         int gridWidth = 3;
//         int gridHeight = 3;
//         List<Entity> cells = createMockEntities(gridWidth, gridHeight, false);

//         // Set the center cell to be alive
//         cells.get(4).setProperty("alive", true);

//         List<Boolean> nextGeneration = GameLogic.computeNextGeneration(cells, 10, gridWidth, gridHeight);

//         assertEquals(cells.size(), nextGeneration.size());
//     }
// }
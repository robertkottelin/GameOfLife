// package se.peaccounting;

// import com.almasb.fxgl.app.GameApplication;
// import com.almasb.fxgl.app.GameSettings;
// import com.almasb.fxgl.dsl.FXGL;
// import com.almasb.fxgl.entity.Entity;
// import javafx.scene.paint.Color;
// import javafx.scene.shape.Rectangle;
// import javafx.util.Duration;

// import java.util.ArrayList;
// import java.util.List;

// /**
//  * The main application class for Conway's Game of Life.
//  */
// public class GameOfLifeApp extends GameApplication {

//     private static final int CELL_SIZE = 10;
//     private static final int GRID_WIDTH = 80;
//     private static final int GRID_HEIGHT = 60;
//     private static final double UPDATE_STATE_INTERVAL = 0.1; // How often (seconds) the generations update

//     private final List<Entity> cells = new ArrayList<>();

//     /**
//      * Initializes the game settings.
//      *
//      * @param settings the game settings object
//      */
//     @Override
//     protected void initSettings(GameSettings settings) {
//         configureSettings(settings);
//     }

//     /**
//      * Initializes the game by creating and attaching cells and scheduling game state updates.
//      */
//     @Override
//     protected void initGame() {
//         createAndAttachCells();
//         scheduleGameStateUpdates();
//     }

//     /**
//      * Configures the game settings.
//      *
//      * @param settings the game settings object
//      */
//     private void configureSettings(GameSettings settings) {
//         settings.setWidth(GRID_WIDTH * CELL_SIZE);
//         settings.setHeight(GRID_HEIGHT * CELL_SIZE);
//         settings.setTitle("Conway's Game of Life");
//     }

//     /**
//      * Creates and attaches cells to the game.
//      */
//     private void createAndAttachCells() {
//         for (int y = 0; y < GRID_HEIGHT; y++) {
//             for (int x = 0; x < GRID_WIDTH; x++) {
//                 Entity cell = createCell(x, y);
//                 cells.add(cell);
//             }
//         }
//     }

//     /**
//      * Creates a cell entity at the specified position.
//      *
//      * @param x the x-coordinate of the cell
//      * @param y the y-coordinate of the cell
//      * @return the created cell entity
//      */
//     private Entity createCell(int x, int y) {
//         return FXGL.entityBuilder()
//                 .at(x * CELL_SIZE, y * CELL_SIZE)
//                 .view(new Rectangle(CELL_SIZE, CELL_SIZE, Color.BLACK))
//                 .with("alive", Math.random() < 0.5)
//                 .buildAndAttach();
//     }

//     /**
//      * Schedules the game state updates at the specified interval.
//      */
//     private void scheduleGameStateUpdates() {
//         FXGL.run(() -> {
//             // Call the computeNextGeneration method from the GameLogic class
//             List<Boolean> nextGeneration = GameLogic.computeNextGeneration(cells, CELL_SIZE, GRID_WIDTH, GRID_HEIGHT);
//             GameLogic.updateCells(cells, nextGeneration, CELL_SIZE);
//         }, Duration.seconds(UPDATE_STATE_INTERVAL));
//     }

//     /**
//      * The main entry point for the application.
//      *
//      * @param args command-line arguments
//      */
//     public static void main(String[] args) {
//         launch(args);
//     }
// }

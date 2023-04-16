package se.peaccounting;

import java.util.Scanner;

public class TerminalGuiApp {
    // Grid dimensions (8x8 cells)
    private static final int GRID_WIDTH = 8;
    private static final int GRID_HEIGHT = 8;

    // The current generation grid, containing alive (true) and dead (false) cells
    private static boolean[][] currentGeneration = new boolean[GRID_HEIGHT][GRID_WIDTH];
    
    // The next generation grid, used to store the updated cell states
    private static boolean[][] nextGeneration = new boolean[GRID_HEIGHT][GRID_WIDTH];

    public static void main(String[] args) {
        // Initialize the grid with random cell states
        initializeGridWithRandomValues();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                // Display the current generation grid
                displayCurrentGeneration();

                System.out.println("Press Enter for the next generation, or type 'q' to quit:");
                String input = scanner.nextLine();

                // Exit the loop if the user types 'q' (case insensitive)
                if ("q".equalsIgnoreCase(input)) {
                    break;
                }

                // Calculate and update the grid to the next generation
                updateGeneration();
            }
        }
    }

    /**
     * Initializes the current generation grid with random cell states.
     */
    private static void initializeGridWithRandomValues() {
        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_WIDTH; col++) {
                currentGeneration[row][col] = Math.random() > 0.5;
            }
        }
    }

    /**
     * Displays the current generation grid in the terminal.
     */
    private static void displayCurrentGeneration() {
        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_WIDTH; col++) {
                // Use 'X' to represent alive cells and ' ' (space) to represent dead cells
                System.out.print(currentGeneration[row][col] ? 'X' : ' ');
            }
            System.out.println();
        }
    }

    /**
     * Updates the grid to the next generation based on the Game of Life rules.
     */
    private static void updateGeneration() {
        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_WIDTH; col++) {
                int liveNeighbors = countLiveNeighbors(row, col);

                // Apply the Game of Life rules to determine the cell's next state
                if (currentGeneration[row][col]) {
                    nextGeneration[row][col] = liveNeighbors == 2 || liveNeighbors == 3;
                } else {
                    nextGeneration[row][col] = liveNeighbors == 3;
                }
            }
        }

        // Swap the current generation with the next generation
        boolean[][] tempGeneration = currentGeneration;
        currentGeneration = nextGeneration;
        nextGeneration = tempGeneration;
    }

    /**
     * Counts the number of live neighbors for a given cell.
     *
     * @param row The row index of the cell
     * @param col The column index of the cell
     * @return The number of live neighbors
     */
    private static int countLiveNeighbors(int row, int col) {
        int liveNeighbors = 0;

        // Iterate through the neighboring cells
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {
                // Skip the current cell itself
                if (rowOffset == 0 && colOffset == 0) {
                    continue;
                }

                // Calculate the neighboring cell's row and column indices
                int newRow = (row + rowOffset + GRID_HEIGHT) % GRID_HEIGHT;
                int newCol = (col + colOffset + GRID_WIDTH) % GRID_WIDTH;

                // Increment the live neighbors count if the neighboring cell is alive
                if (currentGeneration[newRow][newCol]) {
                    liveNeighbors++;
                }
            }
        }
        return liveNeighbors;
    }
}


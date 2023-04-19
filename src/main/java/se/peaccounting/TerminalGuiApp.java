package se.peaccounting;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TerminalGuiApp {
    // Define the dimensions of the grid
    private static final int GRID_WIDTH = 44000; // 44200^2 = 1 953 640 000 cells, 8.6 seconds for a generationupdate
    private static final int GRID_HEIGHT = 44000;

    // Create the grid for the current and next generations
    private static BitSet[] currentGeneration = new BitSet[GRID_HEIGHT];
    private static BitSet[] nextGeneration = new BitSet[GRID_HEIGHT];
    
    // Cache for live neighbors count
    // Exception in thread "main" java.lang.OutOfMemoryError: Java heap space (if grid > 10000x10000)
    private static int[][] liveNeighborsCache = new int[GRID_HEIGHT][GRID_WIDTH];

    public static void main(String[] args) {
        // Initialize the grid with random values
        initializeGridWithRandomValues();

        // Use Scanner to read user input
        try (Scanner scanner = new Scanner(System.in)) {
            // Run the loop until the user decides to quit
            while (true) {
                // Display the current generation of cells
                displayCurrentGeneration();

                // Prompt the user for input
                System.out.println("Press Enter for the next generation, or type 'q' to quit:");
                String input = scanner.nextLine();

                // If the user enters 'q', exit the loop
                if ("q".equalsIgnoreCase(input)) {
                    break;
                }

                // Update the grid to the next generation
                updateGeneration();
            }
        }
    }

    // Initialize the grid with random values
    private static void initializeGridWithRandomValues() {
        // Iterate through each cell in the grid
        for (int row = 0; row < GRID_HEIGHT; row++) {
            currentGeneration[row] = new BitSet(GRID_WIDTH);
            nextGeneration[row] = new BitSet(GRID_WIDTH);
            for (int col = 0; col < GRID_WIDTH; col++) {
                // Set the cell's state to alive or dead randomly
                currentGeneration[row].set(col, Math.random() > 0.5);
            }
        }
    }

    // Display the current generation of cells
    private static void displayCurrentGeneration() {
        // Iterate through each cell in the grid
        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_WIDTH; col++) {
                // Print the cell's state (alive = 'X', dead = ' ')
                // System.out.print(currentGeneration[row].get(col) ? 'X' : ' ');
            }
            // Move to the next line for the next row
            // System.out.println();
        }
    }

    // Update the grid to the next generation
    private static void updateGeneration() {
        // Reset liveNeighborsCache
        for (int row = 0; row < GRID_HEIGHT; row++) {
            Arrays.fill(liveNeighborsCache[row], 0);
        }
    
        // Create a fixed thread pool based on the number of available processors
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    
        // Iterate through each cell in the grid
        for (int row = 0; row < GRID_HEIGHT; row++) {
            final int currentRow = row;
            // Execute the cell update in parallel
            executor.execute(() -> {
                for (int col = 0; col < GRID_WIDTH; col++) {
                    // Count the live neighbors of the current cell
                    int liveNeighbors = countLiveNeighbors(currentRow, col);
    
                    // Update the next generation's cell state based on the current cell's state and the number of live neighbors
                    if (currentGeneration[currentRow].get(col)) {
                        nextGeneration[currentRow].set(col, liveNeighbors == 2 || liveNeighbors == 3);
                    } else {
                        nextGeneration[currentRow].set(col, liveNeighbors == 3);
                    }
                }
            });
        }
    
        // Shut down the executor after all tasks are completed
        executor.shutdown();
        try {
            // Wait for all tasks to finish before proceeding
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            // Print the exception if the waiting is interrupted
            e.printStackTrace();
        }
    
        // Swap the currentGeneration and nextGeneration grids
        BitSet[] temp = currentGeneration;
        currentGeneration = nextGeneration;
        nextGeneration = temp;
    }  

    // Count the number of live neighbors for a given cell
    private static int countLiveNeighbors(int row, int col) {
        // Return the cached value if it exists, subtracting 1 to account for the cache offset
        if (liveNeighborsCache[row][col] != 0) {
            return liveNeighborsCache[row][col] - 1;
        }
    
        int liveNeighbors = 0;
    
        // Check all 8 neighboring cells
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {
                // Skip the current cell itself
                if (rowOffset == 0 && colOffset == 0) {
                    continue;
                }
    
                // Calculate the neighboring cell's row and column indices, wrapping around the edges of the grid
                int newRow = (row + rowOffset + GRID_HEIGHT) % GRID_HEIGHT;
                int newCol = (col + colOffset + GRID_WIDTH) % GRID_WIDTH;
    
                // Increment the live neighbors count if the neighboring cell is alive
                if (currentGeneration[newRow].get(newCol)) {
                    liveNeighbors++;
                }
            }
        }
    
        // Store the count in the cache with an offset of 1
        liveNeighborsCache[row][col] = liveNeighbors + 1;
        return liveNeighbors;
    }    
}


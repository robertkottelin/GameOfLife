package se.peaccounting;

import java.util.Scanner;

public class TerminalGuiApp {
    private static final int GRID_WIDTH = 30;
    private static final int GRID_HEIGHT = 15;
    private static boolean[][] currentGeneration = new boolean[GRID_HEIGHT][GRID_WIDTH];
    private static boolean[][] nextGeneration = new boolean[GRID_HEIGHT][GRID_WIDTH];

    public static void main(String[] args) {
        initializeGridWithRandomValues();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                displayCurrentGeneration();
                System.out.println("Press Enter for the next generation, or type 'q' to quit:");
                String input = scanner.nextLine();
                if ("q".equalsIgnoreCase(input)) {
                    break;
                }
                updateGeneration();
            }
        }
    }

    private static void initializeGridWithRandomValues() {
        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_WIDTH; col++) {
                currentGeneration[row][col] = Math.random() > 0.5;
            }
        }
    }

    private static void displayCurrentGeneration() {
        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_WIDTH; col++) {
                System.out.print(currentGeneration[row][col] ? '*' : ' ');
            }
            System.out.println();
        }
    }

    private static void updateGeneration() {
        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_WIDTH; col++) {
                int liveNeighbors = countLiveNeighbors(row, col);
                if (currentGeneration[row][col]) {
                    nextGeneration[row][col] = liveNeighbors == 2 || liveNeighbors == 3;
                } else {
                    nextGeneration[row][col] = liveNeighbors == 3;
                }
            }
        }

        boolean[][] tempGeneration = currentGeneration;
        currentGeneration = nextGeneration;
        nextGeneration = tempGeneration;
    }

    private static int countLiveNeighbors(int row, int col) {
        int liveNeighbors = 0;
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {
                if (rowOffset == 0 && colOffset == 0) {
                    continue;
                }
                int newRow = (row + rowOffset + GRID_HEIGHT) % GRID_HEIGHT;
                int newCol = (col + colOffset + GRID_WIDTH) % GRID_WIDTH;
                if (currentGeneration[newRow][newCol]) {
                    liveNeighbors++;
                }
            }
        }
        return liveNeighbors;
    }
}

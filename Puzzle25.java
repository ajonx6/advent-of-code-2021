import javafx.geometry.Point3D;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Puzzle25 {
    public static void main(String[] args) throws IOException {
        Util.init();
        List<String> data = Util.load("puzzle25");

        int[][] grid = new int[data.size()][data.get(0).length()];
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                grid[r][c] = data.get(r).charAt(c) == '>' ? 1 : (data.get(r).charAt(c) == 'v' ? 2 : 0);
            }
        }

        int[][] previousGrid = new int[grid.length][grid[0].length];

        int times = 0;
//        print(grid);
//        int rounds = 0;
        while (!Arrays.deepEquals(grid, previousGrid)) {
            int[][] nextGrid = new int[grid.length][grid[0].length];
            for (int r = 0; r < grid.length; r++) {
                for (int c = 0; c < grid[r].length; c++) {
                    if (grid[r][c] == 1) {
                        if (grid[r][(c + 1) % grid[r].length] == 0) {
                            nextGrid[r][c] = 0;
                            nextGrid[r][(c + 1) % grid[r].length] = 1;
                        } else {
                            nextGrid[r][c] = grid[r][c];
                        }
                    } else if (grid[r][c] == 2) {
                        nextGrid[r][c] = grid[r][c];
                    }
                }
            }

            int[][] nextNextGrid = new int[nextGrid.length][nextGrid[0].length];
            for (int r = 0; r < grid.length; r++) {
                for (int c = 0; c < grid[r].length; c++) {
                    if (nextGrid[r][c] == 2) {
                        if (nextGrid[(r + 1) % grid.length][c] == 0) {
                            nextNextGrid[r][c] = 0;
                            nextNextGrid[(r + 1) % grid.length][c] = 2;
                        } else {
                            nextNextGrid[r][c] = nextGrid[r][c];
                        }
                    } else if (nextGrid[r][c] == 1) {
                        nextNextGrid[r][c] = nextGrid[r][c];
                    }
                }
            }

            previousGrid = grid;
            grid = nextNextGrid;
            times++;
        }

        System.out.println("Number of steps before stop is " + times);

        Util.end();
    }

    public static void print(int[][] grid) {
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                System.out.print(grid[r][c] == 0 ? ". " : (grid[r][c] == 1 ? "> " : "v "));
            }
            System.out.println();
        }
        System.out.println("==================================");
    }
}
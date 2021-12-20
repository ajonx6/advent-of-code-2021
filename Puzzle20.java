import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Puzzle20 {
    public static void main(String[] args) throws IOException {
        Util.init();
        List<String> data = Util.load("puzzle20");

        List<Boolean> key = data.get(0).chars().mapToObj(c -> c == '#').collect(Collectors.toList());
        boolean[][] grid = new boolean [data.size() - 2][data.get(2).length()];
        for (int i = 2; i < data.size(); i++) {
            String line = data.get(i);
            boolean[] lineArr = new boolean[grid[0].length];
            for (int j = 0; j < line.length(); j++) {
                lineArr[j] = line.charAt(j) == '#';
            }
            grid[i - 2] = lineArr;
        }

        int p1 = runNTimes(key, grid, 2);
        System.out.println("Number lit after 2 times is " + p1);
        int p2 = runNTimes(key, grid, 50);
        System.out.println("Number lit after 50 times is " + p2);

        Util.end();
    }

    public static int runNTimes(List<Boolean> key, boolean[][] g, int amt) {
        boolean[][] grid = new boolean[g.length][g[0].length];
        for (int r = 0; r < grid.length; r++) {
            System.arraycopy(g[r], 0, grid[r], 0, grid[0].length);
        }

        boolean outside = false;
        for (int i = 0; i < amt; i++) {
            grid = enhance(key, grid, outside);
            outside = !outside;

            boolean[][] gs = new boolean[grid.length][grid[0].length];
            for (int r = 0; r < gs.length; r++) {
                System.arraycopy(grid[r], 0, gs[r], 0, grid[0].length);
            }
        }


        int count = 0;
        for (boolean[] arr : grid) {
            for (boolean b : arr) {
                if (b) count++;
            }
        }

        return count;
    }

    public static boolean[][] enhance(List<Boolean> key, boolean[][] grid, boolean outside) {
        boolean[][] newGrid = new boolean [grid.length + 2][grid[0].length + 2];
        for (int r = -1; r <= grid.length; r++) {
            for (int c = -1; c <= grid[0].length; c++) {
                int outputpixel = getBinaryOfCell(r, c, grid, outside);
                newGrid[r + 1][c + 1] = key.get(outputpixel);
            }
        }
        return newGrid;
    }

    public static int getBinaryOfCell(int r, int c, boolean[][] grid, boolean outside) {
        StringBuilder num = new StringBuilder();
        for (int rr = -1; rr <= 1; rr++) {
            for (int cc = -1; cc <= 1; cc++) {
                int rrr = r + rr;
                int ccc = c + cc;
                if (rrr < 0 || ccc < 0 || rrr >= grid.length || ccc >= grid[0].length) num.append(outside ? "1" : "0");
                else num.append(grid[rrr][ccc] ? "1" : "0");
            }
        }
        return Integer.parseInt(num.toString(), 2);
    }

    public static void printGrid(boolean[][] grid) {
        for (boolean[] arr : grid) {
            for (boolean b : arr) {
                System.out.print(b ? '#' + " " : '.' + " ");
            }
            System.out.println();
        }
        System.out.println("====================================\n");
    }
}
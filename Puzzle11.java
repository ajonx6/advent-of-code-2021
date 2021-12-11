import java.awt.*;
import java.util.*;
import java.util.List;

public class Puzzle11 {
    public static void main(String[] args) {
        Util.init();
        List<String> data = Util.load("puzzle11");

        int[][] heights = new int[data.size()][data.get(0).length()];
        int rowIndex = 0;
        int colIndex = 0;
        for (String line : data) {
            int[] row = new int[line.length()];
            for (char c : line.toCharArray()) {
                row[colIndex++] = c - 48;
            }
            colIndex = 0;
            heights[rowIndex++] = row;
        }

        int totalFlashes = 0;
        int i = 0;
        while (true) {
            i++;
            boolean[][] flashed = new boolean[heights.length][heights[0].length];
            for (int r = 0; r < heights.length; r++) {
                for (int c = 0; c < heights[r].length; c++) {
                    heights[r][c]++;
                }
            }
            int count = 0;
            do {
                count = 0;
                for (int r = 0; r < heights.length; r++) {
                    for (int c = 0; c < heights[r].length; c++) {
                        if (heights[r][c] > 9 && !flashed[r][c]) {
                            flashed[r][c] = true;
                            Point[] ns = getNeighbours(r, c, heights);
                            for (Point p : ns) {
                                if (p != null && !flashed[(int) p.getX()][(int) p.getY()]) {
                                    heights[(int) p.getX()][(int) p.getY()]++;
                                }
                            }
                            count++;
                        }
                    }
                }
            } while (count > 0);
            for (int r = 0; r < heights.length; r++) {
                for (int c = 0; c < heights[r].length; c++) {
                    if (heights[r][c] > 9) heights[r][c] = 0;
                }
            }

            int localFlashes = 0;
            for (int r = 0; r < heights.length; r++) {
                for (int c = 0; c < heights[r].length; c++) {
                    localFlashes += flashed[r][c] ? 1 : 0;
                }
            }
            totalFlashes += localFlashes;
            if (i == 100) System.out.println("Number of flashes in 100 days is " + totalFlashes);

            if (localFlashes == heights.length * heights[0].length) {
                System.out.println("Day of synced flashes is " + i);
                break;
            }
        }


        Util.end();
    }

    public static Point[] getNeighbours(int r, int c, int[][] heights) {
        Point[] dirs = new Point[8];
        int index = 0;
        dirs[index++] = r > 0 && c > 0 ? new Point(r - 1, c - 1) : null;
        dirs[index++] = r > 0 ? new Point(r - 1, c) : null;
        dirs[index++] = r > 0 && c < heights[r].length - 1 ? new Point(r - 1, c + 1) : null;
        dirs[index++] = c > 0 ? new Point(r, c - 1) : null;
        dirs[index++] = c < heights[r].length - 1 ? new Point(r, c + 1) : null;
        dirs[index++] = r < heights.length - 1 && c > 0 ? new Point(r + 1, c - 1) : null;
        dirs[index++] = r < heights.length - 1 ? new Point(r + 1, c) : null;
        dirs[index] = r < heights.length - 1 && c < heights[r].length - 1 ? new Point(r + 1, c + 1) : null;

        return dirs;
    }
}
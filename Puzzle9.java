import java.awt.*;
import java.util.*;
import java.util.List;

public class Puzzle9 {
    public static void main(String[] args) {
        Util.init();
        List<String> data = Util.load("puzzle9");

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

        int sumOfRisks = 0;
        for (int r = 0; r < heights.length; r++) {
            for (int c = 0; c < heights[r].length; c++) {
                int up = getUp(r, c, heights);
                int down = getDown(r, c, heights);
                int left = getLeft(r, c, heights);
                int right = getRight(r, c, heights);
                if ((up == -1 || up > heights[r][c]) &&
                    (down == -1 || down > heights[r][c]) &&
                    (left == -1 || left > heights[r][c]) &&
                    (right == -1 || right > heights[r][c])) {
                    sumOfRisks += heights[r][c] + 1;
                }
            }
        }
        System.out.println("Sum of risks is " + sumOfRisks);

        List<Integer> sizes = new ArrayList<>();
        boolean[][] seen = new boolean[data.size()][data.get(0).length()];
        for (int r = 0; r < heights.length; r++) {
            for (int c = 0; c < heights[r].length; c++) {
                if (seen[r][c]) {
                    continue;
                } else if (heights[r][c] == 9) {
                    seen[r][c] = true;
                } else {
                    int count = dfs(r, c, heights, seen);
                    sizes.add(count);
                }
            }
        }
        sizes.sort(Collections.reverseOrder());
        System.out.println("Product is " + sizes.get(0) * sizes.get(1) * sizes.get(2));

        Util.end();
    }

    private static int dfs(int r, int c, int[][] heights, boolean[][] seen) {
        List<Point> dfs = new ArrayList<>();
        dfs.add(new Point(r, c));
        int count = 0;

        while (!dfs.isEmpty()) {
            Point p = dfs.remove(0);
            if (seen[p.x][p.y]) continue;
            seen[p.x][p.y] = true;
            count++;
            int up = getUp(p.x, p.y, heights);
            int down = getDown(p.x, p.y, heights);
            int left = getLeft(p.x, p.y, heights);
            int right = getRight(p.x, p.y, heights);
            if (up >= 0 && up <= 8 && !seen[p.x - 1][p.y]) dfs.add(new Point(p.x - 1, p.y));
            if (down >= 0 && down <= 8 && !seen[p.x + 1][p.y]) dfs.add(new Point(p.x + 1, p.y));
            if (left >= 0 && left <= 8 && !seen[p.x][p.y - 1]) dfs.add(new Point(p.x, p.y - 1));
            if (right >= 0 && right <= 8 && !seen[p.x][p.y + 1]) dfs.add(new Point(p.x, p.y + 1));
        }

        return count;
    }

    public static int getUp(int r, int c, int[][] heights) {
        if (r > 0) return heights[r - 1][c];
        else return -1;
    }

    public static int getDown(int r, int c, int[][] heights) {
        if (r < heights.length - 1) return heights[r + 1][c];
        else return -1;
    }

    public static int getLeft(int r, int c, int[][] heights) {
        if (c > 0) return heights[r][c - 1];
        else return -1;
    }

    public static int getRight(int r, int c, int[][] heights) {
        if (c < heights[r].length - 1) return heights[r][c + 1];
        else return -1;
    }
}
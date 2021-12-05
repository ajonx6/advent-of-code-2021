import javafx.util.Pair;

import java.util.*;

public class Puzzle4 {
    public static void main(String[] args) {
        Util.init();
        List<String> data = Util.load("puzzle4");
        int[] numbers = Arrays.stream(data.get(0).split(",")).mapToInt(Integer::parseInt).toArray();

        List<int[][]> boards = new ArrayList<>();
        List<boolean[][]> seen = new ArrayList<>();
        for (int i = 2; i < data.size(); i += 6) {
            int[][] board = new int[5][5];
            boolean[][] seenInner = new boolean[5][5];
            int outerIndex = 0;
            for (int j = 0; j < 5; j++) {
                int[] curr = new int[5];
                int index = 0;
                String line = data.get(i + j);
                for (int k = 0; k < 5; k++) {
                    curr[index++] = Integer.parseInt(line.substring(k * 3, k * 3 + 2).trim());
                }
                board[outerIndex] = curr;
                seenInner[outerIndex++] = new boolean[5];
            }
            boards.add(board);
            seen.add(seenInner);
        }
        List<Integer> won = new ArrayList<>();
        for (int number : numbers) {
            for (int b = 0; b < boards.size(); b++) {
                int[][] board = boards.get(b);
                boolean[][] localSeen = seen.get(b);

                for (int r = 0; r < board.length; r++) {
                    for (int c = 0; c < board.length; c++) {
                        if (board[r][c] == number) {
                            localSeen[r][c] = true;
                            if (won.contains(b)) continue;
                            int numSeen = 0;
                            for (int rr = 0; rr < 5; rr++) {
                                if (localSeen[rr][c]) numSeen++;
                            }
                            if (numSeen == 5) {
                                System.out.println("Score is " + getScore(number, board, localSeen));
                                won.add(b);
                            }
                            numSeen = 0;
                            for (int cc = 0; cc < 5; cc++) {
                                if (localSeen[r][cc]) numSeen++;
                            }
                            if (numSeen == 5) {
                                System.out.println("Score is " + getScore(number, board, localSeen));
                                won.add(b);
                            }
                        }
                    }
                }
            }
        }

        Util.end();
    }

    public static int getScore(int number, int[][] board, boolean[][] seen) {
        int unmarked = 0;
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                if (!seen[r][c]) unmarked += board[r][c];
            }
        }
        return number * unmarked;
    }
}
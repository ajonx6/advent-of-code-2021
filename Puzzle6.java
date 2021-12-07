import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Puzzle6 {
    public static void main(String[] args) {
        Util.init();
        List<String> data = Util.load("puzzle6");

        System.out.println("Number of fish after 80 days is " + numberOfFishAfterNDays(data, 80));
        System.out.println("Number of fish after 256 days  is " + numberOfFishAfterNDays(data, 256));

        Util.end();
    }

    public static long numberOfFishAfterNDays(List<String> data, int n) {
        HashMap<Integer, Long> timers = new HashMap<>();
        for (int i = 0; i <= 8; i++) {
            timers.put(i, 0L);
        }
        for (String time : data.get(0).split(",")) {
            int t = Integer.parseInt(time);
            timers.put(t, timers.get(t) + 1L);
        }
        for (int i = 0; i < n; i++) {
            long numNext = timers.get(0);
            HashMap<Integer, Long> temp = new HashMap<>();
            for (int j = 1; j <= 8; j++) {
                temp.put(j - 1, timers.get(j));
            }
            temp.put(8, numNext);
            temp.put(6, temp.get(6) + numNext);
            timers = temp;
        }
        long numFish = 0;
        for (int i = 0; i <= 8; i++) {
            numFish += timers.get(i);
        }
        return numFish;
    }
}
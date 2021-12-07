import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Puzzle7 {
    public static void main(String[] args) {
        Util.init();
        List<String> data = Util.load("puzzle7");
        List<Integer> nums = new ArrayList<>();
        for (String s : data.get(0).split(",")) {
            nums.add(Integer.parseInt(s));
        }

        System.out.println("Minimum fuel is " + getFuel(nums, true));
        System.out.println("Minimum fuel is " + getFuel(nums, false));

        Util.end();
    }

    public static int getFuel(List<Integer> data, boolean part1) {
        int minDistance = Integer.MAX_VALUE;
        for (int i = data.stream().mapToInt(j -> j).min().getAsInt(); i <= data.stream().mapToInt(j -> j).max().getAsInt(); i++) {
            int distance = 0;
            for (Integer datum : data) {
                if(part1) {
                    distance += Math.abs(datum - i);
                } else {
                    int distanceToGo = Math.abs(datum - i);
                    for (int j = 1; j <= distanceToGo; j++) {
                        distance += j;
                    }
                }
            }
            if (distance < minDistance) {
                minDistance = distance;
            }
        }
        return minDistance;
    }
}
import java.util.ArrayList;
import java.util.List;

public class Puzzle1 {
    public static void main(String[] args) {
        Util.init();
        List<String> data = Util.load("puzzle1");

        int numIncrease = 0;
        for (int i = 1; i < data.size(); i++) {
            if (Integer.parseInt(data.get(i - 1)) < Integer.parseInt(data.get(i))) numIncrease++;
        }
        System.out.println("Number of increases is " + numIncrease);

        numIncrease = 0;
        for (int i = 0; i < data.size() - 3; i++) {
            int thisSum1 = Integer.parseInt(data.get(i)) + Integer.parseInt(data.get(i + 1)) + Integer.parseInt(data.get(i + 2));
            int thisSum2 = Integer.parseInt(data.get(i + 1)) + Integer.parseInt(data.get(i + 2)) + Integer.parseInt(data.get(i + 3));
            if (thisSum1 < thisSum2) numIncrease++;
        }
        System.out.println("Number of sliding increases is " + numIncrease);

        Util.end();
    }
}
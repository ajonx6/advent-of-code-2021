import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Puzzle8 {
    public static void main(String[] args) {
        Util.init();
        List<String> data = Util.load("puzzle8");

        int num1478 = 0;
        for (String line : data) {
            String[] parts = line.split(" \\| ");
            for (String s : parts[1].split(" ")) {
                if (s.length() == 2 || s.length() == 4 || s.length() == 3 || s.length() == 7) {
                    num1478++;
                }
            }
        }
        System.out.println("Number of 1, 4, 7, 8 is " + num1478);

        int overallSum = 0;
        for (String line : data) {
            String[] parts = line.split(" \\| ");
            HashMap<Integer, String> map = new HashMap<>();

            for (String s : parts[0].split(" ")) {
                if (s.length() == 2) {
                    map.put(1, s);
                } else if (s.length() == 3) {
                    map.put(7, s);
                } else if (s.length() == 4)  {
                    map.put(4, s);
                } else if (s.length() == 7) {
                    map.put(8, s);
                }
            }

            String ulandmid = map.get(4);
            for (char c : map.get(1).toCharArray()) {
                ulandmid = ulandmid.replace(c + "", "");
            }

            for (String s : parts[0].split(" ")) {
                if (s.length() == 5){
                    if (s.contains(ulandmid.substring(0, 1)) && s.contains(ulandmid.substring(1))) {
                        map.put(5, s);
                    } else if (s.contains(map.get(1).substring(0, 1)) && s.contains(map.get(1).substring(1))) {
                        map.put(3, s);
                    }
                } else if (s.length() == 6) {
                    if (!s.contains(ulandmid.substring(0, 1)) || !s.contains(ulandmid.substring(1))) {
                        map.put(0, s);
                    }
                    if (!s.contains(map.get(1).substring(0, 1)) || !s.contains(map.get(1).substring(1))) {
                        map.put(6, s);
                    }
                }
            }

            for (String s : parts[0].split(" ")) {
                if (s.length() == 5) {
                    if (!s.equals(map.get(3)) && !s.equals(map.get(5))) map.put(2, s);
                } else if (s.length() == 6) {
                    if (!s.equals(map.get(0)) && !s.equals(map.get(6))) map.put(9, s);
                }
            }

            HashMap<String, Integer> flip = new HashMap<>();
            for (int i : map.keySet()) {
                char[] cs = map.get(i).toCharArray();
                Arrays.sort(cs);
                flip.put(new String(cs), i);
            }

            String[] elems = parts[1].split(" ");
            int number = 0;
            for (int i = 0; i < 4; i++) {
                char[] cs = elems[i].toCharArray();
                Arrays.sort(cs);
                int num = flip.get(new String(cs));
                number += num * (int) Math.pow(10, 3 - i);
            }
            // System.out.println(number);
            overallSum += number;
        }

        System.out.println("Sum is " + overallSum);

        Util.end();
    }
}
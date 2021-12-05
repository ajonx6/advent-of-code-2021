import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Puzzle3 {
    public static void main(String[] args) {
        Util.init();
        List<String> data = Util.load("puzzle3");

        List<Pair<Integer,Integer>> bits = new ArrayList<>();
        for (int i = 0; i < data.get(0).length(); i++) {
            bits.add(new Pair<>(0, 0));
        }
        for (String line : data) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '0') bits.set(i, new Pair<>(bits.get(i).getKey() + 1, bits.get(i).getValue()));
                else bits.set(i, new Pair<>(bits.get(i).getKey(), bits.get(i).getValue() + 1));
            }
        }
        Collections.reverse(bits);
        int gamma = 0, epsilon = 0;
        for (int i = 0; i < bits.size(); i++) {
            Pair<Integer, Integer> pair = bits.get(i);
            if (pair.getKey() > pair.getValue()) {
                gamma += 0;
                epsilon += 1 << i;
            } else {
                gamma += 1 << i;
                epsilon += 0;
            }
        }
        System.out.println("Gamma is " + gamma + ", epsilon is " + epsilon + ", product is " + (gamma * epsilon));

        List<String> temp = new ArrayList<>(data);
        int index = 0;
        while (temp.size() > 1) {
            Iterator<String> it = temp.iterator();
            int numZeroes = 0, numOnes = 0;
            for (String line : temp) {
                if (line.charAt(index) == '1') numOnes ++;
                else numZeroes++;
            }
            char filter = numZeroes <= numOnes ? '0' :  '1';
            while (it.hasNext()) {
                String s = it.next();
                if (s.charAt(index) == filter) it.remove();
            }
            index++;
        }
        int oxygenRating = Integer.parseInt(temp.get(0), 2);

        temp = new ArrayList<>(data);
        index = 0;
        while (temp.size() > 1) {
            Iterator<String> it = temp.iterator();
            int numZeroes = 0, numOnes = 0;
            for (String line : temp) {
                if (line.charAt(index) == '1') numOnes ++;
                else numZeroes++;
            }
            char filter = numZeroes > numOnes ? '0' :  '1';
            while (it.hasNext()) {
                String s = it.next();
                if (s.charAt(index) == filter) it.remove();
            }
            index++;
        }
        int co2Rating = Integer.parseInt(temp.get(0), 2);
        System.out.println("Oxygen is " + oxygenRating + ", co2 is " + co2Rating + ", product is " + (oxygenRating * co2Rating));

        Util.end();
    }
}
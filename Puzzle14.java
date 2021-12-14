import java.util.*;

public class Puzzle14 {
    public static void main(String[] args) {
        Util.init();
        List<String> data = Util.load("puzzle14");

        String input = data.get(0);
        HashMap<String, Character> pairs = new HashMap<>();
        for (int i = 2; i < data.size(); i++) {
            String[] tokens = data.get(i).split(" -> ");
            pairs.put(tokens[0], tokens[1].charAt(0));
        }

        long diff1 = run(10, pairs, input);
        System.out.println("Difference is " + diff1);
        long diff2 = run(40, pairs, input);
        System.out.println("Difference is " + diff2);

        Util.end();
    }

    public static long run(int steps, HashMap<String, Character> pairs, String input) {
        HashMap<String, Long> pairOccurences = new HashMap<>();
        for (String s : pairs.keySet()) {
            pairOccurences.put(s, 0L);
        }
        for (int j = 0; j < input.length() - 1; j++) {
            pairOccurences.put(input.substring(j, j + 2), pairOccurences.get(input.substring(j, j + 2)) + 1);
        }

        HashMap<Character, Long> charAmts = calculateAmounts(pairOccurences, input);
        for (int i = 0; i < steps; i++) {
            HashMap<String, Long> newAmts = new HashMap<>();
            for (String s : pairs.keySet()) {
                newAmts.put(s, 0L);
            }

            for (String s : pairOccurences.keySet()) {
                if (pairOccurences.get(s) == 0) continue;
                String one = String.valueOf(s.charAt(0)) + pairs.get(s);
                String two = String.valueOf(pairs.get(s)) + s.charAt(1);
                newAmts.put(one, newAmts.get(one) + pairOccurences.get(s));
                newAmts.put(two, newAmts.get(two) + pairOccurences.get(s));
            }

            pairOccurences.clear();
            pairOccurences.putAll(newAmts);

            charAmts = calculateAmounts(pairOccurences, input);
        }
        long max = charAmts.values().stream().mapToLong(l -> l).max().getAsLong();
        long min = charAmts.values().stream().mapToLong(l -> l).min().getAsLong();

        return max - min;
    }

    public static HashMap<Character, Long> calculateAmounts(HashMap<String, Long> pairOccurences, String input) {
        HashMap<Character, Long> charAmts = new HashMap<>();
        for (String s : pairOccurences.keySet()) {
            if (pairOccurences.get(s) == 0) continue;
            if (!charAmts.containsKey(s.charAt(0))) charAmts.put(s.charAt(0), 0L);
            if (!charAmts.containsKey(s.charAt(1))) charAmts.put(s.charAt(1), 0L);
            charAmts.put(s.charAt(0), charAmts.get(s.charAt(0)) + pairOccurences.get(s));
            charAmts.put(s.charAt(1), charAmts.get(s.charAt(1)) + pairOccurences.get(s));
        }
        for (char c : charAmts.keySet()) {
            charAmts.put(c, charAmts.get(c) / 2L);
        }
        charAmts.put(input.charAt(0), charAmts.get(input.charAt(0)) + 1);
        charAmts.put(input.charAt(input.length() - 1), charAmts.get(input.charAt(input.length() - 1)) + 1);

        return charAmts;
    }
}
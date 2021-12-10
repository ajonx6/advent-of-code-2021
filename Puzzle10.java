import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Puzzle10 {
    public static void main(String[] args) {
        Util.init();
        List<String> data = Util.load("puzzle10");

        int sumOfCorruptChunks = 0;
        List<Long> incompleteChunks = new ArrayList<>();
        all: for (String line : data) {
            Stack<Character> charOpeningChunks = new Stack<>();
            for (char c : line.toCharArray()) {
                if (c == '[' || c == '{' || c == '(' || c == '<') charOpeningChunks.push(c);
                else if (c == ']') {
                    char d = charOpeningChunks.pop();
                    if (d != '[') {
                        sumOfCorruptChunks += 57;
                        continue all;
                    }
                } else  if (c == '}') {
                    char d = charOpeningChunks.pop();
                    if (d != '{') {
                        sumOfCorruptChunks += 1197;
                        continue all;
                    }
                } else if (c == ')') {
                    char d = charOpeningChunks.pop();
                    if (d != '(') {
                        sumOfCorruptChunks += 3;
                        continue all;
                    }
                } else if (c == '>') {
                    char d = charOpeningChunks.pop();
                    if (d != '<') {
                        sumOfCorruptChunks += 25137;
                        continue all;
                    }
                }
            }

            long sumOfChunkClosers = 0;
            while (!charOpeningChunks.isEmpty()) {
                char c = charOpeningChunks.pop();
                sumOfChunkClosers *= 5;
                if (c == '(') sumOfChunkClosers += 1;
                if (c == '[') sumOfChunkClosers += 2;
                if (c == '{') sumOfChunkClosers += 3;
                if (c == '<') sumOfChunkClosers += 4;
            }
            incompleteChunks.add(sumOfChunkClosers);
        }
        System.out.println("Total sum of corrupt chunks is " + sumOfCorruptChunks);

        Collections.sort(incompleteChunks);
        System.out.println("Median of incomplete chunk scores is " + incompleteChunks.get(incompleteChunks.size() / 2));

        Util.end();
    }
}
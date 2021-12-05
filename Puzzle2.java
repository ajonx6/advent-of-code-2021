import java.util.List;

public class Puzzle2 {
    public static void main(String[] args) {
        Util.init();
        List<String> data = Util.load("puzzle2");

        int depth = 0;
        int pos = 0;
        for (String line : data) {
            String[] tokens = line.split(" ");
            if (tokens[0].equals("forward")) pos += Integer.parseInt(tokens[1]);
            if (tokens[0].equals("down")) depth += Integer.parseInt(tokens[1]);
            if (tokens[0].equals("up")) depth -= Integer.parseInt(tokens[1]);
        }
        System.out.println("Product is " + (depth * pos));


        depth = 0;
        pos = 0;
        int aim = 0;
        for (String line : data) {
            String[] tokens = line.split(" ");
            if (tokens[0].equals("forward")) {
                pos += Integer.parseInt(tokens[1]);
                depth += aim * Integer.parseInt(tokens[1]);
            }
            if (tokens[0].equals("down")) aim += Integer.parseInt(tokens[1]);
            if (tokens[0].equals("up")) aim -= Integer.parseInt(tokens[1]);
        }
        System.out.println("Product is " + (depth * pos));

        Util.end();
    }
}
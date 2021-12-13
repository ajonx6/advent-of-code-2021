import javafx.util.Pair;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Puzzle13 {
    public static void main(String[] args) {
        Util.init();
        List<String> data = Util.load("puzzle13");

        List<Pair<Integer, Integer>> points = new ArrayList<>();
        List<Pair<Boolean, Integer>> flips = new ArrayList<>();
        boolean flipData = false;
        boolean part1 = false;
        for (String line : data) {
            if (line.trim().equals("")) {
                flipData = true;
            } else if (flipData) {
                Set<Pair<Integer, Integer>> temp = new HashSet<>();
                String[] tokens = line.split(" ")[2].split("=");
                int flipValue = Integer.parseInt(tokens[1]);
                if (tokens[0].equals("x")) {
                    for (Pair<Integer, Integer> p : points) {
                        if (p.getKey() < flipValue) temp.add(p);
                        else temp.add(new Pair<>(2 * flipValue - p.getKey(), p.getValue()));
                    }
                } else {
                    for (Pair<Integer, Integer> p : points) {
                        if (p.getValue() < flipValue) temp.add(p);
                        else temp.add(new Pair<>((int) p.getKey(), 2 * flipValue - p.getValue()));
                    }
                }
                points.clear();
                points.addAll(temp);
                if (!part1) {
                    System.out.println("Number of dots is " + points.size());
                    part1 = true;
                }
            } else {
                String[] tokens = line.split(",");
                points.add(new Pair<>(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1])));
            }
        }
        int maxX = points.stream().mapToInt(Pair::getKey).max().getAsInt();
        int maxY = points.stream().mapToInt(Pair::getValue).max().getAsInt();

        System.out.println("\n===================================================\n");

        StringBuilder activationCode = new StringBuilder();
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                activationCode.append(points.contains(new Pair<>(x, y)) ? '#' : '.');
            }
            System.out.println(activationCode);
            activationCode = new StringBuilder();
        }
        System.out.println();
        Util.end();
    }
}
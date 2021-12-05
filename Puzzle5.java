import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Puzzle5 {
    public static void main(String[] args) {
        Util.init();
        List<String> data = Util.load("puzzle5");

        List<Vent> vents = new ArrayList<>();
        for (String line : data) {
            String[] tokens = line.split(" -> ");
            String[] p1 = tokens[0].split(",");
            String[] p2 = tokens[1].split(",");
            Vent v = new Vent(new Point(Integer.parseInt(p1[0]), Integer.parseInt(p1[1])), new Point(Integer.parseInt(p2[0]), Integer.parseInt(p2[1])));
            vents.add(v);
        }

        int numOverlaps = part1(vents);
        System.out.println("Number of overlaps is " + numOverlaps);
        numOverlaps = part2(vents);
        System.out.println("Number of overlaps is " + numOverlaps);

        Util.end();
    }

    public static int part1(List<Vent> vents) {
        int[][] grid = new int[1000][1000];
        for (Vent v : vents) {
            Point p1 = v.start;
            Point p2 = v.end;
            if (v.start.getX() != v.end.getX() && v.start.getY() != v.end.getY()) continue;

            if (p1.getX() == p2.getX()) {
                for (int y = (int) Math.min(p1.getY(), p2.getY()); y <= Math.max(p1.getY(), p2.getY()); y++) {
                    grid[y][(int) p1.getX()]++;
                }
            } else {
                for (int x = (int) Math.min(p1.getX(), p2.getX()); x <= Math.max(p1.getX(), p2.getX()); x++) {
                    grid[(int) p1.getY()][x]++;
                }
            }
        }

        int numOverlap = 0;
        for (int[] ints : grid) {
            for (int anInt : ints) {
                if (anInt > 1) numOverlap++;
            }
        }

        return numOverlap;
    }

    public static int part2(List<Vent> vents) {
        int[][] grid = new int[1000][1000];
        for (Vent v : vents) {
            Point p1 = v.start;
            Point p2 = v.end;

            if (p1.getX() == p2.getX()) {
                for (int y = (int) Math.min(p1.getY(), p2.getY()); y <= Math.max(p1.getY(), p2.getY()); y++) {
                    grid[y][(int) p1.getX()]++;
                }
            } else if (p1.getY() == p2.getY()) {
                for (int x = (int) Math.min(p1.getX(), p2.getX()); x <= Math.max(p1.getX(), p2.getX()); x++) {
                    grid[(int) p1.getY()][x]++;
                }
            } else if (Math.abs(p1.getX() - p2.getX()) == Math.abs(p1.getY() - p2.getY())) {
                for (int i = 0; i <= Math.abs(p1.getX() - p2.getX()); i++) {
                    if (p2.getX() - p1.getX() > 0 && p2.getY() - p1.getY() > 0) {
                        grid[(int) p1.getY() + i][(int) p1.getX() + i]++;
                    } else if (p2.getX() - p1.getX() < 0 && p2.getY() - p1.getY() < 0) {
                        grid[(int) p1.getY() - i][(int) p1.getX() - i]++;
                    } else if (p2.getX() - p1.getX() > 0 && p2.getY() - p1.getY() < 0) {
                        grid[(int) p1.getY() - i][(int) p1.getX() + i]++;
                    } else if (p2.getX() - p1.getX() < 0 && p2.getY() - p1.getY() > 0) {
                        grid[(int) p1.getY() + i][(int) p1.getX() - i]++;
                    }
                }
            }
        }

        int numOverlap = 0;
        for (int[] ints : grid) {
            for (int anInt : ints) {
                if (anInt > 1) numOverlap++;
            }
        }

        return numOverlap;
    }

    public static class Vent {
        public Point start, end;

        public Vent(Point start, Point end) {
            this.start = start;
            this.end = end;
        }
    }
}
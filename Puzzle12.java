import java.awt.*;
import java.util.*;
import java.util.List;

public class Puzzle12 {
    public static void main(String[] args) {
        Util.init();
        List<String> data = Util.load("puzzle12");

        HashMap<String, List<String>> caves = new HashMap<>();
        for (String line : data) {
            String[] tokens = line.split("-");
            if (!caves.containsKey(tokens[0])) caves.put(tokens[0], new ArrayList<>());
            if (!caves.containsKey(tokens[1])) caves.put(tokens[1], new ArrayList<>());
            caves.get(tokens[0]).add(tokens[1]);
            caves.get(tokens[1]).add(tokens[0]);
        }

        int numPaths = run(caves, false);
        System.out.println("Number of paths is " + numPaths);
        numPaths = run(caves, true);
        System.out.println("Number of paths with one duplicate is " + numPaths);

        Util.end();
    }

    public static int run(HashMap<String, List<String>> caves, boolean part2) {
        int numPaths = 0;
        Set<String> startSeen = new HashSet<>();
        startSeen.add("start");
        List<Path> paths = new ArrayList<>();
        paths.add(new Path("start", startSeen, "", false));
        while (paths.size() > 0) {
            List<Path> nextPaths = new ArrayList<>();

            while (paths.size() > 0) {
                Path p = paths.remove(0);
                if (p.current.equals("end")) {
                    numPaths++;
                    continue;
                }
                List<Path> next = p.getNext(caves, part2);
                nextPaths.addAll(next);
            }

            paths.addAll(nextPaths);
        }
        return numPaths;
    }

    public static class Path {
        public Set<String> seen = new HashSet<>();
        public String current;
        public String path = "";
        public boolean usedTwice = false;

        public Path(String current, Set<String> seen, String oldPath, boolean usedTwice) {
            this.current = current;
            this.seen.addAll(seen);
            if (current.toLowerCase().equals(current)) this.seen.add(current);
            this.path = oldPath + current;
            this.usedTwice = usedTwice;
        }

        public List<Path> getNext(HashMap<String, List<String>> caves, boolean part2) {
            List<Path> res = new ArrayList<>();
            for (String s : caves.get(current)) {
                if (!seen.contains(s)) {
                    res.add(new Path(s, this.seen, path, usedTwice));
                } else if (part2 && s.toLowerCase().equals(s) && !s.equals("start") && !s.equals("end") && !usedTwice) {
                    Path newPath = new Path(s, this.seen, path, true);
                    res.add(newPath);
                }
            }
            return res;
        }
    }
}
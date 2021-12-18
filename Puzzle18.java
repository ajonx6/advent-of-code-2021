import java.util.List;
import java.util.Stack;

public class Puzzle18 {
    public static void main(String[] args) {
        Util.init();
        List<String> data = Util.load("puzzle18");

        Node sum = parseLine(data.get(0));
        for (int i = 1; i < data.size(); i++) {
            Node next = parseLine(data.get(i));
            sum = add(sum, next);
        }
        System.out.println("Magnitude is " + sum.getMagnitude());

        int maxMag = 0;
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.size(); j++) {
                if (i == j) continue;
                Node is = parseLine(data.get(i));
                Node js = parseLine(data.get(j));
                sum = add(is, js);
                int mag = sum.getMagnitude();
                if (mag > maxMag) maxMag = mag;
            }
        }
        System.out.println("Maximum magnitude is " + maxMag);

        Util.end();
    }

    public static Node parseLine(String line) {
        Stack<Node> stack = new Stack<>();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '[') stack.push(new Node(stack.size(), stack.size() > 0 ? stack.get(stack.size() - 1) : null));
            else if (c == ']') {
                if (stack.size() > 1) {
                    Node p = stack.pop();
                    stack.get(stack.size() - 1).setChild(p);
                }
            } else if (c != ',') {
                Node n = new Node(stack.size(), stack.size() > 0 ? stack.get(stack.size() - 1) : null);
                n.value = c - 48;
                stack.get(stack.size() - 1).setChild(n);
            }
        }

        return stack.pop();
    }

    public static Node add(Node sum, Node next) {
        Node newSum = new Node(-1, null);
        newSum.child1 = sum;
        newSum.child1.parent = newSum;
        newSum.child2 = next;
        newSum.child2.parent = newSum;
        newSum.deepen();

        while (true) {
            boolean exploded = explode(true, newSum);
            if (exploded) continue;
            boolean splitted = split(newSum);
            if (!splitted) break;
        }
        return newSum;
    }

    public static boolean explode(boolean keepSearching, Node node) {
        if (!keepSearching) return true;

        boolean exploded = false;
        if (!node.hasChildren()) return false;
        if (node.depth >= 4) {
            if (node.childrenAreValues()) {
                boolean exploded1 = search(node.child1.value, node, true);
                boolean exploded2 = search(node.child2.value, node, false);
                exploded = exploded1 || exploded2;
                Node replace = new Node(node.depth, node.parent);
                replace.value = 0;
                if (node.parent.child1 == node) node.parent.child1 = replace;
                else node.parent.child2 = replace;
            }
        } else if (!node.childrenAreValues()) {
            exploded = explode(true, node.child1) || explode(true, node.child2);
        }

        return exploded;
    }

    public static boolean split(Node node) {
        if (!node.hasChildren()) {
            if (node.value >= 10) {
                boolean c1 = node.parent.child1 == node;
                int oldValue = node.value;
                node = new Node(node.depth, node.parent);
                node.child1 = new Node(node.depth + 1, node);
                node.child2 = new Node(node.depth + 1, node);
                node.child1.value = (int) Math.floor((double) oldValue / 2.0);
                node.child2.value = (int) Math.ceil((double) oldValue / 2.0);
                if (c1) node.parent.child1 = node;
                else node.parent.child2 = node;
                return true;
            }
            return false;
        } else {
            boolean split = split(node.child1);
            if (!split) split = split(node.child2);
            return split;
        }
    }

    public static boolean search(int value, Node node, boolean left) {
        if (node.depth == 0) {
            return false;
        }

        if (!node.hasChildren()) {
            node.value += value;
            return true;
        }

        if (left) {
            if (node.parent.child1 == node) {
                return search(value, node.parent, left);
            } else {
                return goDownRight(value, node.parent.child1);
            }
        } else {
            if (node.parent.child2 == node) {
                return search(value, node.parent, left);
            } else {
                return goDownLeft(value, node.parent.child2);
            }
        }
    }

    public static boolean goDownLeft(int value, Node node) {
        if (!node.hasChildren()) {
            node.value += value;
            return true;
        } else {
            boolean done = goDownLeft(value, node.child1);
            if (!done) done = goDownLeft(value, node.child2);
            return done;
        }
    }

    public static boolean goDownRight(int value, Node node) {
        if (!node.hasChildren()) {
            node.value += value;
            return true;
        } else {
            boolean done = goDownRight(value, node.child2);
            if (!done) done = goDownRight(value, node.child1);
            return done;
        }
    }

    public static class Node {
        public int value = -1;
        public Node parent;
        public Node child1, child2;
        public int depth;

        public Node(int depth, Node parent) {
            this.depth = depth;
            this.parent = parent;
        }

        public void setChild(Node p) {
            if (child1 == null) child1 = p;
            else child2 = p;
        }

        public void deepen() {
            depth++;
            if (hasChildren()) {
                child1.deepen();
                child2.deepen();
            }
        }

        public int getMagnitude() {
            int mag = 0;
            if (!child1.hasChildren() && !child2.hasChildren()) {
                mag += 3 * child1.value;
                mag += 2 * child2.value;
            } else {
                if (child1.hasChildren()) {
                    mag += 3 * child1.getMagnitude();
                }
                if (child2.hasChildren()) {
                    mag += 2 * child2.getMagnitude();
                }
            }
            return mag;
        }

        public boolean childrenAreValues() {
            return (child1 != null && child1.value >= 0) && (child2 != null && child2.value >= 0);
        }

        public boolean hasChildren() {
            return child1 != null && child2 != null;
        }

        public String toString() {
            if (value >= 0) {
                return Integer.toString(value);
            } else {
                return "[" + child1.toString() + "," + child2.toString() + "]";
            }
        }
    }
}
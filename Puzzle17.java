import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Puzzle17 {
    public static void main(String[] args) {
        Util.init();
        String data = Util.load("puzzle17").get(0);
        String[] tokens = data.split(" ");
        int minX = Integer.parseInt(tokens[2].split("=")[1].split("\\.\\.")[0]);
        int maxX = Integer.parseInt(tokens[2].split("=")[1].split("\\.\\.")[1].substring(0, tokens[2].split("=")[1].split("\\.\\.")[1].length() - 1));
        int minY = Integer.parseInt(tokens[3].split("=")[1].split("\\.\\.")[0]);
        int maxY = Integer.parseInt(tokens[3].split("=")[1].split("\\.\\.")[1]);

        int maxYHeight = 0;
        int numVelocities = 0;
        for (int vvx = 1; vvx < 1000; vvx++) {
            all: for (int vvy = -1000; vvy < 1000; vvy++) {
                int vx = vvx;
                int vy = vvy;
                int x = 0, y = 0;
                int potentialY = 0;

                while (true) {
                    x += vx;
                    y += vy;
                    vx = (int) (Math.signum(vx) * (Math.abs(vx) - 1));
                    vy -= 1;
                    if (vy == 0) potentialY = y;

                    if (minX <= x && x <= maxX && minY <= y && y <= maxY) {
                        if (potentialY > maxYHeight) maxYHeight = potentialY;
                        numVelocities++;
                        continue all;
                    } else if (x > maxX || y < minY) {
                        continue all;
                    }
                }
            }
        }

        System.out.println("Maximum y height is " + maxYHeight);
        System.out.println("Number of velocities in area is " + numVelocities);

        Util.end();
    }
}
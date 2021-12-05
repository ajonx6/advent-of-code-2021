import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Util {
	public static double start;

	public static void init() {
		start = System.currentTimeMillis();
	}

	public static List<String> load(String name) {
		List<String> data = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(new File("res/" + name + ".txt")))) {
			String st = "";
			while ((st = br.readLine()) != null) {
				data.add(st);
			}
			return data;
		} catch (IOException e) {
			System.err.println("Could not load: res/" + name + ".txt");
			System.exit(1);
		}
		return null;
	}

	public static void end() {
		System.out.println("Time taken: " + (System.currentTimeMillis() - start) + "ms\n");
	}
}
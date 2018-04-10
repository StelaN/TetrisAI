import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Util {
	private static final int MAX_INDEX = 3;

	public static int getRandomNumber(int min, int max) {
		Random rand = new Random();
		return min + rand.nextInt(max + 1 - min);
	}

	public static Rotation getRotationByCode(int rotationCode) {
		for (Rotation rotation : Rotation.values()) {
			if (rotation.ordinal() == rotationCode) {
				return rotation;
			}
		}
		return null;
	}

	public static Figure[] loadAllFigures() {
		// Map<Integer, Figure> allFigures = new HashMap<>();
		Figure allFigures[] = new Figure[8];
		allFigures[0] = null;
		allFigures[1] = new Figure(new HashMap<Rotation, List<Block>>() {
			{
				put(Rotation._0, Arrays.asList(new Block(0, 0), new Block(1, 0), new Block(2, 0), new Block(3, 0)));
				put(Rotation._90, Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(0, 2), new Block(0, 3)));
			}
		});

		allFigures[2] = new Figure(new HashMap<Rotation, List<Block>>() {
			{
				put(Rotation._0, Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(1, 0), new Block(2, 0)));
				put(Rotation._90, Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(0, 2), new Block(1, 2)));
				put(Rotation._180, Arrays.asList(new Block(0, 1), new Block(1, 1), new Block(2, 0), new Block(2, 1)));
				put(Rotation._270, Arrays.asList(new Block(0, 0), new Block(1, 0), new Block(1, 1), new Block(1, 2)));
			}
		});

		allFigures[3] = new Figure(new HashMap<Rotation, List<Block>>() {
			{
				put(Rotation._0, Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(1, 1), new Block(2, 1)));
				put(Rotation._90, Arrays.asList(new Block(0, 2), new Block(1, 0), new Block(1, 1), new Block(1, 2)));
				put(Rotation._180, Arrays.asList(new Block(0, 0), new Block(1, 0), new Block(2, 0), new Block(2, 1)));
				put(Rotation._270, Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(0, 2), new Block(1, 0)));
			}
		});

		allFigures[4] = new Figure(new HashMap<Rotation, List<Block>>() {
			{
				put(Rotation._0, Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(1, 0), new Block(1, 1)));
			}
		});

		allFigures[5] = new Figure(new HashMap<Rotation, List<Block>>() {
			{
				put(Rotation._0, Arrays.asList(new Block(0, 0), new Block(1, 0), new Block(1, 1), new Block(2, 1)));
				put(Rotation._90, Arrays.asList(new Block(0, 1), new Block(0, 2), new Block(1, 0), new Block(1, 1)));
			}
		});

		allFigures[6] = new Figure(new HashMap<Rotation, List<Block>>() {
			{
				put(Rotation._0, Arrays.asList(new Block(0, 0), new Block(1, 0), new Block(1, 1), new Block(2, 0)));
				put(Rotation._90, Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(0, 2), new Block(1, 1)));
				put(Rotation._180, Arrays.asList(new Block(0, 1), new Block(1, 0), new Block(1, 1), new Block(2, 1)));
				put(Rotation._270, Arrays.asList(new Block(0, 1), new Block(1, 0), new Block(1, 1), new Block(1, 2)));
			}
		});

		allFigures[7] = new Figure(new HashMap<Rotation, List<Block>>() {
			{
				put(Rotation._0, Arrays.asList(new Block(0, 1), new Block(1, 0), new Block(1, 1), new Block(2, 0)));
				put(Rotation._90, Arrays.asList(new Block(0, 0), new Block(0, 1), new Block(1, 1), new Block(1, 2)));
			}
		});
		return allFigures;
	}

	public static void printAllFigures(Map<Integer, Figure> allFigures) {
		allFigures.forEach((number, figure) -> {
			System.out.println("Figure " + number);
			figure.printAllRotations();
		});
	}

	public static void printFigure(List<Block> blocks) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i <= MAX_INDEX; i++) {
			for (int j = 0; j <= MAX_INDEX; j++) {
				if (blocks.contains(new Block(i, j))) {
					sb.append("#");
				} else {
					sb.append(" ");
				}
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}
}

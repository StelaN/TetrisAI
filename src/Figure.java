import java.util.List;
import java.util.Map;

public class Figure {

	private Map<Rotation, List<Block>> rotations;

	public Figure(Map<Rotation, List<Block>> rotations) {
		this.rotations = rotations;
	}

	public Map<Rotation, List<Block>> getRotations() {
		return rotations;
	}

	public void setRotations(Map<Rotation, List<Block>> rotations) {
		this.rotations = rotations;
	}

	public List<Block> getFigureWithRotation(Rotation rotation) {
		if (rotation == null || !rotations.containsKey(rotation)) {
			System.err.println("Invalid rotation " + rotation + " for figure");
			return rotations.get(Rotation._0);
		}
		return rotations.get(rotation);
	}

	public List<Block> getFigureWithRotationCode(int rotation) {
		if (rotation < 0 || rotation > 3) {
			System.out.println("totally wrong rotation " + rotation);
			return rotations.get(Rotation._0);
		}
		if (rotations.size() == 1) {
			return rotations.get(Rotation._0);
		}
		if (rotations.size() == 2) {
			if (rotation % 2 == 0) {
				return rotations.get(Rotation._0);
			} else {
				return rotations.get(Rotation._90);
			}
		}
		return rotations.get(Util.getRotationByCode(rotation));
	}

	public void printAllRotations() {
		rotations.forEach((rotation, figure) -> {
			System.out.println(rotation);
			Util.printFigure(figure);
		});
	}

}

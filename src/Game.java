import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Game {
	private static final int N = 20;
	private static final int M = 10;
	private static final int INVALID_ROW = -1;

	private boolean isGameOver = false;
	private int score = 0;

	private List<Block> board;
	private Queue<Integer> nextFigures;
	private Figure[] allFigures;

	public Game(Queue<Integer> nextFigures) {
		board = new LinkedList<>();
		allFigures = Util.loadAllFigures();
		this.nextFigures = nextFigures;
	}

	public int getScore() {
		return score;
	}

	public void printBoard() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			sb.append("|");
			for (int j = 0; j < M; j++) {
				if (board.contains(new Block(i, j))) {
					sb.append("#");
				} else {
					sb.append(" ");
				}
			}
			sb.append("|");
			sb.append("\n");
		}
		sb.append(" ");
		for (int i = 0; i < M; i++) {
			sb.append("-");
		}
		sb.append("\n ");
		for (int i = 0; i < M; i++) {
			sb.append(i);
		}
		System.out.println(sb.toString());
	}

	private boolean canPut(List<Block> figure, int row, int col) {
		for (Block figureBlock : figure) {
			if (figureBlock.getX() + row >= N) {
				// System.out.println("Figure sticks out from under");
				return false;
			}
			if (figureBlock.getY() + col >= M) {
				System.out.println("Figure sticks out from right");
				return false;
			}
			if (board.contains(new Block(figureBlock.getX() + row, figureBlock.getY() + col))) {
				// System.out.println(
				// "Board(" + (figureBlock.getX() + row) + ", " +
				// (figureBlock.getY() + col) + ") is taken");
				return false;
			}
		}
		return true;
	}

	private int findFreeRowAtColumn(List<Block> figure, int col) {
		int freeRow = INVALID_ROW;
		for (int row = 0; row < N; row++) {
			if (canPut(figure, row, col)) {
				freeRow = row;
			} else {
				break;
			}
		}
		return freeRow;
	}

	private boolean putFigureInBoard(List<Block> figure, int col) {
		int row = findFreeRowAtColumn(figure, col);
		if (row == INVALID_ROW) {
			return false;
		}
		for (Block block : figure) {
			board.add(new Block(block.getX() + row, block.getY() + col));
		}
		score++;
		return true;
	}

	private void clearRowFromBoard(int row) {
		for (int col = 0; col < M; col++) {
			board.remove(new Block(row, col));
		}
	}

	private void fixBlocksOverClearedRow(int row) {
		board.forEach(block -> {
			if (block.getX() < row)
				block.setX(block.getX() + 1);
		});
	}

	private int clearFullRows() {
		int clearedRowsCount = 0;
		for (int row = N - 1; row >= 0; row--) {
			boolean rowFull = true;
			for (int col = 0; col < M; col++) {
				if (!board.contains(new Block(row, col))) {
					rowFull = false;
					break;
				}
			}
			if (rowFull) {
				clearRowFromBoard(row);
				printBoard();
				fixBlocksOverClearedRow(row);
				clearedRowsCount++;
				score += 10;
			}
		}
		return clearedRowsCount;
	}

	private void fixBoard() {
		List<Block> currentBoard = new LinkedList<>();
		currentBoard.addAll(board);
		while (!currentBoard.isEmpty()) {
			Block currentBlock = currentBoard.remove(0);
			List<Block> connectedBlocks = new LinkedList<>();
			connectedBlocks.addAll(getConnectedBlocks(currentBlock, currentBoard));
			// System.out.println("connected figure: ");
			// printFigure(connectedFigure);
			board.removeAll(connectedBlocks);
			// System.out.println("Board without the figure");
			// printBoard();
			putFigureInBoard(connectedBlocks, 0);
			// System.out.println("Board after the figure was placed again");
			// printBoard();
		}
	}

	private Set<Block> getConnectedBlocks(Block block, List<Block> currentBoard) {
		Set<Block> connectedBlocks = new HashSet<>();
		List<Block> neightbours = getNeighbouringBlocks(block, currentBoard);
		if (neightbours.size() == 0) {
			connectedBlocks.add(block);
			return connectedBlocks;
		}
		for (Block neighbour : neightbours) {
			if (currentBoard.contains(neighbour)) {
				currentBoard.remove(neighbour);
				connectedBlocks.add(block);
				connectedBlocks.addAll(getConnectedBlocks(neighbour, currentBoard));
			}
		}
		return connectedBlocks;
	}

	private List<Block> getNeighbouringBlocks(Block block, List<Block> currentBoard) {
		List<Block> neighbours = new LinkedList<>();
		// up
		if (currentBoard.contains(new Block(block.getX() - 1, block.getY())))
			neighbours.add(new Block(block.getX() - 1, block.getY()));
		// right
		if (currentBoard.contains(new Block(block.getX(), block.getY() + 1)))
			neighbours.add(new Block(block.getX(), block.getY() + 1));
		// down
		if (currentBoard.contains(new Block(block.getX() + 1, block.getY())))
			neighbours.add(new Block(block.getX() + 1, block.getY()));
		// left
		if (currentBoard.contains(new Block(block.getX(), block.getY() - 1)))
			neighbours.add(new Block(block.getX(), block.getY() - 1));
		return neighbours;
	}

	public void playGame() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			printBoard();
			System.out.println("Enter figure number [1,7]");
			int figureCode = scanner.nextInt();
			System.out.println("Enter figure rotation [0,3]");
			int rotationCode = scanner.nextInt();
			System.out.println("Enter column number [0," + (M - 1) + "]");
			int col = scanner.nextInt();
			Rotation rotation = Util.getRotationByCode(rotationCode);
			// List<Block> rotatedFigure =
			// allFigures.get(figureCode).getFigureWithRotation(rotation);
			List<Block> rotatedFigure = allFigures[figureCode].getFigureWithRotation(rotation);
			if (putFigureInBoard(rotatedFigure, col)) {
				while (clearFullRows() > 0) {
					fixBoard();
				}
			} else {
				System.out.println("Game over");
				break;
			}
		}
		scanner.close();
	}

	public void playAIgame() {
		while (!nextFigures.isEmpty()) {
			// Figure figure = allFigures.get(nextFigures.remove());
			Figure figure = allFigures[nextFigures.remove()];
			int col = Util.getRandomNumber(0, 8);
			int rotation = Util.getRandomNumber(0, 3);
			List<Block> figureWithRotation = figure.getFigureWithRotationCode(rotation);
			if (putFigureInBoard(figureWithRotation, col)) {
				while (clearFullRows() > 0) {
					fixBoard();
				}
			} else {
				System.out.println("Game over");
				break;
			}
		}
	}
}

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {

	public static void main(String[] args) {
		System.out.println("Tetris game");

		// testGame();
		testAIGame();

		// Scanner scanner = new Scanner(System.in);
		// int n = scanner.nextInt();
		// Queue<Integer> nextFigures = new ArrayBlockingQueue<Integer>(n);
		// for (int i = 0; i < n; i++) {
		// nextFigures.add(scanner.nextInt());
		// }

	}

	private static void testAIGame() {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		Queue<Integer> nextFigures = new ArrayBlockingQueue<Integer>(n);
		for (int i = 0; i < n; i++) {
			int number = Util.getRandomNumber(1, 7);
			nextFigures.add(number);
		}
		Game tetris = new Game(nextFigures);
		tetris.playAIgame();
		tetris.printBoard();
		System.out.println("score: " + tetris.getScore());
	}

	private static void testGame() {
		Game tetrisGame = new Game(null);
		tetrisGame.playGame();
	}

}

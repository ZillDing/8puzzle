package ai.eightpuzzle;
import java.util.Scanner;

public class EightPuzzle {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

		introduction();

		int size = PuzzleState.PUZZLE_SIZE;
		int[] input = new int[size]; // to hold user inputs of 9 int

		// read in all int
		System.out.println("Please enter the initial state:");
		for (int i = 0; i < size; i++) {
			input[i] = in.nextInt();
		}

		// call PuzzleRunner to solve it
		PuzzleSolver solver = new PuzzleSolver(input); // instantiate
		String path = solver.solve();

		// output control.
		System.out.println();

		if (path == null) {
			System.out.println("Solution is impossible");
		} else {
			System.out.println("The path is:");
			System.out.println(path);
		}

	}

	// Explain how to input,
	// and what output to expect.
	private static void introduction() {

	}

}

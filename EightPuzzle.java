import java.util.Scanner;

/* this class is to run the puzzle solver
 * Author: Ding Zeyu
 */
public class EightPuzzle {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
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
}

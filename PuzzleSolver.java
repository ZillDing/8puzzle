import java.util.ArrayList;
import java.util.Collections;

//this is the concrete controller class,
//which does all the logic work,
//including using Puzzle object.

//It accepts the array of initial state,
//and output the path.
public class PuzzleSolver {
	private PuzzleState initState;
	private PuzzleState goalState;

	private ArrayList<PuzzleState> visited; // close-list
	private ArrayList<PuzzleState> candidates;

	public PuzzleSolver(int[] arr) {
		initState = new PuzzleState(arr);
		// init the goal state
		int[] goal = { 1, 2, 3, 8, 0, 4, 7, 6, 5 };
		goalState = new PuzzleState(goal);
		// close-list is an arraylist of states,
		// which mimics a set
		visited = new ArrayList<PuzzleState>();

		// open-list is also an arraylist, which
		// mimics a queue
		candidates = new ArrayList<PuzzleState>();
	}

	public String solve() {

		// position the initial state to the start
		// of the queue
		candidates.add(initState);

		// check the candidates in open-list,
		// until no more candidates
		while (!candidates.isEmpty()) {
			// take out the front of the queue,
			// and mark it as visited
			PuzzleState candidate = candidates.remove(0);

			if (!visited.contains(candidate)) {
				visited.add(candidate);
			}

			// is this state the goal state?
			if (candidate.equals(goalState)) {
				return candidate.getPath();
			}

			// get all possible unvisited moves
			ArrayList<PuzzleState> possibleMoves = candidate.children();
			ArrayList<PuzzleState> nextMoves = new ArrayList<PuzzleState>(); // possible unvisited moves

			for (PuzzleState move : possibleMoves) {
				if (!visited.contains(move)) { // the move is not visited yet.
					nextMoves.add(move);
				}
			}

			// if empty, no need to continue the current loop
			if (nextMoves.isEmpty()) {
				continue;
			}
			// put them into the open list,
			// and sort the list
			candidates.addAll(nextMoves); // append to the end
			Collections.sort(candidates, new HeuristicComparator());

		}
		// if ever jumped out of the queue,
		// it means the goalState is not found.
		return null;
	}
}

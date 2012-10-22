package ai.eightpuzzle;
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

		// init open and close lists

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
			ArrayList<PuzzleState> nextMoves = new ArrayList<PuzzleState>(); // possible
																				// unvisited
																				// moves

			for (PuzzleState move : possibleMoves) {
				if (!visited.contains(move)) { // the move is not visited yet.
					nextMoves.add(move);
				}
			}

			// if empty, no need to continue the current loop
			if (nextMoves.isEmpty()) {
				continue;
			}
			
			/* not need any longer
			// calculate the heuristic value of all states
			// that are going to be in the open list.

			for (PuzzleState move : nextMoves) {
				setHeuristicValue(move);
			}
			*/

			// put them into the open list,
			// and sort the list

			candidates.addAll(nextMoves); // append to the end
			Collections.sort(candidates, new HeuristicComparator());

		}

		// if ever jumped out of the queue,
		// it means the goalState is not found.
		return null;

	}
	
	/* not used any more since got substitution
	// use the 'goalState' as a reference, and
	// set the heuristic value of this state.
	private void setHeuristicValue(PuzzleState state) {
		int count = 0;
		int[] curTiles = state.getTiles();
		int[] refTiles = goalState.getTiles();

		for (int i = 0; i < PuzzleState.PUZZLE_SIZE; i++) {
			int cur, ref;
			cur = curTiles[i];
			ref = refTiles[i];

			// misplacement other than black tile
			if (cur != 0 && cur != ref) {
				count++;
			}
		}

		state.setHeuristic(count);
	}
	*/
}
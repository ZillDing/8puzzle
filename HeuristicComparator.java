import java.util.Comparator;

public class HeuristicComparator implements Comparator<PuzzleState> {
	// compare the A* value, including heuristic value
	// and greedy value
	@Override
	public int compare(PuzzleState obj1, PuzzleState obj2) {

		int value1 = obj1.getLevel() + obj1.getOutOfPlace();
		int value2 = obj2.getLevel() + obj2.getOutOfPlace();

		if (value1 == value2)
			return 0;
		else if (value1 < value2)
			return -1;
		else
			return 1;
	}
}

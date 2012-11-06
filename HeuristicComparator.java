import java.util.Comparator;

/* this class is to compare the heuristic value of two states
 * Author: Ding Zeyu
 */
public class HeuristicComparator implements Comparator<PuzzleState> {
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

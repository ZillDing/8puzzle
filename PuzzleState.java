import java.util.ArrayList;
import java.util.Arrays;

// this class records the state of the puzzle,
// represented by an linear array
public class PuzzleState {
	// size of the puzzle
	public static final int PUZZLE_SIZE = 9;

	// numbers on the tiles
	public static final int BLACK_TILE = 0;

	// These constants are number of indexes
	// 'pos' need to move to reach the semantic
	// position on the 8-puzzle

	public static final int LEFT = -1;
	public static final int RIGHT = 1;
	public static final int UP = -3;
	public static final int DOWN = 3;

	private int[] currentState; // the puzzle's current state
	private int pos; // pos of the black_tile

	private String path; // the path been generated

	private int level; // level of this state in relation to the root

	private int outOfPlace = 0;

	private final int[] GOAL = new int[]{1, 2, 3, 8, 0, 4, 7, 6, 5};

	private int manDist = 0;

	// this is the constructor
	public PuzzleState(int[] board) {
		// init the current state
		int size = board.length;

		currentState = new int[size];

		for (int i = 0; i < size; i++) {
			int val = board[i];
			currentState[i] = val;

			if (val == BLACK_TILE)
				pos = i; // init 'pos'
		}

		// init the path,
		// for state generated in this way,
		// it's empty
		path = "";
		level = 0;
		// get the number of the tiles which are out of place
		setOutOfPlace();
		setManDist();

	}

	private void setManDist() {
		int index = -1;
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				index++;
				int val = (currentState[index] - 1);
				if (val != -1) {
					int horiz = val % 3;
					int vert = val /3;
					manDist  = Math.abs(vert - y) + Math.abs(horiz - x);
				}
			}
		}

	}

	private void setOutOfPlace() {
		for (int i = 0; i < currentState.length; i++) {
			if (currentState[i] != GOAL[i]) {
				outOfPlace++;
			}
		}
	}

	// copy the state, and make a new PuzzleState object
	public PuzzleState(PuzzleState another) {

		this.currentState = another.copyOfTiles(); // 'tiles' is array, cannot be assigned directly
		this.pos = another.pos;
		this.path = another.path;
		this.level = another.level;
		setOutOfPlace();
		setManDist();
	}

	// check whether two puzzle objects
	// have the same sate.
	// this method can be generically
	// used by other classes to compare
	// two Puzzle objects
	@Override
	public boolean equals(Object another) {

		// check extreme case
		if (another == null)
			return false;
		else if ((another instanceof PuzzleState) && (Arrays.equals(currentState, ((PuzzleState) another).getCurrentState())))
			return true;
		else
			return false;
	}

	public int[] getCurrentState() {
		return currentState;
	}

	public String getPath() {
		return path;
	}

	public void appendToPath(int direction) {
		switch (direction) {
		case LEFT:
			path = path.concat("left" + " ");
			break;
		case RIGHT:
			path = path.concat("right" + " ");
			break;
		case UP:
			path = path.concat("up" + " ");
			break;
		case DOWN:
			path = path.concat("down" + " ");
			break;
		default: // throw exception if not valid
			throw new RuntimeException(
					"invalid path information tries to be added");
		}
	}

	public int getLevel() {
		return level;
	}

	// allows increment on the 'level' variable
	public void increaseLevel() {
		level++;
	}

	public int[] getTiles() {
		return copyOfTiles();
	}

	private int[] copyOfTiles() {
		int[] copy = new int[PUZZLE_SIZE];

		for (int i = 0; i < PUZZLE_SIZE; i++) {
			copy[i] = currentState[i];
		}

		return copy;
	}

	// all possible next moves from current state
	// return an Arraylist containing all of the successors for that state
	public ArrayList<PuzzleState> children() {

		ArrayList<PuzzleState> childr = new ArrayList<PuzzleState>();

		// check left/right/up/down
		// a local variable represent the next state
		PuzzleState child;

		if (canMoveLeft()) {
			child = bornChild(LEFT);
			childr.add(child);
		}

		if (canMoveRight()) {
			child = bornChild(RIGHT);
			childr.add(child);
		}

		if (canMoveUp()) {
			child = bornChild(UP);
			childr.add(child);
		}

		if (canMoveDown()) {
			child = bornChild(DOWN);
			childr.add(child);
		}

		return childr;
	}

	// born a child state from this state:
	// copy a new state from this state,
	// swap the tiles according to input.
	private PuzzleState bornChild(int direction) {

		// make a copy
		PuzzleState child = new PuzzleState(this);

		child.increaseLevel();

		child.appendToPath(direction);

		// swap the tiles
		child.swapTiles(direction);

		return child;
	}

	// a small method just help to move
	private void move(int direction) {
		int tmp = currentState[pos + direction];
		currentState[pos + direction] = currentState[pos];
		currentState[pos] = tmp;
		pos += direction;
	}

	// black tile is identified by 'pos',
	// and the other is identified by direction
	private void swapTiles(int direction) {

		switch (direction) {
		case LEFT:
			if (canMoveLeft()) {
				move(LEFT);
			}
			break;
		case RIGHT:
			if (canMoveRight()) {
				move(RIGHT);
			}
			break;
		case UP:
			if (canMoveUp()) {
				move(UP);
			}
			break;
		case DOWN:
			if (canMoveDown()) {
				move(DOWN);
			}
			break;
		default:
			throw new RuntimeException("invalid direction");
		}

		if (currentState[pos] != BLACK_TILE) {
			throw new RuntimeException("pos var at wrong place!");
		}
	}

	private boolean canMoveLeft() {

		if (pos % 3 == 0)
			return false;
		else
			return true;
	}

	private boolean canMoveRight() {

		if (pos % 3 ==2) {
			return false;
		} else
			return true;
	}

	private boolean canMoveUp() {

		if (pos < 3) {
			return false;
		} else
			return true;
	}

	private boolean canMoveDown() {
		if (pos > 5) {
			return false;
		} else
			return true;
	}

	public int getOutOfPlace() {
		return outOfPlace;
	}

	public int getManDist() {
		return manDist;
	}
}

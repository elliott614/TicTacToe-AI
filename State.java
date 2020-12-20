import java.util.ArrayList;
import java.util.List;

public class State {
	private Board board;
	private List<Integer> backtracker;
	private int alphaBeta;
	private List<State> next;
	public static final char X = 'X';
	public static final char Y = 'Y';
	private boolean pruningEnabled;

	public List<State> next() {
		return this.next;
	}

	public int alphaBeta() {
		return this.alphaBeta;
	}

	public List<Integer> backtracker() {
		return this.backtracker;
	}

	public Board board() {
		return this.board;
	}

	// public constructor, with specified first move
	public State(int firstMove, boolean enablePruning) {
		this.pruningEnabled = enablePruning;
		this.board = new Board();
		this.backtracker = new ArrayList<Integer>();
		this.backtracker.add(firstMove);
		this.board.move(firstMove);
		this.next = this.findNextStates();
		System.out.println("Finished constructing all successor states");
	}

	// private constructor for creating next states
	private State(Board board, List<Integer> backtracker) {
		this.board = board;
		this.backtracker = backtracker;
		this.next = this.findNextStates();
	}

	// Returns list of next states
	private List<State> findNextStates() {
		ArrayList<State> states = new ArrayList<State>();
		if (!this.board.isOver()) {
			int v; // initialize minimax value
			if (this.board.currentPlayer() == X)
				v = -2;
			else if (this.board.currentPlayer() == Y)
				v = 2;
			else
				v = 0;
			for (int elem : this.board.remainingCells()) {
				Board newBoard = this.board.clone();
				if (newBoard.move(elem)) {
					ArrayList<Integer> newBacktracker = new ArrayList<Integer>(this.backtracker);
					newBacktracker.add(elem);
					State newState = new State(newBoard, newBacktracker);
					if (!pruningEnabled) // if pruning not enabled, always add successor states
						states.add(newState);
					if (this.board.currentPlayer() == X && newState.alphaBeta > v) {
						if (pruningEnabled) // if pruning enabled, ignore successor states with lower alpha-beta
							states.add(newState);
						v = newState.alphaBeta;
						if (v == 1 && pruningEnabled)
							break; // if pruning enabled, quit looking if v already equals 1
					} else if (this.board.currentPlayer() == Y && newState.alphaBeta < v) {
						if (pruningEnabled) // if pruning enabled, ignore successor states with higher alpha-beta
							states.add(newState);
						v = newState.alphaBeta;
						if (v == -1 && pruningEnabled)
							break; // if pruning enabled, quit looking if v already equals -1
					}
				}
			}
		}
		// if game is over, set alpha-beta
		else {
			if (this.board.winner() == X)
				this.alphaBeta = 1;
			else if (this.board.winner() == Y)
				this.alphaBeta = -1;
			else
				this.alphaBeta = 0;
		}

		return states;
	}

}

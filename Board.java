import java.util.ArrayList;
import java.util.List;

public class Board {
	private char[] config;
	public static final char X = 'X';
	public static final char Y = 'Y';
	private char winner;
	private boolean gameIsOver;
	private char currentPlayer;
	private List<Integer> remainingCells;

	public Board() {
		this.config = new char[9];
		this.winner = '?';
		this.gameIsOver = false;
		this.currentPlayer = X;
		this.remainingCells = new ArrayList<Integer>();
		for (int i = 0; i < 9; i++) {
			this.config[i] = '?'; // '?' signifies a blank cell
			this.remainingCells.add(i);
		}
	}

	// private constructor for implementation of clone method
	private Board(char[] config, char winner, boolean gameIsOver, char currentPlayer, List<Integer> remainingCells) {
		this.config = config.clone();
		this.winner = winner;
		this.gameIsOver = gameIsOver;
		this.currentPlayer = currentPlayer;
		this.remainingCells = new ArrayList<Integer>(remainingCells);
	}

	@Override
	public Board clone() {
		return new Board(this.config, this.winner, this.gameIsOver, this.currentPlayer, this.remainingCells);
	}

	// rows and columns are 1, 2, or 3. Returns true if move was valid
	public boolean move(int row, int col) {
		if (this.gameIsOver) {
			System.out.println("Game is over. No more moves allowed.");
			return false;
		}

		if (row > 0 && row < 4 && col > 0 && col < 4 && this.config[3 * (row - 1) + col - 1] == '?')
			this.config[3 * (row - 1) + col - 1] = this.currentPlayer;
		else {
			System.out.println("Invalid move. Cell is occupied.");
			this.printBoard();
			return false;
		}
//		System.out.println("" + this.currentPlayer + " played (row " + row + ", column " + col + ")");
//		this.printBoard();
		this.checkIfOver();
//		if (this.gameIsOver) {
//			if (this.winner == '?')
//				System.out.println("Game has ended in a draw");
//			else
//				System.out.println("" + this.winner + " has won the game! Congrats player " + this.winner + "!");
//		}

		// update current player
		if (this.currentPlayer == X)
			this.currentPlayer = Y;
		else if (this.currentPlayer == Y)
			this.currentPlayer = X;

		// update remaining cells
		this.remainingCells.removeIf(elem -> elem == (3 * (row - 1) + col - 1));

		return true;
	}

	// move to cell numbered 0-8
	public boolean move(int move) {
		return move(move / 3 + 1, move % 3 + 1);
	}

	public boolean isOver() {
		return this.gameIsOver;
	}

	public char winner() {
		return this.winner;
	}

	public char currentPlayer() {
		return this.currentPlayer;
	}

	public List<Integer> remainingCells() {
		return this.remainingCells;
	}

	private void checkIfOver() {
		// look for winner in rows/columns
		for (int i = 1; i <= 3; i++) {
			if (this.getConfig(1, i) == X && this.getConfig(2, i) == X && this.getConfig(3, i) == X) { // if column of
																										// all Xs
				this.winner = X;
				this.gameIsOver = true;
				return;
			}
			if (this.getConfig(i, 1) == X && this.getConfig(i, 2) == X && this.getConfig(i, 3) == X) { // if row of all
																										// Xs
				this.winner = X;
				this.gameIsOver = true;
				return;
			}
			if (this.getConfig(1, i) == Y && this.getConfig(2, i) == Y && this.getConfig(3, i) == Y) { // if column of
																										// all Ys
				this.winner = Y;
				this.gameIsOver = true;
				return;
			}
			if (this.getConfig(i, 1) == Y && this.getConfig(i, 2) == Y && this.getConfig(i, 3) == Y) { // if row of all
																										// Ys
				this.winner = Y;
				this.gameIsOver = true;
				return;
			}
		}

		// look for winner in diagonals
		if (this.getConfig(1, 1) == X && this.getConfig(2, 2) == X && this.getConfig(3, 3) == X
				|| this.getConfig(3, 1) == X && this.getConfig(2, 2) == X && this.getConfig(1, 3) == X) {
			this.winner = X;
			this.gameIsOver = true;
			return;
		}
		if (this.getConfig(1, 1) == Y && this.getConfig(2, 2) == Y && this.getConfig(3, 3) == Y
				|| this.getConfig(3, 1) == Y && this.getConfig(2, 2) == Y && this.getConfig(1, 3) == Y) {
			this.winner = Y;
			this.gameIsOver = true;
			return;
		}

		// check for tie game
		boolean tied = true;
		for (int i = 0; i < 9; i++)
			if (this.config[i] == '?')
				tied = false;
		if (tied)
			this.gameIsOver = true;
	}

	public char[] getConfig() {
		return this.config;
	}

	// get the value of a specific cell
	public char getConfig(int row, int col) {
		if (row > 0 && row < 4 && col > 0 && col < 4)
			return this.config[3 * (row - 1) + col - 1];
		else
			return '?';
	}

	public void printBoard() {
		System.out.println("+-+-+-+");
		System.out.println("|" + this.config[0] + "|" + this.config[1] + "|" + this.config[2] + "|");
		System.out.println("+-+-+-+");
		System.out.println("|" + this.config[3] + "|" + this.config[4] + "|" + this.config[5] + "|");
		System.out.println("+-+-+-+");
		System.out.println("|" + this.config[6] + "|" + this.config[7] + "|" + this.config[8] + "|");
		System.out.println("+-+-+-+");
	}
}

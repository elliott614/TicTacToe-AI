import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TicTacToe {
	public static final char X = 'X';
	public static final char Y = 'Y';
	public static final int FIRST_MOVE = 5;
	public static final String OUTPUT_PATH = "./output.txt";
	public static final boolean PRUNING_ENABLED = false; // controls whether pruning occurs during search of subgames

	public static void main(String[] args) {

		State first = new State(FIRST_MOVE, PRUNING_ENABLED);
		List<List<Integer>> optimalPaths = new ArrayList<List<Integer>>();
		System.out.println("Finding optimal paths...");
		findOptimalPaths(first, optimalPaths);
		System.out.println("Done finding optimal paths");
		try {
			printOptimalPaths(optimalPaths);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Wrote output.txt");
	}

	public static void findOptimalPaths(State curr, List<List<Integer>> optimalPaths) {
		if (curr.board().isOver())
			optimalPaths.add(curr.backtracker());
		else
			for (State next : curr.next()) {
				if (next.alphaBeta() == curr.alphaBeta())
					findOptimalPaths(next, optimalPaths);
			}

	}

	public static void printOptimalPaths(List<List<Integer>> optimalPaths) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_PATH));
		for (List<Integer> path : optimalPaths) {
			for (int move : path)
				bw.write("" + move);
			bw.newLine();
		}
		bw.close();
	}
}

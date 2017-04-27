import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
	private MinPQ<Board> minpq; 
	private Node search;
	private int moves;

	private class Node {
		private Board board;
		private Node prev;

		public Node() {
			item = null;
			prev = null;
		}
	}

	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial)   {
		minpq = new MinPQ<Board>();
		moves = 0;
		search = new Node();
		search.item = initial;
	}
	// min number of moves to solve initial board
	public int moves() {
		return moves;
	}
	// sequence of boards in a shortest solution
	public Iterable<Board> solution() {
		//usar pilha
		Node new_node;
		Stack<Board> stack = new Stack<Board>();
		Queue<Board> queue;
		Board tmp;
		Iterator<Board> it;
		while (!search.board.isGoal()) {
			queue = search.board.neighbors();
			it = queue.iterator();
			while (it.hasNext()) {
				tmp = it.next();
				if (!search.prev.board.isEqual(tmp)) {
					minpq.insert(tmp);
				}
			}
			new_node = new Node();
			new_node.prev = search;
			new_node.board = minpq.delMin();
			search = new_node;
		}	

		while (search != null) {
			stack.add(seach.board);
			search = search.prev;
		}

		return stack;
	}
	//checa se eh soluvel antes de 
	// unit testing 
	public static void main(String[] args) {
		In in = new In(args[0]);
        n = Integer.parseInt(in.readLine());
        tiles1 = new int[n][n];
		int[][] tiles1;
		for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles1[i][j] = Integer.parseInt(in.readString());
                if (i < n-1 || j < n-1) {
                	tiles2[i][j] = i*n + j + 1;
                }
                else {
                	tiles2[i][j] = 0;
                }
            }
        }

        board = new Board(tiles1);

	}
}

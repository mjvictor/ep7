import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Queue;
import java.util.Iterator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;

public class Solver {
	private MinPQ<Node> minpq; 
	private Node search;
	private int moves;
	private Stack<Board> stack;

	public class Node {
		private Board board;
		private Node prev;
		private int moves;

		public Node(Board tiles, int m) {
			board = tiles;
			prev = null;
			moves = m;
		}
	}

	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial)   {
		if (initial == null) throw new java.lang.NullPointerException("initial board is null");

		if (!initial.isSolvable()) throw new java.lang.IllegalArgumentException("initial board is not solvable");
		
		minpq = new MinPQ<Node>(new boardComparator());
		search = new Node(initial, 0);
		moves = 0;
		stack = new Stack<Board>();

		Node new_node, next_node;
		
		Iterable<Board> queue;
		Board tmp;
		Iterator<Board> it;

		while (!search.board.isGoal()) {
			queue = search.board.neighbors();
			it = queue.iterator();
			while (it.hasNext()) {

				tmp = it.next();
				
				if (tmp != null && (search.prev == null || !search.prev.board.equals(tmp))) {
					new_node = new Node(tmp, search.moves + 1);
					new_node.prev = search;
					minpq.insert(new_node);
				}
			}

			
			next_node = minpq.delMin();
			search = next_node;
		}

		moves = search.moves;

		while (search != null) {
			stack.push(search.board);
			search = search.prev;
		}

	}
	// min number of moves to solve initial board
	public int moves() {
		return moves;
	}
	// sequence of boards in a shortest solution
	public Iterable<Board> solution() {
		return stack;
	}
	private static class boardComparator implements Comparator<Node> {
		public int compare (Node node1, Node node2) {
			if ((node1.board.manhattan() + node1.moves) > (node2.board.manhattan() + node2.moves)) return 1;
			else if ((node1.board.manhattan() + node1.moves) == (node2.board.manhattan() + node2.moves)) return 0;
			else if ((node1.board.manhattan() + node1.moves) < (node2.board.manhattan() + node2.moves)) return -1;
			return 0;
		}
	} 
	//checa se eh soluvel antes de 
	// unit testing 
	public static void main(String[] args) {
		Solver solver;
		Iterable<Board> stack;
		Board board;
		String s;
		In in = new In(args[0]);
        int n = Integer.parseInt(in.readLine());
        int[][] tiles1 = new int[n][n];

		for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles1[i][j] = Integer.parseInt(in.readString());
            }
        }

        board = new Board(tiles1);
        solver = new Solver(board);
        
       	stack = solver.solution();
       	StdOut.println("Minimum number of moves = " + solver.moves());
       	Iterator<Board> it = stack.iterator();
        
        while (it.hasNext()) {
        	StdOut.println(n);
        	StdOut.println(it.next().toString());
       	}
      
	}
}

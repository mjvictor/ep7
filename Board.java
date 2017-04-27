import edu.princeton.cs.algs4.StdOut;
import java.lang.Math;
import java.lang.StringBuilder;
import java.lang.Object;
import java.lang.Integer;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import java.util.Iterator;    

public class Board {   
    private int[][] board;
    private int n;
    // construct a board from an N-by-N array of tile
    // (where tiles[i][j] = tile at row i, column j)
    public Board(int[][] tiles) {
        n = tiles.length;
        board = new int[n][n];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                board[i][j] = tiles[i][j];
            }
        }
    }

    // return tile at row i, column j (or 0 if blank)
    public int tileAt(int i, int j) {
        return board[i][j];
    }
    // board size N
    public int size() {
        return n;
    }   
    // number of tiles out of place                       
    public int hamming() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0 && board[i][j] != idealPosition(i, j))
                    count++;
            }
        }
        return count;
    }   
    // sum of Manhattan distances between tiles and goal                    
    public int manhattan() {
        int count, row, col;
        count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0 && board[i][j] != idealPosition(i, j)) {
                    row = (board[i][j] - 1)/n;
                    col = (board[i][j] - 1)%n;
                    row = Math.abs(row - i);
                    col = Math.abs(col - j);
                    count += (row + col);
                }
            }
        }
        return count;
    }   
    // is this board the goal board?                  
    public boolean isGoal() {
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++) {
                if (i < n-1 || j < n-1) {
                    if (board[i][j] != 0 && board[i][j] != idealPosition(i, j))
                        return false;
                }
            }
        }
        return true;
    }   
    // is this board solvable?                 
    public boolean isSolvable() {
        int i, inv, flag;
        if (n%2 == 0) {
            flag = 0;
            for (i = 0; i < n && flag == 0; i++) {
                for (int j = 0; j < n && flag == 0; j++){
                    if (board[i][j] == 0) flag = 1;
                }
            }
            inv = inversions();
            if ((i+inv) % 2 == 0) return false;
        }
        else {
            inv = inversions();
            if (inv % 2 != 0) return false; 
        }
        return true;

    }   
    // does this board equal y?
    public boolean equals(Object y) {
    	Board tmp = (Board) y;
    	for (int i = 0; i < n; i++) {
    		for (int j = 0; j < n; j++) {
    			if (board[i][j] !=  tmp.tileAt(i,j)) return false;
    		}
    	}
    	return true;
    }   
    // all neighboring boards
    public Iterable<Board> neighbors() {
        int i, j, tmp, flag;
        i = 0;
        j = 0;
        Queue<Board> queue = new Queue<Board>();
        for (i = 0, flag = 0; i < n && flag == 0; i++) {
            for (j = 0; j < n && flag == 0; j++) {
                if (board[i][j] == 0) flag = 1;
            }
        }
        j--;
        i--;

        //move o zero pra cima cima
        if ((i - 1) >= 0) {
            Board new_board = new Board(board);
            new_board.board[i][j] = new_board.board[i - 1][j];
            new_board.board[i - 1][j] = 0;
            queue.enqueue(new_board);
        }
        //baixo
        if ((i + 1) < n) {
            Board new_board = new Board(board);
            tmp = new_board.board[i][j];
            new_board.board[i][j] = new_board.board[i + 1][j];
            new_board.board[i + 1][j] = tmp;
            queue.enqueue(new_board);
        }
        //direita
        if ((j + 1) < n) {
            Board new_board = new Board(board);
            tmp = new_board.board[i][j];
            new_board.board[i][j] = new_board.board[i][j + 1];
            new_board.board[i][j + 1] = tmp;
            queue.enqueue(new_board);
        }
        //esquerda
        if ((j - 1) >= 0) {
            Board new_board = new Board(board);
            tmp = new_board.board[i][j];
            new_board.board[i][j] = new_board.board[i][j - 1];
            new_board.board[i][j - 1] = tmp;
            queue.enqueue(new_board);
        }

        return queue;
    }
    // string representation of this board (in the output format specified below)         
    public String toString() {
    	StringBuilder tmp = new StringBuilder();
    	for (int i = 0; i < n; i++) {
    		for (int j = 0; j < n; j++) {
    			tmp.append(" ");
    			if (board[i][j] == 0) {
    				tmp.append(" ");
    			}
    			else {
    				tmp.append(board[i][j]);
    			}
    			tmp.append(" ");
    		}
    		tmp.append("\n");
    	}
    	return tmp.toString();
    }   
    //return the expected number for the tile in the row i and column j.       
    private int idealPosition(int i, int j) {
        return ((i*n) + j + 1);
    }
    //return the number of inversions in the board.
    private int inversions() {
        int[] inv;
        int i, j, count, ind;
        ind = 0;
        count = 0;
        inv = new int[n*n];
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (board[i][j] != 0) inv[ind++] = board[i][j];
            }
        }
        for (i = 0; i < n; i++) {
            for (j = i + 1; j < n; j++){
                if (inv[i] > inv[j]) count++;
            }
        }
        return count;
    }
    // unit testing (required)
    public static void main(String[] args) {
        int n;
        Board board, tmp;
        int[][] tiles1, tiles2;
        String out;
        Iterable<Board> queue;
        In in = new In(args[0]);
        n = Integer.parseInt(in.readLine());
        tiles1 = new int[n][n];
        tiles2 = new int[n][n];

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
        tmp = new Board(tiles2);
        
        // testa a solvabilidade.
        if (board.isSolvable()) {
        	StdOut.println("Is solvable!");
        }
        else {
        	StdOut.println("Is not solvable!");
        }
        StdOut.println();

        //testa a função equals
        if(board.equals(tmp)){
        	StdOut.println("Is equal!");
        }
        else {
        	StdOut.println("Is not equal!");
        }
        StdOut.println();

        //testa a função isGoal
        if(board.isGoal()) {
        	StdOut.println("Is goal!");
        }
        else {
        	StdOut.println("Is not goal!");
        }
        StdOut.println();

        //testa a função hamming
        StdOut.println("hamming " + board.hamming());

        //testa a função manhattan
        StdOut.println("manhattan " + board.manhattan());

        //testa a função neighbors
        queue = board.neighbors();
        Iterator<Board> it = queue.iterator();
        while (it.hasNext()) {
            out = it.next().toString();
            StdOut.println(tiles1.length);
            StdOut.println(out); 
        }

        //formato do print
        out = board.toString();
        StdOut.println(tiles1.length);
        StdOut.println(out); 
    }
}
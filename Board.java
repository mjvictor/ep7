import edu.princeton.cs.algs4.StdOut;
import java.lang.Math

public class Board {   
    private int[][] board;
    private int n;
    // construct a board from an N-by-N array of tile
    // (where tiles[i][j] = tile at row i, column j)
    public Board(int[][] tiles) {
        board = tiles;
        n = tiles.length;
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
                    row = board[i][j]/n;
                    col = (board[i][j]%n) - 1;
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
        int inv, flag;
        if (n%2 == 0) {
            flag = 0;
            for (int i = 0; i < n && flag == 0; i++) {
                for (int j = 0; j < n flag == 0; j++){
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

    }   
    // all neighboring boards
    public Iterable<Board> neighbors() {

    }
    // string representation of this board (in the output format specified below)         
    public String toString() {

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
        for (i = 0, i < n; i++) {
            for (j = 0; j < n; j++) {
                if (board[i][j] != 0) inv[ind++] = board[i][j];
            }
        }
        for (i = 0; i < n; i++) {
            for (j = i + 1; j < n; j++){
                if (inv[i] > inv[j]) count++;
            }
        }
    }
    // unit testing (required)
    public static void main(String[] args) {
        int[][] tiles;
        tiles = new int[4][1];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 1; j++) {
                tiles[i][j] = 0;
            }
        }
        StdOut.println(tiles.length); 
    }
}
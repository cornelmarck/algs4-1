import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF id;
    private boolean[] _open;
    private final int n;
    private int openSitesCounter;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n<1) {
            throw new IllegalArgumentException("Invalid grid size");
        }
        this.n = n;
        this._open = new boolean[n*n+2];
        this.id = new WeightedQuickUnionUF(n*n+2);
        this.openSitesCounter = 0;

        for (int i=0; i < _open.length; i++) { //Initialize to 0 if closed
            this._open[i] = false;
        }
        //Initialise the virtual sites
        _open[0] = true;
        _open[_open.length-1] = true;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkInput(row, col);
        if (isOpen(row,col)) {
            return;
        }

        _open[getIdx(row, col)] = true;
        openSitesCounter++;

        //connect the site to its neighbours
        if (row==1) {
            connect_to_top(row, col);
            if (col==1) {
                connect(row, col, row+1, col);
                connect(row, col, row, col+1);
            }
            else if (col==n) {
                connect(row, col, row+1, col);
                connect(row, col, row, col-1);
            }
            else {
                connect(row, col, row+1, col);
                connect(row, col, row, col+1);
                connect(row, col, row, col-1);
            }
        }
        else if (row==n) {
            connect_to_bottom(row, col);
            if (col == 1) {
                connect(row, col, row-1, col);
                connect(row, col, row, col+1);
            }
            else if (col == n) {
                connect(row, col, row-1, col);
                connect(row, col, row, col-1);
            }
            else {
                connect(row, col, row-1, col);
                connect(row, col, row, col+1);
                connect(row, col, row, col-1);
            }
        }
        else {
            if (col==1) {
                connect(row, col, row+1, col);
                connect(row, col, row-1, col);
                connect(row, col, row, col+1);
            }
            else if (col==n) {
                connect(row, col, row+1, col);
                connect(row, col, row-1, col);
                connect(row, col, row, col-1);
            }
            else {
                connect(row, col, row+1, col);
                connect(row, col, row-1, col);
                connect(row, col, row, col+1);
                connect(row, col, row, col-1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkInput(row, col);
        return _open[getIdx(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkInput(row, col);
        return id.find(getIdx(row, col)) == id.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCounter;
    }

    // does the system percolate?
    public boolean percolates() {
        return id.find(_open.length-1) == id.find(0);
    }

    //Get the flattened array index. Row and col are 1-based.
    private int getIdx(int row, int col) {
        return n*(row-1) + col-1 + 1;
    }

    private void checkInput(int row, int col) {
        if (row < 1 || row > n) {
            throw new IllegalArgumentException("Invalid row number");
        }
        else if (col < 1 || col > n) {
            throw new IllegalArgumentException("Invalid column number");
        }
    }

    private void connect(int x1, int y1, int x2, int y2) {
            checkInput(x1, y1);
            checkInput(x2, y2);

            if (isOpen(x2,y2)) {
                id.union(getIdx(x1, y1), getIdx(x2, y2));
            }
    }

    private void connect_to_top(int x, int y) {
        checkInput(x,y);
        id.union(getIdx(x,y), 0);
    }
    private void connect_to_bottom(int x, int y) {
        checkInput(x,y);
        id.union(getIdx(x,y), _open.length-1);
    }

    // test client (optional)
    public static void main(String[] args) {
        return;
    }
}



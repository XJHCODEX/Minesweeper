package Minesweeper;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.util.concurrent.ThreadLocalRandom;

public class Grid {

    private boolean[][] BombGrid;
    private int[][] CountGrid;
    private int[] bomblocation;
    private int numRows;
    private int numColumns;
    private int numBombs;
    public int open_count = 0;
    JButton buttonIndex[][];
    static GridLayout layout;
    
    Grid(){
        this.numRows = 10;
        this.numColumns = 10;
        this.numBombs = 25;
        this.BombGrid = new boolean[numRows][numColumns];
        this.CountGrid = new int[numRows][numColumns];
        createBombGrid();
        createCountGrid();

    }

    Grid(int numRows, int numColumns){
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.numBombs = 25;
        this.BombGrid = new boolean[numRows][numColumns];
        this.CountGrid = new int[numRows][numColumns];
        createBombGrid();
        createCountGrid();
    }

    Grid(int numRows, int numColumns, int numBombs){
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.numBombs = numBombs;
        this.BombGrid = new boolean[numRows][numColumns];
        this.CountGrid = new int[numRows][numColumns];
        createBombGrid();
        createCountGrid();
    	}
    int getNumRows()
    {
    	return this.numRows;
    }
    int getNumColumns()
    {
    	return this.numColumns;
    }
    int getNumBombs()
    {
    	return this.numBombs;
    }
    boolean[][] getBombGrid()
    {
    	return this.BombGrid;
    }
    int[][] getCountGrid()
    {
    	return this.CountGrid;
    }
    boolean IsBombAtLocation(int row, int column)
    {
    	if(row >= 0 && column >= 0 && row < numRows && column < numColumns && BombGrid[row][column] == true)
    	{
    		return true;
    	}
    	return false;
    }
    
    int getCountAtLocation(int row, int column)
    {
        int count = 0;
        if (IsBombAtLocation(row, column)) {
            count++;
        }
        if (row + 1 < numRows) {
            if (IsBombAtLocation(row + 1, column))
                count++;
            if (column + 1 < numColumns) {
                if (IsBombAtLocation(row + 1, column + 1))
                    count++;
            }

            if (column - 1 >= 0) { 
                if (IsBombAtLocation(row + 1, column - 1)) 
                                                            
                    count++;
            }
        }

        if (row - 1 >= 0) { 
            if (IsBombAtLocation(row - 1, column)) 
                count++;
            if (column - 1 >= 0) {
                if (IsBombAtLocation(row - 1, column - 1)) 
                                                            
                    count++;
            }

            if (column + 1 < numColumns) { 
                if (IsBombAtLocation(row - 1, column + 1)) 
                                                            
                    count++;
            }

        }

        if (column + 1 < numColumns) { 
            if (IsBombAtLocation(row, column + 1))
                count++;
        }

        if (column - 1 >= 0) {
            if (IsBombAtLocation(row, column - 1))
                count++;
        }
        return count;
    }
 //check for all columns except first column
 //Add count by 1 if element at same row,previous column is a bomb
    int gen_random(int max){
    	return ThreadLocalRandom.current().nextInt(0, max);
    	}
    void createBombGrid() 
    {
    	BombGrid = new boolean[numRows][numColumns];
    	bomblocation = new int[numBombs * 2];
    	for(int i=0;i<numRows;i++){
    		for(int j=0;j<numColumns;j++)
    		{
    		BombGrid[i][j] = false;
    		}
    	}
    	for(int i=0;i<numBombs;i++){
    		int x = gen_random(numRows);
    		int y = gen_random(numColumns);
    		BombGrid[x][y] = true;
            bomblocation[i * 2] = x;
            bomblocation[i * 2 + 1] = y;
    		}
    }
    private void createCountGrid(){
    	for(int i=0;i<numRows;i++){
    	for(int j=0;j<numColumns;j++){
    	int bombs = 0;
    	if(IsBombAtLocation(i,j)){
    	bombs += 1;
    	}
    	if(IsBombAtLocation(i,j-1)){
    	bombs += 1;
    	}
    	if(IsBombAtLocation(i-1,j-1)){
    	bombs += 1;
    	}
    	if(IsBombAtLocation(i-1,j)){
    	bombs += 1;
    	}
    	if(IsBombAtLocation(i-1,j+1)){
    	bombs += 1;
    	}
    	if(IsBombAtLocation(i,j+1)){
    	bombs += 1;
    	}
    	if(IsBombAtLocation(i+1,j+1)){
    	bombs += 1;
    	}
    	if(IsBombAtLocation(i,j+1)){
    	bombs += 1;
    	}
    	if(IsBombAtLocation(i+1,j-1)){
    	bombs += 1;
    	}
    	CountGrid[i][j] = bombs;
    	}
    }
   }
    public static void main(String args[]){
    	boolean[][] BombGrid = new Grid().getBombGrid();
    	int[][] CountGrid = new Grid().getCountGrid();
    	
    	for(int i=0;i< new Grid().getNumRows();i++){
    	for(int j=0;j< new Grid().getNumColumns();j++){
    	System.out.print(BombGrid[i][j] ? "T" : "F");
    	}
    	System.out.println();
    	}

    	for(int i=0;i< new Grid().getNumRows();i++){
    	for(int j=0;j< new Grid().getNumColumns();j++){
    	System.out.print(CountGrid[i][j] + " ");
    	}
    	System.out.println();
    	}
    	}
  }

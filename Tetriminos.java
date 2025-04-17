import java.awt.*;

public class Tetriminos {
    public static final int[][] O = {{1, 1},{1, 1}};
    public static final int[][] I = {{1, 1, 1, 1}};
    public static final int[][] S = {{0, 1, 1},{1, 1, 0}};
    public static final int[][] Z = {{1, 1, 0},{0, 1, 1}};
    public static final int[][] L = {{1, 0},{1, 0},{1, 1}};
    public static final int[][] J = {{0, 1},{0, 1},{1, 1}};
    public static final int[][] T = {{1, 1, 1},{0, 1, 0}};

    private int[][] currentBlock;
    private int row = 0;  
    private int col = 7; 
    private Color color;

    public Tetriminos() {
        // Default constructor (no block set)
    }

    public Tetriminos(int[][] block) {
        currentBlock = block;
    }

    public void setRandomBlock() {
         row=0;
         col=3;
        int randomShape = (int) (Math.random() * 7);  // Random number between 0 and 6
        switch (randomShape) {
            case 0: 
               currentBlock = O;
               color = Color.YELLOW; 
               break;
            case 1: 
               currentBlock = I; 
               color = Color.CYAN;
               break;
            case 2: 
               currentBlock = S; 
               color =Color.GREEN;
               break;
            case 3: 
               currentBlock = Z;
               color = Color.RED; 
               break;
            case 4: 
               currentBlock = L; 
               color =Color.ORANGE;
               break;
            case 5: 
               currentBlock = J;
               color = Color.BLUE; 
               break;
            case 6: 
               currentBlock = T;
               color = Color.MAGENTA; 
               break;
        }
    }
    
    public Color getColor()
    {
      return color;
    
    }

    public int[][] getCurrentBlock() {
        return currentBlock;
    }

    public void moveLeft() {
        col--;
    }

    public void moveRight() {
        col++;
    }

    public boolean moveDown() {
        row++;
        return row >= 20;  // Assuming the board height is 20 rows
    }

    public void rotateRight() {
        int numRows = currentBlock.length;
        int numCols = currentBlock[0].length;
        int[][] rotatedShape = new int[numCols][numRows];

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                rotatedShape[c][numRows - 1 - r] = currentBlock[r][c];
            }
        }

        currentBlock = rotatedShape;
    }

    public void rotateLeft() {
        int numRows = currentBlock.length;
        int numCols = currentBlock[0].length;
        int[][] rotatedShape = new int[numCols][numRows];

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                rotatedShape[numCols - 1 - c][r] = currentBlock[r][c];
            }
        }

        currentBlock = rotatedShape;
    }
    //collisions
    public boolean canMove(int dx, int dy, int boardWidth, int boardHeight, int[][] boardGrid)
    {
      for(int r = 0; r < currentBlock.length; r++)
      {
         for(int c = 0; c< currentBlock[0].length;c++)
         {
            if(currentBlock[r][c] == 1)
            {
               int newRow = row + r +dy;
               int newCol = col+c+dx;
            
            
               if(newCol < 0 || newCol >= boardWidth || newRow < 0 || newRow >= boardHeight)
               {
                  return false;
               }
               if(boardGrid[newRow][newCol] ==1)
               {
                  return false;
               }
            }
         }
         
      }
      return true;
    }
    public boolean moveDown(int[][] boardGrid, int boardWidth, int boardHeight)
    {
      if(canMove(0,1,boardWidth,boardHeight,boardGrid)){
         row++;
        return true;
      }
       return false;
    }
    public void moveLeft(int[][] boardGrid, int boardWidth, int boardHeight)
    {
      if(canMove(-1,0,boardWidth,boardHeight,boardGrid))
      {
      col--;
      }
    }
    public void moveRight(int[][] boardGrid, int boardWidth, int boardHeight)
    {
      if(canMove(1,0,boardWidth,boardHeight,boardGrid))
      {
         col++;
      }
    }
  
public boolean canRotateLeft(int[][] boardGrid,int boardWidth,int boardHeight ) {
    int numRows = currentBlock.length;
    int numCols = currentBlock[0].length;
    int[][] rotatedShape = new int[numCols][numRows];

    for (int r = 0; r < numRows; r++) {
        for (int c = 0; c < numCols; c++) {
            rotatedShape[numCols - 1 - c][r] = currentBlock[r][c];
        }
    }

    for (int r = 0; r < rotatedShape.length; r++) {
        for (int c = 0; c < rotatedShape[0].length; c++) {
            if (rotatedShape[r][c] == 1) {
                int newRow = row + r;
                int newCol = col + c;
                if (newCol < 0 || newCol >= boardWidth || newRow < 0 || newRow >= boardHeight) {
                    return false;
                }
                if (boardGrid[newRow][newCol] == 1) {
                    return false;
                }
            }
        }
    }

    return true;
}
 

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements KeyListener {

    private final int ROWS = 20;
    private final int COLS = 10;
    private final int BLOCK_SIZE = 30;
    private Tetriminos block;
    private Timer timer;
    private int[][] boardGrid = new int[ROWS][COLS];
    private Color[][] boardColors = new Color[ROWS][COLS];
    private ScoreManager scoreManager = new ScoreManager();
    

    public Board() 
    {
        setPreferredSize(new Dimension(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE));
        setBackground(Color.BLACK);
        block = new Tetriminos();
        block.setRandomBlock();
        
        timer = new Timer(500, new ActionListener(){
        public void actionPerformed(ActionEvent e)
        {
         boolean moved = block.moveDown(boardGrid,COLS,ROWS);
         if(!moved)
         {
            mergeBlockToBoard();
            block.setRandomBlock();
         }
          repaint(); 
        }
        });
        timer.start();
     }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        
        
        //draw settle block
        
        
        for(int row = 0; row < ROWS; row++)
        {
         for(int col = 0; col < COLS; col++)
         {
            if(boardGrid[row][col] ==1)
            {
               g.setColor(boardColors[row][col]);
               g.fillRect(col* BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE,BLOCK_SIZE);
            }
         }
        }
        
        
        //draw block
        if(block !=null)
        {
            int[][] shape = block.getCurrentBlock();
            int x = block.getCol();
            int y = block.getRow();
            
            g.setColor(block.getColor());
            for(int r = 0; r< shape.length;r++)
            {
               for(int c =0; c< shape[0].length; c++)
               {
                  if(shape[r][c] ==1)
                  {
                     g.fillRect((x+c)* BLOCK_SIZE, (y+r)* BLOCK_SIZE,BLOCK_SIZE,BLOCK_SIZE);
                  }
               }
            }
        }
        // Draw board
        g.setColor(Color.GRAY);
        for (int row = 0; row < ROWS; row++) 
        {
            for (int col = 0; col < COLS; col++) 
            {
                g.drawRect(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
        }
    }
    
   @Override 
   public void keyReleased(KeyEvent e)
   {
   }
   @Override
   public void keyTyped(KeyEvent e)
   {
   }
   
   @Override
   public void keyPressed(KeyEvent e)
   {
      int source = e.getKeyCode();
      switch(source)
      {
         case KeyEvent.VK_LEFT:
            if(block.canMove(-1,0,COLS,ROWS,boardGrid))
            {
               block.moveLeft();
            }
            break;
         case KeyEvent.VK_RIGHT:
         if(block.canMove(1,0,COLS,ROWS,boardGrid))
            {
               block.moveRight();
            }
         
            break;
         case KeyEvent.VK_DOWN:
            block.moveDown(boardGrid,COLS,ROWS);
            break;
         case KeyEvent.VK_UP:
            if(block.canRotateLeft(boardGrid,COLS,ROWS))
            {
               block.rotateLeft();
            }
            break;
         
         
      
      }
      repaint();
   }
   private void mergeBlockToBoard() 
   {
    int[][] shape = block.getCurrentBlock();
    int x = block.getCol();
    int y = block.getRow();

    for (int r = 0; r < shape.length; r++) {
        for (int c = 0; c < shape[0].length; c++) {
            if (shape[r][c] == 1) {
                int boardRow = y + r;
                int boardCol = x + c;

                if (boardRow >= 0 && boardRow < ROWS && boardCol >= 0 && boardCol < COLS) {
                    boardGrid[boardRow][boardCol] = 1;
                    boardColors[boardRow][boardCol] = block.getColor();
                }
            }
        }
    }
    clearFullLines();
}
   private void clearFullLines()
   {
      int linesCleared =0;
      for(int row =0; row < ROWS; row++)
      {
         boolean isFull = true;
         for(int col = 0; col < COLS; col++)
         {
            if(boardGrid[row][col] ==0)
            {
               isFull = false;
               break;
            }
         }
         if(isFull)
         {
            linesCleared++;
            for(int r = row; r > 0; r--)
            {
               for(int c = 0;c <COLS;c++)
               {
                  boardGrid[r][c] =boardGrid[r-1][c];
                  boardColors[r][c] = boardColors[r-1][c];
               }
            }
            for(int c =0;c<COLS;c++)
            {
               boardGrid[0][c]=0;
               boardColors[0][c]=null;
            }
            row++;
         }
      }
      scoreManager.addScore(linesCleared);
   }
   

    
}


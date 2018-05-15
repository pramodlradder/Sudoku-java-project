import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.*;
import java.awt.event.ActionListener;


public class ProjectSudoku extends JPanel{
    
   //Declaration of all  components.
    public int count;                                                     //For counting the number of backtracking
    public static final int SIZE = 9;                                    //The size of grid
    
     public static int[][] get = {                                       //Array of 9*9 & values for initialization of Sudoku
                                     {6,5,0,8,7,3,0,9,0},
                                     {0,0,3,2,5,0,0,0,8},
                                     {9,8,0,1,0,4,3,5,7},
                                     {1,0,5,0,0,0,0,0,0},
                                     {4,0,0,0,0,0,0,0,2},
                                     {0,0,0,0,0,0,5,0,3},
                                     {5,7,8,3,0,1,0,2,6},
                                     {2,0,0,0,4,8,9,0,0},
                                     {0,9,0,6,2,5,0,8,1}
                                 };
    
    JTextField[][] textField;                                            //For creating a 9*9 blocks
    JPanel panel;                                                        //For holding the blocks & button
    JButton button;
    Font font;                                                           //Setting the fonts
    
 public final void gui(int r, int c,String s){                           //Giving color for textfields(blocks)
      int row_start = (r/3)*3;                                          //Finding of starting position of row in 3*3 block
      int col_start = (c/3)*3;                                          //Finding of starting position of column in 3*3 block
        for(int i=row_start;i<row_start+3;i++){
            for(int j=col_start;j<col_start+3;j++){
                if(s=="blue")
                textField[i][j].setBackground(Color.blue);
                if(s=="orange")
                textField[i][j].setBackground(Color.orange);
                if(s=="red")
                textField[i][j].setBackground(Color.red);
                if(s=="green")
                textField[i][j].setBackground(Color.green);
                if(s=="white")
                textField[i][j].setBackground(Color.white);
                if(s=="pink")
                textField[i][j].setBackground(Color.pink);
                if(s=="cyan")
                textField[i][j].setBackground(Color.cyan);
                if(s=="yellow"){
                textField[i][j].setBackground(Color.black);
                textField[i][j].setForeground(Color.yellow);            //Gives the font color
                 }
                if(s=="lightGray")
                textField[i][j].setBackground(Color.lightGray);
            }
        }
        }

 //Constructor 
     ProjectSudoku() {
        JFrame frame = new JFrame("Sudoku Solver");                    //Create a frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);          //Terminating the program       
        font=new Font("SansSerif",Font.BOLD,40);                                 
        textField = new JTextField[9][9];                             //Create a array[9][9] to hold values
        button=new JButton("solve");                                 //Create a button
        panel = new JPanel ();                                       //Create a panel
        panel.setPreferredSize(new Dimension(600,600));              //Setting the size of panel
        GridLayout grid =new GridLayout(10,9);                      //Creating a 10*9 grids(1 for button)
        panel.setLayout(grid);                                      //Add the gridlayout      
        frame.setContentPane(panel);                                //add panel to frame
  
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                 panel.add(textField[i][j]=new JTextField(5));      //Add array[9][9] to panel with size 5
                if(get[i][j]==0){
                    textField[i][j].setHorizontalAlignment(textField[i][j].CENTER);
                                                                                             //make values to be align center
                    textField[i][j].setFont(font);                                           //set the font
                    continue;
                }
                else{
               
                textField[i][j].setText(""+get[i][j]);//add values into text field
                textField[i][j].setHorizontalAlignment(textField[i][j].CENTER);
                                                                                               //make values to be align center
                textField[i][j].setFont(font);//set the font
                }
             }
        }
        gui(1,1,"blue");                                                                     //Giving color to 3*3 block
        gui(1,3,"orange");
        gui(1,6,"red");
        gui(4,1,"green");
        gui(4,3,"white");
        gui(4,7,"pink");
        gui(6,1,"cyan");
        gui(6,3,"yellow");
        gui(6,7,"lightGray");
        
       panel.add(button);                                                                   //Add solve button to panel
                                                                                            //Onclick of button perform..... 
        button.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent ae){
          for(int i = 0; i < 9; i++) {
              for(int j = 0; j < 9; j++) {
                if(textField[i][j].getText().equals("")){
                    get[i][j]=0;
                    }
                    else
                            get[i][j]=Integer.parseInt(textField[i][j].getText());
                }
            }
            
            if (solveSudoku())
            {
                    System.out.println("Number of Backtrackings:"+count);
                    printSudoku();
            }
            else
                System.out.println("No solution");
        }
    
        
    public boolean solveSudoku()
    {
        int row=0;
        int col=0;
        int[] a = FindEmptyLoction(row, col);
        //if there is no empty locations then the sudoku is already solved
        if(a[0] == 0)
            return true;
        row = a[1];                                                                        //get the position of empty row
        col = a[2];                                                                        //get the position of empty column
        for(int i=1;i<=SIZE;i++)                                                           //attempt to fill number between 1 to 9
        {
            if(Numberissafe(i, row, col))                                  //Check the number i can be filled or not into empty location
            {
                get[row][col] = i;                                         //Fill the number i
                                                                           //Backtracking
               if(solveSudoku())
                    return true;
                                                                /* Know how backtracking works
                                                                                 for(int k=0;k<SIZE;k++)               1 0 3 0
                                                                                 {                                     0 0 2 1
                                                                                   for(int j=0;j<SIZE;j++)             0 1 0 2
                                                                                      {                                2 4 0 0
                                                                                    System.out.print(get[k][j]+"\t");
                                                                                 }
                                                                             System.out.println("");
                                                                  }System.out.println("000000000");
                                                        */
                count++;                                                       //For knowing the number of times backtracking happens
                
               get[row][col]=0;                                               //Solution is wrong Reassign the value
            }
        }
        return false;
    }
    
  
 public int[] FindEmptyLoction(int row, int col)                             //Finding the empty location
    {
        int flag= 0;
        for(int i=0;i<SIZE;i++){
            for(int j=0;j<SIZE;j++){
                if(get[i][j] == 0)                                           //Check cell is empty
                {
                    row = i;                                                //Changing the values of row and col
                    col = j;
                    flag = 1;
                    int[] a = {flag, row, col};
                    return a;
                }
            }
        }
        int[] a = {flag, -1, -1};
        return a;
    }


public boolean Numberissafe(int n, int r, int c){
      
        for(int i=0;i<SIZE;i++){                                               //Checking in col
            if(get[r][i] == n)                                                 //If there is a cell with value equal to i
                return false;
        } 
      
        for(int i=0;i<SIZE;i++){                                              //Checking row
            if(get[i][c] == n)                                               //If there is a cell with value equal to i
                return false;
        }
        
    
        int row_start = (r/3)*3;
        int col_start = (c/3)*3;
        for(int i=row_start;i<row_start+3;i++){                              //Checking sub matrix
            for(int j=col_start;j<col_start+3;j++){
                if(get[i][j]==n)
                    return false;
            }
        }
        return true;
    }
    
  
    public void printSudoku(){                                                  //Printing the Sudoku
        for(int i = 0; i < 9; i++) {
        for(int j = 0; j < 9; j++) {
            textField[i][j].setText(""+get[i][j]);                             //Print the values on to board
            textField[i][j].setHorizontalAlignment(textField[i][j].CENTER);
            textField[i][j].setFont(font);
            }
        }
    }
        });                                                                    //End of action performed
      
        frame.pack();                                                          //To change the size Dynamically
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        ProjectSudoku sample = new ProjectSudoku();
    }
}




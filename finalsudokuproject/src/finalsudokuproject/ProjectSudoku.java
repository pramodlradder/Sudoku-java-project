package finalsudokuproject;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.*;

import java.awt.event.ActionListener;
public class ProjectSudoku extends JPanel{
//Declaration of all  components.
    public int count;
public static final int SIZE = 9;//the size of grid
    public static int[][] get = {//array 9*9
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
    JTextField[][] textField;
    JPanel panel;
    JButton button;
    Font font;
    
public final void gui(int r, int c,String s){
      int row_start = (r/3)*3;
      int col_start = (c/3)*3;
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
                textField[i][j].setForeground(Color.yellow);
                }
                if(s=="lightGray")
                textField[i][j].setBackground(Color.lightGray);
            }
        }
        }

//Constructor 
   ProjectSudoku() {
        JFrame frame = new JFrame("Sudoku Solver");//create a frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//terminating the program       
        font=new Font("SansSerif",Font.BOLD,40);
        textField = new JTextField[9][9];//Create a array[9][9] to hold values
        button=new JButton("solve");//create a button
        panel = new JPanel ();//create a panal
        panel.setPreferredSize(new Dimension(600,600));//setting the size of panal
        GridLayout grid =new GridLayout(10,9);//creating a 10*9 grids 
        panel.setLayout(grid);//add the gridlayout      
        frame.setContentPane(panel);//add panel to frame
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                 panel.add(textField[i][j]=new JTextField(5));//Add array[9][9] to panal pl with size 5
                if(get[i][j]==0){
                    //textField[i][j].setText(null);
                    textField[i][j].setHorizontalAlignment(textField[i][j].CENTER);//make values to be align center
                    textField[i][j].setFont(font);//set the font
                    continue;
                }
                else{
               
                textField[i][j].setText(""+get[i][j]);//add values into text field
                textField[i][j].setHorizontalAlignment(textField[i][j].CENTER);//make values to be align center
                textField[i][j].setFont(font);//set the font
                }
             }
        }
        gui(0,1,"blue");
        gui(1,3,"orange");
        gui(1,6,"red");
        gui(4,1,"green");
        gui(4,3,"white");
        gui(4,7,"pink");
        gui(6,1,"cyan");
        gui(6,3,"yellow");
        gui(6,7,"lightGray");
        panel.add(button);//add solve button to panel
        //onclick of button perform..... 
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
        row = a[1];//get the position of empty row
        col = a[2];//get the position of empty column
        for(int i=1;i<=SIZE;i++)//attempt to fill number between 1 to 9
        {
            if(Numberissafe(i, row, col))//check the number i can be filled or not into empty location
            {
                get[row][col] = i;//fill the number i
                //backtracking
               if(solveSudoku())
                    return true;/*
                       for(int k=0;k<SIZE;k++)                1 0 3 0
                           {                                  0 0 2 1
                            for(int j=0;j<SIZE;j++)           0 1 0 2
                                 {                            2 4 0 0
                                System.out.print(get[k][j]+"\t");
                                }
                                System.out.println("");
                            }System.out.println("000000000");*/
                //if we can't proceed with this solution
                //reassign the cell
                count++;
                
               get[row][col]=0;
            }
        }
        
        return false;
    }
    //finding the empty location
    public int[] FindEmptyLoction(int row, int col)
    {
        int flag= 0;
        for(int i=0;i<SIZE;i++){
            for(int j=0;j<SIZE;j++){
                if(get[i][j] == 0)//check cell is empty
                {
                    //changing the values of row and col
                    row = i;
                    col = j;
                    //there is  empty cells
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
        //checking in col
        for(int i=0;i<SIZE;i++){
            if(get[r][i] == n)//there is a cell with same value
                return false;
        } 
        //checking row
        for(int i=0;i<SIZE;i++){
            if(get[i][c] == n)//there is a cell with the value equal to i
                return false;
        }
        //checking sub matrix
        int row_start = (r/3)*3;
        int col_start = (c/3)*3;
        for(int i=row_start;i<row_start+3;i++){
            for(int j=col_start;j<col_start+3;j++){
                if(get[i][j]==n)
                    return false;
            }
        }
        return true;
    }
    //printing the sudoku
    public void printSudoku(){
        for(int i = 0; i < 9; i++) {
        for(int j = 0; j < 9; j++) {
            textField[i][j].setText(""+get[i][j]);//print the vaues on to board
            textField[i][j].setHorizontalAlignment(textField[i][j].CENTER);
            textField[i][j].setFont(font);
            }
        }
    }

        });
        //end of action performed
        frame.pack();//to change the size
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        ProjectSudoku sample = new ProjectSudoku();
    }
}
import java.util.*;
import java.io.*;
class Main {
  public static void main(String[] args) throws FileNotFoundException {
    System.out.println("\033[4;37m"+"Welcome to Conway's Game of Life!"+"\033[0m");
    Scanner input = new Scanner(System.in);
    System.out.println("Enter the board size [int]: ");
    int bsize=input.nextInt();
    int [][] board = new int[bsize][bsize];
    int loadtype=0;
    int gen=1;
    String nextg="L";
    while(loadtype!=1&&loadtype!=2){
      System.out.println("Would you like to (1 randomBoard) (2 loadBoard):");
      loadtype=input.nextInt();
    }
    if(loadtype==1) randomBoard(board);
    else if(loadtype==2) loadBoard(board,input);
    System.out.println("Board for generation #"+gen);
    printBoard(board);
    
    
    while(!nextg.equals("N")&&!nextg.equals("NO")){
      gen++;
      nextg="L";
      while (!nextg.equals("N")&&!nextg.equals("NO")&&!nextg.equals("Y")&&!nextg.equals("YES")){
        System.out.println("Would you like another generation? (Y/N): ");
        nextg=input.next();
        nextg=nextg.toUpperCase();
      }
      if(nextg.equals("Y")||nextg.equals("YES")){
        nextBoard(board);
        printBoard(board);
      }
    }
  }
  
  
  public static void randomBoard(int [][] board){
    Random r = new Random();
    for(int x=0;x<board.length;x++){
      for(int y=0;y<board.length;y++){
        if(r.nextInt(4)==1){
          board[x][y]=1;
        }
      }
    }
  }
  public static void loadBoard(int[][] board, Scanner s) throws FileNotFoundException {
    System.out.print("Enter filename [grid1.txt]: ");
    String filename="grid1.txt";
    filename=s.next();
    Scanner text = new Scanner(new File(filename));
    int a=0;
    int b=0;
    while(text.hasNextInt()){
      a = text.nextInt();
      b = text.nextInt();
      board[a][b]=1;
    }
    System.out.println("");
  }
  public static void printBoard(int[][] board){
    System.out.print("  ");
    for(int z=0;z<board.length;z++){
      System.out.printf("%2d",z);
    }
    System.out.println();
    for(int x=0;x<board.length;x++){
      System.out.printf("%2d",x);
      for(int y=0;y<board.length;y++){
        if(board[x][y]==1){ 
          System.out.printf("%2s", "*");
        }
        else if(board[x][y]==0){ 
          System.out.printf("%2s", " ");
        }
      }
      System.out.println();
    }
  }
  public static int[][] nextBoard(int [][] board){
    int [][] futureboard = new int[board.length][board.length];
    int numn=0;
    int ccell=0;
    for(int x=0;x<board.length;x++){
      for(int y=0;y<board.length;y++){
        numn=countNeighbors(board,x,y);
        // System.out.println("x: "+x+"y: "+y+"numn: "+numn);
        ccell=checkCell(board,x,y);
        if(numn<2) futureboard[x][y]=0;
        else if(numn==2) futureboard[x][y]=board[x][y];
        else if(numn==3) futureboard[x][y]=1;
        else if(numn>3) futureboard[x][y]=0;
        else futureboard[x][y]=0;
      }
    }
    for(int x=0;x<board.length;x++){
      for(int y=0;y<board.length;y++){
        board[x][y]=futureboard[x][y];
      }
    }
    return board;
  }
  public static int checkCell(int [][] board, int r, int c){
    if(r>=board.length||c>=board.length||r<0||c<0) return 0;
    if(board[r][c]==1) return 1;
    else return 0;
  }
  public static int countNeighbors(int [][] board, int r, int c){
    int total=0;
    total=total+checkCell(board,r,c+1);
    total=total+checkCell(board,r,c-1);
    total=total+checkCell(board,r+1,c);
    total=total+checkCell(board,r-1,c);
    total=total+checkCell(board,r+1,c+1);
    total=total+checkCell(board,r-1,c-1);
    total=total+checkCell(board,r+1,c-1);
    total=total+checkCell(board,r-1,c+1);
    return total;
  }
  public static boolean checkEmptyBoard(int [][] board){
    int count = 0;
    for(int x=0;x<board.length;x++){
      for(int y=0;y<board.length;y++){
        count=count+checkCell(board,x,y);
      }
    }
    if(count==0) return true;
    else return false;
  }
}
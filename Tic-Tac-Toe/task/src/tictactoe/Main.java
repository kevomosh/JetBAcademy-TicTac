package tictactoe;

import java.io.IOException;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[][] arr = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0;  j< 3; j++) {
               arr[i][j] = ' ';
            }
        }
        int moves = 1;
        char letter;
        do {
            printArr(arr);
             letter = moves % 2 == 0 ? 'O' : 'X';
             if (takeInputs(sc,arr, letter)) moves++;
             if (moves > 2) {
                 checkRowsAndColumns(arr);
             }
        } while (moves < 10);

   }

   private static void checkRowsAndColumns(char[][] arr) {
        int[][] ansArr = new int[3][6];
        for (int i = 0; i < 3 ; i++) {
           int xRowCount = 0;
           int oRowCount = 0;
           int emptyCount = 0;
           var inArray = arr[i];
           int xColCount = 0;
           int oColCount = 0;
           int z = i;
           var colArray = IntStream.range(0, 3)
                   .map(e -> arr[e][z])
                   .collect(StringBuilder::new,
                           StringBuilder::appendCodePoint,
                           StringBuilder::append)
                   .toString()
                   .toCharArray();

           for (int j = 0; j < 3 ; j++) {
               if (inArray[j] == 'X') {
                   xRowCount++;
               } else if (inArray[j] == 'O') {
                   oRowCount++;
               } else {
                   emptyCount++;
               }

               if (colArray[j] == 'X') {
                   xColCount++;
               } else if (colArray[j] == 'O') {
                   oColCount++;
               }

               if (j == 2) {
                   ansArr[0][i] = xRowCount;
                   ansArr[1][i] = oRowCount;
                   ansArr[2][i] = emptyCount;
                   ansArr[0][3 + i] = xColCount;
                   ansArr[1][3 + i ] = oColCount;
               }
           }
       }

       susAnsArr(ansArr,arr);
   }

    private static void susAnsArr(int[][] ans, char[][] arr) {
        int xTotal = 0; int oTotal = 0; int emptyTotal = 0;
        boolean xRowWins = false; boolean xColWins = false;
        boolean oRoWins = false; boolean oColWins = false;
        for (int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 6 ; j++) {
                if (i == 0) {
                    if (j < 3) {
                        xTotal = xTotal + ans[i][j];
                        if (ans[i][j] == 3 ) {
                            xRowWins = true;
                        }
                    } else {
                        if (ans[i][j] == 3) {
                            xColWins = true;
                        }
                    }
                } else if (i == 1) {
                    if (j < 3) {
                        oTotal = oTotal + ans[i][j];
                        if (ans[i][j] == 3 ) {
                            oRoWins = true;
                        }
                    } else {
                        if (ans[i][j] == 3 ) {
                            oColWins = true;
                        }
                    }
                } else {
                    emptyTotal = emptyTotal + ans[i][j];
                }
            }
        }
        if (!xRowWins && !oRoWins && !oColWins && !xColWins) {
            var y = getDiagonal(arr);
            switch (y){
                case 'X':
                    xRowWins = true;
                    break;
                case 'O':
                    oColWins = true;
                    break;
            }
        }
        if (emptyTotal == 0 && !xRowWins && !oRoWins && !xColWins && !oColWins) {
            printArr(arr);
            System.out.println("Draw");
            System.exit(0);
        } else if ((xRowWins || xColWins)) {
            printArr(arr);
            System.out.println("X wins");
            System.exit(0);
        } else if (oRoWins || oColWins) {
            printArr(arr);
            System.out.println("O wins");
            System.exit(0);
        }
    }

    private static char getDiagonal(char[][] arr) {
        char mid = arr[1][1];
        if( mid == arr[2][2] && mid == arr[0][0]) {
            return mid;
        } else if (mid == arr[0][2] && mid == arr[2][0]){
            return mid;
        } else return 'k';
    }


   private static boolean takeInputs(Scanner sc, char[][] arr, char letter) {
        boolean added = false;
       try {
            System.out.print("Enter the coordinates: ");
            var a = sc.nextInt() - 1;
            var b = sc.nextInt() - 1;
            if (a > 2 || b > 2) {
                throw new Exception();
            }
            if (arr[a][b] == 'X' || arr[a][b] == 'O') {
                throw new IOException();
            } else {
                arr[a][b] = letter;
            }
            added = true;

        } catch (NumberFormatException ne) {
            System.out.println("You should enter numbers!");
            takeInputs(sc, arr, letter);
        } catch (IOException ioe){
           System.out.println("The cell is occupied! Choose another one!");
           takeInputs(sc,arr, letter);
       } catch (Exception e) {
            System.out.println("Coordinates should be from 1 to 3!");
            takeInputs(sc, arr, letter);
        }
       return added;
   }

   private static void printArr(char[][] arr) {
       System.out.print("---------");
       System.out.println();
       for (int i = 0; i < 3; i++) {
           for (int j = 0; j < 3; j++) {
               if (j == 0) {
                   System.out.print("| ");
               }
               System.out.print(arr[i][j] + " ");
               if (j == 2) {
                   System.out.print("|");
               }
           }
           System.out.println();
       }
       System.out.print("---------");
       System.out.println();
   }
}

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        var size = n * n;
        int[][] arr = new int[size][size];
        int[] correctArr = IntStream.rangeClosed(1, size).toArray();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                arr[i][j] = sc.nextInt();
            }
        }

        int[][] arrCopy = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(arr[i], 0, arrCopy[i], 0, size);
        }
        var allCellsPassed = checkCells(n, size, arr, correctArr);
        var allRowsPassed = checkRows(arrCopy, correctArr, size);
        var allColumnsPassed = checkColumns(arr, correctArr, size);


        var allPassed = allRowsPassed && allColumnsPassed && allCellsPassed;
        if (allPassed) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }

    private static boolean checkRows(int[][] arrCopy, int[] correctArr, int size) {

        boolean pass;
        int count = 0;
        for (int i = 0; i < size; i++) {
            Arrays.sort(arrCopy[i]);
            pass = Arrays.equals(correctArr, arrCopy[i]);
            if (pass) {
                count++;
            }
        }
        return count == size;
    }

    private static boolean checkColumns(int[][] arr, int[] correctArr, int size) {
        boolean pass;
        int count = 0;
        for (int i = 0; i < size; i++) {
            int finalI = i;
            var u = IntStream.range(0, size)
                    .map(j -> arr[j][finalI])
                    .sorted()
                    .toArray();
            pass = Arrays.equals(correctArr, u);
            if (pass) {
                count++;
            }
        }
        return size == count;
    }


    private static boolean checkCells(int n, int size, int[][] arr, int[] correctArr) {
        int c = 0;
        for (int k = 1; k <= size; k++) {
            var t = getCellLimit(k, n);
            var ansArr = new int[size];

            int count = 0;
            for (int i = t[0][0]; i < t[0][1]; i++) {
                for (int j = t[1][0]; j < t[1][1]; j++) {
                    ansArr[count] = arr[i][j];
                    count++;
                }
            }
            Arrays.sort(ansArr);
            if (Arrays.equals(correctArr, ansArr)) {
                c++;
            }
        }
        return c == size;
    }

    private static int[][] getCellLimit(int cellNumber, int n) {
        int h = 0;
        int row = 0;
        int col = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                h++;
                if (h == cellNumber) {
                    row = i;
                    col = j;
                }
            }
        }

        var rowFrom = row * n;
        var colFrom = col * n;
        return new int[][]{
            {rowFrom, rowFrom + n},
            {colFrom, colFrom + n}
        };
    }
}

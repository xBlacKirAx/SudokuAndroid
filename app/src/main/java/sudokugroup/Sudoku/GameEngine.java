package sudokugroup.Sudoku;

import android.content.Context;

import java.util.LinkedList;
import java.util.Random;

public class GameEngine {

    Context context;
    private static int[][][] map = {
            {{2, 4, 3}, {8, 5, 9}, {7, 6, 1}},
            {{8, 7, 6}, {1, 4, 2}, {5, 3, 9}},
            {{5, 9, 1}, {3, 6, 7}, {4, 2, 8}},
            {{3, 8, 7}, {5, 2, 6}, {9, 1, 4}},
            {{6, 5, 9}, {4, 8, 1}, {3, 7, 2}},
            {{1, 2, 4}, {7, 9, 3}, {8, 5, 6}},
            {{7, 3, 8}, {6, 1, 4}, {2, 9, 5}},
            {{9, 1, 5}, {2, 3, 8}, {6, 4, 7}},
            {{4, 6, 2}, {9, 7, 5}, {1, 8, 3}},};
    public static int difficulty = 4;
    public static boolean hint = false;
    public SudokuCell[][] cells;
    public LinkedList<SudokuCell> blankCellList;


    public int[] getNewShuffleArray() {
        int[] shuffleNumber = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] shuffle = new int[10];

        Random random = new Random();

        int end = 8;
        for (int i = 0; i < shuffleNumber.length; i++) {
            int num = random.nextInt(end + 1);
            shuffle[i] = shuffleNumber[num];
            shuffleNumber[num] = shuffleNumber[end];
            end--;
        }
        shuffle[9] = shuffle[0];
//        for (int i = 0; i < shuffle.length; i++) {
//            System.out.println(shuffle[i]);
//        }
        return shuffle;
    }

    //用shuffle数组来替换所有9个小九宫格内的数字
    public void shuffleMap() {
        int[] shuffle = getNewShuffleArray();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                for (int k = 0; k < map[i].length; k++) {

                    for (int shuffleCount = 0; shuffleCount < shuffle.length - 1; shuffleCount++) {
                        if (map[i][j][k] == shuffle[shuffleCount]) {
                            map[i][j][k] = shuffle[shuffleCount + 1];
                            break;
                        }

                    }
                }
            }
        }

    }


//    String data = "243859761"+
//                   "876142539"+
//                   "591367428"+
//                   "387526914"+
//                   "659481372"+
//                   "124793856"+
//                   "738614295"+
//                   "915238647"+
//                   "462975183";

    String data = "";
    int numbers[][] = new int[9][9];

    public GameEngine() {
        shuffleMap();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {

                    data += map[i][j][k];

                }
            }
        }


        cells = new SudokuCell[9][9];
        blankCellList = new LinkedList<SudokuCell>();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (Math.random() * 10 < difficulty) {
                    cells[i][j] = new SudokuCell();
                    cells[i][j].recordLocation(i, j);
                    cells[i][j].recordAnswer(data.charAt(i + j * 9) - '0');
                    blankCellList.add(cells[i][j]);
                    numbers[i][j] = 0;
                } else {
                    numbers[i][j] = data.charAt(i + j * 9) - '0'; //字符转化为整形
                }
            }
        }
        System.out.println(data.length());
    }

    public String getNumber(int x, int y) {
        if (numbers[x - 1][y - 1] == 0)
            return "";
        else
            return "" + numbers[x - 1][y - 1];
    }

    public boolean checkBlank(int x, int y) {
        for (int i = 0; i < blankCellList.size(); i++) {
            SudokuCell current = blankCellList.get(i);
            if (current.i == x && current.j == y) {
                return true;
            }
        }
        return false;
    }

    public int[] getUsed(int x, int y) {
        //横
        int c[] = new int[9];
        for (int i = 0; i < 9; i++) {
            if (numbers[x][i] != 0) {
                c[numbers[x][i] - 1] = numbers[x][i];
            }
        }
        //竖
        for (int i = 0; i < 9; i++) {
            if (numbers[i][y] != 0) {
                c[numbers[i][y] - 1] = numbers[i][y];
            }
        }
        //小九宫格
        x = (x / 3) * 3;
        y = (y / 3) * 3;
        for (int i = 0; i < 9; i++) {
            if (numbers[x + i % 3][y + i / 3] != 0) {
                c[numbers[x + i % 3][y + i / 3] - 1] = numbers[x + i % 3][y + i / 3];
            }
        }
        return c;
    }

    public boolean checkContainsZero(int[] c) {
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 0) {
                return true;
            }
        }
        return false;
    }

    public void fillBlank(int i, int x, int y) {
        numbers[x][y] = i;
    }

    public boolean checkClear() {

        boolean clear = true;
        for (int i = 0; i < blankCellList.size(); i++) {
            SudokuCell current = blankCellList.get(i);
            if (checkContainsZero(getUsed(current.i, current.j))) {
                clear = false;
            }
        }

        return clear;
    }
}
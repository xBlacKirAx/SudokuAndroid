package sudokugroup.Sudoku;

/**
 * Created by Kira on 12/5/2017.
 */

public class SudokuCell {
    public int i;
    public int j;
    public int answer;

    public SudokuCell() {
    }

    public void recordLocation(int i, int j) {
        this.i = i;
        this.j = j;
    }

    void recordAnswer(int answer) {
        this.answer = answer;
    }
}
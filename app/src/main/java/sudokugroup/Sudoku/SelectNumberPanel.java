package sudokugroup.Sudoku;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;


public class SelectNumberPanel extends Dialog {

    GameView mView;
    View keys[] = new View[9];
    int[] used = new int[9];

    public SelectNumberPanel(Context context, int[] useed, GameView m) {
        super(context);
        this.mView = m;
        this.used = useed;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Select Number");
        setContentView(R.layout.table);
        int id[] = new int[]{R.id.bt1, R.id.bt2, R.id.bt3, R.id.bt4,
                R.id.bt5, R.id.bt6, R.id.bt7, R.id.bt8, R.id.bt9};
        for (int i = 0; i < 9; i++) {
            final int t = i + 1;
            keys[i] = findViewById(id[i]);
            keys[i].setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    mView.fillBlank(t);
                    dismiss();
                }
            });
        }
        for (int i = 0; i < 9; i++) {
            if (used[i] != 0) {
                if (GameEngine.hint) {
                    keys[used[i] - 1].setVisibility(View.INVISIBLE);
                } else {
                    keys[used[i] - 1].setVisibility(View.VISIBLE);
                }
            }

        }
    }
}
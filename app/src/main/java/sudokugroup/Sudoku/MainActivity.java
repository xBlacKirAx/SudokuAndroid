package sudokugroup.Sudoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public interface Callback {
        void onModifiedTextView(String value);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final GameView mv = findViewById(R.id.MainView);

        Button btndplus = findViewById(R.id.btndplus);
        btndplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GameEngine.difficulty == 8) {
                    Toast.makeText(getApplicationContext(), "The difficulty cannot be higher than 8 ", Toast.LENGTH_SHORT).show();
                } else {
                    GameEngine.difficulty++;
                    Toast.makeText(getApplicationContext(), "Now the difficulty is " + GameEngine.difficulty, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnminus = findViewById(R.id.btndminus);
        btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GameEngine.difficulty == 1) {
                    Toast.makeText(getApplicationContext(), "The difficulty cannot be lower than 1 ", Toast.LENGTH_SHORT).show();
                } else {
                    GameEngine.difficulty--;
                    Toast.makeText(getApplicationContext(), "Now the difficulty is " + GameEngine.difficulty, Toast.LENGTH_SHORT).show();
                }

            }
        });


        Button btnhint = findViewById(R.id.btnhint);
        btnhint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GameEngine.hint) {
                    GameEngine.hint = false;
                    Toast.makeText(getApplicationContext(), "Now the hint is OFF!", Toast.LENGTH_SHORT).show();
                } else {
                    GameEngine.hint = true;
                    Toast.makeText(getApplicationContext(), "Now the hint is ON!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button btnreset = findViewById(R.id.btnreset);
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameView.reset();
                mv.invalidate();
            }
        });
    }
}
package sudokugroup.Sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class GameView extends View {
    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    //方格长宽
    float width;
    float height;
    //点击的坐标
    int selectX;
    int selectY;
    static GameEngine sudoku = new GameEngine();


    //获得屏幕尺寸
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w / 9.02f;
        this.height = h / 9.02f * 2 / 3;

    }


    //绘图函数
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画背景
        Paint bgPaint = new Paint();
        bgPaint.setColor(getResources().getColor(R.color.light));
        canvas.drawRect(0, 0, getWidth(), getHeight() * 2 / 3f, bgPaint);

        //画空格
        Paint blankPaint = new Paint();
        blankPaint.setColor(Color.YELLOW);
        for (int i = 0; i < sudoku.blankCellList.size(); i++) {
            SudokuCell current = sudoku.blankCellList.get(i);
            canvas.drawRect(current.i * width, current.j * height, (current.i + 1) * width, (current.j + 1) * height, blankPaint);
        }

        //画线
        Paint thinPaint = new Paint();
        thinPaint.setColor(Color.BLACK);
        thinPaint.setStrokeWidth(3);
        Paint thickPaint = new Paint();
        thickPaint.setColor(Color.BLACK);
        thickPaint.setStrokeWidth(5);
        Paint lightPaint = new Paint();
        lightPaint.setColor(getResources().getColor(R.color.light));
        //绘制线条
        for (int i = 0; i <= 9; i++) {
            canvas.drawLine(0, i * height, getWidth(), i * height, thinPaint);
            canvas.drawLine(i * width, 0, i * width, getHeight() * 2 / 3f, thinPaint);
            if (i % 3 == 0) {
                canvas.drawLine(0, i * height, getWidth(), i * height, thickPaint);
                canvas.drawLine(i * width, 0, i * width, getHeight() * 2 / 3f, thickPaint);
            }
        }
        //画数字
        Paint numberPaint = new Paint();
        numberPaint.setColor(Color.BLACK);
        numberPaint.setStyle(Paint.Style.STROKE);
        numberPaint.setTextSize(height * 0.75f);
        numberPaint.setTextAlign(Align.CENTER);

        //调节文字居中
        FontMetrics fMetrics = numberPaint.getFontMetrics();
        float x = width / 2;
        float y = height / 2 - (fMetrics.ascent + fMetrics.descent) / 2;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                canvas.drawText(sudoku.getNumber(i + 1, j + 1), i * width + x, y + j * height, numberPaint);

            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) (event.getX() / width);
        int y = (int) (event.getY() / height);
        if (x <= 8 && y <= 8 && sudoku.checkBlank(x, y)) {
            selectX = x;
            selectY = y;
            fillBlank(0);
            invalidate();
            int[] t = sudoku.getUsed(x, y);
            SelectNumberPanel mDialog = new SelectNumberPanel(getContext(), t, this);
            mDialog.show();
        }
        invalidate();
        return super.onTouchEvent(event);
    }

    public void fillBlank(int i) {
        sudoku.fillBlank(i, selectX, selectY);
        invalidate();
        if (sudoku.checkClear()) {
            sudoku = new GameEngine();
        }
    }

    public static void reset() {
        sudoku = new GameEngine();

    }
}
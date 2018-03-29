package com.example.mypc.a15puzzlegametechkids;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainGameActivity extends AppCompatActivity {

    // declaration


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_current_moving)
    TextView tvCurrentMoving;
    @BindView(R.id.tv_best_moving)
    TextView tvBestMoving;
    @BindView(R.id.cl_main_board)
    ConstraintLayout clMainBoard;
    @BindView(R.id.iv_continue)
    ImageView ivContinue;
    @BindView(R.id.iv_newgame)
    ImageView ivNewgame;
    @BindView(R.id.iv_solve)
    ImageView ivSolve;
    @BindView(R.id.iv_quit)
    ImageView ivQuit;
    @BindView(R.id.cl_menu_box)
    ConstraintLayout clMenuBox;
    @BindView(R.id.cl_score_board)
    ConstraintLayout clScoreBoard;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.tv_current_result)
    TextView tvCurrentResult;
    @BindView(R.id.tv_best_result)
    TextView tvBestResult;
    @BindView(R.id.tv_best_timer)
    TextView tvBestTimer;
    ConstraintLayout clDielogReset;
    View vCancelDielog, vYesDielog, vNoDielog;


    Timer timer;


    private static final String TAG = "MainGameActivity";
    Chronometer chronometer;
    private ImageView[][] ivPuzzle = new ImageView[10][10];
    private GestureDetector gestureDetector;
    private SpecialPuzzle emptyPuzzle;
    private int[][] puzzle = new int[6][6];
    private boolean[] onTouchable = new boolean[6];
    private boolean firstMoving = false;
    private final int[] directX = {0, -1, 0, 1};
    private final int[] directY = {1, 0, -1, 0};
    private final int LEFT_TO_RIGHT = 0, DOWN_TO_UP = 1, RIGHT_TO_LEFT = 2, UP_TO_DOWN = 3;
    private final int WIDTH = 4, HEIGHT = 4;
    private int numberMoving = 0;
    private int[][] idIvPuzzles = {
            {R.drawable.aa, R.drawable.ab, R.drawable.ac, R.drawable.ad},
            {R.drawable.ba, R.drawable.bb, R.drawable.bc, R.drawable.bd},
            {R.drawable.ca, R.drawable.cb, R.drawable.cc, R.drawable.cd},
            {R.drawable.da, R.drawable.db, R.drawable.dc, R.drawable.dd}
    };


    // end declaration

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_game);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        Define();
        Initialization();
        setupUI();



    }

    @OnClick({R.id.iv_back, R.id.tv_current_moving, R.id.tv_best_moving, R.id.cl_main_board, R.id.iv_continue, R.id.iv_newgame, R.id.iv_solve, R.id.iv_quit, R.id.cl_menu_box, R.id.iv_menu, R.id.cl_score_board, R.id.v_cancel_dielog, R.id.v_no_dielog, R.id.v_yes_dielog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if(!onTouchable[0]) break;
                this.finish();
                break;
            case R.id.tv_current_moving:
                break;
            case R.id.tv_best_moving:
                break;
            case R.id.cl_score_board:
                clDielogReset.setVisibility(View.VISIBLE);

                if (firstMoving) timer.Pause();
                onTouchable[0] = false;


                break;
            case R.id.iv_continue:
                clMenuBox.setVisibility(View.GONE);
                if (firstMoving) timer.Continue();
                onTouchable[0] = true;
                break;
            case R.id.iv_newgame:
                numberMoving = 0;
                tvCurrentMoving.setText("0");
                if (firstMoving) timer.Stop();

                onTouchable[0] = true;
                clMenuBox.setVisibility(View.GONE);

                Initialization();
                getRandomMap();
                break;
            case R.id.iv_solve:
                int[][] newTable = getNewTable(puzzle);
                final String solution = SolvingPuzzle.solving(puzzle);
                Log.d(TAG, "onViewClicked: " + solution);
                String direct = "RULD";

                CountDownTimer countDownTimer = new CountDownTimer(solution.length()*250, 250) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int pos = (int) (solution.length())  - (int) (millisUntilFinished)/250-1;
                        switch ((int)solution.charAt(pos)){
                            case (int)('L') : swapContent(RIGHT_TO_LEFT);
                            break;
                            case (int)('U') : swapContent(DOWN_TO_UP);
                            break;
                            case (int)('D') : swapContent(UP_TO_DOWN);
                            break;
                            case (int)('R') : swapContent(LEFT_TO_RIGHT);
                            break;
                        }
                    }

                    @Override
                    public void onFinish() {
                        switch ((int)solution.charAt(solution.length() - 1)){
                            case (int)('L') : swapContent(RIGHT_TO_LEFT);
                                break;
                            case (int)('U') : swapContent(DOWN_TO_UP);
                                break;
                            case (int)('D') : swapContent(UP_TO_DOWN);
                                break;
                            case (int)('R') : swapContent(LEFT_TO_RIGHT);
                                break;
                        }

                    }
                }.start();


                String show = "";
                for(int i = 0 ; i  < 4 ;i ++){
                    for(int j = 0 ;j < 4 ;j ++) show += (" " + puzzle[i][j]);
                    show += " \n";
                }
                Log.d(TAG, "onViewClicked: " + show);
                Toast.makeText(MainGameActivity.this, solution, Toast.LENGTH_SHORT).show();
                clMenuBox.setVisibility(View.GONE);
                onTouchable[0] = true;
                break;
            case R.id.iv_quit:
                this.finish();
                break;
            case R.id.cl_menu_box:
                break;
            case R.id.iv_menu:
                if(!onTouchable[0]) break;
                clMenuBox.setVisibility(View.VISIBLE);
                if (firstMoving) timer.Pause();
                onTouchable[0] = false;
                break;
            case R.id.v_cancel_dielog:
                clDielogReset.setVisibility(View.GONE);
                onTouchable[0] = true;
                break;
            case R.id.v_yes_dielog:
                if (timer.started) {
                    timer.Stop();
                    timer.Pause();
                    firstMoving = false;
                }
                numberMoving = 0;
                tvCurrentMoving.setText("0");
                onTouchable[0] = true;
                clDielogReset.setVisibility(View.GONE);
                break;
            case R.id.v_no_dielog:
                clDielogReset.setVisibility(View.GONE);
                onTouchable[0] = true;
                break;

        }
    }

    private void Define() {
        chronometer = findViewById(R.id.cr_chromometer);
        clDielogReset = findViewById(R.id.cl_dielog__reset);
        vCancelDielog = findViewById(R.id.v_cancel_dielog);
        vYesDielog = findViewById(R.id.v_yes_dielog);
        vNoDielog = findViewById(R.id.v_no_dielog);

        ivPuzzle[0][0] = (ImageView) findViewById(R.id.iv_puzzle_0_0);
        ivPuzzle[0][1] = (ImageView) findViewById(R.id.iv_puzzle_0_1);
        ivPuzzle[0][2] = (ImageView) findViewById(R.id.iv_puzzle_0_2);
        ivPuzzle[0][3] = (ImageView) findViewById(R.id.iv_puzzle_0_3);
        ivPuzzle[1][0] = (ImageView) findViewById(R.id.iv_puzzle_1_0);
        ivPuzzle[1][1] = (ImageView) findViewById(R.id.iv_puzzle_1_1);
        ivPuzzle[1][2] = (ImageView) findViewById(R.id.iv_puzzle_1_2);
        ivPuzzle[1][3] = (ImageView) findViewById(R.id.iv_puzzle_1_3);
        ivPuzzle[2][0] = (ImageView) findViewById(R.id.iv_puzzle_2_0);
        ivPuzzle[2][1] = (ImageView) findViewById(R.id.iv_puzzle_2_1);
        ivPuzzle[2][2] = (ImageView) findViewById(R.id.iv_puzzle_2_2);
        ivPuzzle[2][3] = (ImageView) findViewById(R.id.iv_puzzle_2_3);
        ivPuzzle[3][0] = (ImageView) findViewById(R.id.iv_puzzle_3_0);
        ivPuzzle[3][1] = (ImageView) findViewById(R.id.iv_puzzle_3_1);
        ivPuzzle[3][2] = (ImageView) findViewById(R.id.iv_puzzle_3_2);
        ivPuzzle[3][3] = (ImageView) findViewById(R.id.iv_puzzle_3_3);


    }

    private void Initialization() {
        firstMoving = false;

        numberMoving = 0;
        timer = new Timer(chronometer, 0);
        emptyPuzzle = new SpecialPuzzle(HEIGHT-1, WIDTH-1, 0, true);
        for (int line = 0; line < HEIGHT; line++) {
            for (int column = 0; column < WIDTH; column++) {
                puzzle[line][column] = (line * WIDTH + column + 1)%(HEIGHT*WIDTH);
            }
        }
        puzzle[emptyPuzzle.x][emptyPuzzle.y] = emptyPuzzle.value;
        for (int line = 0; line < HEIGHT; line++) {
            for (int column = 0; column < WIDTH; column++) {
                ivPuzzle[line][column].setImageResource(idIvPuzzles[line][column]);
                ivPuzzle[line][column].setBackgroundResource(R.drawable.border4);
            }
        }
        ivPuzzle[HEIGHT-1][WIDTH-1].setImageDrawable(null);
        ivPuzzle[HEIGHT-1][WIDTH-1].setBackground(null);


        for (int position = 0; position <= 4; position++) {
            onTouchable[position] = true;
        }

    }

    private void getRandomMap(){
        int countTimes = 0;
        Random random = new Random();
        while(countTimes < 100){

            int dir = random.nextInt(4);
            int currentX = emptyPuzzle.x;
            int currentY = emptyPuzzle.y;
            int newX = currentX - directX[dir];
            int newY = currentY - directY[dir];
            if(newX < 0 || newY < 0 || newX >= HEIGHT || newY >= WIDTH){
                continue;
            }

            countTimes ++;

            puzzle[currentX][currentY] = puzzle[newX][newY];
            ivPuzzle[currentX][currentY].setImageDrawable(ivPuzzle[newX][newY].getDrawable());
            ivPuzzle[currentX][currentY].setBackgroundResource(R.drawable.border4);
            puzzle[newX][newY] = 0;
            ivPuzzle[newX][newY].setImageDrawable(null);
            ivPuzzle[newX][newY].setBackground(null);
            emptyPuzzle.x = newX;
            emptyPuzzle.y = newY;
        }
    }

    private void setupUI() {
        gestureDetector = new GestureDetector(this, new myGestureDetector());

        clMainBoard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (onTouchable[0] && onTouchable[1] && onTouchable[2] && onTouchable[3] && onTouchable[4])
                    gestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });


    }

    private int[][] getNewTable(int[][] currentTable){
        int[][] table = new int[6][6];
        for(int i = 0 ;i < HEIGHT ;i ++){
            for(int j = 0 ;j < WIDTH ;j ++){
                table[i][j] = (currentTable[i][j] + 1) % (HEIGHT * WIDTH);
            }
        }

        return table;
    }

    private boolean swapContent(int dir) {
        if (!onTouchable[0] || !onTouchable[1] || !onTouchable[2] || !onTouchable[3] || !onTouchable[4])
            return false;
        if (!firstMoving) {
            firstMoving = true;
            timer.Reset();

        }

        int oldX = emptyPuzzle.x - directX[dir];
        int oldY = emptyPuzzle.y - directY[dir];
        int newX = emptyPuzzle.x;
        int newY = emptyPuzzle.y;
        if (oldX < 0 || oldX >= HEIGHT || oldY < 0 || oldY >= HEIGHT) return false;

        ivPuzzle[newX][newY].setImageDrawable(ivPuzzle[oldX][oldY].getDrawable());
        ivPuzzle[newX][newY].setBackgroundResource(R.drawable.border4);
        ivPuzzle[oldX][oldY].setImageDrawable(null);
        ivPuzzle[oldX][oldY].setBackground(null);

        puzzle[newX][newY] = puzzle[oldX][oldY];
        puzzle[oldX][oldY] = 0;
        emptyPuzzle = new SpecialPuzzle(oldX, oldY, 0, true);

        tvCurrentMoving.setText(String.valueOf(++numberMoving));
        boolean isWin = autoCheckCorrect(puzzle);
        if (isWin) {
            timer.Pause();
            Toast.makeText(MainGameActivity.this, "Congratulations", Toast.LENGTH_SHORT).show();
        } else {
            if (timer.isPausing) timer.Continue();
        }

        return true;
    }

    private boolean autoCheckCorrect(int[][] puzzles) {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (puzzles[i][j] != i * WIDTH + j) return false;
            }
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        clMenuBox.setVisibility(View.VISIBLE);
        onTouchable[0] = false;
    }

    @OnClick(R.id.iv_menu)
    public void onViewClicked() {
    }

    private class myGestureDetector implements GestureDetector.OnGestureListener {
        final int RIGHT_QUARTER = 0;
        final int UP_QUARTER = 1;
        final int LEFT_QUARTER = 2;
        final int DOWN_QUARTER = 3;
        final int SWIP_VELOCITY = 100;
        final int SWIP_THERSHOLD = 100;


        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float velocityX, float velocityY) {

            float fromX = motionEvent1.getX();
            float fromY = motionEvent1.getY();
            float toX = motionEvent2.getX();
            float toY = motionEvent2.getY();
            int theQuarter = RIGHT_QUARTER;

            theQuarter = toX > fromX
                    ? (fromX - toX < toY - fromY && toY - fromY < toX - fromX)
                    ? RIGHT_QUARTER : toY > fromY ? DOWN_QUARTER : UP_QUARTER
                    : (fromX - toX > toY - fromY && toY - fromY > toX - fromX)
                    ? LEFT_QUARTER : toY > fromY ? DOWN_QUARTER : UP_QUARTER;


            if ((theQuarter == RIGHT_QUARTER || theQuarter == LEFT_QUARTER) && Math.abs(velocityX) > SWIP_VELOCITY) {
                if (fromX - toX > SWIP_THERSHOLD) {
                    swapContent(RIGHT_TO_LEFT);
                    Log.d(TAG, "onFling: " + "RIGHT TO LEFT");
                    //    Toast.makeText(MainGameActivity.this, "Right to Left", Toast.LENGTH_SHORT).show();
                }
                if (toX - fromX > SWIP_THERSHOLD) {
                    swapContent(LEFT_TO_RIGHT);
                    Log.d(TAG, "onFling: " + "LEFT TO RIGHT");
                    //  Toast.makeText(MainGameActivity.this, "Left to Right", Toast.LENGTH_SHORT).show();
                }
            }

            if ((theQuarter == UP_QUARTER || theQuarter == DOWN_QUARTER) && Math.abs(velocityY) > SWIP_VELOCITY) {
                if (fromY - toY > SWIP_THERSHOLD) {
                    swapContent(DOWN_TO_UP);
                    Log.d(TAG, "onFling: " + "DOWN TO UP");
                    //  Toast.makeText(MainGameActivity.this, "DOWN to Up", Toast.LENGTH_SHORT).show();
                }

                if (toY - fromY > SWIP_THERSHOLD) {
                    swapContent(UP_TO_DOWN);
                    Log.d(TAG, "onFling: " + "UP TO DOWN");
                    //  Toast.makeText(MainGameActivity.this, "UP to DOWN", Toast.LENGTH_SHORT).show();
                }
            }


            return true;
        }
    }
}

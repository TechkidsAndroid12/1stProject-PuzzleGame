package com.example.mypc.a15puzzlegametechkids;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.SupportActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.SupportMenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.iv_playgame)
    ImageView ivPlaygame;
    @BindView(R.id.iv_highscores)
    ImageView ivHighscores;
    @BindView(R.id.iv_introduction)
    ImageView ivIntroduction;
    @BindView(R.id.iv_options)
    ImageView ivOptions;
    @BindView(R.id.cl_menu_main)
    ConstraintLayout clMenuMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
    }


    @OnClick({R.id.iv_playgame, R.id.iv_options, R.id.iv_highscores, R.id.iv_introduction})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_playgame:
                Intent playgameIntent = new Intent(MainActivity.this, MainGameActivity.class);
                startActivity(playgameIntent);
                break;
            case R.id.iv_options:
                Intent optionsIntent = new Intent(MainActivity.this, OptionsActivity.class);
                startActivity(optionsIntent);
                break;
            case R.id.iv_highscores:
                Intent scoresIntent = new Intent(MainActivity.this, HighScoreActivity.class);
                startActivity(scoresIntent);
                break;
            case R.id.iv_introduction:
                Intent introductionIntent = new Intent(MainActivity.this, IntroductionActivity.class);
                startActivity(introductionIntent);

        }
    }
}

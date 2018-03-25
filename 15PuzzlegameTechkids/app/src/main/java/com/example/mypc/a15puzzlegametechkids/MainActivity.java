package com.example.mypc.a15puzzlegametechkids;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv_high_core)
    ImageView ivHighCore;
    @BindView(R.id.iv_playgame)
    ImageView ivPlaygame;
    @BindView(R.id.iv_introduction)
    ImageView ivIntroduction;
    @BindView(R.id.iv_options)
    ImageView ivOptions;
    @BindView(R.id.cl_menu_box)
    ConstraintLayout clMenuBox;
    @BindView(R.id.iv_quit)
    ImageView ivQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_high_core, R.id.iv_playgame, R.id.iv_introduction, R.id.iv_options})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_high_core:
                Intent intentHighScore = new Intent(MainActivity.this, HighScoreActivity.class);
                startActivity(intentHighScore);
                break;
            case R.id.iv_playgame:
                Intent intentPlaygame = new Intent(MainActivity.this, MainGameActivity.class);
                startActivity(intentPlaygame);
                break;
            case R.id.iv_introduction:
                clMenuBox.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_options:

                Intent intentOptions = new Intent(MainActivity.this, OptionsActivity.class);
                startActivity(intentOptions);
                break;
        }
    }

    @OnClick(R.id.iv_quit)
    public void onViewClicked() {
        clMenuBox.setVisibility(View.GONE);
    }
}

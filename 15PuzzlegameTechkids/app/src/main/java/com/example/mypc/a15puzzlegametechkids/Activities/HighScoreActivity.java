package com.example.mypc.a15puzzlegametechkids.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mypc.a15puzzlegametechkids.Database.DataManager;
import com.example.mypc.a15puzzlegametechkids.Database.ScoreModel;
import com.example.mypc.a15puzzlegametechkids.R;

import java.util.List;

public class HighScoreActivity extends AppCompatActivity {

    String[] position = new String[]{"1st", "2nd", "3rd", "4th", "5th"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        //getSupportActionBar().hide();

        ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView[] textViews = new TextView[5];

        textViews[0] = findViewById(R.id.tv_1st);
        textViews[1] = findViewById(R.id.tv_2nd);
        textViews[2] = findViewById(R.id.tv_3rd);
        textViews[3] = findViewById(R.id.tv_4th);
        textViews[4] = findViewById(R.id.tv_5th);

        for (int i = 0; i < 5; i++)
            textViews[i].setText("");

        DataManager dataManager = new DataManager(this);
        //dataManager.NewScore("xx", "3:05", 2);
        //dataManager.NewScore("xx", "1:00", 10);
        //dataManager.NewScore("xx", "5:05", 6);
        List<ScoreModel> scoreModelList = dataManager.getAllItems();

        for (int i = 0; i < scoreModelList.size(); i++)
            textViews[i].setText(position[i] + ":   "
                    + scoreModelList.get(i).name + "\n        in "
                    + scoreModelList.get(i).time + "\n        and "
                    + scoreModelList.get(i).move + " moves");

    }
}

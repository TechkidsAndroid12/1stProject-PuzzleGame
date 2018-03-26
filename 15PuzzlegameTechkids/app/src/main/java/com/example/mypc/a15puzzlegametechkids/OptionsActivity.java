package com.example.mypc.a15puzzlegametechkids;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OptionsActivity extends AppCompatActivity {

    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;
    @BindView(R.id.iv_main_image)
    ImageView ivMainImage;
    @BindView(R.id.iv_arrow_left)
    ImageView ivArrowLeft;
    @BindView(R.id.iv_arrow_right)
    ImageView ivArrowRight;

    GestureDetector gestureDetector;
    ImageView[] ivSmallPicture = new ImageView[15];
    private int[] idSmallPictures = {R.drawable.tnh, R.drawable.tnh, R.drawable.tnh, R.drawable.tnh, R.drawable.tnh, R.drawable.tnh, R.drawable.tnh, R.drawable.tnh, R.drawable.tnh, R.drawable.tnh, 0};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        ButterKnife.bind(this);
        Define();
        Initialization();
        setupUI();


    }

    @OnClick({R.id.iv_main_image, R.id.iv_arrow_left, R.id.iv_arrow_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_main_image:
                break;
            case R.id.iv_arrow_left:
                idSmallPictures[10] = (--idSmallPictures[10] + 20) % 10;
                ivMainImage.setImageResource(idSmallPictures[idSmallPictures[10]]);
                for (int i = 0; i < 10; i++)
                    ivSmallPicture[i].setBackgroundResource(R.drawable.border);
                ivSmallPicture[idSmallPictures[10]].setBackgroundResource(R.drawable.border3);
                break;
            case R.id.iv_arrow_right:
                idSmallPictures[10] = (++idSmallPictures[10] + 20) % 10;
                ivMainImage.setImageResource(idSmallPictures[idSmallPictures[10]]);
                for (int i = 0; i < 10; i++)
                    ivSmallPicture[i].setBackgroundResource(R.drawable.border);
                ivSmallPicture[idSmallPictures[10]].setBackgroundResource(R.drawable.border3);
                break;
        }
    }

    private void Define() {
        ivSmallPicture[0] = findViewById(R.id.iv_zero);
        ivSmallPicture[1] = findViewById(R.id.iv_one);
        ivSmallPicture[2] = findViewById(R.id.iv_two);
        ivSmallPicture[3] = findViewById(R.id.iv_three);
        ivSmallPicture[4] = findViewById(R.id.iv_four);
        ivSmallPicture[5] = findViewById(R.id.iv_five);
        ivSmallPicture[6] = findViewById(R.id.iv_six);
        ivSmallPicture[7] = findViewById(R.id.iv_seven);
        ivSmallPicture[8] = findViewById(R.id.iv_eight);
        ivSmallPicture[9] = findViewById(R.id.iv_nine);


    }

    private void Initialization() {
        for (int i = 0; i < 10; i++)
            ivSmallPicture[i].setImageResource(idSmallPictures[i]);
        idSmallPictures[10] = 0;
        ivMainImage.setImageResource(idSmallPictures[idSmallPictures[10]]);

    }

    private void setupUI() {

        for (int position = 0; position < 10; position++)
            setOnClickInHorizontalScoll(position);


    }

    private void setOnClickInHorizontalScoll(final int position) {
        ivSmallPicture[position].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivMainImage.setImageDrawable(ivSmallPicture[position].getDrawable());
                for (int i = 0; i < 10; i++)
                    ivSmallPicture[i].setBackgroundResource(R.drawable.border);
                ivSmallPicture[position].setBackgroundResource(R.drawable.border3);
                idSmallPictures[10] = position;

            }
        });
    }
}

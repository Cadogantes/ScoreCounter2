package com.example.android.scorecounter;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class MainActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    ImageView img_animation = (ImageView) findViewById(R.id.pear_1);

    TranslateAnimation animation = new TranslateAnimation(0.0f, 400.0f, 0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
    animation.setDuration(5000);  // animation duration
    animation.setRepeatCount(5);  // animation repeat count
    animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
    //animation.setFillAfter(true);

    img_animation.startAnimation(animation);  // start animation
}
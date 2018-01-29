package com.example.android.scorecounter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    ImageView img_animation;
    TextView tvPearCount, tvBananaCount, tvStrawberryCount;
    public float x, y, X, Y, dx, dy, xWidth, yHeight;
    RelativeLayout root;
    int iPearCount, iBananaCount, iStrawberryCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img_animation = findViewById(R.id.pear_1);
        root = findViewById(R.id.main_root);
        tvBananaCount = findViewById(R.id.banana_count);
        tvStrawberryCount = findViewById(R.id.strawberry_count);
        tvPearCount = findViewById(R.id.pear_count);

        dx = 10f;      //dx and dy will determine whether we move towards the higher or lower coordinates
        dy = 8f;
        iPearCount = 0;
        iStrawberryCount = 0;
        iBananaCount = 0;

        startAnimation(img_animation);  //starts animation of selected view
    }

    public void resetCounts(View view) {
        iPearCount = 0;
        iStrawberryCount = 0;
        iBananaCount = 0;
        actuateCounts();
    }

    public void testAnimation(View view) {
        TranslateAnimation animation = new TranslateAnimation(0.0f, 400.0f, 0.0f, 400.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(1000);
        animation.setFillAfter(true);
        img_animation.startAnimation(animation);
    }

    public void bounceAnimation(View view) {
        X = root.getWidth();        // get width of layout where we will be moving images
        Y = root.getHeight();         // get height of layout where we will be moving images
        x = view.getX();
        y = view.getY();
        xWidth = view.getWidth();
        yHeight = view.getHeight();


        if ((y <= Y - yHeight && dy > 0) || (y > 0 && dy < 0)) {   //if we are not at the edge value of y
            y = y + dy;
        } else if (y > Y - yHeight && dy > 0) {  //if we are at the edge of y
            dy = -dy;
            y = Y - yHeight;
        } else if (y < 0 && dy < 0) {
            dy = -dy;
            y = -y;
        }

        if ((x < (X - xWidth) && x >= 0 && dx > 0) || (x >= 0 && dx < 0))  //if we are not at the edge value of x
        {
            x = x + dx;
        } else if (x >= X - xWidth && dx > 0) {  //if we are at the edge of x
            dx = -dx;
            x = (X - xWidth) - (x - (X - xWidth));
        } else if (x < 0 && dx < 0) {
            dx = -dx;
            x = -x;
        }

        view.setX(x);
        view.setY(y);

    }

    public void testSet(View view) {
        img_animation.setX(img_animation.getX() + 100f);
        img_animation.setY(img_animation.getY() + 100f);
    }

    public void startAnimation(final View view) {
        Timer t = new Timer();

        t.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        bounceAnimation(view);
                    }
                },
                0,      // run first occurrence immediatetly
                40); // run every xx miliseconds
    }

    public void actuateCounts() {
        tvPearCount.setText("x " + iPearCount);
        tvBananaCount.setText("x " + iBananaCount);
        tvStrawberryCount.setText("x " + iStrawberryCount);
    }

    public void countPears(View view) {
        iPearCount++;
        actuateCounts();
    }

    public void countBananas(View view) {
        iBananaCount++;
        actuateCounts();
    }

    public void countStrawberries(View view) {
        iStrawberryCount++;
        actuateCounts();
    }
}
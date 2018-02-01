package com.example.android.scorecounter;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.abs;

public class MainActivity extends Activity {
    public int numberOfImages = 20;
    TextView tvPearCount, tvBananaCount, tvStrawberryCount;

    public float[] dx = new float[numberOfImages];       //dx and dy indicate both value (in pixels) and direction in which particular image should be moved
    public float[] dy = new float[numberOfImages];       //they are arrays as those information need to be stored betweeen each animation frame. Every animated image needs its own set of dx and dy values
    public Timer[] timers = new Timer[numberOfImages];

    RelativeLayout root;
    public float X, Y;
    int iPearCount, iBananaCount, iStrawberryCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();     //method where I put variables needing initialization

        createImageView(numberOfImages);
    }

    public void initialize() {      //method where I put variables needing initialization
        root = findViewById(R.id.main_root);
        tvBananaCount = findViewById(R.id.banana_count);
        tvStrawberryCount = findViewById(R.id.strawberry_count);
        tvPearCount = findViewById(R.id.pear_count);
        iPearCount = 0;
        iStrawberryCount = 0;
        iBananaCount = 0;

        root.post(new Runnable() {
            public void run() {
                Y = root.getHeight();
                X = root.getWidth();
            }
        });

    }

    public void reset(View view) {
        this.recreate();
    }

    private void bounceAnimation(View view, int i) {
        float x, xWidth, y, yHeight;       //declaring variables here prevents their values from being swapped between different animations
        x = view.getX();
        y = view.getY();
        xWidth = view.getWidth();
        yHeight = view.getHeight();

        if ((y <= Y - yHeight && dy[i] > 0) || (y >= 0 && dy[i] < 0)) {   //if we are not at the edge value of y
            y = y + dy[i];
        } else if (y > Y - yHeight && dy[i] > 0) {  //if we are at the edge of y
            dy[i] = -dy[i];
            y = Y - yHeight;
        } else if (y < 0 && dy[i] < 0) {
            dy[i] = -dy[i];
            y = -y;
        } else {
            y = 10;
            dy[i] = abs(dy[i]);
        }

        if ((x < (X - xWidth) && x >= 0 && dx[i] > 0) || (x >= 0 && dx[i] < 0))  //if we are not at the edge value of x
        {
            x = x + dx[i];
        } else if (x >= X - xWidth && dx[i] > 0) {  //if we are at the edge of x
            dx[i] = -dx[i];
            x = (X - xWidth) - (x - (X - xWidth));
        } else if (x < 0 && dx[i] < 0) {
            dx[i] = -dx[i];
            x = -x;
        } else {
            x = 10;
            dx[i] = abs(dx[i]);
        }

        view.setX(x);
        view.setY(y);

    }

    public void actuateCounts() {
        tvPearCount.setText("x " + iPearCount);
        tvBananaCount.setText("x " + iBananaCount);
        tvStrawberryCount.setText("x " + iStrawberryCount);
    }

    public void countPears(View view) {
        iPearCount++;
        actuateCounts();
        killView(view);

    }

    public void countBananas(View view) {
        iBananaCount++;
        actuateCounts();
        killView(view);
    }

    public void countStrawberries(View view) {
        iStrawberryCount++;
        actuateCounts();
        killView(view);
    }

    public void createImageView(int nrOfImages) {
        Random r = new Random();
        int randomInt;
        final Timer t = new Timer();    //this timer will handle calling next animation frames

        for (int i = 0; i < nrOfImages; i++) {  //we create as much images as stated in nrOfImages input variable
            final ImageView image = new ImageView(this);
            image.setId(i);
            randomInt = r.nextInt(100);
            //we have 3 types of fruits, new image will be assigned one of them randomly using result of %3
            if (randomInt % 3 == 0) {
                image.setImageResource(R.drawable.strawberry);
                image.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        countStrawberries(v);
                    }
                });
            } else if (randomInt % 3 == 1) {
                image.setImageResource(R.drawable.banana);
                image.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        countBananas(v);
                    }
                });
            } else if (randomInt % 3 == 2) {
                image.setImageResource(R.drawable.pear);
                image.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        countPears(v);
                    }
                });
            } else
                image.setImageResource(R.drawable.football);    //football on screen will mean that something is wrong with randomInt generated;

            dx[i] = r.nextFloat() * 10 + 5;     //set random start values to move vectors
            dy[i] = r.nextFloat() * 10 + 5;
            // Adds the view to the layout
            image.setX(r.nextFloat() * 800);
            image.setY(r.nextFloat() * 800);
            root.addView(image);

            //start view animation
            final int finalI = i;
            Thread thread = new Thread() {      //each animation will be handled by separate thread to lessen process load on main thread
                @Override
                public void run() {
                    t.scheduleAtFixedRate(
                            new TimerTask() {
                                public void run() {
                                    bounceAnimation(image, finalI);
                                }
                            },
                            0,      // run first occurrence immediatetly
                            20); // run every xx miliseconds. This parameter decides time between animation frames
                }
            };
            thread.start();
        }
    }

    public void killView(View view) {    //removes view from main screen
        root.removeView(view);
    }
}
package com.example.slotmachine;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.math.MathUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private TextView txt, lifeTxt, pointsTxt;
    private ImageView slot1, slot2, slot3;
    private Wheel wheel1, wheel2, wheel3;
    private Button btn;

    private boolean isStarted;
    private boolean reset = false;
    private int stopCount = 0;
    private int speed = 200;
    private int life = 3;
    private long points = 0;
    private Set combination = new HashSet();
    private int valuesImages = 0;

    private static final Random RANDOM = new Random();

    private static long randomLong(long lower, long upper) {
        return (lower + (long) (RANDOM.nextDouble() * (upper - lower)));
    }

    private void updateText() {
        lifeTxt.setText(String.valueOf(life));
        pointsTxt.setText(String.valueOf(points));
    }

    private void verifyResult() {
        if(combination.size() == 1) {
            points += 1000000 * valuesImages;
            txt.setText("Parabéns!!!");
            speed -= (speed/3);
        } else if(combination.size() == 2) {
            life--;
            points += 500 * valuesImages;
            txt.setText("Quase...");
            speed -= (speed/4);
        } else {
            life--;
            txt.setText("Perdeu uma vida!");
        }

        if(life <= 0) {
            btn.setText("RETRY");
            reset = true;
        }

        updateText();
    }

    public void setUp() {
        if(reset) {
            life = 3;
            points = 0;
            speed = 200;
            reset = false;
        }
        txt.setText("");
        updateText();
        combination.clear();
        valuesImages = 0;
    }

    private Wheel defineWheel(Wheel wheel, ImageView slot, long lower, long upper) {
        wheel = new Wheel(new Wheel.WheelListener() {
            @Override
            public void newImage(final int image) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        slot.setImageResource(image);
                    }
                });
            }
        },speed,randomLong(lower,upper));

        return wheel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slot1 = (ImageView) findViewById(R.id.image1);
        slot2 = (ImageView) findViewById(R.id.image2);
        slot3 = (ImageView) findViewById(R.id.image3);
        txt = (TextView) findViewById(R.id.txt);
        btn = (Button) findViewById(R.id.btn);
        lifeTxt = (TextView) findViewById(R.id.life);
        pointsTxt = (TextView) findViewById(R.id.points);

        updateText();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(isStarted) {
                    stopCount++;
                    if(stopCount == 1) {
                        combination.add(wheel1.getCurrentIndex());
                        valuesImages += wheel1.getValueImage();
                        wheel1.stopWheel();
                    } else if(stopCount == 2) {
                        combination.add(wheel2.getCurrentIndex());
                        valuesImages += wheel2.getValueImage();
                        wheel2.stopWheel();
                    } else {
                        combination.add(wheel3.getCurrentIndex());
                        valuesImages += wheel3.getValueImage();
                        wheel3.stopWheel();

                        btn.setText("START");
                        isStarted = false;
                        stopCount = 0;

                        verifyResult();
                    }
                } else {
                    setUp();

                    wheel1 = defineWheel(wheel1, slot1, 0, 200);
                    wheel1.start();

                    wheel2 = defineWheel(wheel2, slot2, 0, 200);
                    wheel2.start();

                    wheel3 = defineWheel(wheel3, slot3, 0, 200);
                    wheel3.start();

                    btn.setText("STOP");
                    isStarted = true;
                }
            }
        });

    }
}
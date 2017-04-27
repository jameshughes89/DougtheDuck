package com.jameshughes89.dougtheduck;

import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Random rng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide both the navigation bar and the status bar.
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);



        rng = new Random();



    }


    /**
     * When clicking the image, Doug will throw out a new tip.
     *
     * @param view This will be the onClick for the ImageView
     */
    public void dougTip(View view){

        // Picks a random tip for Doug to say
        int nextTipIndex = rng.nextInt(DebugTips.Tips.length);
        String theTip = DebugTips.Tips[nextTipIndex];

        // Puts the tip on the screen (in the textView specifically)
        TextView displayTip = (TextView) findViewById(R.id.textView);
        displayTip.setText(theTip);
    }


}

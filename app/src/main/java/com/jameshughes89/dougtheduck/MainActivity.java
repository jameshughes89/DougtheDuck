package com.jameshughes89.dougtheduck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Random rng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rng = new Random();         // Random Number Generator (rng) for selecting a tip (used in dougTip)
    }

    @Override
    public void onStart(){
        super.onStart();
        // Nothing to do in here as of now I gues...
    }

    @Override
    public void onResume() {
        super.onResume();
        hideNavAndOtherThings();

    }

    /**
     * When clicking the image, Doug will throw out a new tip.
     *
     * @param view This will be the onClick for the ImageView
     */
    public void dougTip(View view){
        // Picks a random tip for Doug to say
        String theTip = getTip();

        // Puts the tip on the screen (in the textView specifically)
        TextView displayTip = (TextView) findViewById(R.id.textView);
        displayTip.setText(theTip);
    }


    /**
     * Method to hide the navigation bar, top notifivation, and other things
     */
    private void hideNavAndOtherThings(){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }


    /**
     * Picks a random tip for Doug to say
     *
     * This method will use the system language to pick a tip in the proper language.
     *
     * @return The tip Doug will say in the devices' Language
     */
    private String getTip(){
        String language = Locale.getDefault().getLanguage();
        int nextTipIndex;
        String theTip;

        // Selects the tip based on the device's language (the case is the language)
        switch (language){
            case "en":  // English --- Also the 'default' option.
                nextTipIndex = rng.nextInt(DebugTipsEnglish.Tips.length);
                theTip = DebugTipsEnglish.Tips[nextTipIndex];
                break;

            case "fr":  // French
                nextTipIndex = rng.nextInt(DebugTipsFrench.Tips.length);
                theTip = DebugTipsFrench.Tips[nextTipIndex];
                break;

            default:    // default --- Will be English.
                nextTipIndex = rng.nextInt(DebugTipsEnglish.Tips.length);
                theTip = DebugTipsEnglish.Tips[nextTipIndex];
        }

        return theTip;
    }


}

package com.jameshughes89.dougtheduck;

import android.animation.AnimatorSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Random rng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Random Number Generator (rng) for selecting a tip (used in dougTip)
        rng = new Random();
        animateDoug();
        animateBubble();
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

    /***
     * Set's Doug's animation. When you click him, he will animate in some way.
     * The ultimate goal is to have him like be pressed somehow.
     */
    private void animateDoug(){
        View view = findViewById(R.id.imageView2);
        view.setOnTouchListener(new View.OnTouchListener() {
            ImageView doug = (ImageView)findViewById(R.id.imageView2);
            ImageView bubble = (ImageView)findViewById(R.id.imageView3);
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        doug.setImageResource(R.drawable.doug_click);
                        break;
                    case MotionEvent.ACTION_UP:
                        doug.setImageResource(R.drawable.doug);
                        if(bubble.getVisibility() == View.INVISIBLE){
                            bubble.setVisibility(View.VISIBLE);
                        }
                        dougTip(v);                     // Say the tip
                        break;
                }
                return true;
            }
        });
    }

    /**
     * Makes the speech bubble pop when you click it.
     *
     */
    private void animateBubble(){
        View view = findViewById(R.id.imageView3);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView bubble = (ImageView)findViewById(R.id.imageView3);
                TextView bubbleText = (TextView)findViewById(R.id.textView);
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        // if the bubble is visible, then pop it.
                        // Tried to do '.clearComposingText()' on the textView, but that didn't work
                        // Could've set it to invisible, but I didn't because it's slightly easier this way
                        if(bubble.getVisibility() == View.VISIBLE){
                            bubble.setImageResource(R.drawable.bubble_click);

                            // Animation chunk. Broken. Consider doing in XML format
                            //Animation poppingFade = new AlphaAnimation(1.0f, 0.0f);
                            //poppingFade.setDuration(500);
                            //Animation poppingGrow = new ScaleAnimation(1.0f, 1.05f, 1.0f, 1.05f, bubble.getWidth()/2, bubble.getHeight()/2);
                            //poppingGrow.setDuration(500);
                            //bubble.startAnimation(poppingFade);
                            //bubble.startAnimation(poppingGrow);
                            //bubbleText.startAnimation(poppingGrow);
                            // Busted above


                            bubbleText.setText("");
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        bubble.setVisibility(View.INVISIBLE);
                        bubble.setImageResource(R.drawable.bubble);
                        break;
                }
                return true;
            }
        });
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
        TextView bubbleText = (TextView)findViewById(R.id.textView);
        bubbleText.setText(theTip);
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
        // Gets the device's set language
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

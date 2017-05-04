package com.jameshughes89.dougtheduck;

import android.animation.AnimatorInflater;
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
            TextView bubbleText = (TextView)findViewById(R.id.textView);
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Have the pressed version of Doug showing when the user has finger down
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        doug.setImageResource(R.drawable.doug_click);
                        break;
                    // Have the normal Doug showing when the user stops touching him
                    case MotionEvent.ACTION_UP:
                        doug.setImageResource(R.drawable.doug);
                        // If the bubble is not FULLY visible, make it visible
                        if(bubble.getAlpha() != 1){
                            // Return bubble properties back to normal
                            bubble.setAlpha(1.0f);
                            bubbleText.setAlpha(1.0f);
                            bubble.setScaleX(1.0f);
                            bubble.setScaleY(1.0f);
                            bubbleText.setScaleX(1.0f);
                            bubbleText.setScaleY(1.0f);
                        }
                        dougTip(v);                     // Say the tip
                        break;
                }
                return true;
            }
        });
    }

    /**
     * Calls the animation that makes the speech bubble pop when you click it.
     *
     */
    private void animateBubble(){
        // Sets the proper views (bubble and bubbleText) to the animation
        ImageView bubble = (ImageView)findViewById(R.id.imageView3);
        TextView bubbleText = (TextView)findViewById(R.id.textView);
        final AnimatorSet bubbleAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.bubble);
        final AnimatorSet bubbleTextAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.bubble);
        bubbleAnimator.setTarget(bubble);
        bubbleTextAnimator.setTarget(bubbleText);

        bubble.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView bubble = (ImageView)findViewById(R.id.imageView3);
                // If the bubble is currently visible, play popping animation.
                if(bubble.getAlpha() == 1){
                    //bubble.setImageResource(R.drawable.bubble_click);
                    bubbleAnimator.start();
                    bubbleTextAnimator.start();
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

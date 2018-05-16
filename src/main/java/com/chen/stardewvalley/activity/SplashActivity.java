package com.chen.stardewvalley.activity;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.chen.stardewvalley.R;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.androidanimations.library.bouncing_entrances.BounceInDownAnimator;

public class SplashActivity extends AppCompatActivity {
    private ImageView ivTitle;
    private Transition explode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ivTitle = findViewById(R.id.iv_splash_title);

        setAnimation();
    }
    private void setAnimation(){
        BounceInDownAnimator dropOutAnimator = new BounceInDownAnimator();
        dropOutAnimator.setDuration(1500);
        YoYo.with(dropOutAnimator)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        Intent intent;
                        intent = new Intent(SplashActivity.this,HomeActivity.class);
                        SplashActivity.this.startActivity(intent);
                        finish();
                        SplashActivity.this.overridePendingTransition(0,0);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                }).playOn(ivTitle);
    }
}

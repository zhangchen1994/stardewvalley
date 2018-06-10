package com.chen.stardewvalley.utils;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by zc on 2018/6/9.
 */

public class FarmCardAnimation {
    private int centerX;
    private int centerY;
    private int depthZ = 0;
    private int duration = 400;
    private Rotate3dAnimation openAnimation;
    private Rotate3dListener RoListener;
    private TranslationListener TrListener;
    private AlphaListener AlListener;
    private ScaleListener ScListener;
    public interface Rotate3dListener{
        public void onAnimationEnd(int x,int y,int z) ;
    }
    public interface TranslationListener{
        public void onAnimationStart(TranslateAnimation animation) ;
    }
    public interface AlphaListener{
        public void onAnimationStart(AlphaAnimation animation) ;
    }
    public interface ScaleListener{
        public void onAnimationStart(ScaleAnimation animation) ;
    }
    public void setonAnimationEnd(Rotate3dListener listener){
        this.RoListener = listener;
    }
    public void setTranslationListener(TranslationListener listener){
        this.TrListener = listener;
    }
    public void setAlphaListener(AlphaListener listener){
        this.AlListener = listener;
    }
    public void setScaleListener(ScaleListener listener){
        this.ScListener = listener;
    }
    private void initOpenAnim() { //从0到90度，顺时针旋转视图，此时reverse参数为true，达到90度时动画结束时视图变得不可见，
        openAnimation = new Rotate3dAnimation(360, 270, centerX,
                centerY, depthZ, true);
        openAnimation.setDuration(duration);
        openAnimation.setFillAfter(true);
        openAnimation.setInterpolator(new AccelerateInterpolator());
        setRotateAnimListener();
    }

    public void setAnimView(View view,int x,int y) {
        centerX = x;
        centerY = y;
        initOpenAnim();
        view.startAnimation(openAnimation);
    }
    public void setRotateAnimListener(){
        openAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(RoListener != null){
                    RoListener.onAnimationEnd(centerX,centerY,depthZ);
                }

            }
        });
    }
    public void setTranslation(View view){
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,-1,
                Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0
        );
        animation.setDuration(400);
        view.startAnimation(animation);
        if(TrListener != null){
            TrListener.onAnimationStart(animation);
        }
    }
    public void setAlphaAnimation(View view){
        AlphaAnimation alphaAnimation = new AlphaAnimation(
                1,0
        );
        alphaAnimation.setDuration(1500);
        view.startAnimation(alphaAnimation);
        if(AlListener != null){
            AlListener.onAnimationStart(alphaAnimation);
        }
    }
    public void setScaleAnimation(View view){
        ScaleAnimation animation = new ScaleAnimation(1,0,1,0,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(1000);
        view.startAnimation(animation);
        if(ScListener != null){
            ScListener.onAnimationStart(animation);
        }
    }
}
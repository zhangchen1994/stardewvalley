package com.chen.stardewvalley;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * Created by zc on 2018/5/14.
 */

public class HousePopupWindow {
    private Context context;
    public PopupWindow mPopWindow;
    public HousePopupWindow(Context context){
        this.context = context;
    }
    public void initPopupWindow(){
        View contentView = LayoutInflater.from(context).inflate(R.layout.house_popup_window, null);
        mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);
        mPopWindow.setWidth(getAndroiodScreenPropertyWidth());
        mPopWindow.setHeight(getAndroiodScreenPropertyeight()/2);
        mPopWindow.setContentView(contentView);
    }
    public void showPopupWindow(){
        View viewRoot = View.inflate(context,R.layout.activity_drawer_news,null);
        mPopWindow.showAsDropDown(viewRoot,0,toolsBarHeight());
    }
    public int getAndroiodScreenPropertyWidth() {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）

        return width;
    }
    public int getAndroiodScreenPropertyeight() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）

        return height;
    }
    public int toolsBarHeight(){
        int ActionBarHeight = ((AppCompatActivity) context).getSupportActionBar().getHeight();

        return ActionBarHeight;
    }
    public AnimationSet animationOpen(int fromYDelta){
        AnimationSet set=new AnimationSet(context,null);
        set.setFillAfter(true);

        TranslateAnimation animation=new TranslateAnimation(0,0,fromYDelta,0);
        animation.setDuration(600);
        set.addAnimation(animation);

        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1);
        alphaAnimation.setDuration(600);
        set.addAnimation(alphaAnimation);


        return set;
    }
    public AnimationSet animationClose(int fromYDelta){
        AnimationSet set=new AnimationSet(context,null);
        set.setFillAfter(true);

        TranslateAnimation animation=new TranslateAnimation(0,0,0,fromYDelta);
        animation.setDuration(600);
        set.addAnimation(animation);

        AlphaAnimation alphaAnimation=new AlphaAnimation(1,0);
        alphaAnimation.setDuration(600);
        set.addAnimation(alphaAnimation);


        return set;
    }
}

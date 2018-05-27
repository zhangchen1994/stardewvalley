package com.chen.stardewvalley;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.chen.stardewvalley.domain.NewsBean;

import java.util.ArrayList;

/**
 * Created by zc on 2018/5/18.
 */

public class TouchImageView extends android.support.v7.widget.AppCompatImageView{
    private static final int REGION_NUM_H = 11;
    private static final int REGION_NUM_W = 16;
    public ArrayList<Integer> regionWList = new ArrayList<>();
    public ArrayList<Integer> regionHList = new ArrayList<>();
    private int viewWidth;
    private int viewHigth;
    public int regionWigth;
    public int regionHeight;
    private onRegionTouchListener listener;
    private onRegionLoadFinshListener listener2;

    public static interface onRegionTouchListener {
        public void onRegionTouch(int region,int x,int y);
    }
    public void setOnRegionTouchListener(onRegionTouchListener listener){
        this.listener = listener;

    }
    public static interface onRegionLoadFinshListener {
        public void onRegionFinsh(int w,int y);
    }
    public void setonRegionLoadFinshListener(onRegionLoadFinshListener listener){
        this.listener2 = listener;

    }
    public TouchImageView(Context context) {
        super(context);
        init();
    }

    public TouchImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TouchImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        this.post(new Runnable() {
            @Override
            public void run() {
                viewWidth = getWidth();
                viewHigth = getHeight();
                initRegion();
            }
        });
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    int x = (int) motionEvent.getX();
                    int y = (int) motionEvent.getY();

                    System.out.println("x="+x+" y="+y);

                    listener.onRegionTouch(getRegion(x,y),x,y);
                }
                return false;
            }
        });
    }
    private int getRegion(int x,int y){
        int w = 0;
        int h = 0;
        for(int i=0;i<regionWList.size();i++){
            if(i == regionWList.size() -1){
                if((i*regionWigth <= x && viewWidth > x)
                        || viewWidth == x){
                    w = i;
                }
            }else{
                if(i*regionWigth <= x && (i+1)*regionWigth > x){
                    w = i;
                }
            }
        }

        for(int i=0;i<regionHList.size();i++){
            if(i == regionHList.size() -1){
                if((i*regionHeight <= y && viewHigth > y)
                        || viewHigth == y){
                    h = i;
                }
            }else{
                if(i*regionHeight <= y && (i+1)*regionHeight > y){
                    h = i;
                }
            }
        }
        System.out.println(w+" "+h+" area="+(h*regionWList.size()+w));
        int regionNum = 0;
        if(regionWList.size() == REGION_NUM_W){
            regionNum = h*(regionWList.size()+1)+w;
        }else{
            regionNum = h*regionWList.size()+w;
        }
        return regionNum;
    }
    private void initRegion(){
        regionWigth = viewWidth/REGION_NUM_W;
        regionHeight = viewHigth/REGION_NUM_H;
        if(listener2 != null){
            listener2.onRegionFinsh(regionWigth,regionHeight);
        }
        int limit = 0;
        int num = 1;
        while (limit <= viewWidth){
            limit = num*regionWigth;
            num++;
            if(limit < viewWidth){
                regionWList.add(limit);
            }
        }
        if(regionWList.get(regionWList.size()-1) < viewWidth){
            regionWList.add(viewWidth);
        }
        limit = 0;
        num = 1;
        while (limit <= viewHigth){
            limit = num*regionHeight;
            num++;
            if(limit < viewHigth){
                regionHList.add(limit);
            }
        }
        if(regionHList.get(regionHList.size()-1) < viewHigth){
            regionHList.add(viewHigth);
        }
    }
}

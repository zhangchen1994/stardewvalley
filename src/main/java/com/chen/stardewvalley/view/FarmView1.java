package com.chen.stardewvalley.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.domain.CalendarBean;
import com.chen.stardewvalley.utils.DisplayUtils;
import com.chen.stardewvalley.utils.JsonParse;
import com.chen.stardewvalley.utils.Rotate3dAnimation;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by zc on 2018/6/24.
 */

public class FarmView1 {
    public View view;
    public CardView cardView;
    public Context context;
    public TextView tv1;
    public TextView tv2;
    public ExcelView excelView;
    public CalendarBean calendarBean;
    public ImageButton ib_left;
    public ImageButton ib_right;
    public int centerX;
    public int centerY;
    public int depthZ = 0;
    public int duration = 400;
    public int pager = 0;
    public int pagerSize = 0;
    public int[] title = new int[]{
            R.string.spring,
            R.string.summer,
            R.string.autumn
    };
    public Rotate3dAnimation openAnimation1;
    public Rotate3dAnimation openAnimation2;
    public int[] cardColors = new int[]{
            Color.parseColor("#ff98d1e1"),
            Color.parseColor("#ff7cdebd"),
            Color.parseColor("#FF51D169"),
            Color.parseColor("#FFC4D94E"),
            Color.parseColor("#FFCC79D6"),
            Color.parseColor("#ffd47996"),
            Color.parseColor("#FF7E84C1"),
    };
    public int[] titleFram1 = new int[]{
            R.string.image,
            R.string.tools_name,
            R.string.seed_price,
            R.string.grow_up_day,
            R.string.fram_times,
            R.string.fram_price
    } ;
    public FarmView1(Context context){
        this.context = context;
        initView(context);
    }
    public void initView(Context context){
        view = View.inflate(context, R.layout.farm_view_1,null);
        init();
    }
    public void init(){
        cardView = view.findViewById(R.id.cd_farm);
        tv1 = view.findViewById(R.id.tv_cd_1);
        tv2 = view.findViewById(R.id.tv_cd_2);
        ib_left = view.findViewById(R.id.ib_cd_left);
        ib_right = view.findViewById(R.id.ib_cd_right);
        excelView = view.findViewById(R.id.cd_ex_1);
        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) cardView.getLayoutParams();
        params.width = getAndroiodScreenPropertyWidth()- DisplayUtils
                .dp2px(context,20);
        params.height = getAndroiodScreenPropertyeight()-DisplayUtils
                .dp2px(context,20);
        int color = cardColors[getRandom(cardColors.length)];
        cardView.setCardBackgroundColor(color);
        excelView.setToplineGone();
        setTv2Text("null");
        setPagerSize();
        setCardView(0);

        ib_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = 0;
                if (pager == 0){
                    position = pagerSize -1;
                }else{
                    position = --pager;
                }
                pager = position;
                setAnimViewLeft(cardView,cardView.getWidth()/2,cardView.getHeight()/2);
                final int finalPosition = position;
                openAnimation2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Rotate3dAnimation rotateAnimation = new Rotate3dAnimation(270, 360,
                                cardView.getWidth() / 2,
                                cardView.getHeight() / 2, depthZ, false);
                        rotateAnimation.setDuration(400);
                        rotateAnimation.setFillAfter(true);
                        rotateAnimation.setInterpolator(new DecelerateInterpolator());
                        cardView.startAnimation(rotateAnimation);
                        setCardView(finalPosition);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
        ib_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = 0;
                if (pager == (pagerSize-1)){
                    position = 0;
                }else{
                    position = ++pager;
                }
                pager = position;
                setAnimViewRight(cardView,cardView.getWidth()/2,cardView.getHeight()/2);
                final int finalPosition = position;
                openAnimation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Rotate3dAnimation rotateAnimation = new Rotate3dAnimation(90, 0,
                                cardView.getWidth() / 2,
                                cardView.getHeight() / 2, depthZ, false);
                        rotateAnimation.setDuration(400);
                        rotateAnimation.setFillAfter(true);
                        rotateAnimation.setInterpolator(new DecelerateInterpolator());
                        cardView.startAnimation(rotateAnimation);
                        setCardView(finalPosition);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
    }

    public void setPagerSize(){
        pagerSize = 3;
    }
    public void setTv2Text(String text) {
        if("null".equals(text)){
            tv2.setText("<1/3>");
            return;
        }
        tv2.setText(text);
    }

    public void setCardView(int position){
        int color = cardColors[getRandom(cardColors.length)];
        cardView.setCardBackgroundColor(color);
        excelView.setLayouttTitleClear();
        tv1.setText(title[position]);
        setTv2Text("<"+(position+1)+"/3>");
        calendarBean = JsonParse.returnCalendar();
        excelView.setTitleList(titleFram1);
        excelView.setWeigthList(new int[]{1,2,2,2,2,2});
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<String> list3 = new ArrayList<>();
        ArrayList<String> list4 = new ArrayList<>();
        ArrayList<String> list5 = new ArrayList<>();
        ArrayList<String> list6 = new ArrayList<>();
        for(int i=0;i<calendarBean.links.get(position).fram_1.size();i++){
            list1.add(calendarBean.links.get(position).fram_1.get(i).images);
            list2.add(calendarBean.links.get(position).fram_1.get(i).name);
            list3.add(calendarBean.links.get(position).fram_1.get(i).seed_price);
            list4.add(calendarBean.links.get(position).fram_1.get(i).grow_up_day);
            list5.add(calendarBean.links.get(position).fram_1.get(i).times);
            list6.add(calendarBean.links.get(position).fram_1.get(i).price);
        }
        for(int i=0;i<calendarBean.links.get(position).fram_2.size();i++){
            list1.add(calendarBean.links.get(position).fram_2.get(i).images);
            list2.add(calendarBean.links.get(position).fram_2.get(i).name);
            list3.add(calendarBean.links.get(position).fram_2.get(i).seed_price);
            list4.add(calendarBean.links.get(position).fram_2.get(i).grow_up_day);
            list5.add("N/A");
            list6.add("N/A");
        }
        ArrayList<ArrayList<String>> lists = new ArrayList<>();
        lists.add(list1);lists.add(list2);
        lists.add(list3);lists.add(list4);
        lists.add(list5);lists.add(list6);
        excelView.setDataList(lists);
        excelView.setLayouttTitle();
        excelView.setAdapter();
    }
    public View returnView(){
        return view;
    }
    public int getAndroiodScreenPropertyWidth() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
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
    public int getRandom(int num) {
        Random random = new Random();
        return random.nextInt(num);
    }

    public void setAnimViewRight(View view,int x,int y) {
        centerX = x;
        centerY = y;
        openAnimation1 = new Rotate3dAnimation(360, 270, centerX,
                centerY, depthZ, true);
        openAnimation1.setDuration(duration);
        openAnimation1.setFillAfter(true);
        openAnimation1.setInterpolator(new AccelerateInterpolator());
        view.startAnimation(openAnimation1);
    }
    public void setAnimViewLeft(View view,int x,int y) {
        centerX = x;
        centerY = y;
        openAnimation2 = new Rotate3dAnimation(0, 90, centerX,
                centerY, depthZ, true);
        openAnimation2.setDuration(duration);
        openAnimation2.setFillAfter(true);
        openAnimation2.setInterpolator(new AccelerateInterpolator());
        view.startAnimation(openAnimation2);
    }
}

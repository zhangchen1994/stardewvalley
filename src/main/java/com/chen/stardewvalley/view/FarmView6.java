package com.chen.stardewvalley.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.domain.FarmBean;
import com.chen.stardewvalley.utils.DisplayUtils;
import com.chen.stardewvalley.utils.GetImageIdByName;
import com.chen.stardewvalley.utils.JsonParse;

import java.util.Random;

/**
 * Created by zc on 2018/6/29.
 */

public class FarmView6 {
    private Context context;
    private View view;
    public CardView cardView;
    public ImageView ivTitle;
    private TextView tv1;
    private TextView tv2;
    private FarmBean farmBean;
    public int[] cardColors = new int[]{
            Color.parseColor("#ff98d1e1"),
            Color.parseColor("#ff7cdebd"),
            Color.parseColor("#FF51D169"),
            Color.parseColor("#FFC4D94E"),
            Color.parseColor("#FFCC79D6"),
            Color.parseColor("#ffd47996"),
            Color.parseColor("#FF7E84C1"),
    };
    public FarmView6(Context context){
        this.context = context;
        init();
    }
    private void init(){
        view = View.inflate(context, R.layout.farm_view_5,null);
        cardView = view.findViewById(R.id.farm_5_cardview);
        ivTitle = view.findViewById(R.id.farm_5_title);
        tv1 = view.findViewById(R.id.tv_farm_5_1);
        tv2 = view.findViewById(R.id.tv_farm_5_2);
        farmBean = JsonParse.returnFarm();
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) cardView.getLayoutParams();
        params.width = getAndroiodScreenPropertyWidth()- DisplayUtils
                .dp2px(context,20);
        params.height = getAndroiodScreenPropertyeight()-DisplayUtils
                .dp2px(context,20);
        int color = cardColors[getRandom(cardColors.length)];
        cardView.setCardBackgroundColor(color);

        int imageId = GetImageIdByName.getImageId(farmBean.main.get(5).des.get(0).images,context);
        if (imageId != 0) {
            ivTitle.setImageResource(imageId);
        }
        tv1.setText(farmBean.main.get(5).des.get(0).des1);
        tv2.setText(farmBean.main.get(5).des.get(0).des2);
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
}

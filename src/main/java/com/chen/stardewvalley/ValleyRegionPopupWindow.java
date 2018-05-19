package com.chen.stardewvalley;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chen.stardewvalley.utils.DisplayUtils;

/**
 * Created by zc on 2018/5/19.
 */

public class ValleyRegionPopupWindow {
    private Context context;
    public PopupWindow mPopWindow;
    private int windtLimit;
    public ValleyRegionPopupWindow(Context context){
        this.context = context;
    }
    public void init(int x,int y,String name){
        windtLimit = (getAndroiodScreenPropertyWidth()*2)/3;
        View contentView;
        if(x<=windtLimit){
            contentView = LayoutInflater.from(context).inflate(R.layout.popup_region_view, null);
        }else{
            contentView = LayoutInflater.from(context).inflate(R.layout.popup_region_right_view, null);
        }
        TextView tvName = contentView.findViewById(R.id.tv_popup_region);
        tvName.setText(name);

        if(mPopWindow != null){
            mPopWindow.dismiss();
        }
        mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);
        mPopWindow.setContentView(contentView);
    }
    public void showPopupWindow(int x,int y){
        View viewRoot = View.inflate(context,R.layout.activity_drawer_valley,null);
        if(x<=windtLimit){
            mPopWindow.showAsDropDown(viewRoot,x,y+toolsBarHeight(),toolsBarHeight());
        }else{
            mPopWindow.showAsDropDown(viewRoot,x - DisplayUtils.dp2px(context,
                    90),y+toolsBarHeight(),toolsBarHeight());
        }
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

}

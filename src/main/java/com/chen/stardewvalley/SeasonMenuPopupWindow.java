package com.chen.stardewvalley;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.chen.stardewvalley.utils.DisplayUtils;

import java.lang.reflect.Method;

/**
 * Created by zc on 2018/6/2.
 */

public class SeasonMenuPopupWindow {
    private Context context;
    public PopupWindow popupWindow;
    private int xDown = 0;
    private int yDown = 0;
    private int xDown2 = 0;
    private int yDown2 = 0;
    private int xDown3 = 0;
    private int yDown3 = 0;
    public OnMenuClickListener listener;
    private static  SeasonMenuPopupWindow seasonMenuPopupWindow;
    public interface OnMenuClickListener{
        public void onClick();
    }
    public static SeasonMenuPopupWindow getInstance(Context context){
        if(seasonMenuPopupWindow == null){
            seasonMenuPopupWindow = new SeasonMenuPopupWindow(context);
        }
        return seasonMenuPopupWindow;
    }
    public static void removeSeasonMenuPopupWindow(){
        seasonMenuPopupWindow = null;
    }
    public void setOnMenuClickListener(OnMenuClickListener listener){
        this.listener = listener;
    }
    private SeasonMenuPopupWindow(Context context) {
        this.context = context;
        System.out.println("x = "+xDown+",y = "+yDown);
    }

    public void initPopupWindow() {
        View view = View.inflate(context, R.layout.season_menu_popup_view, null);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        xDown = (int) motionEvent.getRawX();
                        yDown = (int) motionEvent.getRawY();

                        xDown2 = (int) motionEvent.getX();
                        yDown2 = (int) motionEvent.getY();

                        xDown3 = xDown;
                        yDown3 = yDown;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int moveX = (int) motionEvent.getRawX();
                        int moveY = (int) motionEvent.getRawY();

                        xDown = moveX - xDown2;
                        yDown = moveY - yDown2;

                        if(yDown<=toolsBarHeight()){
                            yDown = toolsBarHeight();
                        }
                        if(hasNavBar(context)){
                            if(yDown>=(getAndroiodScreenPropertyeight()-getNavigationBarHeight(context))){
                                yDown = getAndroiodScreenPropertyeight()-getNavigationBarHeight(context);
                            }
                        }else{
                            if(yDown>=(getAndroiodScreenPropertyeight())){
                                yDown = getAndroiodScreenPropertyeight();
                            }
                        }
                        popupWindow.update(xDown, yDown, DisplayUtils.dp2px(context, 40),
                                DisplayUtils.dp2px(context, 40));
                        break;
                    case MotionEvent.ACTION_UP:
                        System.out.println("x = "+ (motionEvent.getRawX() - xDown3)+
                        ",y="+(motionEvent.getRawY() - yDown3));
                        int offx = (int) (motionEvent.getRawX() - xDown3);
                        int offy = (int) (motionEvent.getRawY() - yDown3);
                        if(offx <= 5&&offy <= 5&&offx>= -5&& offy >= -5){
                            listener.onClick();
                        }
                        break;
                }

                return true;
            }
        });

        popupWindow = new PopupWindow(view, DisplayUtils.dp2px(context, 40),
                DisplayUtils.dp2px(context, 40), false);

    }

    public void showPopupWindow(View view) {
        //View rootView = View.inflate(context,R.layout.activity_drawer_offer,null);

        popupWindow.showAsDropDown(view, 0, 0+DisplayUtils.dp2px(context, 40));
    }
    public int toolsBarHeight(){
        int ActionBarHeight = ((AppCompatActivity) context).getSupportActionBar().getHeight();

        return ActionBarHeight;
    }
    //获取屏幕高度
    public int getAndroiodScreenPropertyeight() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）

        return height;
    }
    //获取虚拟导航栏的高度
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }
    //判断是否有虚拟导航栏
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }

}

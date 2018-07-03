package com.chen.stardewvalley.activity;

import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.SeasonMenuPopupWindow;
import com.chen.stardewvalley.domain.CalendarBean;
import com.chen.stardewvalley.domain.PeopleBean;
import com.chen.stardewvalley.fragment.SeasonFishFragment;
import com.chen.stardewvalley.fragment.SeasonFramFragment;
import com.chen.stardewvalley.fragment.SeasonMainFragment;
import com.chen.stardewvalley.fragment.SeasonPickFragment;
import com.chen.stardewvalley.utils.DarwerLayoutUtils;
import com.chen.stardewvalley.utils.DisplayUtils;
import com.chen.stardewvalley.utils.GetImageIdByName;
import com.chen.stardewvalley.utils.JsonParse;
import com.chen.stardewvalley.utils.PeopleNameToEn;
import com.chen.stardewvalley.view.CalendarView;
import com.chen.stardewvalley.view.ExcelView;

import java.util.ArrayList;

/**
 * Created by zc on 2018/5/30.
 */

public class SeasonActivity extends AppCompatActivity{
    public final static int SEASON_MAIN = 0;
    public final static int SEASON_FRAM = 1;
    public final static int SEASON_PICK = 2;
    public final static int SEASON_FISH = 3;
    private Toolbar toolbar;
    private TextView toobarTv;
    private SeasonMainFragment mainFragment;
    private SeasonFramFragment framFragment;
    private SeasonPickFragment pickFragment;
    private SeasonFishFragment fishFragment;
    private FragmentManager fragmentManager;
    private int position;
    private LinearLayout llSeasonMenu;
    private LinearLayout menuAdd;
    private LinearLayout menuFram;
    private LinearLayout menuPick;
    private LinearLayout menuFish;
    private LinearLayout menuRemove;
    private ImageView ivFram;
    private ImageView ivPick;
    private ImageView ivFish;
    private ImageView llSeasonMinMenu;
    private boolean isAnimation = false;
    private int fragmentStatus = 0;
    private SeasonMenuPopupWindow mPopupWindow;
    private CalendarBean calendarBean;
    private DrawerLayout drawerLayout;
    private ImageButton imageButton;
    private int[] toolBarTitle = new int[]{
            R.string.spring,
            R.string.summer,
            R.string.autumn,
            R.string.winter
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_season);
        position = getIntent().getIntExtra("position",0);
        setToolBar();

        llSeasonMenu = findViewById(R.id.season_menu);
        menuAdd = findViewById(R.id.iv_season_menu_add);
        menuFram = findViewById(R.id.iv_season_menu_farm);
        menuPick = findViewById(R.id.iv_season_menu_pick);
        menuFish = findViewById(R.id.iv_season_menu_fish);
        menuRemove = findViewById(R.id.iv_season_menu_remove);
        ivFram = findViewById(R.id.iv_season_menu_farm_but);
        ivPick = findViewById(R.id.iv_season_menu_pick_but);
        ivFish = findViewById(R.id.iv_season_menu_fish_but);
        llSeasonMinMenu = findViewById(R.id.ll_season_min_menu);
        mPopupWindow = SeasonMenuPopupWindow.getInstance(this);
        calendarBean = JsonParse.returnCalendar();
        drawerLayout = findViewById(R.id.drll_season);
        imageButton = findViewById(R.id.im_but_menu);
        DarwerLayoutUtils.setDarewrClick(imageButton,drawerLayout);
        DarwerLayoutUtils.setNavigationViewMenu(drawerLayout,this);

        initFragment();

        menuRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llSeasonMenu.setVisibility(View.INVISIBLE);
                setMenuGoneAnimation();
            }
        });
        menuAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainFragment.setAddDialog();
            }
        });
        menuFram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(calendarBean.links.get(position).fram_2.size() == 0){
                    Toast.makeText(SeasonActivity.this,R.string.winter_fram,Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("position",position);
                framFragment.setArguments(bundle);
                if(fragmentStatus != SEASON_MAIN){
                    fragmentStatus = SEASON_FRAM;
                    fragmentManager.beginTransaction().setCustomAnimations(
                            R.anim.animator_up,R.anim.animator_up2
                    ).replace(R.id.sf_season, framFragment).commit();
                }else{
                    fragmentStatus = SEASON_FRAM;
                    fragmentManager.beginTransaction().setCustomAnimations(
                            R.anim.animator_up,0
                    ).add(R.id.sf_season, framFragment).commit();
                }
                setMenuStatus();
            }
        });
        menuPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("position",position);
                pickFragment.setArguments(bundle);
                if(fragmentStatus != SEASON_MAIN){
                    fragmentStatus = SEASON_PICK;
                    fragmentManager.beginTransaction().setCustomAnimations(
                            R.anim.animator_up,R.anim.animator_up2
                    ).replace(R.id.sf_season, pickFragment).commit();
                }else{
                    fragmentStatus = SEASON_PICK;
                    fragmentManager.beginTransaction().setCustomAnimations(
                            R.anim.animator_up,0
                    ).add(R.id.sf_season, pickFragment).commit();
                }
                setMenuStatus();
            }
        });
        menuFish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("position",position);
                fishFragment.setArguments(bundle);
                if(fragmentStatus != SEASON_MAIN){
                    fragmentStatus = SEASON_FISH;
                    fragmentManager.beginTransaction().setCustomAnimations(
                            R.anim.animator_up,R.anim.animator_up2
                    ).replace(R.id.sf_season, fishFragment).commit();
                }else{
                    fragmentStatus = SEASON_FISH;
                    fragmentManager.beginTransaction().setCustomAnimations(
                            R.anim.animator_up,0
                    ).add(R.id.sf_season, fishFragment).commit();
                }
                setMenuStatus();
            }
        });
    }
    private void setMenuStatus(){
        if(fragmentStatus == SEASON_MAIN){
            menuAdd.setClickable(true);
            menuFram.setClickable(true);
            menuPick.setClickable(true);
            menuFish.setClickable(true);
            ivFram.setBackgroundResource(R.drawable.season_menu_farm);
            ivFish.setBackgroundResource(R.drawable.season_menu_fish);
            ivPick.setBackgroundResource(R.drawable.season_menu_pick);
        }else if(fragmentStatus == SEASON_FRAM){
            menuAdd.setClickable(false);
            menuFram.setClickable(false);
            menuPick.setClickable(true);
            menuFish.setClickable(true);
            ivFram.setBackgroundResource(R.drawable.season_menu_farm_click);
            ivFish.setBackgroundResource(R.drawable.season_menu_fish);
            ivPick.setBackgroundResource(R.drawable.season_menu_pick);
        }else if(fragmentStatus == SEASON_PICK){
            menuAdd.setClickable(false);
            menuFram.setClickable(true);
            menuPick.setClickable(false);
            menuFish.setClickable(true);
            ivFram.setBackgroundResource(R.drawable.season_menu_farm);
            ivFish.setBackgroundResource(R.drawable.season_menu_fish);
            ivPick.setBackgroundResource(R.drawable.season_menu_pick_click);
        }else if(fragmentStatus == SEASON_FISH){
            menuAdd.setClickable(false);
            menuFram.setClickable(true);
            menuPick.setClickable(true);
            menuFish.setClickable(false);
            ivFram.setBackgroundResource(R.drawable.season_menu_farm);
            ivFish.setBackgroundResource(R.drawable.season_menu_fish_click);
            ivPick.setBackgroundResource(R.drawable.season_menu_pick);
        }
    }
    private void initFragment(){
        fragmentStatus = SEASON_MAIN;
        mainFragment = new SeasonMainFragment();
        framFragment = new SeasonFramFragment();
        pickFragment = new SeasonPickFragment();
        fishFragment = new SeasonFishFragment();
        fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        mainFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.sf_season, mainFragment).commit();
        setMenuStatus();
    }
    private void setToolBar(){
        toolbar = findViewById(R.id.season_toolbar);
        toobarTv = findViewById(R.id.tv_season_toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toobarTv.setText(toolBarTitle[position]);
    }
    private void setMenuGoneAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(1,0,1,1,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f);
        translateAnimation.setDuration(500);
        llSeasonMenu.setAnimation(translateAnimation);
        translateAnimation.start();
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                menuRemove.setClickable(false);
                isAnimation = false;
                mPopupWindow.initPopupWindow();
                mPopupWindow.showPopupWindow(llSeasonMinMenu);

                mPopupWindow.setOnMenuClickListener(new SeasonMenuPopupWindow.OnMenuClickListener() {
                    @Override
                    public void onClick() {
                        if(isAnimation){
                            menuRemove.setClickable(true);
                            mPopupWindow.popupWindow.dismiss();
                            llSeasonMenu.setVisibility(View.VISIBLE);
                            llSeasonMenu.setElevation(5);
                            llSeasonMenu.setTranslationZ(5);
                        }
                    }
                });
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimation = true;
                llSeasonMenu.setVisibility(View.INVISIBLE);
                llSeasonMenu.setElevation(0);
                llSeasonMenu.setTranslationZ(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SeasonMenuPopupWindow.removeSeasonMenuPopupWindow();
    }

    @Override
    public void onBackPressed() {
        if(fragmentStatus != SEASON_MAIN){
            fragmentStatus = SEASON_MAIN;
            Bundle bundle = new Bundle();
            bundle.putInt("position",position);
            mainFragment.setArguments(bundle);
            fragmentManager.beginTransaction().setCustomAnimations(
                    0,R.anim.animator_down
            ).replace(R.id.sf_season, mainFragment).commit();
            setMenuStatus();
        }else{
            finish();
        }
    }
}

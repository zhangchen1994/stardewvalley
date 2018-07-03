package com.chen.stardewvalley.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.utils.DarwerLayoutUtils;
import com.chen.stardewvalley.view.TouchImageView;
import com.chen.stardewvalley.ValleyRegionPopupWindow;
import com.chen.stardewvalley.fragment.ValleyDeailsFragment;
import com.chen.stardewvalley.fragment.ValleyListFragment;
import com.chen.stardewvalley.utils.GetMapRegion;

/**
 * Created by zc on 2018/5/18.
 */

public class ValleyActivity extends AppCompatActivity{
    private TouchImageView touchImageView;
    private ValleyRegionPopupWindow popupWindow;
    private Toolbar toolbar;
    private boolean isShow = false;
    private ValleyListFragment valleyListFragment;
    private ValleyDeailsFragment valleyDeailsFragment;
    private FragmentManager fragmentManager;
    private int regionWigth;
    private int regionHeight;
    private boolean isShowDetails = false;
    private DrawerLayout drawerLayout;
    private ImageButton imageButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_valley);

        touchImageView = findViewById(R.id.my_image);
        toolbar = findViewById(R.id.valley_toolbar);
        drawerLayout = findViewById(R.id.dr_valley);
        imageButton = findViewById(R.id.im_but_menu);
        DarwerLayoutUtils.setDarewrClick(imageButton,drawerLayout);
        DarwerLayoutUtils.setNavigationViewMenu(drawerLayout,this);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initPopup();
        initFragment();

    }
    private void initFragment(){
        valleyListFragment = new ValleyListFragment();
        valleyDeailsFragment = new ValleyDeailsFragment();
        fragmentManager = getSupportFragmentManager();

        touchImageView.setonRegionLoadFinshListener(new TouchImageView.onRegionLoadFinshListener() {
            @Override
            public void onRegionFinsh(int w, int y) {
                regionWigth = w;
                regionHeight = y;

                Bundle bundle = new Bundle();
                bundle.putInt("region_w",regionWigth);
                bundle.putInt("region_h",regionHeight);
                valleyListFragment.setArguments(bundle);
            }
        });
        fragmentManager.beginTransaction().replace(R.id.rv_valley, valleyListFragment).commit();

        valleyListFragment.setOnListClickListener(new ValleyListFragment.ListClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isShowDetails = true;

                Bundle bundle = new Bundle();
                bundle.putInt("position",i);
                if(isShow){
                    popupWindow.mPopWindow.dismiss();
                }
                valleyDeailsFragment.setArguments(bundle);
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.animator_enter,
                        R.anim.animator_back_2)
                        .replace(R.id.rv_valley, valleyDeailsFragment).commit();
            }
        });
    }
    private void initPopup() {
        popupWindow = ValleyRegionPopupWindow.getInstance(this);
        touchImageView.setOnRegionTouchListener(new TouchImageView.onRegionTouchListener() {
            @Override
            public void onRegionTouch(int region,int x,int y) {
                String regionName = GetMapRegion.getRegion(region);
                popupWindow.init(x,y,regionName);
                popupWindow.mPopWindow.setFocusable(false);
                if(regionName != null){
                    popupWindow.showPopupWindow(x,y);
                    isShow = true;
                }else{
                    if(isShow){
                        popupWindow.mPopWindow.dismiss();
                        isShow = false;
                    }
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(isShowDetails){
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.animator_enter_2,
                    R.anim.animator_back)
                    .replace(R.id.rv_valley, valleyListFragment).commit();
            isShowDetails = false;
        }else{
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ValleyRegionPopupWindow.removeValleyRegionPopupWindow();
    }
}

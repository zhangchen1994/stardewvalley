package com.chen.stardewvalley.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.fragment.PeopleDetailsFragment;
import com.chen.stardewvalley.fragment.PeopleListFragment;
import com.chen.stardewvalley.fragment.ToolsDeailsFragment;
import com.chen.stardewvalley.fragment.ToolsListFragment;
import com.chen.stardewvalley.utils.DarwerLayoutUtils;
import com.chen.stardewvalley.utils.JsonParse;


/**
 * Created by zc on 2018/5/22.
 */

public class ToolsActivity extends AppCompatActivity{
    private ToolsListFragment toolsListFragment;
    private ToolsDeailsFragment toolsDeatilsFragment;
    private FragmentManager fragmentManager;
    private boolean isShowDetails = false;
    private DrawerLayout drawerLayout;
    private ImageButton imageButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_tools);
        drawerLayout = findViewById(R.id.drll_home);
        imageButton = findViewById(R.id.im_but_menu);
        DarwerLayoutUtils.setDarewrClick(imageButton,drawerLayout);
        DarwerLayoutUtils.setNavigationViewMenu(drawerLayout,this);

        initFragment();
    }
    private void initFragment(){
        toolsListFragment = new ToolsListFragment();
        toolsDeatilsFragment = new ToolsDeailsFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.tools_ll, toolsListFragment).commit();

//        toolsListFragment.setOnListClickListener(new PeopleListFragment.ListClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                isShowDetails = true;
//                Bundle bundle = new Bundle();
//                bundle.putInt("position",i);
//                peopleDetailsFragment.setArguments(bundle);
//                fragmentManager.beginTransaction().setCustomAnimations(R.anim.animator_enter,
//                        R.anim.animator_back_2)
//                        .replace(R.id.ll_people, peopleDetailsFragment).commit();
//            }
//        });
        toolsListFragment.setGridViewClickListener(new ToolsListFragment.gridViewClickListener() {
            @Override
            public void onItemClick(int pager, int div, String name) {
                isShowDetails = true;
                Bundle bundle = new Bundle();
                bundle.putInt("pager",pager);
                bundle.putString("name",name);
                bundle.putInt("div",div);
                toolsDeatilsFragment.setArguments(bundle);
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.animator_enter,
                        R.anim.animator_back_2)
                        .replace(R.id.tools_ll, toolsDeatilsFragment).commit();

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(isShowDetails){
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.animator_enter_2,
                    R.anim.animator_back)
                    .replace(R.id.tools_ll, toolsListFragment).commit();
            isShowDetails = false;
        }else{
            finish();
        }
    }
}

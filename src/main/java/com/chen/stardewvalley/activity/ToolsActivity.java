package com.chen.stardewvalley.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.fragment.PeopleDetailsFragment;
import com.chen.stardewvalley.fragment.PeopleListFragment;
import com.chen.stardewvalley.fragment.ToolsListFragment;
import com.chen.stardewvalley.utils.JsonParse;


/**
 * Created by zc on 2018/5/22.
 */

public class ToolsActivity extends AppCompatActivity{
    private Fragment toolsListFragment;
    private Fragment toolsDeatilsFragment;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_tools);

        initFragment();
    }
    private void initFragment(){
        toolsListFragment = new ToolsListFragment();
        //toolsDeatilsFragment = new PeopleDetailsFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.tools_ll, toolsListFragment).commit();

//        peopleListFragment.setOnListClickListener(new PeopleListFragment.ListClickListener() {
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
    }
}

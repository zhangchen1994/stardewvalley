package com.chen.stardewvalley.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.domain.NewDivBean;
import com.chen.stardewvalley.domain.PeopleBean;
import com.chen.stardewvalley.fragment.PeopleDetailsFragment;
import com.chen.stardewvalley.fragment.PeopleListFragment;
import com.google.gson.Gson;
import com.yyydjk.library.DropDownMenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by zc on 2018/5/15.
 */

public class PeopleActivity extends AppCompatActivity{
    private PeopleListFragment peopleListFragment;
    private PeopleDetailsFragment peopleDetailsFragment;
    private boolean isShowDetails = false;
    private FragmentManager fragmentManager = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_people);

        initFragment();
    }
    private void initFragment(){
        peopleListFragment = new PeopleListFragment();
        peopleDetailsFragment = new PeopleDetailsFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.ll_people, peopleListFragment).commit();

        peopleListFragment.setOnListClickListener(new PeopleListFragment.ListClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isShowDetails = true;
                Bundle bundle = new Bundle();
                bundle.putInt("position",i);
                peopleDetailsFragment.setArguments(bundle);
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.animator_enter,
                        R.anim.animator_back_2)
                        .replace(R.id.ll_people, peopleDetailsFragment).commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(isShowDetails){
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.animator_enter_2,
                    R.anim.animator_back)
                    .replace(R.id.ll_people, peopleListFragment).commit();
            isShowDetails = false;
        }else{
            finish();
        }
    }
}

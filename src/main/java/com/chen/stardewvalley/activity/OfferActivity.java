package com.chen.stardewvalley.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.fragment.OfferListFragment;

/**
 * Created by zc on 2018/6/1.
 */

public class OfferActivity extends AppCompatActivity{
    private OfferListFragment offerListFragment;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_offer);

        initFragment();
    }
    private void initFragment(){
        offerListFragment = new OfferListFragment();
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.offer_ll, offerListFragment).commit();
    }
}

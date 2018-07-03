package com.chen.stardewvalley.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.fragment.OfferDetailsFragment;
import com.chen.stardewvalley.fragment.OfferListFragment;
import com.chen.stardewvalley.utils.DarwerLayoutUtils;

/**
 * Created by zc on 2018/6/1.
 */

public class OfferActivity extends AppCompatActivity{
    private OfferListFragment offerListFragment;
    private OfferDetailsFragment offerDetailsFragment;
    private FragmentManager fragmentManager;
    private boolean isShowDetails = false;
    private DrawerLayout drawerLayout;
    private ImageButton imageButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_offer);
        drawerLayout = findViewById(R.id.drll_offer);
        imageButton = findViewById(R.id.im_but_menu);
        DarwerLayoutUtils.setDarewrClick(imageButton,drawerLayout);
        DarwerLayoutUtils.setNavigationViewMenu(drawerLayout,this);

        initFragment();
    }
    private void initFragment(){
        offerListFragment = new OfferListFragment();
        offerDetailsFragment = new OfferDetailsFragment();
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.offer_ll, offerListFragment).commit();
        offerListFragment.setOfferClickListenter(new OfferListFragment.OfferClickListenter() {
            @Override
            public void viewClick(int position, int i) {
                isShowDetails = true;
                Bundle bundle = new Bundle();
                bundle.putInt("position",position);
                bundle.putInt("i",i);
                offerDetailsFragment.setArguments(bundle);
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.animator_enter,
                        R.anim.animator_back_2)
                        .replace(R.id.offer_ll, offerDetailsFragment).commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(isShowDetails){
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.animator_enter_2,
                    R.anim.animator_back)
                    .replace(R.id.offer_ll, offerListFragment).commit();
            isShowDetails = false;
        }else{
            finish();
        }
    }
}

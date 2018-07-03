package com.chen.stardewvalley.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.fragment.FarmCardDetailsFragment;
import com.chen.stardewvalley.fragment.FarmCardListFragment;
import com.chen.stardewvalley.utils.DarwerLayoutUtils;

/**
 * Created by zc on 2018/5/22.
 */

public class FarmActivity extends AppCompatActivity{
    private FarmCardListFragment farmCardListFragment;
    private FarmCardDetailsFragment farmCardDetailsFragment;
    private FragmentManager fragmentManager;
    private DrawerLayout drawerLayout;
    private ImageButton imageButton;
    private boolean isShowDetails = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_farm);
        drawerLayout = findViewById(R.id.drll_farm);
        imageButton = findViewById(R.id.im_but_menu);
        DarwerLayoutUtils.setDarewrClick(imageButton,drawerLayout);
        DarwerLayoutUtils.setNavigationViewMenu(drawerLayout,this);

        initFragment();
    }
    private void initFragment(){
        farmCardListFragment = new FarmCardListFragment();
        farmCardDetailsFragment = new FarmCardDetailsFragment();
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.fall_farm, farmCardListFragment).commit();

        farmCardListFragment.setOnFarmCardListener(new FarmCardListFragment.FarmCardListListener() {
            @Override
            public void farmListclick(int i) {
                isShowDetails = true;
                Bundle bundle = new Bundle();
                bundle.putInt("i",i);
                farmCardDetailsFragment.setArguments(bundle);
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.animator_enter,
                        R.anim.animator_back_2)
                        .replace(R.id.fall_farm, farmCardDetailsFragment).commit();
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(isShowDetails){
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.animator_enter_2,
                    R.anim.animator_back)
                    .replace(R.id.fall_farm, farmCardListFragment).commit();
            isShowDetails = false;
        }else{
            finish();
        }
    }
}

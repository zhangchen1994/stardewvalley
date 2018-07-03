package com.chen.stardewvalley.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.activity.FarmActivity;
import com.chen.stardewvalley.activity.HomeActivity;
import com.chen.stardewvalley.activity.NewsActivity;
import com.chen.stardewvalley.activity.OfferActivity;
import com.chen.stardewvalley.activity.PeopleActivity;
import com.chen.stardewvalley.activity.ToolsActivity;
import com.chen.stardewvalley.activity.ValleyActivity;

/**
 * Created by zc on 2018/7/1.
 */

public class DarwerLayoutUtils {
    public static void setDarewrClick(ImageButton imageButton, final DrawerLayout drawerLayout){
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
    }
    public static void setNavigationViewMenu(DrawerLayout drawerLayout, final Activity context){
        NavigationView navigationView = drawerLayout.findViewById(R.id.navigation_header_container);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                if(item.toString().equals(context.getString(R.string.home_people))){
                    Intent intent = new Intent(context,PeopleActivity.class);
                    context.startActivity(intent);
                }else if(item.toString().equals(context.getString(R.string.home_tools))){
                    Intent intent = new Intent(context,ToolsActivity.class);
                    context.startActivity(intent);
                }else if(item.toString().equals(context.getString(R.string.home_farm))){
                    Intent intent = new Intent(context,FarmActivity.class);
                    context.startActivity(intent);
                }else if(item.toString().equals(context.getString(R.string.home_offer))){
                    Intent intent = new Intent(context,OfferActivity.class);
                    context.startActivity(intent);
                }else if(item.toString().equals(context.getString(R.string.home_valley))){
                    Intent intent = new Intent(context,ValleyActivity.class);
                    context.startActivity(intent);
                }else if(item.toString().equals(context.getString(R.string.home_news))){
                    Intent intent = new Intent(context,NewsActivity.class);
                    context.startActivity(intent);
                }
                return true;
            }
        });
    }
}

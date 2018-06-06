package com.chen.stardewvalley.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.stardewvalley.HomeNewsGet;
import com.chen.stardewvalley.R;
import com.chen.stardewvalley.domain.NewsBean;
import com.sunfusheng.marqueeview.MarqueeView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by zc on 2018/5/12.
 */

public class HomeActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private ViewPager vpHome;
    private MyAdapter myAdapter =new MyAdapter();
    private  MagicIndicator magicIndicator;
    private ImageButton imageButton;
    private ViewPager vpHomeNews;
    private HomeNewsGet homeNewsGet;
    private NewsViewPageAdapter newsViewPagerAdapter;
    private ArrayList<NewsBean> newsList = new ArrayList<>();
    private int[] imageId = new int[]{
            R.drawable.spring,R.drawable.summer,R.drawable.autumn,R.drawable.winter
    };
    private ArrayList<ImageView> imageViews;
    private GridView gvHome;
    private static final int TIME = 4000;
    private static final int PEOPLE_PAGE = 0;
    private static final int TOOLS_PAGE = 1;
    private static final int FRAME_PAGE = 2;
    private static final int OFFER_PAGE = 3;
    private static final int VALLEY_PAGE = 4;
    private static final int NEWS_PAGE = 5;
    private static final int NEWS_NUMBER = 5;
    private static final int HOME_BACK_TIME = 3500;
    private GvAdapter gvAdapter = new GvAdapter();
    private DrawerLayout drawerLayout;
    private boolean isHaveFocus = false;
    private long timeFlag = 0;
    private int[] gvStringId = new int[]{
            R.string.home_people,R.string.home_tools,
            R.string.home_farm,R.string.home_offer,
            R.string.home_valley,R.string.home_news
    };
    private int[] titleStringIds = new int[]{
            R.string.spring,
            R.string.summer,
            R.string.autumn,
            R.string.winter
    };
    private int[] gvImageId = new int[]{
            R.drawable.home_people,R.drawable.home_tools,
            R.drawable.home_fram,R.drawable.home_offer,
            R.drawable.home_vallyr,R.drawable.home_news
    };
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                setViewPagerPosition();
            }else if(msg.what ==1){
                newsViewPagerAdapter = new NewsViewPageAdapter();
                vpHomeNews.setAdapter(newsViewPagerAdapter);
                if (newTimerTask != null && isHaveFocus){
                    timer.schedule(newTimerTask,8000,8000);
                }
            }else if(msg.what == 2){
                setNesViewPagerPosition();
            }
        }
    };
    Timer timer = new Timer();
    TimerTask timerTask;
    TimerTask newTimerTask;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_home);

        drawerLayout = findViewById(R.id.drll_home);
        toolbar = findViewById(R.id.home_toolbar);
        vpHome = findViewById(R.id.vp_ssaw);
        gvHome = findViewById(R.id.gv_home_page);
        magicIndicator = findViewById(R.id.magic_indicator);
        imageButton = findViewById(R.id.im_but_menu);
        vpHomeNews = findViewById(R.id.vp_home_news);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });

        homeNewsGet = new HomeNewsGet(this);
        imageViews = new ArrayList<>();
        setToolBar();
        initImageViews();

        vpHome.setAdapter(myAdapter);
        gvHome.setAdapter(gvAdapter);
        setIndicator();
        ViewPagerHelper.bind(magicIndicator,vpHome);
        setNavigationViewMenu();

        gvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = null;
                switch (i){
                    case PEOPLE_PAGE :
                        intent = new Intent(HomeActivity.this,PeopleActivity.class);
                        break;
                    case TOOLS_PAGE :
                        intent = new Intent(HomeActivity.this,ToolsActivity.class);
                        break;
                    case FRAME_PAGE :
                        intent = new Intent(HomeActivity.this,FarmActivity.class);
                        break;
                    case OFFER_PAGE :
                        intent = new Intent(HomeActivity.this,OfferActivity.class);
                        break;
                    case VALLEY_PAGE :
                        intent = new Intent(HomeActivity.this,ValleyActivity.class);
                        break;
                    case NEWS_PAGE :
                        intent = new Intent(HomeActivity.this,NewsActivity.class);
                        break;
                }
                HomeActivity.this.startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        isHaveFocus = true;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };
        newTimerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(2);
            }
        };

        initNewsData();
        timer.schedule(timerTask,TIME,TIME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isHaveFocus = false;
        timerTask.cancel();
        newTimerTask.cancel();
    }

    private void initNewsData() {
        homeNewsGet.urlJsoup();
        homeNewsGet.setLoadingListener(new HomeNewsGet.LoadingListener() {
            @Override
            public void onFinishedLoading(ArrayList<NewsBean> newsList) {
                HomeActivity.this.newsList = newsList;
                if (isHaveFocus){
                    handler.sendEmptyMessage(1);
                }
            }
        });
    }

    private void setViewPagerPosition(){
        int i = vpHome.getCurrentItem();
        if(i>=imageId.length-1){
            i = -1;
        }
        vpHome.setCurrentItem(i+1);
    }
    private void setNesViewPagerPosition(){
        int i = vpHomeNews.getCurrentItem();
        if(i>=imageId.length-1){
            i = -1;
        }
        vpHomeNews.setCurrentItem(i+1);
    }
    private void setIndicator(){
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return imageId.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.DKGRAY);
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setText(titleStringIds[index]);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vpHome.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
    }
    private void initImageViews(){
        for(int i:imageId){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(i);
            imageViews.add(imageView);
        }
    }
    private void setToolBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView view = imageViews.get(position);
            container.addView(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeActivity.this,SeasonActivity.class);
                    intent.putExtra("position",position);
                    HomeActivity.this.startActivity(intent);
                }
            });
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    class GvAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return gvStringId.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder v;
            if(view == null){
                v = new ViewHolder();
                view = View.inflate(HomeActivity.this,R.layout.view_gv_home,null);
                v.textView = view.findViewById(R.id.tv_gv_home);
                v.imageView = view.findViewById(R.id.gv_home_img);

                view.setTag(v);
            }else {
                v = (ViewHolder) view.getTag();
            }
            v.textView.setText(gvStringId[i]);
            v.imageView.setImageResource(gvImageId[i]);
            return view;
        }
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
    class NewsViewPageAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return NEWS_NUMBER;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final int newsPosition = position;
            View view = View.inflate(HomeActivity.this,R.layout.home_news,null);
            MarqueeView marqueeView = view.findViewById(R.id.marqueeView);
            marqueeView.startWithText(newsList.get(position).getTitle());
            marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
                @Override
                public void onItemClick(int i, TextView textView) {
                    Intent intent = new Intent(HomeActivity.this,NewsContentActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("url",newsList.get(newsPosition).getUrl());
                    bundle.putString("title",newsList.get(newsPosition).getTitle());
                    bundle.putString("writer",newsList.get(newsPosition).getDate());
                    bundle.putString("image_url",newsList.get(newsPosition).getImageUrl());
                    intent.putExtras(bundle);

                    HomeActivity.this.startActivity(intent);
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    private void setNavigationViewMenu(){
        NavigationView navigationView = drawerLayout.findViewById(R.id.navigation_header_container);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if(item.equals(getString(R.string.home_people))){

                }else if(item.equals(getString(R.string.home_people))){

                }else if(item.equals(getString(R.string.home_tools))){

                }else if(item.equals(getString(R.string.home_farm))){

                }else if(item.equals(getString(R.string.home_offer))){

                }else if(item.equals(getString(R.string.home_valley))){

                }else if(item.equals(getString(R.string.home_news))){

                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
       if(timeFlag == 0 || (System.currentTimeMillis() - timeFlag) > HOME_BACK_TIME){
           timeFlag = System.currentTimeMillis();
           Toast.makeText(this,R.string.home_back_toast,Toast.LENGTH_LONG).show();
       }else{
           finish();
       }
    }
}

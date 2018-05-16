package com.chen.stardewvalley.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.stardewvalley.HousePopupWindow;
import com.chen.stardewvalley.R;
import com.chen.stardewvalley.db.HouseDb;
import com.chen.stardewvalley.domain.NewsBean;
import com.chen.stardewvalley.utils.SpUtils;
import com.chen.stardewvalley.utils.AppStringUtils;
import com.scwang.smartrefresh.header.DropBoxHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.api.ScrollBoundaryDecider;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by zc on 2018/5/13.
 */

public class NewsActivity extends AppCompatActivity{
    private ListView lvNews;
    private android.support.v7.widget.Toolbar toolbar;
    private Document document = null;
    private ArrayList<NewsBean> newsList = new ArrayList<>();
    private ArrayList<NewsBean> newsMoreList = new ArrayList<>();
    private ArrayList<NewsBean> newsAddList = new ArrayList<>();
    private int newsPage = 0;
    private MyAdapter myAdapter;
    private DrawerLayout drawerLayout;
    private static final String URL_KEY = "url_list";
    private boolean refreshReturnFlag = false;
    private ImageButton ivMenu;
    private ImageButton ivHouse;
    private HousePopupWindow housePopupWindow;
    private HouseDb db;
    private ArrayList<NewsBean> houseList;
    private ListView lvHouse;
    private HouseAdapter houseAdapter = new HouseAdapter();
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                if(msg.what == 0){
                    newsAddList.clear();
                    newsAddList.addAll(newsList);
                    myAdapter.notifyDataSetChanged();
                }else if(msg.what == 1){
                    Toast.makeText(
                            NewsActivity.this,
                            R.string.fail,Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){}
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_news);

        lvNews = findViewById(R.id.lv_news);
        toolbar = findViewById(R.id.news_toolbar);
        ivMenu = findViewById(R.id.im_but_menu_news);
        drawerLayout = findViewById(R.id.drll_news);
        ivHouse = findViewById(R.id.im_but_house);
        db = HouseDb.getInstance(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        urlJsoup();
        initXUtils();
        myAdapter = new MyAdapter();

        lvNews.setAdapter(myAdapter);
        setRefash();
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NewsActivity.this,NewsContentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url",newsAddList.get(i).getUrl());
                bundle.putString("title",newsAddList.get(i).getTitle());
                bundle.putString("writer",newsAddList.get(i).getDate());
                bundle.putString("image_url",newsAddList.get(i).getImageUrl());
                intent.putExtras(bundle);

                NewsActivity.this.startActivity(intent);
            }
        });

        housePopupWindow = new HousePopupWindow(this);
        housePopupWindow.initPopupWindow();
        lvHouse = housePopupWindow.mPopWindow.getContentView().
                findViewById(R.id.lv_news_house);
        ivHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!housePopupWindow.mPopWindow.isShowing()){
                    housePopupWindow.mPopWindow.getContentView()
                            .setAnimation(housePopupWindow.animationOpen(-housePopupWindow.mPopWindow
                            .getHeight()));
                    TextView tvHouse = housePopupWindow.mPopWindow.getContentView().
                                    findViewById(R.id.tv_no_house);
                    if(db.findAll().size() != 0){
                        tvHouse.setVisibility(View.GONE);
                    }else{
                        tvHouse.setVisibility(View.VISIBLE);
                    }
                    houseList = db.findAll();
                    lvHouse.setAdapter(houseAdapter);
                    housePopupWindow.showPopupWindow();
                }else{
                    housePopupWindow.mPopWindow.dismiss();
                }
            }
        });
        lvHouse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NewsActivity.this,NewsContentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url",houseList.get(i).getUrl());
                bundle.putString("title",houseList.get(i).getTitle());
                bundle.putString("writer",houseList.get(i).getDate());
                bundle.putString("image_url",houseList.get(i).getImageUrl());
                intent.putExtras(bundle);

                NewsActivity.this.startActivity(intent);

                housePopupWindow.mPopWindow.dismiss();
            }
        });
    }
    class HouseAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return houseList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if(view != null){
                viewHolder = (ViewHolder) view.getTag();
            }else{
                viewHolder = new ViewHolder();
                view = View.inflate(getApplicationContext(),R.layout.listview_news,null);
                viewHolder.tvTitle = view.findViewById(R.id.tv_title);
                viewHolder.tvDate = view.findViewById(R.id.tv_date);
                viewHolder.imageView = view.findViewById(R.id.iv_title);
                view.setTag(viewHolder);
            }
            viewHolder.tvTitle.setText(houseList.get(i).getTitle());
            viewHolder.tvDate.setText(houseList.get(i).getDate());
            x.image().bind(viewHolder.imageView,houseList.get(i).getImageUrl());
            return view;
        }
    }
    private void setRefash(){
        final RefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        DropBoxHeader dropBoxHeader = new DropBoxHeader(this);
        BallPulseFooter ballPulseFooter = new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale);
        refreshLayout.setRefreshHeader(dropBoxHeader);
        refreshLayout.setRefreshFooter(ballPulseFooter);

        refreshLayout.autoRefresh();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                newsPage = 0;
                urlJsoupReflash();
                refreshLayout.finishRefresh(4500,refreshReturnFlag);
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            sleep(4500);
                            if (refreshReturnFlag){
                                handler.sendEmptyMessage(0);
                            }else{
                                handler.sendEmptyMessage(1);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMoreNews();
                refreshLayout.finishLoadMore(4500,refreshReturnFlag,false);
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            sleep(4500);
                            if (refreshReturnFlag){
                                newsList.addAll(newsMoreList);
                                handler.sendEmptyMessage(0);

                            }else{
                                handler.sendEmptyMessage(1);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
        refreshLayout.setEnableFooterFollowWhenLoadFinished(true);
    }
    private void loadMoreNews(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    newsPage++;
                    document = null;
                    document = Jsoup.connect(AppStringUtils.getNewsUrl(newsPage)).timeout(4000).get();
                    if(document != null){
                        refreshReturnFlag = true;
                        SpUtils.setSpString(NewsActivity.this,URL_KEY,
                                document.toString());

                    }else{
                        refreshReturnFlag = false;
                    }
                    Elements element = document.select("div.result");
                    Elements element2 = document.select("p.c-author");
                    for (int i=0;i<element.size();i++){
                        String title = element.get(i).select("h3.c-title").select("a").text();
                        String url = element.get(i).select("h3.c-title").select("a").attr("href");
                        String imageUrl = element.get(i).select("div.c-span6").select("a")
                                .select("img").attr("src");
                        String date = element2.get(i).select("p.c-author").text();
                        NewsBean news = new NewsBean();
                        news.setTitle(title);
                        news.setUrl(url);
                        news.setImageUrl(imageUrl);
                        news.setDate(date);

                        newsMoreList.add(news);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void initXUtils(){
        x.Ext.init(getApplication());
        x.Ext.setDebug(true);
        x.view().inject(this);
    }
    private void urlJsoupReflash(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    document = null;
                    document = Jsoup.connect(AppStringUtils.getNewsUrl(0)).timeout(4000).get();
                    if(document != null){
                        refreshReturnFlag = true;
                        newsList.clear();
                        newsMoreList.clear();
                        SpUtils.setSpString(NewsActivity.this,URL_KEY,
                                document.toString());

                    }else{
                        refreshReturnFlag = false;
                    }
                    Elements element = document.select("div.result");
                    Elements element2 = document.select("p.c-author");
                    for (int i=0;i<element.size();i++){
                        String title = element.get(i).select("h3.c-title").select("a").text();
                        String url = element.get(i).select("h3.c-title").select("a").attr("href");
                        String imageUrl = element.get(i).select("div.c-span6").select("a")
                                .select("img").attr("src");
                        String date = element2.get(i).select("p.c-author").text();

                        NewsBean news = new NewsBean();
                        news.setTitle(title);
                        news.setUrl(url);
                        news.setImageUrl(imageUrl);
                        news.setDate(date);

                        newsList.add(news);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void urlJsoup(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    if(SpUtils.getSpString(NewsActivity.this,URL_KEY) == null){
                        document = Jsoup.connect(AppStringUtils.getNewsUrl(0)).get();
                        SpUtils.setSpString(NewsActivity.this,URL_KEY,
                                document.toString());
                    }else{
                        document = Jsoup.parse(SpUtils.getSpString(NewsActivity.this,URL_KEY));
                    }
                    Elements element = document.select("div.result");
                    Elements element2 = document.select("p.c-author");
                    for (int i=0;i<element.size();i++){
                        String title = element.get(i).select("h3.c-title").select("a").text();
                        String url = element.get(i).select("h3.c-title").select("a").attr("href");
                        String imageUrl = element.get(i).select("div.c-span6").select("a")
                                .select("img").attr("src");
                        String date = element2.get(i).select("p.c-author").text();

                        NewsBean news = new NewsBean();
                        news.setTitle(title);
                        news.setUrl(url);
                        news.setImageUrl(imageUrl);
                        news.setDate(date);

                        newsList.add(news);
                    }
                    handler.sendEmptyMessage(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return newsAddList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if(view != null){
                viewHolder = (ViewHolder) view.getTag();
            }else{
                viewHolder = new ViewHolder();
                view = View.inflate(getApplicationContext(),R.layout.listview_news,null);
                viewHolder.tvTitle = view.findViewById(R.id.tv_title);
                viewHolder.tvDate = view.findViewById(R.id.tv_date);
                viewHolder.imageView = view.findViewById(R.id.iv_title);
                view.setTag(viewHolder);
            }
            viewHolder.tvTitle.setText(newsAddList.get(i).getTitle());
            viewHolder.tvDate.setText(newsAddList.get(i).getDate());
            x.image().bind(viewHolder.imageView,newsAddList.get(i).getImageUrl());
            return view;
        }
    }
    class ViewHolder{
        private TextView tvTitle;
        private TextView tvDate;
        private ImageView imageView;
    }
}

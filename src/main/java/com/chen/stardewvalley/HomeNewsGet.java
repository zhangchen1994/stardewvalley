package com.chen.stardewvalley;

import android.content.Context;

import com.chen.stardewvalley.activity.NewsActivity;
import com.chen.stardewvalley.domain.NewsBean;
import com.chen.stardewvalley.utils.AppStringUtils;
import com.chen.stardewvalley.utils.SpUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by zc on 2018/5/14.
 */

public class HomeNewsGet {
    private Context context;
    private Document document;
    private ArrayList<NewsBean> newsList = new ArrayList<>();
    private LoadingListener mLoadingListener;
    private static final String URL_KEY = "url_list";
    public HomeNewsGet(Context context){
        this.context = context;
    }

    public static interface LoadingListener {
        public void onFinishedLoading(ArrayList<NewsBean> newsList);
    }

    public void setLoadingListener(LoadingListener listener) {
        mLoadingListener = listener;
    }
    public void urlJsoup(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    document = Jsoup.connect(AppStringUtils.getNewsUrl(0)).timeout(3000).get();
                    if(document != null){
                        SpUtils.setSpString(context,URL_KEY,
                                document.toString());
                    }else{
                        document = Jsoup.parse(SpUtils.getSpString(context,URL_KEY));
                    }
                } catch (IOException e) {
                    document = Jsoup.parse(SpUtils.getSpString(context,URL_KEY));
                    e.printStackTrace();
                }
                if (document == null){
                    return;
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
                mLoadingListener.onFinishedLoading(newsList);
            }
        }.start();
    }
}

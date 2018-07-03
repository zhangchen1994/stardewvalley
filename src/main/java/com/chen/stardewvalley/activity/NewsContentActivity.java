package com.chen.stardewvalley.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.db.HouseDb;
import com.chen.stardewvalley.domain.NewDivBean;
import com.chen.stardewvalley.utils.SpUtils;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

/**
 * Created by zc on 2018/5/14.
 */

public class NewsContentActivity extends AppCompatActivity{
    private static final String SRC_HTTP = "src=\"//";
    private static final String SRC_HTTP_REPLACE = "src=\"https://";
    private static final String SRC_PCONLINE_REPLACE = "src=\"https://www1.pconline.com.cn/wap/2013/cms/img/loading.png\" #";
    private static final String HTML_HEAD = "<head>" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
            "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
            "<style>body{word-wrap:break-word;font-family:Arial}</style>"+
            "</head>";
    private Document document = null;
    String url;
    WebView webView;
    String html;
    Toolbar toolbar;
    private String title;
    private String writer;
    private TextView tvTitle;
    private TextView tvWriter;
    private NewDivBean urlBeanean;
    private ProgressBar progressBar;
    private ImageButton ivBack;
    private ImageButton ivHouse;
    private HouseDb db;
    private String imageUrl;
    private boolean isHaveDiv = false;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                setWebView();
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);

        webView = findViewById(R.id.web_view);
        toolbar = findViewById(R.id.news_content_toolbar);
        tvTitle = findViewById(R.id.tv_write_title);
        tvWriter = findViewById(R.id.tv_writer);
        progressBar = findViewById(R.id.pb_news);
        ivBack = findViewById(R.id.im_but_finish);
        ivHouse = findViewById(R.id.im_but_house);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        url = getIntent().getExtras().getString("url");
        title = getIntent().getExtras().getString("title");
        writer = getIntent().getExtras().getString("writer");
        imageUrl = getIntent().getExtras().getString("image_url");
        db = HouseDb.getInstance(this);

        tvTitle.setText(title);
        tvWriter.setText(writer);

        jsonParse();
        urlJsoup();
        setOnclick();

        if(db.find(url)){
            ivHouse.setBackgroundResource(R.drawable.abc_btn_rating_star_on_mtrl_alpha);
        }else{
            ivHouse.setBackgroundResource(R.drawable.abc_btn_rating_star_off_mtrl_alpha);
        }
    }
    private void setOnclick(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(db.find(url)){
                db.delete(url);
                ivHouse.setBackgroundResource(R.drawable.abc_btn_rating_star_off_mtrl_alpha);
                Toast.makeText(NewsContentActivity.this,R.string.house_cancel,
                        Toast.LENGTH_SHORT).show();
            }else{
                db.insert(title,imageUrl,writer,url);
                ivHouse.setBackgroundResource(R.drawable.abc_btn_rating_star_on_mtrl_alpha);
                Toast.makeText(NewsContentActivity.this,R.string.house_ok,
                        Toast.LENGTH_SHORT).show();
            }

            }
        });
    }
    private void setWebView(){
        if(isHaveDiv){
            progressBar.setVisibility(View.GONE);
            webView.loadData(html, "text/html; charset=UTF-8", null);
        }else{
            webView.loadUrl(url);
        }
        webView.setBackgroundColor(0);
        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);// 可以使用插件
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
    }
    private void urlJsoup(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    if(SpUtils.getSpString(NewsContentActivity.this,
                            url) != null){
                        document = Jsoup.parse(SpUtils.getSpString(NewsContentActivity.this,
                                url));
                    }else{
                        document = Jsoup.connect(url).get();
                        SpUtils.setSpString(NewsContentActivity.this,
                                url,document.toString());
                    }
                    String div = achieveDiv();
                    Elements element = document.select(div);
                    String url = element.toString().replace(SRC_HTTP,SRC_HTTP_REPLACE);
                    url = url.replace(SRC_PCONLINE_REPLACE,"");
                    html = "<html>" + HTML_HEAD + "<body>" + url + "</body></html>";
                    handler.sendEmptyMessage(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void jsonParse(){
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("assets/"+"label.json");
        String gsonStr = inToString(inputStream);
        Gson gson = new Gson();
        urlBeanean = gson.fromJson(gsonStr,NewDivBean.class);
    }
    private String inToString(InputStream in){
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString().trim();
    }
    public String achieveDiv(){
        String div = "div";
        String documentStr = document.toString();
        for(NewDivBean.HtmlPart b :urlBeanean.links){
            String[] strings = b.label.split(Pattern.quote("."));
            String label = null;
            String label_2 = null;
            if(b.id == null){
                if(strings.length<3){
                    label = strings[0]+" class=\""+strings[1]+"\"";
                }else{
                    label = strings[0]+" class=\""+strings[1]+" "+strings[2]+"\"";
                }
            }else{
                if(strings.length<3){
                    label = strings[0]+" class=\""+strings[1]+"\""
                    +" id=\""+b.id+"\"";
                    label_2 = strings[0]+" id=\""+b.id+"\""+" class=\""+strings[1]+"\"";
                }else{
                    label = strings[0]+" class=\""+strings[1]+" "+strings[2]+"\""
                            +" id=\""+b.id+"\"";
                    label_2 = strings[0]+" id=\""+b.id+"\""+" class=\""+strings[1]+"\""+
                            " "+strings[2]+"\"";
                }
            }
            if(label != null){
                if(documentStr.contains(label)){
                    div = b.label;
                    isHaveDiv = true;
                }
            }
            if("div".equals(div)&&label_2 !=null){
                if(documentStr.contains(label_2)){
                    div = b.label;
                    isHaveDiv = true;
                }
            }
        }
        return div;
    }
}

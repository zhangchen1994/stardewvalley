package com.chen.stardewvalley.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.domain.CalendarBean;
import com.chen.stardewvalley.utils.JsonParse;
import com.chen.stardewvalley.view.ExcelView;

import java.util.ArrayList;

/**
 * Created by zc on 2018/6/5.
 */

public class SeasonFishFragment extends Fragment{
    private View view;
    private CalendarBean calendarBean;
    private int position;
    private ProgressBar progressBar;
    private TextView tvPb;
    private ExcelView excelView;
    private int[] titleFish = new int[]{
            R.string.image,
            R.string.tools_name,
            R.string.tools_describe,
            R.string.tools_local,
            R.string.fish_time,
            R.string.season,
            R.string.weather
    };
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                progressBar.setVisibility(View.GONE);
                tvPb.setVisibility(View.GONE);
            }else{
                excelView.setLayouttTitle();
                excelView.setAdapter();
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_season_fish,null);
        init();
        return view;
    }
    private void init(){
        position = getArguments().getInt("position");
        calendarBean = JsonParse.returnCalendar();
        excelView = view.findViewById(R.id.ex_season_fish);
        progressBar = view.findViewById(R.id.pb_season_pick);
        tvPb = view.findViewById(R.id.tv_season_pb_pick);

        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
                excelView.setTitleList(titleFish);
                excelView.setWeigthList(new int[]{2,2,3,3,2,2,2});

                ArrayList<String> list1 = new ArrayList<>();
                ArrayList<String> list2 = new ArrayList<>();
                ArrayList<String> list3 = new ArrayList<>();
                ArrayList<String> list4 = new ArrayList<>();
                ArrayList<String> list5 = new ArrayList<>();
                ArrayList<String> list6 = new ArrayList<>();
                ArrayList<String> list7 = new ArrayList<>();

                for (int i=0;i<calendarBean.links.get(position).fish.size();i++){
                    list1.add(calendarBean.links.get(position).fish.get(i).images);
                    list2.add(calendarBean.links.get(position).fish.get(i).name);
                    list3.add(calendarBean.links.get(position).fish.get(i).describe);
                    list4.add(calendarBean.links.get(position).fish.get(i).local);
                    list5.add(calendarBean.links.get(position).fish.get(i).time);
                    list6.add(calendarBean.links.get(position).fish.get(i).season);
                    String weather = calendarBean.links.get(position).fish.get(i).weather;
                    if(weather == ""){
                        weather = "N/A";
                    }
                    list7.add(weather);
                }
                ArrayList<ArrayList<String>> lists = new ArrayList<>();
                lists.add(list1);
                lists.add(list2);
                lists.add(list3);
                lists.add(list4);
                lists.add(list5);
                lists.add(list6);
                lists.add(list7);

                excelView.setDataList(lists);

                handler.sendEmptyMessage(0);
            }
        }.start();
    }
}

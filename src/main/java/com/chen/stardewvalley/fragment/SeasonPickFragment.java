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

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by zc on 2018/6/5.
 */

public class SeasonPickFragment extends Fragment{
    private View view;
    private ExcelView excelView;
    private int position;
    private CalendarBean calendarBean;
    private ProgressBar progressBar;
    private TextView tvPb;
    private int[] titlePick = new int[]{
            R.string.image,
            R.string.tools_name,
            R.string.tools_local,
            R.string.tools_describe
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
        view = inflater.inflate(R.layout.fragment_season_pick,null);
        init();
        return view;
    }
    private void init(){
        position = getArguments().getInt("position");
        excelView = view.findViewById(R.id.ex_season_pick);
        progressBar = view.findViewById(R.id.pb_season_pick);
        tvPb = view.findViewById(R.id.tv_season_pb_pick);
        calendarBean = JsonParse.returnCalendar();

        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
                excelView.setTitleList(titlePick);
                excelView.setWeigthList(new int[]{1,2,2,3});
                ArrayList<String> list1 = new ArrayList<>();
                ArrayList<String> list2 = new ArrayList<>();
                ArrayList<String> list3 = new ArrayList<>();
                ArrayList<String> list4 = new ArrayList<>();
                for(int i=0;i<calendarBean.links.get(position).pick.size();i++){
                    list1.add(calendarBean.links.get(position).pick.get(i).images);
                    list2.add(calendarBean.links.get(position).pick.get(i).name);
                    list3.add(calendarBean.links.get(position).pick.get(i).local);
                    list4.add(calendarBean.links.get(position).pick.get(i).describe);
                }
                ArrayList<ArrayList<String>> lists = new ArrayList<>();
                lists.add(list1);
                lists.add(list2);
                lists.add(list3);
                lists.add(list4);

                excelView.setDataList(lists);

                handler.sendEmptyMessage(0);
            }
        }.start();


    }
}

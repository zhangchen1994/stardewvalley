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
import android.widget.Toast;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.activity.SeasonActivity;
import com.chen.stardewvalley.domain.CalendarBean;
import com.chen.stardewvalley.utils.JsonParse;
import com.chen.stardewvalley.view.ExcelView;

import java.util.ArrayList;

/**
 * Created by zc on 2018/6/5.
 */

public class SeasonFramFragment extends Fragment{
    private View view;
    private ExcelView excelView1;
    private ExcelView excelView2;
    private CalendarBean calendarBean;
    private int position;
    private ProgressBar progressBar;
    private TextView tvPb;
    private int[] titleFram1 = new int[]{
            R.string.image,
            R.string.tools_name,
            R.string.seed_price,
            R.string.grow_up_day,
            R.string.fram_times,
            R.string.fram_price
    } ;
    private int[] titleFram2 = new int[]{
            R.string.image,
            R.string.tools_name,
            R.string.seed_price,
            R.string.grow_up_day,
            R.string.grow_up_days
    } ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_season_fram,null);
        init();
        return view;
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                progressBar.setVisibility(View.GONE);
                tvPb.setVisibility(View.GONE);
            }else{
                excelView1.setLayouttTitle();
                excelView1.setAdapter();
                excelView1.setListHeigh();
                excelView2.setLayouttTitle();
                excelView2.setAdapter();
                excelView2.setListHeigh();
            }
        }
    };
    private void init(){
        position = getArguments().getInt("position");
        excelView1 = view.findViewById(R.id.ex_season_fram_1);
        excelView2 = view.findViewById(R.id.ex_season_fram_2);
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

                excelView1.setTitleList(titleFram1);
                excelView1.setWeigthList(new int[]{1,2,2,2,2,2});
                ArrayList<String> list1 = new ArrayList<>();
                ArrayList<String> list2 = new ArrayList<>();
                ArrayList<String> list3 = new ArrayList<>();
                ArrayList<String> list4 = new ArrayList<>();
                ArrayList<String> list5 = new ArrayList<>();
                ArrayList<String> list6 = new ArrayList<>();
                for(int i=0;i<calendarBean.links.get(position).fram_1.size();i++){
                    list1.add(calendarBean.links.get(position).fram_1.get(i).images);
                    list2.add(calendarBean.links.get(position).fram_1.get(i).name);
                    list3.add(calendarBean.links.get(position).fram_1.get(i).seed_price);
                    list4.add(calendarBean.links.get(position).fram_1.get(i).grow_up_day);
                    list5.add(calendarBean.links.get(position).fram_1.get(i).times);
                    list6.add(calendarBean.links.get(position).fram_1.get(i).price);
                }
                ArrayList<ArrayList<String>> lists = new ArrayList<>();
                lists.add(list1);lists.add(list2);
                lists.add(list3);lists.add(list4);
                lists.add(list5);lists.add(list6);
                excelView1.setDataList(lists);

                excelView2.setTitleList(titleFram2);
                excelView2.setWeigthList(new int[]{1,2,2,2,2});
                ArrayList<String> list2_1 = new ArrayList<>();
                ArrayList<String> list2_2 = new ArrayList<>();
                ArrayList<String> list2_3 = new ArrayList<>();
                ArrayList<String> list2_4 = new ArrayList<>();
                ArrayList<String> list2_5 = new ArrayList<>();
                for(int i=0;i<calendarBean.links.get(position).fram_2.size();i++){
                    list2_1.add(calendarBean.links.get(position).fram_2.get(i).images);
                    list2_2.add(calendarBean.links.get(position).fram_2.get(i).name);
                    list2_3.add(calendarBean.links.get(position).fram_2.get(i).seed_price);
                    list2_4.add(calendarBean.links.get(position).fram_2.get(i).grow_up_day);
                    list2_5.add(calendarBean.links.get(position).fram_2.get(i).grow_up_days);
                }
                ArrayList<ArrayList<String>> lists2 = new ArrayList<>();
                lists2.add(list2_1);lists2.add(list2_2);
                lists2.add(list2_3);lists2.add(list2_4);
                lists2.add(list2_5);
                excelView2.setDataList(lists2);

                handler.sendEmptyMessage(0);
            }
        }.start();
    }
}

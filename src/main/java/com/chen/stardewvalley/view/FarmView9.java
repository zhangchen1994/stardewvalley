package com.chen.stardewvalley.view;

import android.content.Context;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.utils.JsonParse;

import java.util.ArrayList;

/**
 * Created by zc on 2018/6/27.
 */

public class FarmView9 extends FarmView1 {
    public FarmView9(Context context) {
        super(context);
        initView(context);
    }

    @Override
    public void setPagerSize() {
        pagerSize = 4;
    }

    @Override
    public void setTv2Text(String text) {
        if("null".equals(text)){
            tv2.setText("<1/4>");
            return;
        }
        tv2.setText(text);
    }

    @Override
    public void setCardView(int position) {
        int[] title = new int[]{
                R.string.spring,
                R.string.summer,
                R.string.autumn,
                R.string.winter
        };
        int[] titleFish = new int[]{
                R.string.image,
                R.string.tools_name,
                R.string.tools_describe,
                R.string.tools_local,
                R.string.fish_time,
                R.string.season,
                R.string.weather
        };
        int color = cardColors[getRandom(cardColors.length)];
        cardView.setCardBackgroundColor(color);
        excelView.setLayouttTitleClear();
        tv1.setText(title[position]);
        setTv2Text("<"+(position+1)+"/4>");
        calendarBean = JsonParse.returnCalendar();
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

        excelView.setLayouttTitle();
        excelView.setAdapter();
    }
}

package com.chen.stardewvalley.view;

import android.content.Context;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.utils.JsonParse;

import java.util.ArrayList;

/**
 * Created by zc on 2018/7/1.
 */

public class FarmView8 extends FarmView1 {
    public FarmView8(Context context) {
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
                R.string.tools_local,
                R.string.tools_describe
        };
        int color = cardColors[getRandom(cardColors.length)];
        cardView.setCardBackgroundColor(color);
        excelView.setLayouttTitleClear();
        tv1.setText(title[position]);
        setTv2Text("<"+(position+1)+"/4>");
        calendarBean = JsonParse.returnCalendar();
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

        excelView.setTitleList(titleFish);
        excelView.setWeigthList(new int[]{1,2,2,3});
        excelView.setLayouttTitle();
        excelView.setDataList(lists);
        excelView.setAdapter();
    }
}

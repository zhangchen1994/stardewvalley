package com.chen.stardewvalley.view;

import android.content.Context;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.domain.FarmBean;
import com.chen.stardewvalley.utils.JsonParse;

import java.util.ArrayList;

/**
 * Created by zc on 2018/6/30.
 */

public class FarmView3 extends FarmView1 {
    private FarmBean farmBean;
    public FarmView3(Context context) {
        super(context);
        farmBean = JsonParse.farmBean;
        initView(context);
    }
    @Override
    public void setPagerSize() {
        pagerSize = farmBean.main.get(2).des.size();
    }
    @Override
    public void setTv2Text(String text) {
        farmBean = JsonParse.farmBean;
        int num = farmBean.main.get(2).des.size();
        if ("null".equals(text)) {
            tv2.setText("<1/"+num+">");
            return;
        }
        tv2.setText(text);
    }

    @Override
    public void setCardView(int position) {
        int color = cardColors[getRandom(cardColors.length)];
        cardView.setCardBackgroundColor(color);
        excelView.setLayouttTitleClear();
        tv1.setText(farmBean.main.get(2).des.get(position).title);
        int num = farmBean.main.get(2).des.size();
        setTv2Text("<" + (position + 1) + "/"+num+">");
        int[] title = new int[]{
                R.string.image,
                R.string.tools_name,
                R.string.tools_describe,
                R.string.time,
                R.string.price
        };
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<String> list3 = new ArrayList<>();
        ArrayList<String> list4 = new ArrayList<>();
        ArrayList<String> list5 = new ArrayList<>();

        for (int i=0;i<farmBean.main.get(2).des.get(position).content.size();i++){
            list1.add(farmBean.main.get(2).des.get(position).content.get(i).images);
            list2.add(farmBean.main.get(2).des.get(position).content.get(i).name);
            list3.add(farmBean.main.get(2).des.get(position).content.get(i).des);
            list4.add(farmBean.main.get(2).des.get(position).content.get(i).time);
            list5.add(farmBean.main.get(2).des.get(position).content.get(i).price);
        }
        ArrayList<ArrayList<String>> lists = new ArrayList<>();
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);
        lists.add(list4);
        lists.add(list5);

        excelView.setTitleList(title);
        excelView.setWeigthList(new int[]{1,1,2,1,3});
        excelView.setLayouttTitle();
        excelView.setDataList(lists);
        excelView.setAdapter();
    }
}

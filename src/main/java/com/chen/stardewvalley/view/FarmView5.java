package com.chen.stardewvalley.view;

import android.content.Context;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.domain.FarmBean;
import com.chen.stardewvalley.utils.JsonParse;

import java.util.ArrayList;

/**
 * Created by zc on 2018/7/1.
 */

public class FarmView5 extends FarmView1 {
    public FarmView5(Context context) {
        super(context);
        initView(context);
    }
    @Override
    public void setPagerSize() {
        pagerSize = 2;
    }

    @Override
    public void setTv2Text(String text) {
        if ("null".equals(text)) {
            tv2.setText("<1/2>");
            return;
        }
        tv2.setText(text);
    }

    @Override
    public void setCardView(int position) {
        int[] title = new int[]{
                R.string.animal_1,
                R.string.animal_2
        };
        int[] title2 = new int[]{
                R.string.image,
                R.string.tools_name,
                R.string.fram_price,
                R.string.production
        };
        int color = cardColors[getRandom(cardColors.length)];
        cardView.setCardBackgroundColor(color);
        excelView.setLayouttTitleClear();
        tv1.setText(title[position]);
        setTv2Text("<" + (position + 1) + "/2>");
        FarmBean farmBean = JsonParse.returnFarm();
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<String> list3 = new ArrayList<>();
        ArrayList<String> list4 = new ArrayList<>();

        for (int i = 0;i<farmBean.main.get(4).des.get(position).animal_content.size();i++){
            list1.add(farmBean.main.get(4).des.get(position).animal_content.get(i).images);
            list2.add(farmBean.main.get(4).des.get(position).animal_content.get(i).name);
            list3.add(farmBean.main.get(4).des.get(position).animal_content.get(i).price);
            list4.add(farmBean.main.get(4).des.get(position).animal_content.get(i).production);
        }
        ArrayList<ArrayList<String>> lists = new ArrayList<>();
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);
        lists.add(list4);

        excelView.setTitleList(title2);
        excelView.setWeigthList(new int[]{2,1,1,1});
        excelView.setDataList(lists);
        excelView.setLayouttTitle();
        excelView.setAdapter();
    }
}

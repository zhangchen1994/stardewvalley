package com.chen.stardewvalley.view;

import android.content.Context;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.domain.FarmBean;
import com.chen.stardewvalley.utils.JsonParse;

import java.util.ArrayList;

/**
 * Created by zc on 2018/6/27.
 */

public class FarmView2 extends FarmView1 {
    public FarmView2(Context context) {
        super(context);
        initView(context);
    }

    @Override
    public void setPagerSize() {
        pagerSize = 3;
    }

    @Override
    public void setTv2Text(String text) {
        if ("null".equals(text)) {
            tv2.setText("<1/3>");
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
        };
        int[] title2 = new int[]{
                R.string.tree_child,
                R.string.leave_5,
                R.string.harvest_title
        };
        int color = cardColors[getRandom(cardColors.length)];
        cardView.setCardBackgroundColor(color);
        excelView.setLayouttTitleClear();
        tv1.setText(title[position]);
        setTv2Text("<" + (position + 1) + "/3>");
        FarmBean farmBean = JsonParse.returnFarm();
        excelView.setTitleList(title2);
        excelView.setWeigthList(new int[]{1, 3, 1});
        setImagesList(position, farmBean);
        setTextList(position, farmBean);

        excelView.setIsImagesExcel();
        excelView.setLayouttTitle();
        excelView.setAdapter();
    }

    private void setImagesList(int position, FarmBean farmBean) {
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<String> list3 = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                list1.add(farmBean.main.get(1).des.get(position).tree1.get(0).image);
                list2.add(farmBean.main.get(1).des.get(position).tree1.get(1).image);
                list3.add(farmBean.main.get(1).des.get(position).tree1.get(2).image);
            } else {
                list1.add(farmBean.main.get(1).des.get(position).tree2.get(0).image);
                list2.add(farmBean.main.get(1).des.get(position).tree2.get(1).image);
                list3.add(farmBean.main.get(1).des.get(position).tree2.get(2).image);
            }

        }
        ArrayList<ArrayList<String>> lists = new ArrayList<>();
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);
        excelView.setDataImageList(lists);
    }
    private void setTextList(int position, FarmBean farmBean) {
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<String> list3 = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                list1.add(farmBean.main.get(1).des.get(position).tree1.get(0).des);
                list2.add(farmBean.main.get(1).des.get(position).tree1.get(1).des);
                list3.add(farmBean.main.get(1).des.get(position).tree1.get(2).des);
            } else {
                list1.add(farmBean.main.get(1).des.get(position).tree2.get(0).des);
                list2.add(farmBean.main.get(1).des.get(position).tree2.get(1).des);
                list3.add(farmBean.main.get(1).des.get(position).tree2.get(2).des);
            }

        }
        ArrayList<ArrayList<String>> lists = new ArrayList<>();
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);
        excelView.setDataTextList(lists);
    }
}

package com.chen.stardewvalley.view;

import android.content.Context;
import android.view.View;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.domain.ToolsBean;
import com.chen.stardewvalley.utils.GetImageIdByName;
import com.chen.stardewvalley.utils.JsonParse;

import java.util.ArrayList;

/**
 * Created by zc on 2018/6/30.
 */

public class FarmView7 extends FarmView1 {
    private ToolsBean toolsBean;
    public FarmView7(Context context) {
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
        int[] titles8 = new int[]{
                R.string.image,
                R.string.tools_name,
                R.string.raw_material,
                R.string.tools_local,
                R.string.tools_describe
        };
        int[] titles = new int[]{
                R.string.work_tools,
                R.string.other
        };
        int color = cardColors[getRandom(cardColors.length)];
        cardView.setCardBackgroundColor(color);
        toolsBean = JsonParse.returnTools();
        ToolsBean.MyManufacture myManufacture = toolsBean.manufacture;
        tv1.setText(titles[position]);
        excelView.setLayouttTitleClear();
        excelView.setTitleList(titles8);
        excelView.setWeigthList(new int[]{1,1,2,1,2});
        excelView.setLayouttTitle();
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<String> list3 = new ArrayList<>();
        ArrayList<String> list4 = new ArrayList<>();
        ArrayList<String> list5 = new ArrayList<>();
        int i1 = 0;
        if(position == 0){
            i1 = 1;
        }else{
            i1 = 4;
        }
        for(ToolsBean.FactureList links1 : myManufacture.facture.content.get(i1).links){
            list1.add(links1.image);
            list2.add(links1.name);
            list3.add(links1.material);
            list4.add(links1.local);
            list5.add(links1.describe);

        }
        ArrayList<ArrayList<String>> lists1 = new ArrayList<>();
        lists1.add(list1);lists1.add(list2);
        lists1.add(list3);lists1.add(list4);
        lists1.add(list5);
        excelView.setDataList(lists1);
        excelView.setAdapter();
    }
}

package com.chen.stardewvalley.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.utils.GetImageIdByName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zc on 2018/5/22.
 */

public class ExcelView extends LinearLayout{
    private ImageView iv_content_title;
    private TextView tv_contnet_0;
    private TextView tv_contnet_1;
    private TextView tv_contnet_2;
    private TextView tv_contnet_3;
    private TextView tv_contnet_4;
    private TextView tv_contnet_5;
    private ArrayList<ArrayList<String>> contentList;
    private ListView listView;
    private int[] weigthList;
    private int[] titleList;
    public ExcelView(Context context) {
        super(context);
        init();
    }

    public ExcelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExcelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ExcelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    private void init(){
        View view = View.inflate(getContext(),R.layout.excel_view,null);
        addView(view);
        iv_content_title = findViewById(R.id.iv_excel_content_title);
        tv_contnet_0 = findViewById(R.id.tv_excel_content_0);
        tv_contnet_1 = findViewById(R.id.tv_excel_content_1);
        tv_contnet_2 = findViewById(R.id.tv_excel_content_2);
        tv_contnet_3 = findViewById(R.id.tv_excel_content_3);
        tv_contnet_4 = findViewById(R.id.tv_excel_content_4);
        tv_contnet_5 = findViewById(R.id.tv_excel_content_5);
        listView = findViewById(R.id.lv_excel);
        //setAdapter(new MyAdapter());
    }
    public void setLayouttTitle(){
        iv_content_title.setVisibility(GONE);
        LinearLayout.LayoutParams layoutParams0 = (LinearLayout.LayoutParams)
                tv_contnet_0.getLayoutParams();
        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams)
                tv_contnet_1.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams)
                tv_contnet_2.getLayoutParams();
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams)
                tv_contnet_3.getLayoutParams();
        LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams)
                tv_contnet_4.getLayoutParams();
        LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams)
                tv_contnet_5.getLayoutParams();
        ArrayList<LinearLayout.LayoutParams> layoutParamsList =
                new ArrayList<>();
        ArrayList<TextView> textViewArrayList =
                new ArrayList<>();
        int num = 0;
        switch (titleList.length){
            case 4:
                tv_contnet_0.setVisibility(VISIBLE);
                tv_contnet_1.setVisibility(VISIBLE);
                tv_contnet_2.setVisibility(VISIBLE);
                tv_contnet_3.setVisibility(VISIBLE);
                tv_contnet_4.setVisibility(GONE);
                tv_contnet_5.setVisibility(GONE);
                layoutParamsList.clear();
                textViewArrayList.clear();
                layoutParamsList.add(layoutParams0);
                layoutParamsList.add(layoutParams1);
                layoutParamsList.add(layoutParams2);
                layoutParamsList.add(layoutParams3);
                textViewArrayList.add(tv_contnet_0);
                textViewArrayList.add(tv_contnet_1);
                textViewArrayList.add(tv_contnet_2);
                textViewArrayList.add(tv_contnet_3);
                break;
            case 5:
                tv_contnet_0.setVisibility(VISIBLE);
                tv_contnet_1.setVisibility(VISIBLE);
                tv_contnet_2.setVisibility(VISIBLE);
                tv_contnet_3.setVisibility(VISIBLE);
                tv_contnet_4.setVisibility(VISIBLE);
                tv_contnet_5.setVisibility(GONE);
                layoutParamsList.clear();
                textViewArrayList.clear();
                layoutParamsList.add(layoutParams0);
                layoutParamsList.add(layoutParams1);
                layoutParamsList.add(layoutParams2);
                layoutParamsList.add(layoutParams3);
                layoutParamsList.add(layoutParams4);
                textViewArrayList.add(tv_contnet_0);
                textViewArrayList.add(tv_contnet_1);
                textViewArrayList.add(tv_contnet_2);
                textViewArrayList.add(tv_contnet_3);
                textViewArrayList.add(tv_contnet_4);
                break;
            case 6:
                tv_contnet_0.setVisibility(VISIBLE);
                tv_contnet_1.setVisibility(VISIBLE);
                tv_contnet_2.setVisibility(VISIBLE);
                tv_contnet_3.setVisibility(VISIBLE);
                tv_contnet_4.setVisibility(VISIBLE);
                tv_contnet_5.setVisibility(VISIBLE);
                layoutParamsList.clear();
                textViewArrayList.clear();
                layoutParamsList.add(layoutParams0);
                layoutParamsList.add(layoutParams1);
                layoutParamsList.add(layoutParams2);
                layoutParamsList.add(layoutParams3);
                layoutParamsList.add(layoutParams4);
                layoutParamsList.add(layoutParams5);
                textViewArrayList.add(tv_contnet_0);
                textViewArrayList.add(tv_contnet_1);
                textViewArrayList.add(tv_contnet_2);
                textViewArrayList.add(tv_contnet_3);
                textViewArrayList.add(tv_contnet_4);
                textViewArrayList.add(tv_contnet_5);
                break;
        }
        for(int i=0;i<weigthList.length;i++){
            layoutParamsList.get(i).weight = weigthList[i];
            textViewArrayList.get(i).setText(titleList[i]);
        }
    }
    public void setTitleList(int[] list){
        titleList = list;
    }
    public void setDataList(ArrayList<ArrayList<String>> list){
        contentList = list;
    }
    public void setWeigthList(int[] list){
        weigthList = list;
    }
    public void setAdapter(){
        listView.setAdapter(new MyAdapter());
        listView.setOverScrollMode(OVER_SCROLL_NEVER );
    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return contentList.get(0).size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if(view == null){
                viewHolder = new ViewHolder();
                view = View.inflate(getContext(), R.layout.excel_list_view,null);
                viewHolder.iv_title = view.findViewById(R.id.iv_excel_title);
                viewHolder.tv_0 = view.findViewById(R.id.tv_excel_0);
                viewHolder.tv_1 = view.findViewById(R.id.tv_excel_1);
                viewHolder.tv_2 = view.findViewById(R.id.tv_excel_2);
                viewHolder.tv_3 = view.findViewById(R.id.tv_excel_3);
                viewHolder.tv_4 = view.findViewById(R.id.tv_excel_4);
                viewHolder.tv_5 = view.findViewById(R.id.tv_excel_5);

                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }
            LinearLayout.LayoutParams layoutParams0 = (LinearLayout.LayoutParams)
                    viewHolder.tv_0.getLayoutParams();
            LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams)
                    viewHolder.tv_1.getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams)
                    viewHolder.tv_2.getLayoutParams();
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams)
                    viewHolder.tv_3.getLayoutParams();
            LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams)
                    viewHolder.tv_4.getLayoutParams();
            LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams)
                    viewHolder.tv_5.getLayoutParams();
            ArrayList<LinearLayout.LayoutParams> layoutParamsList =
                    new ArrayList<>();
            ArrayList<TextView> textViewArrayList =
                    new ArrayList<>();
            int num = 0;
            switch (titleList.length-1){
                case 3:
                    viewHolder.tv_0.setVisibility(VISIBLE);
                    viewHolder.tv_1.setVisibility(VISIBLE);
                    viewHolder.tv_2.setVisibility(VISIBLE);
                    viewHolder.tv_3.setVisibility(GONE);
                    viewHolder.tv_4.setVisibility(GONE);
                    viewHolder.tv_5.setVisibility(GONE);
                    layoutParamsList.clear();
                    textViewArrayList.clear();
                    layoutParamsList.add(layoutParams0);
                    layoutParamsList.add(layoutParams1);
                    layoutParamsList.add(layoutParams2);
                    textViewArrayList.add(viewHolder.tv_0);
                    textViewArrayList.add(viewHolder.tv_1);
                    textViewArrayList.add(viewHolder.tv_2);
                    break;
                case 4:
                    viewHolder.tv_0.setVisibility(VISIBLE);
                    viewHolder.tv_1.setVisibility(VISIBLE);
                    viewHolder.tv_2.setVisibility(VISIBLE);
                    viewHolder.tv_3.setVisibility(VISIBLE);
                    viewHolder.tv_4.setVisibility(GONE);
                    viewHolder.tv_5.setVisibility(GONE);
                    layoutParamsList.clear();
                    textViewArrayList.clear();
                    layoutParamsList.add(layoutParams0);
                    layoutParamsList.add(layoutParams1);
                    layoutParamsList.add(layoutParams2);
                    layoutParamsList.add(layoutParams3);
                    textViewArrayList.add(viewHolder.tv_0);
                    textViewArrayList.add(viewHolder.tv_1);
                    textViewArrayList.add(viewHolder.tv_2);
                    textViewArrayList.add(viewHolder.tv_3);
                    break;
                case 5:
                    viewHolder.tv_0.setVisibility(VISIBLE);
                    viewHolder.tv_1.setVisibility(VISIBLE);
                    viewHolder.tv_2.setVisibility(VISIBLE);
                    viewHolder.tv_3.setVisibility(VISIBLE);
                    viewHolder.tv_4.setVisibility(VISIBLE);
                    viewHolder.tv_5.setVisibility(GONE);
                    layoutParamsList.clear();
                    textViewArrayList.clear();
                    layoutParamsList.add(layoutParams0);
                    layoutParamsList.add(layoutParams1);
                    layoutParamsList.add(layoutParams2);
                    layoutParamsList.add(layoutParams3);
                    layoutParamsList.add(layoutParams4);
                    textViewArrayList.add(viewHolder.tv_0);
                    textViewArrayList.add(viewHolder.tv_1);
                    textViewArrayList.add(viewHolder.tv_2);
                    textViewArrayList.add(viewHolder.tv_3);
                    textViewArrayList.add(viewHolder.tv_4);
                    break;
                case 6:
                    viewHolder.tv_0.setVisibility(VISIBLE);
                    viewHolder.tv_1.setVisibility(VISIBLE);
                    viewHolder.tv_2.setVisibility(VISIBLE);
                    viewHolder.tv_3.setVisibility(VISIBLE);
                    viewHolder.tv_4.setVisibility(VISIBLE);
                    viewHolder.tv_5.setVisibility(VISIBLE);
                    layoutParamsList.clear();
                    textViewArrayList.clear();
                    layoutParamsList.add(layoutParams0);
                    layoutParamsList.add(layoutParams1);
                    layoutParamsList.add(layoutParams2);
                    layoutParamsList.add(layoutParams3);
                    layoutParamsList.add(layoutParams4);
                    layoutParamsList.add(layoutParams5);
                    textViewArrayList.add(viewHolder.tv_0);
                    textViewArrayList.add(viewHolder.tv_1);
                    textViewArrayList.add(viewHolder.tv_2);
                    textViewArrayList.add(viewHolder.tv_3);
                    textViewArrayList.add(viewHolder.tv_4);
                    textViewArrayList.add(viewHolder.tv_5);
                    break;
            }

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)
                    viewHolder.iv_title.getLayoutParams();
            layoutParams.weight = 1;
            viewHolder.iv_title.setLayoutParams(layoutParams);
            ArrayList<String> imageIds = contentList.get(0);
            String imagesStr = imageIds.get(i);
            int ImagesId = GetImageIdByName.getImageId(imagesStr,getContext());
            if(ImagesId != 0){
                viewHolder.iv_title.setImageResource(ImagesId);
            }
               for(int k=0;k<weigthList.length-1;k++){
                   layoutParamsList.get(k).weight = weigthList[k+1];
                   textViewArrayList.get(k).setText(contentList.get(k+1).get(i));
               }

            return view;
        }
    }
    class ViewHolder{
        ImageView iv_title;
        TextView tv_0;
        TextView tv_1;
        TextView tv_2;
        TextView tv_3;
        TextView tv_4;
        TextView tv_5;

    }
}

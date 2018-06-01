package com.chen.stardewvalley.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.utils.DisplayUtils;
import com.chen.stardewvalley.utils.GetImageIdByName;
import com.daimajia.easing.linear.Linear;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zc on 2018/5/22.
 */

public class ExcelView extends LinearLayout{
    private ArrayList<ArrayList<String>> contentList;
    private ListView listView;
    private int[] weigthList;
    private int[] titleList;
    private LinearLayout llExcelTitle;
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
        listView = findViewById(R.id.lv_excel);
        llExcelTitle = findViewById(R.id.ll_excel);
    }
    public void setLayouttTitle(){
        for(int i=0;i<titleList.length;i++){
            View v = View.inflate(getContext(),R.layout.excel_child_view,null);
            llExcelTitle.addView(v);
            ImageView imageViewTitle = v.findViewById(R.id.iv_excel_child);
            imageViewTitle.setVisibility(GONE);
            if(i != 0){
                ImageView imageView = v.findViewById(R.id.excel_child_line_1);
                imageView.setVisibility(GONE);
            }
            LinearLayout.LayoutParams layoutParams = new LayoutParams(0,LayoutParams.MATCH_PARENT,
                    weigthList[i]);
            v.setLayoutParams(layoutParams);
            TextView textView = v.findViewById(R.id.tv_excel_child);
            textView.setText(titleList[i]);
            textView.setGravity(Gravity.CENTER);
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
    public void setListHeigh(){
        setListViewHeightBasedOnChildren(listView);
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
                viewHolder.linearLayout = view.findViewById(R.id.ll_excel_list);

                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.linearLayout.removeAllViews();
            for(int k=0;k<titleList.length;k++){
                View v = View.inflate(getContext(),R.layout.excel_child_view,null);
                viewHolder.linearLayout.addView(v);
                ImageView imageViewTitle = v.findViewById(R.id.iv_excel_child);
                TextView textView = v.findViewById(R.id.tv_excel_child);
                LinearLayout.LayoutParams layoutParams = new LayoutParams(0,LayoutParams.MATCH_PARENT,
                        weigthList[k]);
                layoutParams.gravity = Gravity.CENTER;
                v.setLayoutParams(layoutParams);
                if(k != 0){
                    ImageView imageView = v.findViewById(R.id.excel_child_line_1);
                    imageView.setVisibility(GONE);
                    imageViewTitle.setVisibility(GONE);
                    textView.setVisibility(VISIBLE);
                    textView.setText(contentList.get(k).get(i));
                    textView.setGravity(Gravity.CENTER);

                }else{
                    imageViewTitle.setVisibility(VISIBLE);
                    textView.setVisibility(GONE);
                    int imageId = GetImageIdByName.getImageId(contentList.get(k).get(i),getContext());
                    if (imageId != 0){
                        imageViewTitle.setImageResource(imageId);
                    }
                }

            }
            return view;
        }
    }
    class ViewHolder{
        LinearLayout linearLayout;

    }
    public void setListViewHeightBasedOnChildren(ListView listView){
        ListAdapter listAdapter = listView.getAdapter();
        int listViewWidth = getAndroiodScreenPropertyWidth() - DisplayUtils.dp2px
                (getContext(), 10);
        int widthSpec = View.MeasureSpec.makeMeasureSpec
                (listViewWidth, View.MeasureSpec.AT_MOST);
        //初始化高度
        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            //计算子项View的宽高，注意listview所在的要是linearlayout布局
            listItem.measure(widthSpec, 0);
            //统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        /*
         * listView.getDividerHeight()获取子项间分隔符占用的高度，有多少项就乘以多少个减一
         * params.height最后得到整个ListView完整显示需要的高度
         * 最后将params.height设置为listview的高度
         */
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);

    }
    public int getAndroiodScreenPropertyWidth() {
        WindowManager wm = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）

        return width;
    }
}

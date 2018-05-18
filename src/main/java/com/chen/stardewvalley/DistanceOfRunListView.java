package com.chen.stardewvalley;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chen.stardewvalley.utils.DisplayUtils;

import java.util.ArrayList;

/**
 * Created by zc on 2018/5/17.
 */

public class DistanceOfRunListView extends ListView{
    private int count;
    private String title;
    private ArrayList<String> timeList;
    private ArrayList<String> actionList;

    public DistanceOfRunListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DistanceOfRunListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DistanceOfRunListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public DistanceOfRunListView(Context context) {
        super(context);
    }
    public void setCount(int count){
        this.count = count;
    }
    public void setMyTitle(String title){
        this.title = title;
    }
    public void setTimeList(ArrayList<String> list){
        timeList = list;
    }
    public void setActionList(ArrayList<String> list){
        actionList = list;
    }
    public void setMyAdapter(){
        setAdapter(new MyAdapter());
    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return count+1;
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final ViewHolder viewHolder;
            if(view == null){
                view = View.inflate(getContext(),R.layout.view_list_ditance_of_run,null);
                viewHolder = new ViewHolder();
                viewHolder.tv_1 = view.findViewById(R.id.tv_v_l_dor);
                viewHolder.tv_2 = view.findViewById(R.id.tv_v_time);
                viewHolder.tv_3 = view.findViewById(R.id.tv_v_content);

                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }
            if(i == 0){
                viewHolder.tv_1.setVisibility(VISIBLE);
                viewHolder.tv_2.setVisibility(GONE);
                viewHolder.tv_3.setVisibility(GONE);
                viewHolder.tv_1.setText(title);
            }else{
                viewHolder.tv_1.setVisibility(GONE);
                viewHolder.tv_2.setVisibility(VISIBLE);
                viewHolder.tv_3.setVisibility(VISIBLE);
                if(timeList !=null){
                    viewHolder.tv_2.setText(timeList.get(i-1));
                }
                if(getContext().getString(R.string.condition).equals(title)
                        ||getContext().getString(R.string.process).equals(title)
                        ||getContext().getString(R.string.things_result).equals(title))
                    viewHolder.tv_2.setVisibility(GONE);
                viewHolder.tv_3.setText(actionList.get(i-1));
                if(actionList.get(i-1).length() > 40){
                    System.out.println(actionList.get(i-1).length());
                    viewHolder.tv_3.setHeight(DisplayUtils.dp2px(getContext(),actionList.get(i-1).length()+20));
                }
            }
            return view;
        }
    }
    class ViewHolder{
        TextView tv_1;
        TextView tv_2;
        TextView tv_3;
    }
}

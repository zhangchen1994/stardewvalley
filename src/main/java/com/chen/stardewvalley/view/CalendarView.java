package com.chen.stardewvalley.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.domain.CalendarBean;
import com.chen.stardewvalley.utils.DisplayUtils;
import com.chen.stardewvalley.utils.GetImageIdByName;
import com.chen.stardewvalley.utils.PeopleNameToEn;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by zc on 2018/5/30.
 */

public class CalendarView extends LinearLayout{
    private static final int CALENDAR_NUM = 28;
    private View view;
    private GridView gridView;
    private boolean clickStuats = false;
    private TextView tvTitle;
    private LinearLayout llTitle;
    private ArrayList<CalendarBean.DayList> dayList;
    private String tilte;
    private OnCalendarViewClickListener listener;
    private int clickPosition = 0;
    private View clickView;
    public static interface OnCalendarViewClickListener{
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l);
    }
    public void setOnCalendarViewClickListener(OnCalendarViewClickListener listener){
        this.listener = listener;
    }
    public CalendarView(Context context) {
        super(context);
        init();
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    private void init(){
        view = View.inflate(getContext(), R.layout.calendar_view,null);
        addView(view);
        gridView = view.findViewById(R.id.gv_calender_view);
        tvTitle = view.findViewById(R.id.tv_calendar_view_title);
        llTitle = view.findViewById(R.id.ll_calendar_view_title);
        post(new Runnable() {
            @Override
            public void run() {
                LinearLayout.LayoutParams params = new LayoutParams(
                        getWidth()- DisplayUtils.dp2px(getContext(),20),
                        getHeight()-DisplayUtils.dp2px(getContext(),30)-
                                tvTitle.getHeight()-llTitle.getHeight());
                gridView.setLayoutParams(params);
                tvTitle.setText(tilte);
                gridView.post(new Runnable() {
                    @Override
                    public void run() {
                        gridView.setAdapter(new GvAdapter(
                                gridView.getWidth(),
                                gridView.getHeight()
                        ));
                    }
                });
            }
        });
        gridView.setOverScrollMode(OVER_SCROLL_NEVER );
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(listener != null){
                    listener.onItemClick(adapterView,view,i,l);
                }
                if(clickStuats){
                    clickView.setBackgroundColor(Color.parseColor("#fffaebc2"));
                    clickView = view;
                    view.setBackgroundColor(Color.parseColor("#FF9F9A8C"));
                }else{
                    clickStuats = true;
                    view.setBackgroundColor(Color.parseColor("#FF9F9A8C"));
                    clickView = view;
                }

            }
        });
    }
    public void setDayList(ArrayList<CalendarBean.DayList> list){
        dayList = list;
    }
    public void setCalendarTitle(String title){
        this.tilte = title;
    }
    class GvAdapter extends BaseAdapter{
        int wigth;
        int hidth;
        public GvAdapter(int wigth,int hidth){
            this.wigth = wigth;
            this.hidth = hidth;
        }
        @Override
        public int getCount() {
            return CALENDAR_NUM;
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
                view = View.inflate(getContext(),R.layout.calendar_child_view,null);
                viewHolder = new ViewHolder();
                viewHolder.ivCalendar = view.findViewById(R.id.iv_calendar_child);
                viewHolder.tvCalendar = view.findViewById(R.id.tv_calendar_child);

                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }
            if(i >20 ){
                AbsListView.LayoutParams param = new AbsListView.LayoutParams(
                        LayoutParams.MATCH_PARENT,hidth/4-
                        DisplayUtils.dp2px(getContext(),2));
                view.setLayoutParams(param);
            }else{
                AbsListView.LayoutParams param = new AbsListView.LayoutParams(
                        LayoutParams.MATCH_PARENT,hidth/4-
                        DisplayUtils.dp2px(getContext(),1));
                view.setLayoutParams(param);
            }
            viewHolder.tvCalendar.setText(dayList.get(i).day);
            if(dayList.get(i).birthday != ""){
                String images = PeopleNameToEn.getNameEn(dayList.get(i).birthday)+"_icon";
                int imageId = GetImageIdByName.getImageId(images,getContext());
                if (imageId != 0){
                    viewHolder.ivCalendar.setBackgroundResource(imageId);
                }
            }
            if(dayList.get(i).festival != ""){
                viewHolder.ivCalendar .setBackgroundResource(R.drawable.flag_animation_list);
                ((AnimationDrawable) viewHolder.ivCalendar .getBackground()).start();
            }
            return view;
        }
    }
    class ViewHolder{
        ImageView ivCalendar;
        TextView tvCalendar;
    }
}

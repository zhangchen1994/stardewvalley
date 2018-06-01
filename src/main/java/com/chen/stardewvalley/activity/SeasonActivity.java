package com.chen.stardewvalley.activity;

import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.domain.CalendarBean;
import com.chen.stardewvalley.domain.PeopleBean;
import com.chen.stardewvalley.utils.DisplayUtils;
import com.chen.stardewvalley.utils.GetImageIdByName;
import com.chen.stardewvalley.utils.JsonParse;
import com.chen.stardewvalley.utils.PeopleNameToEn;
import com.chen.stardewvalley.view.CalendarView;
import com.chen.stardewvalley.view.ExcelView;

import java.util.ArrayList;

/**
 * Created by zc on 2018/5/30.
 */

public class SeasonActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private TextView toobarTv;
    private int position;
    private PeopleBean peopleBean;
    private boolean isFestival = true;
    private RelativeLayout rlFestivalTitle;
    private ExcelView season_excel_view_1;
    private RelativeLayout rlBirthdayTitle;
    private ExcelView season_excel_view_2;
    private int[] festivalTitle = new int[]{
            R.string.image,
            R.string.tools_name,
            R.string.calendar_day
    };
    private int[] birthdayTitle = new int[]{
            R.string.image,
            R.string.tools_name,
            R.string.like,
            R.string.calendar_day
    };
    private int[] toolBarTitle = new int[]{
            R.string.spring,
            R.string.summer,
            R.string.autumn,
            R.string.winter
    };
    private CalendarBean calendarBean;
    private CalendarView calendarView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_season);
        position = getIntent().getIntExtra("position",0);
        setToolBar();

        calendarView = findViewById(R.id.season_calendar);
        rlFestivalTitle = findViewById(R.id.ll_season_festival);
        season_excel_view_1 = findViewById(R.id.season_excel_view_1);
        rlBirthdayTitle = findViewById(R.id.ll_season_birthday);
        season_excel_view_2 = findViewById(R.id.season_excel_view_2);

        calendarBean = JsonParse.returnCalendar();
        peopleBean = JsonParse.returnPeople();
        calendarView.setCalendarTitle(calendarBean.links.get(position).name);
        calendarView.setDayList(calendarBean.links.get(position).month);

        setCalendarViewListener();
        setExcelView(season_excel_view_1);
        setExcelView(season_excel_view_2);
        setRlClickListener();
    }

    private void setRlClickListener() {
        rlFestivalTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageViewUp = rlFestivalTitle.findViewById(R.id.ex_arrow_up);
                ImageView imageViewDown = rlFestivalTitle.findViewById(R.id.ex_arrow_down);
                if(season_excel_view_1.getVisibility() == View.VISIBLE){
                    season_excel_view_1.setVisibility(View.GONE);
                    imageViewUp.setVisibility(View.VISIBLE);
                    imageViewDown.setVisibility(View.GONE);
                }else{
                    season_excel_view_1.setVisibility(View.VISIBLE);
                    imageViewUp.setVisibility(View.GONE);
                    imageViewDown.setVisibility(View.VISIBLE);
                }
            }
        });
        rlBirthdayTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageViewUp = rlBirthdayTitle.findViewById(R.id.ex_arrow_up_birthday);
                ImageView imageViewDown = rlBirthdayTitle.findViewById(R.id.ex_arrow_down_birthday);
                if(season_excel_view_2.getVisibility() == View.VISIBLE){
                    season_excel_view_2.setVisibility(View.GONE);
                    imageViewUp.setVisibility(View.VISIBLE);
                    imageViewDown.setVisibility(View.GONE);
                }else{
                    season_excel_view_2.setVisibility(View.VISIBLE);
                    imageViewUp.setVisibility(View.GONE);
                    imageViewDown.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setCalendarViewListener() {
        calendarView.setOnCalendarViewClickListener(new CalendarView.OnCalendarViewClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(calendarBean.links.get(position).month.get(i).festival == ""){

                }else{

                }

                if(calendarBean.links.get(position).month.get(i).birthday == ""){

                }else{

                }
            }
        });
    }

    private void setExcelView(ExcelView excelView){
        int id = excelView.getId();
        switch (id){
            case R.id.season_excel_view_1:
                excelView.setTitleList(festivalTitle);
                excelView.setWeigthList(new int[]{1,2,2});
                excelView.setLayouttTitle();
                ArrayList<String> list1 = new ArrayList<>();
                ArrayList<String> list2 = new ArrayList<>();
                ArrayList<String> list3 = new ArrayList<>();
                for(int i2=0;i2<calendarBean.links.get(position).festival.size();i2++){
                    list1.add(calendarBean.links.get(position).festival.get(i2).images);
                    list2.add(calendarBean.links.get(position).festival.get(i2).name);
                    list3.add(calendarBean.links.get(position).festival.get(i2).data);
                }
                ArrayList<ArrayList<String>> lists = new ArrayList<>();
                lists.add(list1);
                lists.add(list2);
                lists.add(list3);

                excelView.setDataList(lists);
                excelView.setAdapter();
                excelView.setListHeigh();
                break;
            case R.id.season_excel_view_2:
                excelView.setTitleList(birthdayTitle);
                excelView.setWeigthList(new int[]{1,1,2,1});
                excelView.setLayouttTitle();
                ArrayList<String> list2_1 = new ArrayList<>();
                ArrayList<String> list2_2 = new ArrayList<>();
                ArrayList<String> list2_3 = new ArrayList<>();
                ArrayList<String> list2_4 = new ArrayList<>();
                for(int i3=0;i3<calendarBean.links.get(position).birthday.size();i3++){
                    int num = getListPosition(calendarBean.links.get(position).birthday.get(i3).name);

                    list2_1.add(PeopleNameToEn.getNameEn(peopleBean.links.get(num).name)
                    +"_icon");
                    list2_2.add(peopleBean.links.get(num).name);
                    StringBuffer stringBuffer = new StringBuffer();
                    for(int k=0;k<peopleBean.links.get(num).like.size();k++){
                        if(k != peopleBean.links.get(num).like.size()-1){
                            stringBuffer.append(peopleBean.links.get(num).like.get(k).name+";");
                        }else{
                            stringBuffer.append(peopleBean.links.get(num).like.get(k).name);
                        }
                    }
                    list2_3.add(stringBuffer.toString());
                    list2_4.add(calendarBean.links.get(position).birthday.get(i3).data);
                }
                ArrayList<ArrayList<String>> lists2 = new ArrayList<>();
                lists2.add(list2_1);
                lists2.add(list2_2);
                lists2.add(list2_3);
                lists2.add(list2_4);

                excelView.setDataList(lists2);
                excelView.setAdapter();
                excelView.setListHeigh();
                break;
        }

    }
    private void setToolBar(){
        toolbar = findViewById(R.id.season_toolbar);
        toobarTv = findViewById(R.id.tv_season_toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toobarTv.setText(toolBarTitle[position]);
    }
   private int getListPosition(String name){
       int num = 0;
       for(PeopleBean.PeoPle peoPle : peopleBean.links){
           if(peoPle.name.equals(name)){
               break;
           }
           num++;
       }
       return num;
   }
}

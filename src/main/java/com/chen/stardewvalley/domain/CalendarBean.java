package com.chen.stardewvalley.domain;

import java.util.ArrayList;

/**
 * Created by zc on 2018/5/30.
 */

public class CalendarBean {
    public ArrayList<MyCalendarBean> links;
    public class MyCalendarBean{
        public String name;
        public ArrayList<DayList> month;
        public ArrayList<MyFestival> festival;
        public ArrayList<MyFram1> fram_1;
        public ArrayList<MyFram2> fram_2;
        public ArrayList<MyPick> pick;
        public ArrayList<MyFish> fish;
    }
    public class MyFestival{
        public String name;
        public String images;
        public String data;
    }
    public class DayList{
        public String day;
        public String birthday;
        public String festival;
    }
    public class MyFram1{
        public String name;
        public String images;
        public String seed_price;
        public String grow_up_day;
        public String times;
        public String price;
    }
    public class MyFram2{
        public String name;
        public String images;
        public String seed_price;
        public String grow_up_day;
        public String grow_up_days;
    }
    public class MyPick{
        public String name;
        public String images;
        public String describe;
        public String local;
    }
    public class MyFish{
        public String name;
        public String images;
        public String describe;
        public String local;
        public String time;
        public String season;
        public String weather;
    }
}

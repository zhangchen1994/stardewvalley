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
        public ArrayList<MyBirthday> birthday;
    }
    public class MyFestival{
        public String name;
        public String images;
        public String data;
    }
    public class MyBirthday{
        public String name;
        public String data;
    }
    public class DayList{
        public String day;
        public String birthday;
        public String festival;
    }
}

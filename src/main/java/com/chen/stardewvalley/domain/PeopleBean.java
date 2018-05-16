package com.chen.stardewvalley.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zc on 2018/5/16.
 */

public class PeopleBean {
    public List<PeoPle> links;
    public class PeoPle{
        public String name;
        public String birthday;
        public String local;
        public Family family;
        public List<Friend> friend;
        public List<Like> like;
        public List<MyCalendar> calendar;
//        public List<Love_thing> love_thing;
//        public List<Likes>likes;
    }
    public class Family{
        public String father;
        public String mather;
        public String grandfather;
        public String grandmather;
        public String sister;
        public String younger_sister;
        public String brother;
        public String younger_brother;
    }
    public class Friend{
        public String name;
    }
    public class Like{
        public String name;
        public String name_en;
    }
    public class MyCalendar{
        public List<MyWeek> week;
    }
    public class Love_thing{
        public String condition;
        public String things;
        public String result;
    }
    public class Likes{
        public List<MyLikes> likes_1;
        public List<MyLikes> likes_2;
        public List<MyLikes> likes_3;
        public List<MyLikes> likes_4;

    }
    public class MyWeek{
        public String action;
    }
    public class MyLikes{
        public String name;
        public String en;
    }
}

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
        public List<Family> family;
        public String marriage;
        public List<Friend> friend;
        public List<Like> like;
        public List<MyCalendar> calendar;
        public List<Love_thing> love_thing;
        public Likes likes;
    }
    public class Family{
        public String name;
        public String en;
    }
    public class Friend{
        public String name;
        public String en;
    }
    public class Like{
        public String name;
        public String name_en;
    }
    public class MyCalendar{
        public List<MyWeek> weeks;
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
        public String week;
    }
    public class MyLikes{
        public String name;
        public String en;
    }
}

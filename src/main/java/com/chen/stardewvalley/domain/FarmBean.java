package com.chen.stardewvalley.domain;

import java.util.ArrayList;

/**
 * Created by zc on 2018/6/11.
 */

public class FarmBean {
    public ArrayList<MyFarm> main;
    public class MyFarm{
        public String name;
        public ArrayList<FarmContent> images;
        public ArrayList<FarmDes> des;
    }
    public class FarmContent{
        public String name;
        public String image;
    }
    public class FarmDes{
        public ArrayList<FarmTreeDes> tree1;
        public ArrayList<FarmTreeDes> tree2;
        public String images;
        public String des1;
        public String des2;
        public String title;
        public ArrayList<MyFarmContent> content;
        public ArrayList<MyFarmCave> cave_content;
        public ArrayList<MyAnimalContent> animal_content;
    }
    public class FarmTreeDes{
        public String image;
        public String des;
    }
    public class MyFarmCave{
        public String images;
        public String name;
        public String des;
        public String use;
    }
    public class MyAnimalContent{
        public String images;
        public String name;
        public String price;
        public String production;
    }
    public class MyFarmContent{
        public String images;
        public String name;
        public String des;
        public String time;
        public String price;
    }
}

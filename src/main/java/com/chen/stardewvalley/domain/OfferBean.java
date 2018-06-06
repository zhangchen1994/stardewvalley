package com.chen.stardewvalley.domain;

import java.util.ArrayList;

/**
 * Created by zc on 2018/6/3.
 */

public class OfferBean {
    public ArrayList<MyOffer> links;
    public class MyOffer{
        public String name;
        public String reward;
        public ArrayList<MyContent> content;
    }
    public class MyContent{
        public String name;
        public String image;
        public String reward;
        public String reward_images;
        public ArrayList<OfferContent> offer_con;
    }
    public class OfferContent{
        public String name;
        public String images;
        public String describe;
    }
}

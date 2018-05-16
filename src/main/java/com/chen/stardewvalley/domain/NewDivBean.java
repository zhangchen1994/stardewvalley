package com.chen.stardewvalley.domain;

import java.util.List;

/**
 * Created by zc on 2018/5/14.
 */

public class NewDivBean {
    public List<HtmlPart> links;

    public static class HtmlPart{
        public String name;
        public String label;
        public String id;
    }
}

package com.chen.stardewvalley.utils;

/**
 * Created by zc on 2018/5/18.
 */

public class PeopleNameToEn {
    private static String[] nameList = new String[]{
            "亚历克斯",
            "艾利欧特",
            "哈维",
            "山姆",
            "塞巴斯蒂安",
            "谢恩",
            "阿比盖尔",
            "艾米丽",
            "海莉",
            "莉亚",
            "玛鲁",
            "潘妮",
            "卡洛琳",
            "克林特",
            "德米特里厄斯",
            "矮人",
            "艾芙琳",
            "乔治",
            "格斯",
            "贾斯",
            "乔迪",
            "肯特",
            "科罗布斯",
            "刘易斯",
            "莱纳斯",
            "玛妮",
            "潘姆",
            "皮埃尔",
            "罗宾",
            "桑迪",
            "文森特",
            "威利",
            "法师",
            "祝尼魔",
            "冈瑟",
            "莫里斯"
    };
    private static String[] nameEnList = new String[]{
           "alex",
            "elliott",
            "harvey",
            "sam",
            "sebastian",
            "shane",
            "abigail",
            "emily",
            "haley",
            "leah",
            "maru",
            "penny",
            "caroline",
            "clint",
            "demetrius",
            "dwarf",
            "evelyn",
            "george",
            "gus",
            "jas",
            "jodi",
            "kent",
            "krobus",
            "lewis",
            "linus",
            "marnie",
            "pam",
            "pierre",
            "robin",
            "sandy",
            "vincent",
            "willy",
            "wizard",
            "junimo",
            "gunther",
            "morris"
    };

    public static String getNameEn(String name){
        int index = 0;
        for(int i = 0;i<nameList.length;i++){
            if(name.equals(nameList[i])){
                index = i;
                break;
            }
        }
        return nameEnList[index];
    }
    public static String getNameEnUp(String name){
        int index = 0;
        for(int i = 0;i<nameList.length;i++){
            if(name.equals(nameList[i])){
                index = i;
                break;
            }
        }
        String nameEn = nameEnList[index];
        char[] chars = nameEn.toCharArray();
        char c = (char) (chars[0]-32);
        char[] chars1 = new char[chars.length];
        for(int i = 0;i<chars.length;i++){
            if(i == 0){
                chars1[i] = c;
            }else{
                chars1[i] = chars[i];
            }
        }
        return String.valueOf(chars1);
    }
}

package com.chen.stardewvalley.utils;

import java.util.ArrayList;

/**
 * Created by zc on 2018/5/19.
 */

public class GetMapRegion {
    public static int[] desert = new int[]{
            0,1,2,3,4,17,18,19,20,21,34,35,36,37
    };
    public static int[] tube = new int[]{
            56,57
    };
    public static int[] robin = new int[]{
            44
    };
    public static int[] bathroom = new int[]{
            8,25,26
    };public static int[] railway_station = new int[]{
            9,10
    };public static int[] lewis = new int[]{
            28
    };
    public static int[] mineCaveLake = new int[]{
            46
    };public static int[] mineCave = new int[]{
            29
    };public static int[] adventurer = new int[]{
            47
    };
    public static int[] quarry = new int[]{
            48,49,50
    };
    public static int[] forest = new int[]{
            68,69,70,71,85,86,87,102,103,104,120,121
    };
    public static int[] frostcragSpire = new int[]{
            88,105
    };
    public static int[] chapman = new int[]{
            89,106
    };
    public static int[] farm = new int[]{
            73,74,75,90,91,92
    };
    public static int[] marnie = new int[]{
            108,109
    };
    public static int[] jodi = new int[]{
            111
    };
    public static int[] sewerA = new int[]{
            158,159
    };
    public static int[] sewerB = new int[]{
            129,112
    };
    public static int[] square = new int[]{
            94
    };
    public static int[] bar = new int[]{
            95
    };
    public static int[] store = new int[]{
            78
    };
    public static int[] Geore = new int[]{
            79,96
    };
    public static int[] liuis = new int[]{
            113
    };
    public static int[] joja = new int[]{
            80,81,98
    };
    public static int[] blacksmith = new int[]{
            114
    };
    public static int[] repository = new int[]{
            115
    };
    public static int[] sandbeach = new int[]{
            147,148,149
    };
    public static int[] willy = new int[]{
            164,165,182,183
    };
    public static int[] lonely = new int[]{
            180,181
    };
    public static int[] community = new int[]{
            61
    };
    public static int[] equipment = new int[]{
            60
    };
    private static ArrayList<int[]> list = new ArrayList<>();
    private static ArrayList<String> list_2 = new ArrayList<>();
    public static String getRegion(int region){
        String regionName = null;
        list.add(desert); list_2.add("沙漠");
        list.add(tube); list_2.add("隧道");
        list.add(robin); list_2.add("木匠店");
        list.add(bathroom); list_2.add("浴室");
        list.add(railway_station); list_2.add("火车站");
        list.add(lewis); list_2.add("莱纳斯家");
        list.add(mineCaveLake); list_2.add("矿洞湖");
        list.add(mineCave); list_2.add("矿洞");
        list.add(adventurer); list_2.add("冒险家协会");
        list.add(quarry); list_2.add("采石场");
        list.add(forest); list_2.add("秘密森林");
        list.add(frostcragSpire); list_2.add("法师塔");
        list.add(chapman); list_2.add("旅行商人");
        list.add(farm); list_2.add("农场");
        list.add(marnie); list_2.add("玛妮，莉亚家");
        list.add(jodi); list_2.add("柳巷1号和柳巷2号");
        list.add(sewerA); list_2.add("下水道入口A");
        list.add(sewerB); list_2.add("下水道入口B");
        list.add(square); list_2.add("广场");
        list.add(bar); list_2.add("星之果实餐吧");
        list.add(store); list_2.add("皮埃尔的杂货店和哈维的诊所");
        list.add(Geore); list_2.add("河路一号和拖车");
        list.add(liuis); list_2.add("镇长的庄园");
        list.add(joja); list_2.add("Joja超市");
        list.add(blacksmith); list_2.add("铁匠铺");
        list.add(repository); list_2.add("博物馆");
        list.add(sandbeach); list_2.add("沙滩");
        list.add(willy); list_2.add("鱼店");
        list.add(lonely); list_2.add("lonely store");
        list.add(community); list_2.add("社区中心");
        list.add(equipment); list_2.add("游乐设施");


        for(int i = 0;i<list.size();i++){
            boolean isThis = false;
            for (int k=0;k<list.get(i).length;k++){
                if(list.get(i)[k] == region){
                    isThis = true;
                }
            }
            if (isThis){
                regionName = list_2.get(i);
                break;
            }
        }
        return regionName;
    }
    private static int[] list_1 = new int[]{10,4};
    private static int[] list_2_2 = new int[]{10,4};
    private static int[] list_3 = new int[]{10,3};
    private static int[] list_4 = new int[]{13,6};
    private static int[] list_5 = new int[]{12,6};
    private static int[] list_6 = new int[]{13,4};
    private static int[] list_7 = new int[]{10,5};
    private static int[] list_8 = new int[]{11,5};
    private static int[] list_9 = new int[]{11,5};
    private static int[] list_10 = new int[]{11,6};
    private static int[] list_11 = new int[]{9,6};
    private static int[] list_12 = new int[]{9,6};
    private static int[] list_13 = new int[]{12,8};
    private static int[] list_14 = new int[]{1,1};
    private static int[] list_15 = new int[]{3,6};
    private static int[] list_16 = new int[]{5,9};
    private static int[] list_17 = new int[]{10,2};
    private static int[] list_18 = new int[]{11,1};
    private static int[] list_19 = new int[]{12,1};

    public static int[] getListRegion(int i){
        ArrayList<int[]> listRegion_1 = new ArrayList<>();
        listRegion_1.add(list_1);
        listRegion_1.add(list_2_2);
        listRegion_1.add(list_3);
        listRegion_1.add(list_4);
        listRegion_1.add(list_5);
        listRegion_1.add(list_6);
        listRegion_1.add(list_7);
        listRegion_1.add(list_8);
        listRegion_1.add(list_9);
        listRegion_1.add(list_10);
        listRegion_1.add(list_11);
        listRegion_1.add(list_12);
        listRegion_1.add(list_13);
        listRegion_1.add(list_14);
        listRegion_1.add(list_15);
        listRegion_1.add(list_16);
        listRegion_1.add(list_17);
        listRegion_1.add(list_18);
        listRegion_1.add(list_19);

        return listRegion_1.get(i);
    }

}

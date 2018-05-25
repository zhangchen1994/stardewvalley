package com.chen.stardewvalley.domain;

import java.util.List;

/**
 * Created by zc on 2018/5/22.
 */

public class ToolsBean {
    public MyTools tools;
    public MyMineral mineral;
    public MyDecorate decorate;
    public MyCooking cooking;
    public MyFurniture furniture;
    public MyManufacture manufacture;

    public class MyTools{
        public MyTool tool;
        public MyWeapon weapon;
        public MyFishingGear fishing_gear;
    }
    public class MyTool{
        public String name;
        public List<MyContent> content;
    }
    public class MyContent{
        public String name;
        public String describe;
        public List<MyToolLinks1> links;
    }
    public class MyToolLinks1{
        public String name;
        public String image;
        public String spend;
        public String raw_material;
        public String promote;
    }
    public class MyWeapon{
        public String name;
        public List<MyWeaponContent> conten;
    }
    public class MyWeaponContent{
        public String name;
        public String describe;
        public List<MyWeaponLinks1> links;
    }
    public class MyWeaponLinks1{
        public String name;
        public String image;
        public String lenvel;
        public String local;
        public String damage;
        public String addition;
    }
    public class MyFishingGear{
        public String name;
        public List<MyContent> content;
    }
    public class MyMineral{
        public MyOre ore;
        public MyCultural cultural_relic;
    }
    public class MyOre{
        public String name;
        public List<MineralContent> content;
    }
    public class MineralContent{
        public String name;
        public List<OreLinks> links;
    }
    public class OreLinks{
        public String name;
        public String image;
        public String use;
        public String local;
        public String describe;
    }
    public class MyCultural{
        public String name;
        public List<MineralContent2> content;
    }
    public class MineralContent2{
        public String name;
        public List<CulturalLinks> links;
    }
    public class CulturalLinks{
        public String name;
        public String image;
        public String local;
        public String describe;
    }
    public class MyDecorate{
        public MyHead head;
        public MyShoes shoes;
        public MyRing ring;
    }
    public class MyHead{
        public String name;
        public List<HeadContent> content;
    }
    public class HeadContent{
        public String name;
        public List<CulturalLinks> links;
    }
    public class MyRing{
        public String name;
        public List<ShopConten> content;
    }
    public class MyShoes{
        public String name;
        public List<ShopConten> content;
    }
    public class ShopConten{
        public String name;
        public List<ShopLinks> links;
    }
    public class ShopLinks{
        public String name;
        public String image;
        public String describe;
        public String local;
        public String addition;
    }
    public class MyCooking{
        public MycookBook cookbook;
        public MySeasoning seasoning;
    }
    public class MycookBook{
        public String name;
        public List<cookContent> content;
    }
    public class MySeasoning{
        public String name;
        public List<cookContent> content;
    }
    public class cookContent{
        public String name;
        public List<cookLinks> links;
    }
    public class cookLinks{
        public String name;
        public String image;
        public String material;
        public String rely;
        public String addition;
        public String local;
    }
    public class MyFurniture{
        public MyFurns furnturs;
    }
    public class MyFurns{
        public String name;
        public List<FurnitureContent> content;
    }
    public class FurnitureContent{
        public String name;
        public List<FurnitureList> links;
    }
    public class FurnitureList{
        public String name;
        public String image;
        public String local;
        public String price;
    }
    public class MyManufacture{
        public Facture facture;
    }
    public class Facture{
        public String name;
        public List<FactureContent> content;
    }
    public class FactureContent{
        public String name;
        public List<FactureList> links;
    }
    public class FactureList{
        public String name;
        public String image;
        public String material;
        public String local;
        public String describe;
    }
}

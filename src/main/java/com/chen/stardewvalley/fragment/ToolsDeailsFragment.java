package com.chen.stardewvalley.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.domain.ToolsBean;
import com.chen.stardewvalley.utils.GetImageIdByName;
import com.chen.stardewvalley.utils.JsonParse;
import com.chen.stardewvalley.view.ExcelView;

import java.util.ArrayList;

/**
 * Created by zc on 2018/5/29.
 */

public class ToolsDeailsFragment extends Fragment{
    private View view;
    private int pager;
    private String name;
    private int div;
    private ExcelView excelView;
    private ToolsBean toolsBean;
    private TextView textView;
    private ImageView imageView;
    private int[] titles1 = new int[]{
            R.string.image,
            R.string.tools_name,
            R.string.spend,
            R.string.raw_material,
            R.string.promote
    };
    private int[] weight1 = new int[]{
           1,1,1,1,3
    };
    private int[] titles2 = new int[]{
            R.string.image,
            R.string.tools_name,
            R.string.lenvel,
            R.string.tools_local,
            R.string.damage,
            R.string.addition
    };
    private int[] weight2 = new int[]{
            1,1,1,3,1,2
    };
    private int[] titles3 = new int[]{
            R.string.image,
            R.string.tools_name,
            R.string.use,
            R.string.tools_local,
            R.string.tools_describe,
    };
    private int[] weight3 = new int[]{
            1,1,3,2,3
    };
    private int[] titles4 = new int[]{
            R.string.image,
            R.string.tools_name,
            R.string.tools_local,
            R.string.tools_describe,
    };
    private int[] weight4 = new int[]{
            1,1,3,3
    };
    private int[] titles5 = new int[]{
            R.string.image,
            R.string.tools_name,
            R.string.addition,
            R.string.tools_local,
            R.string.tools_describe,
    };
    private int[] weight5 = new int[]{
            1,1,2,3,2
    };
    private int[] titles6 = new int[]{
            R.string.image,
            R.string.tools_name,
            R.string.raw_material,
            R.string.rely,
            R.string.addition,
            R.string.tools_local
    };
    private int[] weight6 = new int[]{
            1,1,3,2,2,3
    };
    private int[] titles7 = new int[]{
            R.string.image,
            R.string.tools_name,
            R.string.tools_local,
            R.string.price
    };
    private int[] weight7 = new int[]{
            1,1,1,1
    };
    private int[] titles8 = new int[]{
            R.string.image,
            R.string.tools_name,
            R.string.raw_material,
            R.string.tools_local,
            R.string.tools_describe
    };
    private int[] weight8 = new int[]{
            1,1,2,1,2
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_deails_view,null);
        init();
        return view;
    }
    private void init(){
        pager = getArguments().getInt("pager");
        div = getArguments().getInt("div");
        name = getArguments().getString("name");
        excelView = view.findViewById(R.id.fragment_excel);
        textView = view.findViewById(R.id.tv_frag_des);
        imageView = view.findViewById(R.id.iv_frag_des);
        toolsBean = JsonParse.returnTools();
        setExcel();
    }
    private void setExcel(){
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<String> list3 = new ArrayList<>();
        ArrayList<String> list4 = new ArrayList<>();
        ArrayList<String> list5 = new ArrayList<>();
        ArrayList<String> list6 = new ArrayList<>();
        switch (pager){
            case 0:
                ToolsBean.MyTools myTools = toolsBean.tools;
                if(div == 0){
                    excelView.setTitleList(titles1);
                    excelView.setWeigthList(weight1);
                    excelView.setLayouttTitle();
                    int i = 0;
                    for(ToolsBean.MyContent myContent : myTools.tool.content){
                        if(name.equals(myContent.name)){
                            break;
                        }
                        i++;
                    }
                    if(myTools.tool.content.get(i).describe != null){
                        textView.setText(textView.getText()+myTools.tool.content.get(i).describe);
                    }else{
                        textView.setVisibility(View.GONE);
                    }
                    imageView.setImageResource(GetImageIdByName.getImageId(
                            myTools.tool.content.get(i).links.get(0).image,getActivity()
                    ));
                    for(ToolsBean.MyToolLinks1 links1 : myTools.tool.content.get(i).links){
                        list1.add(links1.image);
                        list2.add(links1.name);
                        list3.add(links1.spend);
                        list4.add(links1.raw_material);
                        list5.add(links1.promote);
                    }
                    ArrayList<ArrayList<String>> lists = new ArrayList<>();
                    lists.add(list1);lists.add(list2);
                    lists.add(list3);lists.add(list4);
                    lists.add(list5);
                    excelView.setDataList(lists);
                }else if(div == 1){
                    excelView.setTitleList(titles2);
                    excelView.setWeigthList(weight2);
                    excelView.setLayouttTitle();
                    int i = 0;
                    for(ToolsBean.MyWeaponContent myContent : myTools.weapon.conten){
                        if(name.equals(myContent.name)){
                            break;
                        }
                        i++;
                    }
                    if(myTools.weapon.conten.get(i).describe != null){
                        textView.setText(textView.getText()+myTools.weapon.conten.get(i).describe);
                    }else{
                        textView.setVisibility(View.GONE);
                    }
                    imageView.setImageResource(GetImageIdByName.getImageId(
                            myTools.weapon.conten.get(i).links.get(0).image,getActivity()
                    ));
                    for(ToolsBean.MyWeaponLinks1 links1 : myTools.weapon.conten.get(i).links){
                        list1.add(links1.image);
                        list2.add(links1.name);
                        list3.add(links1.lenvel);
                        list4.add(links1.local);
                        list5.add(links1.damage);
                        list6.add(links1.addition);
                    }
                    ArrayList<ArrayList<String>> lists = new ArrayList<>();
                    lists.add(list1);lists.add(list2);
                    lists.add(list3);lists.add(list4);
                    lists.add(list5);lists.add(list6);
                    excelView.setDataList(lists);
                }else{
                    excelView.setTitleList(titles1);
                    excelView.setWeigthList(weight1);
                    excelView.setLayouttTitle();
                    int i = 0;
                    for(ToolsBean.MyContent myContent : myTools.fishing_gear.content){
                        if(name.equals(myContent.name)){
                            break;
                        }
                        i++;
                    }
                    if(myTools.fishing_gear.content.get(i).describe != null){
                        textView.setText(textView.getText()+myTools.fishing_gear.content.get(i).describe);
                    }else{
                        textView.setVisibility(View.GONE);
                    }
                    imageView.setImageResource(GetImageIdByName.getImageId(
                            myTools.fishing_gear.content.get(i).links.get(0).image,getActivity()
                    ));
                    for(ToolsBean.MyToolLinks1 links1 : myTools.fishing_gear.content.get(i).links){
                        list1.add(links1.image);
                        list2.add(links1.name);
                        list3.add(links1.spend);
                        list4.add(links1.raw_material);
                        list5.add(links1.promote);
                    }
                    ArrayList<ArrayList<String>> lists = new ArrayList<>();
                    lists.add(list1);lists.add(list2);
                    lists.add(list3);lists.add(list4);
                    lists.add(list5);
                    excelView.setDataList(lists);
                }
                break;
            case 1:
                ToolsBean.MyMineral myMineral = toolsBean.mineral;
                if(div == 0){
                    excelView.setTitleList(titles3);
                    excelView.setWeigthList(weight3);
                    excelView.setLayouttTitle();
                    int i = 0;
                    for(ToolsBean.MineralContent myContent : myMineral.ore.content){
                        if(name.equals(myContent.name)){
                            break;
                        }
                        i++;
                    }
                    textView.setVisibility(View.GONE);
                    imageView.setImageResource(GetImageIdByName.getImageId(
                            myMineral.ore.content.get(i).links.get(0).image,getActivity()
                    ));
                    for(ToolsBean.OreLinks links1 : myMineral.ore.content.get(i).links){
                        list1.add(links1.image);
                        list2.add(links1.name);
                        list3.add(links1.use);
                        list4.add(links1.local);
                        list5.add(links1.describe);
                    }
                    ArrayList<ArrayList<String>> lists = new ArrayList<>();
                    lists.add(list1);lists.add(list2);
                    lists.add(list3);lists.add(list4);
                    lists.add(list5);
                    excelView.setDataList(lists);
                }else{
                    excelView.setTitleList(titles3);
                    excelView.setWeigthList(weight3);
                    excelView.setLayouttTitle();
                    int i = 0;
                    for(ToolsBean.MineralContent2 myContent : myMineral.cultural_relic.content){
                        if(name.equals(myContent.name)){
                            break;
                        }
                        i++;
                    }
                    textView.setVisibility(View.GONE);
                    imageView.setImageResource(GetImageIdByName.getImageId(
                            myMineral.cultural_relic.content.get(i).links.get(0).image,getActivity()
                    ));
                    for(ToolsBean.CulturalLinks links1 : myMineral.cultural_relic.content.get(i).links){
                        list1.add(links1.image);
                        list2.add(links1.name);
                        list3.add(getString(R.string.cultural_relic_use));
                        list4.add(links1.local);
                        list5.add(links1.describe);
                    }
                    ArrayList<ArrayList<String>> lists = new ArrayList<>();
                    lists.add(list1);lists.add(list2);
                    lists.add(list3);lists.add(list4);
                    lists.add(list5);
                    excelView.setDataList(lists);
                }
                break;
            case 2:
                ToolsBean.MyDecorate decorate = toolsBean.decorate;
                if(div == 0){
                    excelView.setTitleList(titles4);
                    excelView.setWeigthList(weight4);
                    excelView.setLayouttTitle();
                    int i = 0;
                    for(ToolsBean.HeadContent myContent : decorate.head.content){
                        if(name.equals(myContent.name)){
                            break;
                        }
                        i++;
                    }
                    textView.setVisibility(View.GONE);
                    imageView.setImageResource(GetImageIdByName.getImageId(
                            decorate.head.content.get(i).links.get(0).image,getActivity()
                    ));
                    for(ToolsBean.CulturalLinks links1 : decorate.head.content.get(i).links){
                        list1.add(links1.image);
                        list2.add(links1.name);
                        list3.add(links1.local);
                        list4.add(links1.describe);
                    }
                    ArrayList<ArrayList<String>> lists = new ArrayList<>();
                    lists.add(list1);lists.add(list2);
                    lists.add(list3);lists.add(list4);
                    excelView.setDataList(lists);
                }else if(div == 1){
                    excelView.setTitleList(titles5);
                    excelView.setWeigthList(weight5);
                    excelView.setLayouttTitle();
                    int i = 0;
                    for(ToolsBean.ShopConten myContent : decorate.shoes.content){
                        if(name.equals(myContent.name)){
                            break;
                        }
                        i++;
                    }
                    textView.setVisibility(View.GONE);
                    imageView.setImageResource(GetImageIdByName.getImageId(
                            decorate.shoes.content.get(i).links.get(0).image,getActivity()
                    ));
                    for(ToolsBean.ShopLinks links1 : decorate.shoes.content.get(i).links){
                        list1.add(links1.image);
                        list2.add(links1.name);
                        list3.add(links1.addition);
                        list4.add(links1.local);
                        list5.add(links1.describe);
                    }
                    ArrayList<ArrayList<String>> lists = new ArrayList<>();
                    lists.add(list1);lists.add(list2);
                    lists.add(list3);lists.add(list4);
                    lists.add(list5);
                    excelView.setDataList(lists);
                }else {
                    excelView.setTitleList(titles5);
                    excelView.setWeigthList(weight5);
                    excelView.setLayouttTitle();
                    int i = 0;
                    for(ToolsBean.ShopConten myContent : decorate.ring.content){
                        if(name.equals(myContent.name)){
                            break;
                        }
                        i++;
                    }
                    textView.setVisibility(View.GONE);
                    imageView.setImageResource(GetImageIdByName.getImageId(
                            decorate.ring.content.get(i).links.get(0).image,getActivity()
                    ));
                    for(ToolsBean.ShopLinks links1 : decorate.ring.content.get(i).links){
                        list1.add(links1.image);
                        list2.add(links1.name);
                        list3.add(links1.addition);
                        list4.add(links1.local);
                        list5.add(links1.describe);
                    }
                    ArrayList<ArrayList<String>> lists = new ArrayList<>();
                    lists.add(list1);lists.add(list2);
                    lists.add(list3);lists.add(list4);
                    lists.add(list5);
                    excelView.setDataList(lists);
                }
                break;
            case 3:
                ToolsBean.MyCooking myCooking = toolsBean.cooking;
                if(div == 0){
                    excelView.setTitleList(titles6);
                    excelView.setWeigthList(weight6);
                    excelView.setLayouttTitle();
                    int i = 0;
                    for(ToolsBean.cookContent myContent : myCooking.cookbook.content){
                        if(name.equals(myContent.name)){
                            break;
                        }
                        i++;
                    }
                    textView.setVisibility(View.GONE);
                    imageView.setImageResource(GetImageIdByName.getImageId(
                            myCooking.cookbook.content.get(i).links.get(0).image,getActivity()
                    ));
                    for(ToolsBean.cookLinks links1 : myCooking.cookbook.content.get(i).links){
                        list1.add(links1.image);
                        list2.add(links1.name);
                        list3.add(links1.material);
                        list4.add(links1.rely);
                        list5.add(links1.addition);
                        list6.add(links1.local);

                    }
                    ArrayList<ArrayList<String>> lists = new ArrayList<>();
                    lists.add(list1);lists.add(list2);
                    lists.add(list3);lists.add(list4);
                    lists.add(list5);lists.add(list6);
                    excelView.setDataList(lists);
                }else {
                    excelView.setTitleList(titles6);
                    excelView.setWeigthList(weight6);
                    excelView.setLayouttTitle();
                    int i = 0;
                    for(ToolsBean.cookContent myContent : myCooking.seasoning.content){
                        if(name.equals(myContent.name)){
                            break;
                        }
                        i++;
                    }
                    textView.setVisibility(View.GONE);
                    imageView.setImageResource(GetImageIdByName.getImageId(
                            myCooking.seasoning.content.get(i).links.get(0).image,getActivity()
                    ));
                    for(ToolsBean.cookLinks links1 : myCooking.seasoning.content.get(i).links){
                        list1.add(links1.image);
                        list2.add(links1.name);
                        list3.add(links1.material);
                        list4.add(links1.rely);
                        list5.add(links1.addition);
                        list6.add(links1.local);

                    }
                    ArrayList<ArrayList<String>> lists = new ArrayList<>();
                    lists.add(list1);lists.add(list2);
                    lists.add(list3);lists.add(list4);
                    lists.add(list5);lists.add(list6);
                    excelView.setDataList(lists);
                }
                break;
            case 4:
                ToolsBean.MyFurniture myFurniture = toolsBean.furniture;
                excelView.setTitleList(titles7);
                excelView.setWeigthList(weight7);
                excelView.setLayouttTitle();
                int i = 0;
                for(ToolsBean.FurnitureContent myContent : myFurniture.furnturs.content){
                    if(name.equals(myContent.name)){
                        break;
                    }
                    i++;
                }
                textView.setVisibility(View.GONE);
                imageView.setImageResource(GetImageIdByName.getImageId(
                        myFurniture.furnturs.content.get(i).links.get(0).image,getActivity()
                ));
                for(ToolsBean.FurnitureList links1 : myFurniture.furnturs.content.get(i).links){
                    list1.add(links1.image);
                    list2.add(links1.name);
                    list3.add(links1.local);
                    list4.add(links1.price);

                }
                ArrayList<ArrayList<String>> lists = new ArrayList<>();
                lists.add(list1);lists.add(list2);
                lists.add(list3);lists.add(list4);
                excelView.setDataList(lists);
                break;
            case 5:
                ToolsBean.MyManufacture myManufacture = toolsBean.manufacture;
                excelView.setTitleList(titles8);
                excelView.setWeigthList(weight8);
                excelView.setLayouttTitle();
                int i1 = 0;
                for(ToolsBean.FactureContent myContent : myManufacture.facture.content){
                    if(name.equals(myContent.name)){
                        break;
                    }
                    i1++;
                }
                textView.setVisibility(View.GONE);
                imageView.setImageResource(GetImageIdByName.getImageId(
                        myManufacture.facture.content.get(i1).links.get(0).image,getActivity()
                ));
                for(ToolsBean.FactureList links1 : myManufacture.facture.content.get(i1).links){
                    list1.add(links1.image);
                    list2.add(links1.name);
                    list3.add(links1.material);
                    list4.add(links1.local);
                    list5.add(links1.describe);

                }
                ArrayList<ArrayList<String>> lists1 = new ArrayList<>();
                lists1.add(list1);lists1.add(list2);
                lists1.add(list3);lists1.add(list4);
                lists1.add(list5);
                excelView.setDataList(lists1);
                break;
        }
        excelView.setAdapter();
    }
}

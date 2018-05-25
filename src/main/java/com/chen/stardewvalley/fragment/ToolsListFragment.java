package com.chen.stardewvalley.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.domain.ToolsBean;
import com.chen.stardewvalley.domain.ValleyBean;
import com.chen.stardewvalley.utils.GetImageIdByName;
import com.chen.stardewvalley.utils.JsonParse;
import com.google.gson.Gson;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by zc on 2018/5/22.
 */

public class ToolsListFragment extends Fragment{
    private static final int PAGE_COUNT = 6;
    private static final int PAGE_TOOLS_COUNT = 3;
    private static final int PAGE_MINERAL_COUNT = 2;
    private static final int PAGE_DECORATE_COUNT = 3;
    private static final int PAGE_COOK_COUNT = 2;
    private static final int PAGE_FURNITURE_COUNT = 1;
    private static final int PAGE_FACTURE_COUNT = 1;
    private View view;
    private MagicIndicator magicIndicator;
    private ViewPager vpMain;
    private ToolsBean toolsBean;
    private int[] titlesStrings = new int[]{
            R.string.tools_div,
            R.string.mineral_div,
            R.string.decorate_div,
            R.string.cooking_div,
            R.string.furniture_div,
            R.string.manufacture_div
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tools_list,null);
        init();
        return view;
    }
    private void init(){
        magicIndicator = view.findViewById(R.id.tools_magic_indicator);
        vpMain = view.findViewById(R.id.vp_tools_main);
        setIndicator();
        toolsBean = JsonParse.returnTools();
        System.out.println(toolsBean.tools.tool.content.get(0).links.size());
        vpMain.setAdapter(new VpAdaper());
        ViewPagerHelper.bind(magicIndicator,vpMain);
    }

    private void setIndicator(){
        magicIndicator.setBackgroundColor(Color.parseColor("#00c853"));
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setScrollPivotX(0.25f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titlesStrings == null ? 0 : titlesStrings.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(titlesStrings[index]);
                simplePagerTitleView.setNormalColor(Color.parseColor("#c8e6c9"));
                simplePagerTitleView.setSelectedColor(Color.WHITE);
                simplePagerTitleView.setTextSize(14);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vpMain.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setYOffset(UIUtil.dip2px(context, 3));
                indicator.setColors(Color.parseColor("#ffffff"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
    }
    class VpAdaper extends PagerAdapter{

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int num = 0;
            switch (position){
                case 0:
                    num = PAGE_TOOLS_COUNT;
                    break;
                case 1:
                    num = PAGE_MINERAL_COUNT;
                    break;
                case 2:
                    num = PAGE_DECORATE_COUNT;
                    break;
                case 3:
                    num = PAGE_COOK_COUNT;
                    break;
                case 4:
                    num = PAGE_FURNITURE_COUNT;
                    break;
                case 5:
                    num = PAGE_FACTURE_COUNT;
                    break;
            }
            View view = View.inflate(getActivity(),R.layout.viewpage_tools,null);
            LinearLayout ll = view.findViewById(R.id.ll_tools_view_page);

            for(int i=0;i<num;i++){
                View v = View.inflate(getActivity(),R.layout.viewpage_tools_view,null);
                ll.addView(v);
            }
            for(int i=0;i<ll.getChildCount();i++){
                View v = ll.getChildAt(i);
                TextView textView = v.findViewById(R.id.tv_tools_viewpage);
                GridView gridView = v.findViewById(R.id.gv_tools_viewpage);
                setGvView(position, i, textView, gridView);
            }

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private void setGvView(int position, int i, TextView textView, GridView gridView) {
        switch (position){
            case 0:
                if( i == 0){
                    textView.setText(toolsBean.tools.tool.name);
                    gridView.setAdapter(new GvAdapter(toolsBean.tools.tool.content.size()
                            ,position,i));
                    setListViewHeightBasedOnChildren(gridView);
                }else if(i == 1){
                    textView.setText(toolsBean.tools.weapon.name);
                    gridView.setAdapter(new GvAdapter(toolsBean.tools.weapon.conten.size()
                            ,position,i));
                    setListViewHeightBasedOnChildren(gridView);
                }else{
                    textView.setText(toolsBean.tools.fishing_gear.name);
                    gridView.setAdapter(new GvAdapter(toolsBean.tools.fishing_gear.content.size()
                            ,position,i));
                    setListViewHeightBasedOnChildren(gridView);
                }
                break;
            case 1:
                if(i ==0){
                    textView.setText(toolsBean.mineral.ore.name);
                    gridView.setAdapter(new GvAdapter(toolsBean.mineral.ore.content.size()
                            ,position,i));
                    setListViewHeightBasedOnChildren(gridView);
                }else{
                    textView.setText(toolsBean.mineral.cultural_relic.name);
                    gridView.setAdapter(new GvAdapter(toolsBean.mineral.cultural_relic.content.size()
                            ,position,i));
                    setListViewHeightBasedOnChildren(gridView);
                }
                break;
            case 2:
                if( i == 0){
                    textView.setText(toolsBean.decorate.head.name);
                    gridView.setAdapter(new GvAdapter(toolsBean.decorate.head.content.size()
                            ,position,i));
                    setListViewHeightBasedOnChildren(gridView);
                }else if(i == 1){
                    textView.setText(toolsBean.decorate.shoes.name);
                    gridView.setAdapter(new GvAdapter(toolsBean.decorate.shoes.content.size()
                            ,position,i));
                    setListViewHeightBasedOnChildren(gridView);
                }else{
                    textView.setText(toolsBean.decorate.ring.name);
                    gridView.setAdapter(new GvAdapter(toolsBean.decorate.ring.content.size()
                            ,position,i));
                    setListViewHeightBasedOnChildren(gridView);
                }
                break;
            case 3:
                if(i ==0){
                    textView.setText(toolsBean.cooking.cookbook.name);
                    gridView.setAdapter(new GvAdapter(toolsBean.cooking.cookbook.content.size()
                            ,position,i));
                    setListViewHeightBasedOnChildren(gridView);
                }else{
                    textView.setText(toolsBean.cooking.seasoning.name);
                    gridView.setAdapter(new GvAdapter(toolsBean.cooking.seasoning.content.size()
                            ,position,i));
                    setListViewHeightBasedOnChildren(gridView);
                }
                break;
            case 4:
                textView.setText(toolsBean.furniture.furnturs.name);
                gridView.setAdapter(new GvAdapter(toolsBean.furniture.furnturs.content.size()
                        ,position,i));
                setListViewHeightBasedOnChildren(gridView);
                break;
            case 5:
                textView.setText(toolsBean.manufacture.facture.name);
                gridView.setAdapter(new GvAdapter(toolsBean.manufacture.facture.content.size()
                        ,position,i));
                setListViewHeightBasedOnChildren(gridView);
                break;
        }

    }

    class GvAdapter extends BaseAdapter{
        private int count;
        private int pages;
        private int div;
        public GvAdapter(int count,int pages,int div){
            this.count = count;
            this.pages = pages;
            this.div = div;
        }
        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if(view == null){
                viewHolder = new ViewHolder();
                view = View.inflate(getActivity(),R.layout.gv_view_tools_viewpage,null);
                viewHolder.textView = view.findViewById(R.id.tv_gv_view_tools);
                viewHolder.imageView = view.findViewById(R.id.iv_gv_view_tools);

                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }
            int ImageId = 0;
            String textString = null;
            switch (pages){
                case 0:
                    if(div == 0){
                       ImageId = GetImageIdByName.getImageId(
                               toolsBean.tools.tool.content.get(i).links.get(
                                       toolsBean.tools.tool.content.get(i).links.size()-1
                               ).image,getActivity());
                       textString = toolsBean.tools.tool.content.get(i).name;
                    }else if(div ==1){
                        ImageId = GetImageIdByName.getImageId(
                                toolsBean.tools.weapon.conten.get(i).links.get(
                                        toolsBean.tools.weapon.conten.get(i).links.size()-1
                                ).image,getActivity());
                        textString = toolsBean.tools.weapon.conten.get(i).name;
                    }else{
                        ImageId = GetImageIdByName.getImageId(
                                toolsBean.tools.fishing_gear.content.get(i).links.get(
                                        toolsBean.tools.fishing_gear.content.get(i).links.size()-1
                                ).image,getActivity());
                        textString = toolsBean.tools.fishing_gear.content.get(i).name;
                    }
                    break;
                case 1:
                    if(div == 0){
                        ImageId = GetImageIdByName.getImageId(toolsBean.mineral.ore.content.get(i).
                                links.get(0).image,getActivity());
                        textString = toolsBean.mineral.ore.content.get(i).name;
                    }else{
                        ImageId = GetImageIdByName.getImageId(toolsBean.mineral.cultural_relic.content.get(i).
                                links.get(0).image,getActivity());
                        textString = toolsBean.mineral.cultural_relic.content.get(i).name;
                    }
                    break;
                case 2:
                    if(div == 0){
                        ImageId = GetImageIdByName.getImageId(
                                toolsBean.decorate.head.content.get(i).links.get(
                                        toolsBean.decorate.head.content.get(i).links.size()-1
                                ).image,getActivity());
                        textString = toolsBean.decorate.head.content.get(i).name;
                    }else if(div ==1){
                        ImageId = GetImageIdByName.getImageId(
                                toolsBean.decorate.shoes.content.get(i).links.get(
                                        toolsBean.decorate.shoes.content.get(i).links.size()-1
                                ).image,getActivity());
                        textString = toolsBean.decorate.shoes.content.get(i).name;
                    }else{
                        ImageId = GetImageIdByName.getImageId(
                                toolsBean.decorate.ring.content.get(i).links.get(
                                        toolsBean.decorate.ring.content.get(i).links.size()-1
                                ).image,getActivity());
                        textString = toolsBean.decorate.ring.content.get(i).name;
                    }
                    break;
                case 3:
                    if(div == 0){
                        ImageId = GetImageIdByName.getImageId(toolsBean.cooking.cookbook.content.get(i).
                                links.get(0).image,getActivity());
                        textString = toolsBean.cooking.cookbook.content.get(i).name;
                    }else{
                        ImageId = GetImageIdByName.getImageId(toolsBean.cooking.seasoning.content.get(i).
                                links.get(0).image,getActivity());
                        textString = toolsBean.cooking.seasoning.content.get(i).name;
                    }
                    break;
                case 4:
                    ImageId = GetImageIdByName.getImageId(toolsBean.furniture.furnturs.content.get(i).
                            links.get(0).image,getActivity());
                    textString = toolsBean.furniture.furnturs.content.get(i).name;
                    break;
                case 5:
                    ImageId = GetImageIdByName.getImageId(toolsBean.manufacture.facture.content.get(i).
                            links.get(0).image,getActivity());
                    textString = toolsBean.manufacture.facture.content.get(i).name;
                    break;
            }
            viewHolder.imageView.setImageResource(ImageId);
            viewHolder.textView.setText(textString);
            return view;
        }
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
    public void setListViewHeightBasedOnChildren(GridView gridView){
        ListAdapter listAdapter = gridView.getAdapter();
        //初始化高度
        int totalHeight = 0;

        for (int i = 0; i < getCount(listAdapter.getCount(),gridView); i++) {
            View listItem = listAdapter.getView(i, null, gridView);
            //计算子项View的宽高，注意listview所在的要是linearlayout布局
            listItem.measure(0, 0);
            //统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();

        /*
         * listView.getDividerHeight()获取子项间分隔符占用的高度，有多少项就乘以多少个减一
         * params.height最后得到整个ListView完整显示需要的高度
         * 最后将params.height设置为listview的高度
         */
        params.height = totalHeight + (gridView.getHeight() * (getCount(listAdapter.getCount(),gridView) - 1));
        gridView.setLayoutParams(params);

    }
    private int getCount(int i,GridView gridView){
        int num = i / 4;
        if(i%4 != 0){
            num ++;
        }

        return num;
    }
}

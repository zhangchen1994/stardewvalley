package com.chen.stardewvalley.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chen.stardewvalley.DistanceOfRunListView;
import com.chen.stardewvalley.R;
import com.chen.stardewvalley.domain.PeopleBean;
import com.chen.stardewvalley.utils.GetImageIdByName;
import com.chen.stardewvalley.utils.PeopleNameToEn;
import com.google.gson.Gson;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static android.widget.ExpandableListView.getPackedPositionGroup;

/**
 * Created by zc on 2018/5/16.
 */

public class PeopleDetailsFragment extends Fragment{
    private View view;
    private ViewPager vpDetails;
    private MagicIndicator magicIndicator;
    private ExpandableListView expandableListView;
    private TextView tvPeopleTitle;
    private int listPosition;
    private PeopleBean peopleBean;
    private int[] titleStrings = new int[]{
            R.string.distance_of_run,R.string.favorite_items,
            R.string.love_things
    };
    private int[] groupStings = new int[]{
            R.string.spring,R.string.summer,R.string.autumn,
            R.string.winter,R.string.marry_end,
            R.string.like_0,R.string.like_1,
            R.string.like_2,R.string.like_3,R.string.like_4,
            R.string.love_things_0,R.string.love_things_1,
            R.string.love_things_2,R.string.love_things_3,
            R.string.love_things_4
    };
    private List<PeopleBean.MyCalendar> calendar;
    public List<PeopleBean.Love_thing> love_thing;
    public PeopleBean.Likes likes;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragement_people_details,null);
        init();
        return view;
    }
    private void init(){
        vpDetails = view.findViewById(R.id.vp_people_details);
        json();
        listPosition = getArguments().getInt("position");
        calendar = peopleBean.links.get(listPosition).calendar;
        love_thing = peopleBean.links.get(listPosition).love_thing;
        likes = peopleBean.links.get(listPosition).likes;
        magicIndicator = view.findViewById(R.id.people_magic_indicator);
        vpDetails.setAdapter(new DetailsAdapter());
        setIndicator();
        ViewPagerHelper.bind(magicIndicator,vpDetails);
    }
    private void json(){
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("assets/"+"people.json");
        String gsonStr = inToString(inputStream);
        Gson gson = new Gson();
        peopleBean = gson.fromJson(gsonStr,PeopleBean.class);

    }
    private String inToString(InputStream in){
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString().trim();
    }
    class DetailsAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if(position == 0){
                View view = View.inflate(getActivity(),R.layout.view_pager_people,null);
                ImageView imageView = view.findViewById(R.id.iv_people_title_icon);
                LinearLayout ll_birthday = view.findViewById(R.id.ll_birthday);
                LinearLayout ll_local = view.findViewById(R.id.ll_local);
                LinearLayout ll_relatives = view.findViewById(R.id.ll_relatives);
                LinearLayout ll_friend = view.findViewById(R.id.ll_friend);
                LinearLayout ll_like = view.findViewById(R.id.ll_like);

                TextView textViewBigTitle = view.findViewById(R.id.tv_people_name);
                textViewBigTitle.setText(peopleBean.links.get(listPosition).name);

                String imageName = PeopleNameToEn.getNameEn(peopleBean.links.get(listPosition)
                               .name);
                imageView.setImageResource(GetImageIdByName.getImageId(imageName,getActivity()));

                TextView tvNameEn = view.findViewById(R.id.tv_people_name_en);
                tvNameEn.setText(PeopleNameToEn.getNameEnUp(peopleBean.links.get(listPosition).name));

                View v1 = View.inflate(getActivity(),R.layout.infomation_view,null);
                TextView tv1 = v1.findViewById(R.id.tv_information_content);
                tv1.setText(peopleBean.links.get(listPosition).birthday);
                ll_birthday.addView(v1);

                View v2 = View.inflate(getActivity(),R.layout.infomation_view,null);
                TextView tv2 = v2.findViewById(R.id.tv_information_content);
                tv2.setText(peopleBean.links.get(listPosition).local);
                ll_local.addView(v2);

                for(int i=0;i<peopleBean.links.get(listPosition).family.size();i++){
                    View v = View.inflate(getActivity(),R.layout.infomation_view,null);
                    TextView tvContent = v.findViewById(R.id.tv_information_content);
                    tvContent.setText(peopleBean.links.get(listPosition).family.get(i).name);
                    Resources res = getResources();
                    String imageNameEn = peopleBean.links.get(listPosition).family.get(i).en;
                    int imageId = GetImageIdByName.getImageId(
                            imageNameEn,getActivity()
                    );
                    if(imageNameEn != null && imageId != 0){
                        Drawable drawable = res.getDrawable(imageId);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        tvContent.setCompoundDrawables(drawable,null,null,null);
                    }

                    ll_relatives.addView(v);

                }
                TextView tvRelatives = view.findViewById(R.id.tv_people_relatives);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tvRelatives.getLayoutParams();
                layoutParams.gravity = Gravity.CENTER_VERTICAL;

                tvRelatives.setLayoutParams(layoutParams);
                for(int i=0;i<peopleBean.links.get(listPosition).friend.size();i++){
                    View v = View.inflate(getActivity(),R.layout.infomation_view,null);
                    TextView tvContent = v.findViewById(R.id.tv_information_content);
                    tvContent.setText(peopleBean.links.get(listPosition).friend.get(i).name);
                    Resources res = getResources();
                    int ImageId = GetImageIdByName.getImageId(
                            peopleBean.links.get(listPosition).friend.get(i).en,getActivity()
                    );
                    if(ImageId != 0){
                        Drawable drawable = res.getDrawable(ImageId);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        tvContent.setCompoundDrawables(drawable,null,null,null);
                    }

                    ll_friend.addView(v);

                }
                TextView tvFriend = view.findViewById(R.id.tv_people_friend);
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) tvFriend.getLayoutParams();
                layoutParams2.gravity = Gravity.CENTER_VERTICAL;

                tvFriend.setLayoutParams(layoutParams2);
                for(int i=0;i<peopleBean.links.get(listPosition).like.size();i++){
                    View v = View.inflate(getActivity(),R.layout.infomation_view,null);
                    TextView tvContent = v.findViewById(R.id.tv_information_content);
                    tvContent.setText(peopleBean.links.get(listPosition).like.get(i).name);
                    Resources res = getResources();
                    int imageId = GetImageIdByName.getImageId(
                            peopleBean.links.get(listPosition).like.get(i).name_en,getActivity()
                    );
                    if(imageId != 0){
                        Drawable drawable = res.getDrawable(GetImageIdByName.getImageId(
                                peopleBean.links.get(listPosition).like.get(i).name_en,getActivity()
                        ));
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        tvContent.setCompoundDrawables(drawable,null,null,null);
                    }

                    ll_like.addView(v);

                }
                TextView tvLike = view.findViewById(R.id.tv_people_like);
                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) tvLike.getLayoutParams();
                layoutParams3.gravity = Gravity.CENTER_VERTICAL;

                tvLike.setLayoutParams(layoutParams3);

                container.addView(view);
                return view;
            }else{
                View view = View.inflate(getActivity(),R.layout.viewpager_people_details,null);
                expandableListView = view.findViewById(R.id.elv_vp_people);
                tvPeopleTitle = view.findViewById(R.id.ex_tv_details);
                expandableListView.setAdapter(new exAdapter());
                container.addView(view);
                expandableListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView absListView, int i) {

                    }

                    @Override
                    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                        long packedPosition = expandableListView.getExpandableListPosition(i);
                        int groupPosition = getPackedPositionGroup(packedPosition);
                        if(groupPosition < 6){
                            tvPeopleTitle.setText(titleStrings[0]);
                        }else if(groupPosition>=6 && groupPosition<12){
                            tvPeopleTitle.setText(titleStrings[1]);
                        }else if(groupPosition >= 12){
                            tvPeopleTitle.setText(titleStrings[2]);
                        }
                    }
                });
                return view;
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    class exAdapter extends BaseExpandableListAdapter{

        @Override
        public int getGroupCount() {
            return groupStings.length + titleStrings.length;
        }

        @Override
        public int getChildrenCount(int i) {
            int length = 0;
            if(i == 0){
                length = 0;
            }else if(i<=5){
                length = calendar.get(i-1).weeks.size();
            }else if(i == 6){
                length = 0;
            } else if(i<=11){
                if(i == 7){
                    length = peopleBean.links.get(listPosition).like.size();
                }else if(i == 8){
                    length = likes.likes_1.size();
                }else if(i == 9){
                    length = likes.likes_2.size();
                }else if(i == 10){
                    length = likes.likes_3.size();
                }else if(i == 11){
                    length = likes.likes_4.size();
                }
            }else if(i == 12){
                length = 0;
            }else{
                length = 3;
            }
            return length;
        }

        @Override
        public Object getGroup(int i) {
            if(i == 0){
                return titleStrings[0];
            }else if(i<=5){
                return groupStings[i-1];
            }else if(i == 6){
                return titleStrings[0];
            } else if(i<=11){
                return groupStings[i-2];
            }else if(i == 12){
                return titleStrings[0];
            }else{
                return groupStings[i-3];
            }
        }

        @Override
        public Object getChild(int i, int i1) {
            if(i == 0){
                return null;
            }else if(i<=5){
                return calendar.get(i-1).weeks;
            }else if(i == 6){
                return null;
            }else if(i<=9){
                if(i == 7){
                    return peopleBean.links.get(listPosition).like;
                }else if(i == 8){
                    return likes.likes_1;
                }else if(i == 9){
                    return likes.likes_2;
                }else if(i == 10){
                    return likes.likes_3;
                }else{
                    return likes.likes_4;
                }
            }else if(i == 12){
                return null;
            }else{
                return love_thing.get(i-13);
            }
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            ViewHolderGroup viewHolderGroup;
            if(view == null){
                viewHolderGroup = new ViewHolderGroup();
                view = View.inflate(getActivity(),R.layout.ex_list_view,null);
                viewHolderGroup.tv = view.findViewById(R.id.ex_tv_titles);
                viewHolderGroup.tvTitle = view.findViewById(R.id.ex_tv_title);
                viewHolderGroup.up = view.findViewById(R.id.ex_arrow_up);
                viewHolderGroup.down = view.findViewById(R.id.ex_arrow_down);

                view.setTag(viewHolderGroup);
            }else{
                viewHolderGroup = (ViewHolderGroup) view.getTag();
            }
            if(i == 0){
                viewHolderGroup.tv.setVisibility(View.GONE);
                viewHolderGroup.up.setVisibility(View.GONE);
                viewHolderGroup.down.setVisibility(View.GONE);
                viewHolderGroup.tvTitle.setVisibility(View.VISIBLE);
                viewHolderGroup.tvTitle.setText(titleStrings[0]);
            }else if(i<=5){
                viewHolderGroup.tv.setText(groupStings[i-1]);
                viewHolderGroup.tv.setVisibility(View.VISIBLE);
                viewHolderGroup.up.setVisibility(View.VISIBLE);
                viewHolderGroup.down.setVisibility(View.VISIBLE);
                viewHolderGroup.tvTitle.setVisibility(View.GONE);
                if(b){
                    viewHolderGroup.down.setVisibility(View.VISIBLE);
                    viewHolderGroup.up.setVisibility(View.GONE);
                }else{
                    viewHolderGroup.down.setVisibility(View.GONE);
                    viewHolderGroup.up.setVisibility(View.VISIBLE);
                }
            }else if(i == 6){
                viewHolderGroup.tv.setVisibility(View.GONE);
                viewHolderGroup.up.setVisibility(View.GONE);
                viewHolderGroup.down.setVisibility(View.GONE);
                viewHolderGroup.tvTitle.setVisibility(View.VISIBLE);
                viewHolderGroup.tvTitle.setText(titleStrings[1]);
            } else if(i<=11){
                viewHolderGroup.tv.setText(groupStings[i-2]);
                viewHolderGroup.tv.setVisibility(View.VISIBLE);
                viewHolderGroup.up.setVisibility(View.VISIBLE);
                viewHolderGroup.down.setVisibility(View.VISIBLE);
                viewHolderGroup.tvTitle.setVisibility(View.GONE);
                if(b){
                    viewHolderGroup.down.setVisibility(View.VISIBLE);
                    viewHolderGroup.up.setVisibility(View.GONE);
                }else{
                    viewHolderGroup.down.setVisibility(View.GONE);
                    viewHolderGroup.up.setVisibility(View.VISIBLE);
                }
            }else if(i == 12){
                viewHolderGroup.tv.setVisibility(View.GONE);
                viewHolderGroup.up.setVisibility(View.GONE);
                viewHolderGroup.down.setVisibility(View.GONE);
                viewHolderGroup.tvTitle.setVisibility(View.VISIBLE);
                viewHolderGroup.tvTitle.setText(titleStrings[2]);
            }else{
                viewHolderGroup.tv.setText(groupStings[i-3]);
                viewHolderGroup.tv.setVisibility(View.VISIBLE);
                viewHolderGroup.up.setVisibility(View.VISIBLE);
                viewHolderGroup.down.setVisibility(View.VISIBLE);
                viewHolderGroup.tvTitle.setVisibility(View.GONE);
                if(b){
                    viewHolderGroup.down.setVisibility(View.VISIBLE);
                    viewHolderGroup.up.setVisibility(View.GONE);
                }else{
                    viewHolderGroup.down.setVisibility(View.GONE);
                    viewHolderGroup.up.setVisibility(View.VISIBLE);
                }
            }
            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            TextView v = new TextView(getActivity());
            ViewHolderChind viewHolderChind;
            if (view == null){
                viewHolderChind = new ViewHolderChind();
                view = View.inflate(getActivity(),R.layout.ex_child_view,null);
                viewHolderChind.ivTitle =  view.findViewById(R.id.iv_ex_xc);
                viewHolderChind.tvTitle = view.findViewById(R.id.tv_ex_xc);
                viewHolderChind.distanceOfRunListView =
                        view.findViewById(R.id.my_listview);

                view.setTag(viewHolderChind);
            }else{
                viewHolderChind = (ViewHolderChind) view.getTag();
            }
            if (i == 0){

            }else if(i<=5){
                viewHolderChind.ivTitle.setVisibility(View.GONE);
                viewHolderChind.tvTitle.setVisibility(View.GONE);
                viewHolderChind.distanceOfRunListView.setVisibility(View.VISIBLE);
                DistanceOfRunListView distanceOfRunListView = viewHolderChind.distanceOfRunListView;
                distanceOfRunListView.setCount(distanceActionList(i-1,i1).size());
                distanceOfRunListView.setMyTitle(
                        peopleBean.links.get(listPosition).calendar.get(i - 1).weeks.
                                get(i1).week);
                distanceOfRunListView.setTimeList(
                        distanceTimeList(i-1,i1));
                distanceOfRunListView.setActionList(distanceActionList(i-1,i1));
               distanceOfRunListView.setMyAdapter();
                setListViewHeightBasedOnChildren(viewHolderChind.distanceOfRunListView);
            }else if(i == 6){

            }else if(i<=11){
                viewHolderChind.ivTitle.setVisibility(View.VISIBLE);
                viewHolderChind.tvTitle.setVisibility(View.VISIBLE);
                viewHolderChind.distanceOfRunListView.setVisibility(View.GONE);
                if(i == 7){
                    viewHolderChind.ivTitle.setImageResource(GetImageIdByName.getImageId(
                            peopleBean.links.get(listPosition).like.get(i1).name_en,getActivity()
                    ));
                    viewHolderChind.tvTitle.setText(
                            peopleBean.links.get(listPosition).like.get(i1).name
                    );
                }else if(i == 8){
                    viewHolderChind.ivTitle.setImageResource(GetImageIdByName.getImageId(
                            likes.likes_1.get(i1).en,getActivity()
                    ));
                    viewHolderChind.tvTitle.setText(
                            likes.likes_1.get(i1).name
                    );
                }else if(i == 9){
                    viewHolderChind.ivTitle.setImageResource(GetImageIdByName.getImageId(
                            likes.likes_2.get(i1).en,getActivity()
                    ));
                    viewHolderChind.tvTitle.setText(
                            likes.likes_2.get(i1).name
                    );
                }else if(i == 10){
                    viewHolderChind.ivTitle.setImageResource(GetImageIdByName.getImageId(
                            likes.likes_3.get(i1).en,getActivity()
                    ));
                    viewHolderChind.tvTitle.setText(
                            likes.likes_3.get(i1).name
                    );
                }else{
                    viewHolderChind.ivTitle.setImageResource(GetImageIdByName.getImageId(
                            likes.likes_4.get(i1).en,getActivity()
                    ));
                    viewHolderChind.tvTitle.setText(
                            likes.likes_4.get(i1).name
                    );
                }
            }else if(i == 12){

            }else{
                viewHolderChind.ivTitle.setVisibility(View.GONE);
                viewHolderChind.tvTitle.setVisibility(View.GONE);
                viewHolderChind.distanceOfRunListView.setVisibility(View.VISIBLE);
                ArrayList<String> list = new ArrayList<>();
                if(i1 == 0){
                    viewHolderChind.distanceOfRunListView.setCount(1);
                    viewHolderChind.distanceOfRunListView.setMyTitle(getString(R.string.condition));
                    list.add(love_thing.get(i-13).condition);
                    viewHolderChind.distanceOfRunListView.setActionList(list);
                }else if(i1 == 1){
                    viewHolderChind.distanceOfRunListView.setCount(1);
                    viewHolderChind.distanceOfRunListView.setMyTitle(getString(R.string.process));
                    list.add(love_thing.get(i-13).things);
                    viewHolderChind.distanceOfRunListView.setActionList(list);
                }else{
                    viewHolderChind.distanceOfRunListView.setCount(1);
                    viewHolderChind.distanceOfRunListView.setMyTitle(getString(R.string.things_result));
                    list.add(love_thing.get(i-13).result);
                    viewHolderChind.distanceOfRunListView.setActionList(list);
                }
                viewHolderChind.distanceOfRunListView.setMyAdapter();
                setListViewHeightBasedOnChildren(viewHolderChind.distanceOfRunListView);
            }
            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return false;
        }
    }
    private void setIndicator(){
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                if(index ==0){
                    simplePagerTitleView.setText(R.string.profile);
                }else{
                    simplePagerTitleView.setText(R.string.people_details);
                }
                simplePagerTitleView.setNormalColor(Color.parseColor("#88000000"));
                simplePagerTitleView.setSelectedColor(Color.BLACK);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vpDetails.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.parseColor("#fdaa1b"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
    }
    private ArrayList<String> distanceTimeList(int season,int weekNum){
        ArrayList<String> list = new ArrayList<>();
        String s = peopleBean.links.get(listPosition).calendar.get(season).weeks.get(weekNum).action;
        String[] ss = s.split(",");
        for (int i = 0;i<ss.length;i++){
            if(i%2 == 0){
                list.add(ss[i]);
            }
        }
        return list;
    }
    private ArrayList<String> distanceActionList(int season,int weekNum){
        ArrayList<String> list = new ArrayList<>();
        String s = peopleBean.links.get(listPosition).calendar.get(season).weeks.get(weekNum).action;
        String[] ss = s.split(",");
        for (int i = 0;i<ss.length;i++){
            if(i%2 == 1){
                list.add(ss[i]);
            }
        }
        return list;
    }
    class ViewHolderGroup{
        TextView tv;
        TextView tvTitle;
        ImageView up;
        ImageView down;
    }
    class ViewHolderChind{
        ImageView ivTitle;
        TextView tvTitle;
        DistanceOfRunListView distanceOfRunListView;
    }
    public static void setListViewHeightBasedOnChildren(ListView listView){
        ListAdapter listAdapter = listView.getAdapter();
        //初始化高度
        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            //计算子项View的宽高，注意listview所在的要是linearlayout布局
            listItem.measure(0, 0);
            //统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        /*
         * listView.getDividerHeight()获取子项间分隔符占用的高度，有多少项就乘以多少个减一
         * params.height最后得到整个ListView完整显示需要的高度
         * 最后将params.height设置为listview的高度
         */
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);

    }
}

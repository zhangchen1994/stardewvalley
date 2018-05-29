package com.chen.stardewvalley.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.chen.stardewvalley.view.DistanceOfRunListView;
import com.chen.stardewvalley.R;
import com.chen.stardewvalley.domain.ValleyBean;
import com.chen.stardewvalley.utils.GetImageIdByName;
import com.chen.stardewvalley.utils.JsonParse;
import com.chen.stardewvalley.utils.PeopleNameToEn;

import java.util.ArrayList;

/**
 * Created by zc on 2018/5/19.
 */

public class ValleyDeailsFragment extends Fragment {
    private View view;
    private int listPosition;
    private LinearLayout ll;
    private ValleyBean valleyBean;
    private ImageView ivTitle;
    private int[] titleStrings = new int[]{
            R.string.local_name,R.string.people_name,
            R.string.open_time,R.string.describe
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_valley_deails,null);
        init();
        return view;
    }
    private void init(){
        listPosition = getArguments().getInt("position");
        ll = view.findViewById(R.id.ll_valley);
        ivTitle = view.findViewById(R.id.iv_valley_details);
        json();
        int viewNum = 5;

        ArrayList<String > actionList = new ArrayList<>();
        actionList.add(valleyBean.links.get(listPosition).image);
        actionList.add(valleyBean.links.get(listPosition).name);
        actionList.add(valleyBean.links.get(listPosition).people);
        if(valleyBean.links.get(listPosition).open_time == null){
            viewNum--;
        }else{
            actionList.add(valleyBean.links.get(listPosition).open_time);
        }
        if(valleyBean.links.get(listPosition).describe == null){
            viewNum--;
        }else{
            actionList.add(valleyBean.links.get(listPosition).describe);
        }

        ivTitle.setImageResource(GetImageIdByName.getImageId(actionList.get(0),getActivity()));
        for(int i=0;i<viewNum-1;i++){

            View v2 = View.inflate(getActivity(),
                    R.layout.frgment_valley_deatils_list_view,null);
            DistanceOfRunListView listView = v2.findViewById(R.id.dis_of_run_valley);
            ArrayList<String> list = new ArrayList<>();
            if(viewNum == 3){
                if(i == 0){
                    listView.setMyTitle(getString(titleStrings[0]));
                    listView.setCount(1);
                    list.add(actionList.get(1));
                    listView.setActionList(list);
                }else if(i == 1){
                    listView.setMyTitle(getString(titleStrings[1]));
                    ArrayList<Integer> imageIds = new ArrayList<>();
                    list = getStrings(actionList.get(2));
                    for (String name:list){
                        int imageId = GetImageIdByName.getImageId(PeopleNameToEn.getNameEn(name)
                                +"_icon",getActivity());
                        if(imageId != 0){
                            imageIds.add(imageId);
                        }
                    }
                    listView.setImageIds(imageIds);
                    listView.setCount(list.size());
                    listView.setActionList(list);
                }
            }else if(viewNum == 4){
                if(i == 0){
                    listView.setMyTitle(getString(titleStrings[0]));
                    listView.setCount(1);
                    list.add(actionList.get(1));
                    listView.setActionList(list);
                }else if(i == 1){
                    listView.setMyTitle(getString(titleStrings[1]));
                    ArrayList<Integer> imageIds = new ArrayList<>();
                    list = getStrings(actionList.get(2));
                    for (String name:list){
                        int imageId = GetImageIdByName.getImageId(PeopleNameToEn.getNameEn(name)
                                +"_icon",getActivity());
                        if(imageId != 0){
                            imageIds.add(imageId);
                        }
                    }
                    listView.setImageIds(imageIds);
                    listView.setCount(list.size());
                    listView.setActionList(list);
                }else if(i == 2){
                    listView.setMyTitle(getString(titleStrings[3]));
                    list.add(actionList.get(3));
                    listView.setCount(1);
                    listView.setActionList(list);
                }
            }else if(viewNum == 5){
                if(i == 0){
                    listView.setMyTitle(getString(titleStrings[0]));
                    listView.setCount(1);
                    list.add(actionList.get(1));
                    listView.setActionList(list);
                }else if(i == 1){
                    listView.setMyTitle(getString(titleStrings[1]));
                    ArrayList<Integer> imageIds = new ArrayList<>();
                    list = getStrings(actionList.get(2));
                    for (String name:list){
                        int imageId = GetImageIdByName.getImageId(PeopleNameToEn.getNameEn(name)
                                +"_icon",getActivity());
                        System.out.println(name);
                        if(imageId != 0){
                            imageIds.add(imageId);
                        }
                    }
                    listView.setImageIds(imageIds);
                    listView.setCount(list.size());
                    listView.setActionList(list);
                }else if(i == 2){
                    listView.setMyTitle(getString(titleStrings[2]));
                    list.add(actionList.get(3));
                    listView.setCount(1);
                    listView.setActionList(list);
                }else if(i == 3){
                    listView.setMyTitle(getString(titleStrings[3]));
                    list.add(actionList.get(4));
                    listView.setCount(1);
                    listView.setActionList(list);
                }
            }
            listView.setMyAdapter();
            setListViewHeightBasedOnChildren(listView);
            ll.addView(v2);
        }
    }
    private void json(){
        valleyBean = JsonParse.returnValley();
    }
    public ArrayList<String> getStrings(String s){
        String[] ss = s.split(",");
        ArrayList<String> list = new ArrayList<>();
        for(String s1: ss){
            list.add(s1);
        }
        return list;
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

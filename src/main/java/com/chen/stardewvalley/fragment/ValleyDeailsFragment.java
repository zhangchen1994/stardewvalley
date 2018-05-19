package com.chen.stardewvalley.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chen.stardewvalley.DistanceOfRunListView;
import com.chen.stardewvalley.R;
import com.chen.stardewvalley.domain.ValleyBean;
import com.chen.stardewvalley.utils.GetImageIdByName;
import com.chen.stardewvalley.utils.PeopleNameToEn;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by zc on 2018/5/19.
 */

public class ValleyDeailsFragment extends Fragment {
    private View view;
    private int listPosition;
    private LinearLayout ll;
    private ValleyBean valleyBean;
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
        for(int i=0;i<viewNum;i++){
            TextView textView = new TextView(getActivity());
            textView.setText(i+"");

            DistanceOfRunListView listView = new DistanceOfRunListView(getActivity());
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
                        System.out.println("imageis = " +imageId+" name = "
                                +PeopleNameToEn.getNameEn(name)+"_icon");
                        if(imageId != 0){
                            imageIds.add(imageId);
                        }
                    }
                    listView.setImageIds(imageIds);
                    listView.setCount(list.size());
                    listView.setActionList(list);
                }
            }
            listView.setMyAdapter();
            ll.addView(listView);
        }
    }
    private void json(){
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("assets/"+"valley.json");
        String gsonStr = inToString(inputStream);
        Gson gson = new Gson();
        valleyBean = gson.fromJson(gsonStr,ValleyBean.class);
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
    public ArrayList<String> getStrings(String s){
        String[] ss = s.split(",");
        ArrayList<String> list = new ArrayList<>();
        for(String s1: ss){
            list.add(s1);
        }
        return list;
    }
}

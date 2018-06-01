package com.chen.stardewvalley.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.ValleyRegionPopupWindow;
import com.chen.stardewvalley.domain.PeopleBean;
import com.chen.stardewvalley.domain.ValleyBean;
import com.chen.stardewvalley.utils.GetImageIdByName;
import com.chen.stardewvalley.utils.GetMapRegion;
import com.chen.stardewvalley.utils.JsonParse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zc on 2018/5/19.
 */

public class ValleyListFragment extends Fragment{
    private View view;
    private ValleyBean valleyBean;
    public ListView rvList;
    private ListClickListener listener;
    public int regionWigth;
    public int regionHeight;
    private ValleyRegionPopupWindow popupWindow;

    public static interface ListClickListener {
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l);
    }
    public void setOnListClickListener(ListClickListener listener){
        this.listener = listener;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(),R.layout.fragment_valley_list_view,null);
        init();
        return view;
    }
    private void init(){
        json();
        if(popupWindow == null){
            popupWindow = ValleyRegionPopupWindow.getInstance(getActivity());
        }

        rvList = view.findViewById(R.id.gv_valley_list);

        rvList.setAdapter(new myAdapter());
        rvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(regionWigth == 0){
                    regionWigth = getArguments().getInt("region_w");
                    regionHeight = getArguments().getInt("region_h");
                }
                int x  = regionWigth*(GetMapRegion.getListRegion(i)[0]);
                int y = regionHeight*(GetMapRegion.getListRegion(i)[1]);
                popupWindow.init(x+regionWigth/2,y+regionHeight/2,valleyBean.links.get(i).name);
                popupWindow.showPopupWindow(x+regionWigth/2,y+regionHeight/2);

                if(listener != null){
                    listener.onItemClick(adapterView,view,i,l);
                }
            }
        });
    }
    private void json(){
        valleyBean = JsonParse.returnValley();
    }
   class  myAdapter extends BaseAdapter{

       @Override
       public int getCount() {
           return valleyBean.links.size();
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
               view = View.inflate(getActivity(),R.layout.fragmen_valley_gv_view,null);
               viewHolder.iv = view.findViewById(R.id.iv_rv_valley);
               viewHolder.tv = view.findViewById(R.id.tv_rv_valley);

               view.setTag(viewHolder);
           }else{
               viewHolder = (ViewHolder) view.getTag();
           }
           int imageId = GetImageIdByName.getImageId(valleyBean.links.get(i).image,getActivity());
           viewHolder.iv.setImageResource(imageId);
           viewHolder.tv.setText(valleyBean.links.get(i).name);
           return view;
       }
   }
   class ViewHolder{
        TextView tv;
        ImageView iv;
   }
}

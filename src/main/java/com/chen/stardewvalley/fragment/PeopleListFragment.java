package com.chen.stardewvalley.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.domain.PeopleBean;
import com.chen.stardewvalley.utils.GetImageIdByName;
import com.google.gson.Gson;
import com.yyydjk.library.DropDownMenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by zc on 2018/5/16.
 */

public class PeopleListFragment extends Fragment{
    private View view;
    private DropDownMenu dropDownMenu;
    private ArrayList<String> headers;
    private int[] total = new int[]{R.string.total};
    private int[] marriage = new int[]{R.string.marriage_no,R.string.marriage_no};
    private int[] sex = new int[]{R.string.man,R.string.women};
    private int[] name = new int[]{R.string.total};
    private ListView lvPeople;
    private ArrayList<View> dropMenuList;
    private View contentView;
    private PeopleBean peopleBean;
    private PeopleAdapter peopleAdapter;
    private ListClickListener listener;

    public static interface ListClickListener {
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l);
    }
    public void setOnListClickListener(ListClickListener listener){
        this.listener = listener;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_people_list,null);
        json();
        init();
        return view;
    }
    private void init(){
        dropDownMenu = view.findViewById(R.id.dropDownMenu);
        initDropDownMenu();
        dropDownMenu.setDropDownMenu(headers, dropMenuList, contentView);

        peopleAdapter = new PeopleAdapter();
        lvPeople.setAdapter(peopleAdapter);

        lvPeople.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.onItemClick(adapterView,view,i,l);
            }
        });
    }
    private void json(){
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("assets/"+"people.json");
        String gsonStr = inToString(inputStream);
        Gson gson = new Gson();
        peopleBean = gson.fromJson(gsonStr,PeopleBean.class);
        for (int i=0;i<peopleBean.links.size();i++){
            System.out.println(peopleBean.links.get(i).family.grandfather);
        }
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
    private void initDropDownMenu(){
        headers = new ArrayList<>();
        dropMenuList = new ArrayList<>();
        headers.add(getString(R.string.total));
        headers.add(getString(R.string.marriage));
        headers.add(getString(R.string.sex));
        headers.add(getString(R.string.name));

        TextView tvTotal = new TextView(getActivity());
        tvTotal.setText(R.string.total);

        TextView tvTotal2 = new TextView(getActivity());
        tvTotal.setText(R.string.total);

        TextView tvTotal3 = new TextView(getActivity());
        tvTotal.setText(R.string.total);

        TextView tvTotal4 = new TextView(getActivity());
        tvTotal.setText(R.string.total);

        dropMenuList.add(tvTotal);
        dropMenuList.add(tvTotal2);
        dropMenuList.add(tvTotal3);
        dropMenuList.add(tvTotal4);

        contentView = View.inflate(getActivity(),R.layout.drop_menu_people_listview,null);
        //contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        lvPeople = contentView.findViewById(R.id.lv_people);
    }
    class PeopleAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 20;
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
                view = View.inflate(getActivity(),R.layout.listview_people,null);
                viewHolder.ivPeople = view.findViewById(R.id.iv_people_list);
                viewHolder.tvName = view.findViewById(R.id.tv_name_list);
                viewHolder.tvNameEn = view.findViewById(R.id.tv_name_en_list);
                viewHolder.tvMarry = view.findViewById(R.id.tv_marry_list);

                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }
            String imageName = "alex";
            viewHolder.ivPeople.setImageResource(GetImageIdByName.getImageId(imageName,getContext()));
            viewHolder.tvName.setText(peopleBean.links.get(0).name);
            viewHolder.tvNameEn.setText("alex");
            viewHolder.tvMarry.setText(peopleBean.links.get(0).marriage);
            return view;
        }
    }
    class ViewHolder{
        public ImageView ivPeople;
        public TextView tvName;
        public TextView tvNameEn;
        public TextView tvMarry;
    }
}

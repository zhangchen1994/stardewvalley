package com.chen.stardewvalley.fragment;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.activity.PeopleActivity;
import com.chen.stardewvalley.domain.PeopleBean;
import com.chen.stardewvalley.utils.DisplayUtils;
import com.chen.stardewvalley.utils.GetImageIdByName;
import com.chen.stardewvalley.utils.JsonParse;
import com.chen.stardewvalley.utils.PeopleNameToEn;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;

/**
 * Created by zc on 2018/5/16.
 */

public class PeopleListFragment extends Fragment{
    private View view;
    private DropDownMenu dropDownMenu;
    private ArrayList<String> headers;
    private String marrOkStr = "单身可攻略";
    private String marrStr = "单身不可攻略，不明，非单身";
    private int[] total = new int[]{R.string.total};
    private int[] marriage = new int[]{R.string.marriage_ok,R.string.marriage_no};
    private int[] sex = new int[]{R.string.man,R.string.women};
    private int[] name = new int[]{R.string.total};
    private ListView lvPeople;
    private ArrayList<View> dropMenuList;
    private View contentView;
    private PeopleBean peopleBean;
    private ArrayList<PeopleBean.PeoPle> lists = new ArrayList<>();
    private PeopleAdapter peopleAdapter;
    private ListClickListener listener;
    private ListDropTotalAdapter listDropTotalAdapter;
    private ListDropMarriageAdapter listDropMarriageAdapter;
    private ListDropSexAdapter listDropSexAdapter;
    private GvDropContent gvDropContent;
    private ArrayList<PeopleBean.PeoPle> marrManList = new ArrayList<>();
    private ArrayList<PeopleBean.PeoPle> marrWeList = new ArrayList<>();
    private ArrayList<PeopleBean.PeoPle> marrNoManList = new ArrayList<>();
    private ArrayList<PeopleBean.PeoPle> marrNoWeList = new ArrayList<>();

    public static interface ListClickListener {
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l,String name);
    }
    public void setOnListClickListener(ListClickListener listener){
        this.listener = listener;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_people_list,null);
        json();
        setDataList();
        init();
        return view;
    }
    private void init(){
        dropDownMenu = view.findViewById(R.id.dropDownMenu);

        listDropMarriageAdapter = new ListDropMarriageAdapter();
        listDropSexAdapter = new ListDropSexAdapter();
        listDropTotalAdapter = new ListDropTotalAdapter();
        gvDropContent = new GvDropContent();

        initDropDownMenu();
        dropDownMenu.setDropDownMenu(headers, dropMenuList, contentView);
        System.out.println(PeopleActivity.dropStatus);
        peopleAdapter = new PeopleAdapter();
        lvPeople.setAdapter(peopleAdapter);

        lvPeople.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.onItemClick(adapterView,view,i,l,lists.get(i).name);
            }
        });
    }
    private void setDataList(){
//        for(PeopleBean.PeoPle p: lists){
//            System.out.println(p.name);
//        }
        if(PeopleActivity.dropStatus == PeopleActivity.DROP_STATUS_TOTAL
                &&PeopleActivity.dropMarrStatus == PeopleActivity.DROP_STATUS_TOTAL
                &&PeopleActivity.dropSexStatus == PeopleActivity.DROP_STATUS_TOTAL){
            lists.clear();
            lists.addAll(marrNoWeList);
            lists.addAll(marrWeList);
            lists.addAll(marrManList);
            lists.addAll(marrNoManList);
        }else if(PeopleActivity.dropMarrStatus == PeopleActivity.DROP_STATUS_MARRIAGE_OK
                &&PeopleActivity.dropSexStatus == PeopleActivity.DROP_STATUS_TOTAL){
            lists.clear();
            lists.addAll(marrManList);
            lists.addAll(marrWeList);
        }else if(PeopleActivity.dropMarrStatus == PeopleActivity.DROP_STATUS_MARRIAGE_NO
                &&PeopleActivity.dropSexStatus == PeopleActivity.DROP_STATUS_TOTAL){
            lists.clear();
            lists.addAll(marrNoManList);
            lists.addAll(marrNoWeList);
        }else if(PeopleActivity.dropMarrStatus == PeopleActivity.DROP_STATUS_TOTAL
                &&PeopleActivity.dropSexStatus == PeopleActivity.DROP_STATUS_SEX_M){
            lists.clear();
            lists.addAll(marrNoManList);
            lists.addAll(marrManList);

        }else if(PeopleActivity.dropMarrStatus == PeopleActivity.DROP_STATUS_TOTAL
                &&PeopleActivity.dropSexStatus == PeopleActivity.DROP_STATUS_SEX_W){
            lists.clear();
            lists.addAll(marrWeList);
            lists.addAll(marrNoWeList);
        }else if(PeopleActivity.dropMarrStatus == PeopleActivity.DROP_STATUS_MARRIAGE_OK
                &&PeopleActivity.dropSexStatus == PeopleActivity.DROP_STATUS_SEX_M){
            lists.clear();
            lists.addAll(marrManList);
        }else if(PeopleActivity.dropMarrStatus == PeopleActivity.DROP_STATUS_MARRIAGE_OK
                &&PeopleActivity.dropSexStatus == PeopleActivity.DROP_STATUS_SEX_W){
            lists.clear();
            lists.addAll(marrWeList);
        }else if(PeopleActivity.dropMarrStatus == PeopleActivity.DROP_STATUS_MARRIAGE_NO
                &&PeopleActivity.dropSexStatus == PeopleActivity.DROP_STATUS_SEX_M){
            lists.clear();
            lists.addAll(marrNoManList);
        }else if(PeopleActivity.dropMarrStatus == PeopleActivity.DROP_STATUS_MARRIAGE_NO
                &&PeopleActivity.dropSexStatus == PeopleActivity.DROP_STATUS_SEX_W){
            lists.clear();
            lists.addAll(marrNoWeList);
        }
    }
    private void json(){
        peopleBean = JsonParse.returnPeople();

        if(marrNoWeList.size() == 0){
            for(PeopleBean.PeoPle peoPle: peopleBean.links){
                if(marrOkStr.contains(peoPle.marriage) && "男".equals(peoPle.sex)){
                    marrManList.add(peoPle);
                }else if(marrOkStr.contains(peoPle.marriage) && "女".equals(peoPle.sex)){
                    marrWeList.add(peoPle);
                }else if(marrStr.contains(peoPle.marriage) && "男".equals(peoPle.sex)){
                    marrNoManList.add(peoPle);
                }else if(marrStr.contains(peoPle.marriage) && "女".equals(peoPle.sex)){
                    marrNoWeList.add(peoPle);
                }
            }
        }
    }
    private void initDropDownMenu(){
        headers = new ArrayList<>();
        dropMenuList = new ArrayList<>();
        headers.add(getString(R.string.total));
        headers.add(getString(R.string.marriage));
        headers.add(getString(R.string.sex));
        headers.add(getString(R.string.name));
        View v1 = View.inflate(getActivity(),R.layout.drop_menu_list_view_1,null);
        View v2 = View.inflate(getActivity(),R.layout.drop_menu_list_view_2,null);
        View v3 = View.inflate(getActivity(),R.layout.drop_menu_list_view_3,null);
        View v4 = View.inflate(getActivity(),R.layout.drop_menu_list_view_4,null);
        ListView listView_total = v1.findViewById(R.id.lv_drop_view);
        ListView listView_mariage = v2.findViewById(R.id.lv_drop_view_2);
        ListView listView_sex = v3.findViewById(R.id.lv_drop_view_3);
        GridView gridView = v4.findViewById(R.id.gv_drop_view);

        listView_total.setAdapter(listDropTotalAdapter);
        listView_mariage.setAdapter(listDropMarriageAdapter);
        listView_sex.setAdapter(listDropSexAdapter);
        gridView.setAdapter(gvDropContent);

        listView_total.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PeopleActivity.dropStatus = PeopleActivity.DROP_STATUS_TOTAL;
                PeopleActivity.dropMarrStatus = PeopleActivity.DROP_STATUS_TOTAL;
                PeopleActivity.dropSexStatus = PeopleActivity.DROP_STATUS_TOTAL;
                dropDownMenu.closeMenu();
                setAdapterChanges();
            }
        });
        listView_mariage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    PeopleActivity.dropMarrStatus = PeopleActivity.DROP_STATUS_MARRIAGE_OK;
                }else{
                    PeopleActivity.dropMarrStatus = PeopleActivity.DROP_STATUS_MARRIAGE_NO;
                }
                dropDownMenu.closeMenu();
                setAdapterChanges();
            }
        });
        listView_sex.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    PeopleActivity.dropSexStatus = PeopleActivity.DROP_STATUS_SEX_M;
                }else{
                    PeopleActivity.dropSexStatus = PeopleActivity.DROP_STATUS_SEX_W;
                }
                dropDownMenu.closeMenu();
                setAdapterChanges();
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.onItemClick(adapterView,view,i,l,lists.get(i).name);
                dropDownMenu.closeMenu();
            }
        });

        dropMenuList.add(v1);
        dropMenuList.add(v2);
        dropMenuList.add(v3);
        dropMenuList.add(v4);

        contentView = View.inflate(getActivity(),R.layout.drop_menu_people_listview,null);
        //contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        lvPeople = contentView.findViewById(R.id.lv_people);
    }
    private void setAdapterChanges(){
        listDropMarriageAdapter.notifyDataSetChanged();
        listDropSexAdapter.notifyDataSetChanged();
        listDropTotalAdapter.notifyDataSetChanged();
        setDataList();
        gvDropContent.notifyDataSetChanged();
        peopleAdapter.notifyDataSetChanged();

    }
    class PeopleAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return lists.size();
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
            String imageName = PeopleNameToEn.getNameEn(lists.get(i).name);
            viewHolder.ivPeople.setImageResource(GetImageIdByName.getImageId(imageName,getContext()));
            viewHolder.tvName.setText(lists.get(i).name);
            viewHolder.tvNameEn.setText(
                    PeopleNameToEn.getNameEnUp(lists.get(i).name)
            );
            viewHolder.tvMarry.setText(lists.get(i).marriage);
            return view;
        }
    }
    class ViewHolder{
        public ImageView ivPeople;
        public TextView tvName;
        public TextView tvNameEn;
        public TextView tvMarry;
    }
    class ListDropTotalAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 1;
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
            View v = View.inflate(getActivity(),R.layout.arop_menu_list_content_view,null);
            //v.setBackgroundColor(Color.parseColor("#2f000000"));
            TextView textView = v.findViewById(R.id.tv_drop_menu);
            ImageView imageView = v.findViewById(R.id.iv_drop_menu);
            textView.setText(R.string.total);
            if(PeopleActivity.dropStatus == PeopleActivity.DROP_STATUS_TOTAL
                    &&PeopleActivity.dropMarrStatus == PeopleActivity.DROP_STATUS_TOTAL
                    &&PeopleActivity.dropSexStatus == PeopleActivity.DROP_STATUS_TOTAL){
                textView.setTextColor(Color.parseColor("#ff890c85"));
                imageView.setVisibility(View.VISIBLE);
            }else{
                textView.setTextColor(Color.parseColor("#ff000000"));
                imageView.setVisibility(View.GONE);
            }
            return v;
        }
    }
    class ListDropMarriageAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 2;
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
            View v = View.inflate(getActivity(),R.layout.arop_menu_list_content_view,null);
            //v.setBackgroundColor(Color.parseColor("#2f000000"));
            TextView textView = v.findViewById(R.id.tv_drop_menu);
            ImageView imageView = v.findViewById(R.id.iv_drop_menu);
            textView.setText(marriage[i]);
            if(i == 0){
                if(PeopleActivity.dropMarrStatus == PeopleActivity.DROP_STATUS_MARRIAGE_OK){
                    textView.setTextColor(Color.parseColor("#ff890c85"));
                    imageView.setVisibility(View.VISIBLE);
                }else{
                    textView.setTextColor(Color.parseColor("#ff000000"));
                    imageView.setVisibility(View.GONE);
                }
            }else{
                if(PeopleActivity.dropMarrStatus == PeopleActivity.DROP_STATUS_MARRIAGE_NO){
                    textView.setTextColor(Color.parseColor("#ff890c85"));
                    imageView.setVisibility(View.VISIBLE);
                }else{
                    textView.setTextColor(Color.parseColor("#ff000000"));
                    imageView.setVisibility(View.GONE);
                }
            }
            return v;
        }
    }
    class ListDropSexAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 2;
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
            View v = View.inflate(getActivity(),R.layout.arop_menu_list_content_view,null);
            //v.setBackgroundColor(Color.parseColor("#2f000000"));
            TextView textView = v.findViewById(R.id.tv_drop_menu);
            ImageView imageView = v.findViewById(R.id.iv_drop_menu);
            textView.setText(sex[i]);
            if(i == 0){
                if(PeopleActivity.dropSexStatus == PeopleActivity.DROP_STATUS_SEX_M){
                    textView.setTextColor(Color.parseColor("#ff890c85"));
                    imageView.setVisibility(View.VISIBLE);
                }else{
                    textView.setTextColor(Color.parseColor("#ff000000"));
                    imageView.setVisibility(View.GONE);
                }
            }else{
                if(PeopleActivity.dropSexStatus == PeopleActivity.DROP_STATUS_SEX_W){
                    textView.setTextColor(Color.parseColor("#ff890c85"));
                    imageView.setVisibility(View.VISIBLE);
                }else{
                    textView.setTextColor(Color.parseColor("#ff000000"));
                    imageView.setVisibility(View.GONE);
                }
            }
            return v;
        }
    }
    class GvDropContent extends BaseAdapter{

        @Override
        public int getCount() {
            return lists.size();
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
            ViewDrHolder viewDrHolder;
            if(view == null){
                viewDrHolder = new ViewDrHolder();
                view = View.inflate(getActivity(),R.layout.gv_drop_view,null);
                viewDrHolder.textView = view.findViewById(R.id.tv_gv_drop);

                view.setTag(viewDrHolder);
            }else{
                viewDrHolder = (ViewDrHolder) view.getTag();
            }
            viewDrHolder.textView.setText(lists.get(i).name);
            Resources res = getResources();
            int imageId = GetImageIdByName.getImageId(PeopleNameToEn.getNameEn(lists.get(i).name)
                    +"_icon",getActivity());
            if(imageId != 0){
                Drawable drawable = res.getDrawable(imageId);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                viewDrHolder.textView.setCompoundDrawables(drawable,null,null,null);
            }
            return view;
        }
    }
    class ViewDrHolder{
        public TextView textView;
    }
}

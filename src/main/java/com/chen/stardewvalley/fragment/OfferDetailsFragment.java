package com.chen.stardewvalley.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.domain.OfferBean;
import com.chen.stardewvalley.utils.DisplayUtils;
import com.chen.stardewvalley.utils.GetImageIdByName;
import com.chen.stardewvalley.utils.JsonParse;
import com.chen.stardewvalley.view.ExcelView;

import java.util.ArrayList;

/**
 * Created by zc on 2018/6/6.
 */

public class OfferDetailsFragment extends Fragment {
    private View view;
    private ImageView imageViewTitle;
    private TextView tvTitle;
    private TextView tvSelect;
    private GridView gvContnet;
    private LinearLayout ll_offer_details_content;
    private ImageView ivReward;
    private TextView tvReward;
    private ExcelView excelView;
    private OfferBean offerBean;
    private int viewPosition;
    private int position;
    private PopupWindow popupWindow;
    int x = 0;
    int y = 0;
    private int[] titleString = new int[]{
            R.string.image,
            R.string.tools_name,
            R.string.tools_local
    };
    private int[] titleFishString = new int[]{
            R.string.image,
            R.string.tools_name,
            R.string.tools_local,
            R.string.fish_time,
            R.string.season,
            R.string.weather
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.offer_details_view,null);
        init();
        return view;
    }
    private  void init(){
        viewPosition = getArguments().getInt("position");
        position = getArguments().getInt("i");
        imageViewTitle = view.findViewById(R.id.iv_offer_details_title);
        tvTitle = view.findViewById(R.id.tv_offer_details_title);
        tvSelect = view.findViewById(R.id.tv_offer_details_select);
        gvContnet = view.findViewById(R.id.gv_offer_details);
        ivReward = view.findViewById(R.id.iv_offer_reward);
        tvReward = view.findViewById(R.id.tv_offer_reward);
        excelView = view.findViewById(R.id.ex_offer_details);
        ll_offer_details_content = view.findViewById(R.id.ll_offer_details_content);
        offerBean = JsonParse.returnOffer();
        setTitle();
        setExcelView();
        gvContnet.post(new Runnable() {
            @Override
            public void run() {
                gvContnet.setAdapter(new GvAdapter(gvContnet.getWidth()));
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                        ll_offer_details_content.getLayoutParams();
//                params.height = gvContnet.getHeight()+
//                        DisplayUtils.dp2px(getActivity(),10);
//                ll_offer_details_content.setLayoutParams(params);
            }
        });
    }
    private void initPopupWindow(View view,String name,String describe){
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.offer_details_popup_view, null);
        popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                true);
        popupWindow.setWidth(DisplayUtils.dp2px(getActivity(),130));
        popupWindow.setHeight(DisplayUtils.dp2px(getActivity(),150));
        TextView tv1 = contentView.findViewById(R.id.tv_offer_popup_name);
        TextView tv2 = contentView.findViewById(R.id.tv_offer_popup_dec);
        tv1.setText(name);
        tv2.setText(describe);
        popupWindow.setContentView(contentView);
        popupWindow.showAsDropDown(view,0,-(DisplayUtils.dp2px
                (getActivity(),150)+view.getHeight()));
    }
    private void setExcelView(){
        if(offerBean.links.get(viewPosition).content.get(position).name.contains("鱼")){
            excelView.setTitleList(titleFishString);
            excelView.setWeigthList(new int[]{2,2,2,4,2,3});
            excelView.setLayouttTitle();
            ArrayList<String> list1 = new ArrayList<>();
            ArrayList<String> list2 = new ArrayList<>();
            ArrayList<String> list3 = new ArrayList<>();
            ArrayList<String> list4 = new ArrayList<>();
            ArrayList<String> list5 = new ArrayList<>();
            ArrayList<String> list6 = new ArrayList<>();
            for(int i=0;i<offerBean.links.get(viewPosition).content.get(position).offer_con.size();i++) {
                list1.add(offerBean.links.get(viewPosition).content.get(position).offer_con.get(i).images);
                list2.add(offerBean.links.get(viewPosition).content.get(position).offer_con.get(i).name);
                String[] contrnts = offerBean.links.get(viewPosition).content.
                        get(position).offer_con.get(i).describe.split(",");
                if(contrnts.length <= 3){
                    list3.add(contrnts[0]);
                    list4.add(contrnts[1]);
                    list5.add(contrnts[2]);
                    list6.add("N/A");
                }else{
                    list3.add(contrnts[0]);
                    list4.add(contrnts[1]);
                    list5.add(contrnts[2]);
                    list6.add(contrnts[3]);
                }

            }
            ArrayList<ArrayList<String>> lists = new ArrayList<>();
            lists.add(list1);
            lists.add(list2);
            lists.add(list3);
            lists.add(list4);
            lists.add(list5);
            lists.add(list6);
            excelView.setDataList(lists);
            excelView.setAdapter();
            excelView.setListHeigh();
        }else{
            excelView.setTitleList(titleString);
            excelView.setWeigthList(new int[]{1,2,4});
            excelView.setLayouttTitle();
            ArrayList<String> list1 = new ArrayList<>();
            ArrayList<String> list2 = new ArrayList<>();
            ArrayList<String> list3 = new ArrayList<>();
            for(int i=0;i<offerBean.links.get(viewPosition).content.get(position).offer_con.size();i++){
                list1.add(offerBean.links.get(viewPosition).content.get(position).offer_con.get(i).images);
                list2.add(offerBean.links.get(viewPosition).content.get(position).offer_con.get(i).name);
                list3.add(offerBean.links.get(viewPosition).content.get(position).offer_con.get(i).describe);
            }
            ArrayList<ArrayList<String>> lists = new ArrayList<>();
            lists.add(list1);
            lists.add(list2);
            lists.add(list3);
            excelView.setDataList(lists);
            excelView.setAdapter();
            excelView.setListHeigh();
        }
    }
    private void setTitle(){
        String title1 = offerBean.links.get(viewPosition).content.get(position).name;
        tvTitle.setText(title1);
        if(title1.contains("(")){
            char[] titlec = title1.toCharArray();
            int num = 0;
            for(char c:titlec){
                if(c == '('){
                    break;
                }
                num++;
            }
            String title2 = title1.substring(0,num);
            String title3 = title1.substring(num+1,title1.length()-1);
            if(title2.length()>8){
                StringBuffer sb = new StringBuffer(title2);
                sb.insert(6,"\n");
                title2 = sb.toString();
            }
            tvTitle.setText(title2);
            tvSelect.setText(title3);
        }
        imageViewTitle.setImageResource(GetImageIdByName.getImageId(offerBean.links.
                get(viewPosition).content.get(position).image,getActivity()));
        int imageId = GetImageIdByName.getImageId(offerBean.links.
                get(viewPosition).content.get(position).reward_images,getActivity());
        if(imageId != 0){
            ivReward.setImageResource(imageId);
        }
        tvReward.setText(offerBean.links.get(viewPosition).content.get(position).reward);
    }
    class GvAdapter extends BaseAdapter{
        int width;
        public GvAdapter(int width){
            this.width = width;
        }
        @Override
        public int getCount() {
            return 12;
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if(view == null){
                view = View.inflate(getActivity(),R.layout.gridview_offer_content_view,null);
                viewHolder = new ViewHolder();
                viewHolder.imageView = view.findViewById(R.id.iv_gridview_offer_view);

                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }
            int w = width/6;
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                    w,w
            );
            view.setLayoutParams(params);
            if(i<offerBean.links.get(viewPosition).content.get(position).offer_con.size()){
                String imageId = offerBean.links.get(viewPosition).content.get(position).
                        offer_con.get(i).images;
                viewHolder.imageView.setImageResource(GetImageIdByName.getImageId(imageId,getActivity()));

                view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        switch (motionEvent.getAction()){
                            case MotionEvent.ACTION_DOWN:
                                initPopupWindow(view,
                                        offerBean.links.get(viewPosition).content.get(position).
                                                offer_con.get(i).name,
                                        offerBean.links.get(viewPosition).content.get(position).
                                                offer_con.get(i).describe);
                                x = (int) motionEvent.getX();
                                y = (int) motionEvent.getY();
                                break;
                            case MotionEvent.ACTION_MOVE:
                                int x2 = (int) motionEvent.getX();
                                int y2 = (int) motionEvent.getY();

                                int x3 = x2 - x;
                                int y3 = y2 - y;

                                if(x3 > 100||x3 < -100|| y3 > 10|| y3 < -10){
                                    popupWindow.dismiss();
                                }
                                break;
                            case MotionEvent.ACTION_UP:
                                popupWindow.dismiss();
                                break;
                        }
                        return true;
                    }
                });
            }
            return view;
        }
    }
    class ViewHolder{
        ImageView imageView;
    }
}

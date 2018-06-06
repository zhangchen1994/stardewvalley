package com.chen.stardewvalley.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.domain.OfferBean;
import com.chen.stardewvalley.utils.DisplayUtils;
import com.chen.stardewvalley.utils.JsonParse;

import java.util.ArrayList;

/**
 * Created by zc on 2018/6/1.
 */

public class OfferListFragment extends Fragment{
    View view;
    private ViewPager viewPager;
    private ImageView ivRed;
    private ImageView ivBlue;
    private ImageView ivCyan;
    private ImageView ivPurple;
    private ImageView ivYellow;
    private ImageView ivOrange;
    private TextView tvOffer_1;
    private TextView tvOffer_2;
    private TextView tvOffer_3;
    private TextView tvOffer_4;
    private TextView tvOffer_5;
    private TextView tvOffer_6;
    private ImageButton ivLeft;
    private ImageButton ivRight;

    private OfferBean offerBean;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_offer_list_view,null);
        init();
        return view;
    }
    private void init(){
        viewPager = view.findViewById(R.id.offer_vp);
        offerBean = JsonParse.returnOffer();
        viewPager.setAdapter(new VpAdapter());
        viewPager.setCurrentItem(Integer.MAX_VALUE/2 + (Integer.MAX_VALUE/2)%offerBean.links.size());
    }
    class VpAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position%offerBean.links.size();
            View v = View.inflate(getActivity(),R.layout.offer_list_viewpager_view,null);
            ivRed = v.findViewById(R.id.iv_offer_1);
            ivBlue = v.findViewById(R.id.iv_offer_2);
            ivCyan = v.findViewById(R.id.iv_offer_3);
            ivPurple = v.findViewById(R.id.iv_offer_4);
            ivYellow = v.findViewById(R.id.iv_offer_5);
            ivOrange = v.findViewById(R.id.iv_offer_6);
            tvOffer_1 = v.findViewById(R.id.tv_offer_1);
            tvOffer_2 = v.findViewById(R.id.tv_offer_2);
            tvOffer_3 = v.findViewById(R.id.tv_offer_3);
            tvOffer_4 = v.findViewById(R.id.tv_offer_4);
            tvOffer_5 = v.findViewById(R.id.tv_offer_5);
            tvOffer_6 = v.findViewById(R.id.tv_offer_6);
            ivLeft = v.findViewById(R.id.ib_offer_left);
            ivRight = v.findViewById(R.id.ib_offer_right);
            TextView tvTile = v.findViewById(R.id.offer_list_title);
            TextView tvReward = v.findViewById(R.id.offer_list_reward);
            tvTile.setText(offerBean.links.get(position).name);
            tvReward.setText(offerBean.links.get(position).reward);
            ArrayList<TextView> tvList = new ArrayList<>();
            tvList.add(tvOffer_1);
            tvList.add(tvOffer_2);
            tvList.add(tvOffer_3);
            tvList.add(tvOffer_4);
            tvList.add(tvOffer_5);
            tvList.add(tvOffer_6);
            tvOffer_1.setVisibility(View.GONE);
            tvOffer_2.setVisibility(View.GONE);
            tvOffer_3.setVisibility(View.GONE);
            tvOffer_4.setVisibility(View.GONE);
            tvOffer_5.setVisibility(View.GONE);
            tvOffer_6.setVisibility(View.GONE);
            ivRed.setBackgroundResource(R.drawable.offer_red_animation_list);
            ((AnimationDrawable) ivRed.getBackground()).start();
            ivBlue.setBackgroundResource(R.drawable.offer_blue_animation_list);
            ((AnimationDrawable) ivBlue.getBackground()).start();
            ivCyan.setBackgroundResource(R.drawable.offer_cyan_animation_list);
            ((AnimationDrawable) ivCyan.getBackground()).start();
            ivPurple.setBackgroundResource(R.drawable.offer_purple_animation_list);
            ((AnimationDrawable) ivPurple.getBackground()).start();
            ivYellow.setBackgroundResource(R.drawable.offer_yellow_animation_list);
            ((AnimationDrawable) ivYellow.getBackground()).start();
            ivOrange.setBackgroundResource(R.drawable.offer_orange_animation_list);
            ((AnimationDrawable) ivOrange.getBackground()).start();

            switch (offerBean.links.get(position).content.size()){
                case 3:
                    ivRed.setVisibility(View.VISIBLE);
                    ivBlue.setVisibility(View.VISIBLE);
                    ivCyan.setVisibility(View.VISIBLE);
                    ivPurple.setVisibility(View.GONE);
                    ivYellow.setVisibility(View.GONE);
                    ivOrange.setVisibility(View.GONE);
                    break;
                case 4:
                    ivRed.setVisibility(View.VISIBLE);
                    ivBlue.setVisibility(View.VISIBLE);
                    ivCyan.setVisibility(View.VISIBLE);
                    ivPurple.setVisibility(View.VISIBLE);
                    ivYellow.setVisibility(View.GONE);
                    ivOrange.setVisibility(View.GONE);
                    break;
                case 5:
                    ivRed.setVisibility(View.VISIBLE);
                    ivBlue.setVisibility(View.VISIBLE);
                    ivCyan.setVisibility(View.VISIBLE);
                    ivPurple.setVisibility(View.VISIBLE);
                    ivYellow.setVisibility(View.VISIBLE);
                    ivOrange.setVisibility(View.GONE);
                    break;
                case 6:
                    ivRed.setVisibility(View.VISIBLE);
                    ivBlue.setVisibility(View.VISIBLE);
                    ivCyan.setVisibility(View.VISIBLE);
                    ivPurple.setVisibility(View.VISIBLE);
                    ivYellow.setVisibility(View.VISIBLE);
                    ivOrange.setVisibility(View.VISIBLE);
                    break;
            }
            for(int k=0;k<offerBean.links.get(position).content.size();k++){
                String name = offerBean.links.get(position).content.get(k).name.replace("收集包","");
                if(name.contains("(")){
                    char[] names = name.toCharArray();
                    int num = 0;
                    for(char c:names){
                        if(c == '('){
                            break;
                        }
                        num++;
                    }
                    name = name.substring(0,num);
                }
                tvList.get(k).setText(name);
                if(name.length()>5){
                    tvList.get(k).setTextSize(10);
                }
                tvList.get(k).setVisibility(View.VISIBLE);
            }
            ivLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
                }
            });
            ivRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                }
            });
            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}

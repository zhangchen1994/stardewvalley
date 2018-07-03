package com.chen.stardewvalley.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chen.stardewvalley.view.FarmView1;
import com.chen.stardewvalley.view.FarmView2;
import com.chen.stardewvalley.view.FarmView3;
import com.chen.stardewvalley.view.FarmView4;
import com.chen.stardewvalley.view.FarmView5;
import com.chen.stardewvalley.view.FarmView6;
import com.chen.stardewvalley.view.FarmView7;
import com.chen.stardewvalley.view.FarmView8;
import com.chen.stardewvalley.view.FarmView9;

/**
 * Created by zc on 2018/6/24.
 */

public class FarmCardDetailsFragment extends Fragment {
    private int position;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        position = getArguments().getInt("i");
        View view = null;
        switch (position){
            case 0:
                FarmView1 view1 = new FarmView1(getActivity());
                view = view1.returnView();
                break;
            case 1:
                FarmView2 view2 = new FarmView2(getActivity());
                view = view2.returnView();
                break;
            case 2:
                FarmView3 view3 = new FarmView3(getActivity());
                view = view3.returnView();
                break;
            case 3:
                FarmView4 view4 = new FarmView4(getActivity());
                view = view4.returnView();
                break;
            case 4:
                FarmView5 view5 = new FarmView5(getActivity());
                view = view5.returnView();
                break;
            case 5:
                FarmView6 view6 = new FarmView6(getActivity());
                view = view6.returnView();
                break;
            case 6:
                FarmView7 view7 = new FarmView7(getActivity());
                view = view7.returnView();
                break;
            case 7:
                FarmView8 view8 = new FarmView8(getActivity());
                view = view8.returnView();
                break;
            case 8:
                FarmView9 view9 = new FarmView9(getActivity());
                view = view9.returnView();
                break;
        }
        return view;
    }
}

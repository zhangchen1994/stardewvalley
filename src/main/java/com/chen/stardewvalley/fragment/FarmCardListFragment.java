package com.chen.stardewvalley.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chen.stardewvalley.view.FarmCardView;

/**
 * Created by zc on 2018/6/24.
 */

public class FarmCardListFragment extends Fragment{
    private FarmCardView view;
    public FarmCardListListener listener;
    public static interface FarmCardListListener{
        public void farmListclick(int i);
    }
    public void setOnFarmCardListener(FarmCardListListener listener){
        this.listener = listener;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = new FarmCardView(getActivity());
        init();
        return view;
    }
    private void init(){
        view.setOnFarmCardListener(new FarmCardView.FarmCardListener() {
            @Override
            public void farmListclick(int i) {
                if (listener != null){
                    listener.farmListclick(i);
                }
            }
        });
    }
}

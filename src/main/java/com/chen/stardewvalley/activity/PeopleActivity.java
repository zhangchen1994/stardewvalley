package com.chen.stardewvalley.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.domain.NewDivBean;
import com.chen.stardewvalley.domain.PeopleBean;
import com.google.gson.Gson;
import com.yyydjk.library.DropDownMenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by zc on 2018/5/15.
 */

public class PeopleActivity extends AppCompatActivity{
    private DropDownMenu dropDownMenu;
    private ArrayList<String> headers;
    private int[] total = new int[]{R.string.total};
    private int[] marriage = new int[]{R.string.marriage_no,R.string.marriage_no};
    private int[] sex = new int[]{R.string.man,R.string.women};
    private int[] name = new int[]{R.string.total};
    private ArrayList<View> dropMenuList;
    private TextView contentView;
    private PeopleBean peopleBean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_people);

        dropDownMenu = findViewById(R.id.dropDownMenu);
        initDropDownMenu();
        dropDownMenu.setDropDownMenu(headers, dropMenuList, contentView);

        json();
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

        TextView tvTotal = new TextView(this);
        tvTotal.setText(R.string.total);

        TextView tvTotal2 = new TextView(this);
        tvTotal.setText(R.string.total);

        TextView tvTotal3 = new TextView(this);
        tvTotal.setText(R.string.total);

        TextView tvTotal4 = new TextView(this);
        tvTotal.setText(R.string.total);

        dropMenuList.add(tvTotal);
        dropMenuList.add(tvTotal2);
        dropMenuList.add(tvTotal3);
        dropMenuList.add(tvTotal4);

        contentView = new TextView(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setText("内容显示区域");
        contentView.setGravity(Gravity.CENTER);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
    }
}

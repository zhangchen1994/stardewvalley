package com.chen.stardewvalley.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.SeasonMenuPopupWindow;
import com.chen.stardewvalley.activity.SeasonActivity;
import com.chen.stardewvalley.db.SeasonDb;
import com.chen.stardewvalley.domain.CalendarBean;
import com.chen.stardewvalley.domain.PeopleBean;
import com.chen.stardewvalley.utils.JsonParse;
import com.chen.stardewvalley.utils.PeopleNameToEn;
import com.chen.stardewvalley.view.CalendarView;
import com.chen.stardewvalley.view.ExcelView;

import java.util.ArrayList;

/**
 * Created by zc on 2018/6/2.
 */

public class SeasonMainFragment extends Fragment {
    private int position;
    private View view;
    private PeopleBean peopleBean;
    private TextView tvContentTitle;
    private ListView lvContent;
    private TextView tvContentNoThings;
    private SeasonDb db;
    private int[] festivalTitle = new int[]{
            R.string.image,
            R.string.tools_name,
            R.string.calendar_day
    };
    private int[] birthdayTitle = new int[]{
            R.string.image,
            R.string.tools_name,
            R.string.like,
            R.string.calendar_day
    };
    private CalendarBean calendarBean;
    private CalendarView calendarView;
    private LvAdapter adapter;
    private ArrayList<String> thingsList = new ArrayList<>();
    private int selectCalendar = 0;
    private View viewThings;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.season_fragment_main, null);
        init();
        return view;
    }

    private void init() {
        position = getArguments().getInt("position");
        calendarView = view.findViewById(R.id.season_calendar);
        db = SeasonDb.getInstance(getActivity());

        tvContentTitle = view.findViewById(R.id.tv_season_content_title);
        lvContent = view.findViewById(R.id.lv_season_content);
        tvContentNoThings = view.findViewById(R.id.tv_season_content_no_things);

        calendarBean = JsonParse.returnCalendar();
        peopleBean = JsonParse.returnPeople();

        ArrayList<Integer> thingsList = new ArrayList<>();
        for (int i = 0; i < calendarBean.links.get(position).month.size(); i++) {

            String name = calendarBean.links.get(position).name
                    +calendarBean.links.get(position).month.get(i).day
                    +getContext().getString(R.string.day);
            if(db.find(name)){
                thingsList.add(i);
            }
        }

        calendarView.setThingsList(thingsList);
        calendarView.setCalendarTitle(calendarBean.links.get(position).name);
        calendarView.setDayList(calendarBean.links.get(position).month);
        tvContentTitle.setText(calendarBean.links.get(position).name);
        setCalendarViewListener();

        setListViewContent();

    }

    private void setListViewContent() {
        adapter = new LvAdapter();
        lvContent.setAdapter(adapter);

        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, int i, long l) {
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                View view1 = View.inflate(getActivity(), R.layout.dialog_season_view, null);
                ExcelView excelView = view1.findViewById(R.id.ex_dial_season);
                final EditText editText = view1.findViewById(R.id.ed_dial_season);
                setMyDialogView(i, excelView, editText);

                dialogBuilder.setTitle(R.string.things_details);
                dialogBuilder.setView(view1);

                final int i1 = i;
                if (excelView.getVisibility() == View.GONE) {
                    dialogBuilder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.delete(editText.getText().toString());
                            thingsList.remove(i1);
                            adapter.notifyDataSetChanged();

                            if (thingsList.size() == 0) {
                                ImageView view = viewThings.findViewById(R.id.iv_calendar_child);
                                if (calendarBean.links.get(position).month.get(selectCalendar).birthday == ""
                                        && calendarBean.links.get(position).month.get(selectCalendar).festival == "") {
                                    view.setBackgroundResource(0);
                                }
                            }
                        }
                    });
                }
                dialogBuilder.setPositiveButton(R.string.ok, null);


                dialogBuilder.show();
            }
        });
    }

    private void setMyDialogView(int i, ExcelView excelView, EditText editText) {
        if (calendarBean.links.get(position).month.get(selectCalendar).birthday == ""
                && calendarBean.links.get(position).month.get(selectCalendar).festival == "") {
            editText.setVisibility(View.VISIBLE);
            excelView.setVisibility(View.GONE);
            if(i != Integer.MAX_VALUE){
                editText.setText(thingsList.get(i));
            }
        } else if (calendarBean.links.get(position).month.get(selectCalendar).birthday != "") {
            if (i == 0) {
                editText.setVisibility(View.GONE);
                excelView.setVisibility(View.VISIBLE);

                excelView.setTitleList(birthdayTitle);
                excelView.setWeigthList(new int[]{1, 1, 2, 1});
                excelView.setLayouttTitle();
                ArrayList<String> list1 = new ArrayList<>();
                ArrayList<String> list2 = new ArrayList<>();
                ArrayList<String> list3 = new ArrayList<>();
                ArrayList<String> list4 = new ArrayList<>();

                String name = thingsList.get(i).replace(getString(R.string.birthday), "");
                int num = getListPosition(name);
                list1.add(PeopleNameToEn.getNameEn(peopleBean.links.get(num).name) + "_icon");
                list2.add(name);
                StringBuffer stringBuffer = new StringBuffer();
                for (int k = 0; k < peopleBean.links.get(num).like.size(); k++) {
                    if (k != peopleBean.links.get(num).like.size() - 1) {
                        stringBuffer.append(peopleBean.links.get(num).like.get(k).name + "\n");
                    } else {
                        stringBuffer.append(peopleBean.links.get(num).like.get(k).name);
                    }
                }
                list3.add(stringBuffer.toString());
                list4.add(tvContentTitle.getText().toString());

                ArrayList<ArrayList<String>> lists = new ArrayList<>();
                lists.add(list1);
                lists.add(list2);
                lists.add(list3);
                lists.add(list4);

                excelView.setDataList(lists);
                excelView.setAdapter();
            } else {
                editText.setVisibility(View.VISIBLE);
                excelView.setVisibility(View.GONE);

                if(i != Integer.MAX_VALUE){
                    editText.setText(thingsList.get(i));
                }
            }
        } else if (calendarBean.links.get(position).month.get(selectCalendar).festival != "") {
            if (i == 0) {
                editText.setVisibility(View.GONE);
                excelView.setVisibility(View.VISIBLE);

                excelView.setTitleList(festivalTitle);
                excelView.setWeigthList(new int[]{1, 2, 2});
                excelView.setLayouttTitle();
                ArrayList<String> list1 = new ArrayList<>();
                ArrayList<String> list2 = new ArrayList<>();
                ArrayList<String> list3 = new ArrayList<>();
                int num = 0;
                for (int k = 0; k < calendarBean.links.get(position).festival.size(); k++) {
                    if (thingsList.get(i).equals(calendarBean.links.get(position).festival.get(k).name)) {
                        break;
                    }
                    num++;
                }
                list1.add(calendarBean.links.get(position).festival.get(num).images);
                list2.add(calendarBean.links.get(position).festival.get(num).name);
                list3.add(calendarBean.links.get(position).festival.get(num).data);

                ArrayList<ArrayList<String>> lists = new ArrayList<>();

                lists.add(list1);
                lists.add(list2);
                lists.add(list3);

                excelView.setDataList(lists);
                excelView.setAdapter();
            } else {
                editText.setVisibility(View.GONE);
                excelView.setVisibility(View.VISIBLE);

                if(i != Integer.MAX_VALUE){
                    editText.setText(thingsList.get(i));
                }
            }
        }
    }

    public void setAddDialog() {
        if (!tvContentTitle.getText().toString().contains(getString(R.string.day))) {
            Toast.makeText(getActivity(), R.string.season_tishi,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        View view1 = View.inflate(getActivity(), R.layout.dialog_season_view, null);
        ExcelView excelView = view1.findViewById(R.id.ex_dial_season);
        final EditText editText = view1.findViewById(R.id.ed_dial_season);
        setMyDialogView(Integer.MAX_VALUE, excelView, editText);

        dialogBuilder.setTitle(R.string.things_details);
        dialogBuilder.setView(view1);

        dialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!TextUtils.isEmpty(editText.getText())) {
                    db.insert(tvContentTitle.getText().toString(), editText.getText().toString());
                    ImageView view = viewThings.findViewById(R.id.iv_calendar_child);
                    if (calendarBean.links.get(position).month.get(selectCalendar).birthday == ""
                            && calendarBean.links.get(position).month.get(selectCalendar).festival == "") {
                        view.setBackgroundResource(R.drawable.season_things_icon);
                    }
                    thingsList.add(editText.getText().toString());
                    adapter.notifyDataSetChanged();
                    tvContentNoThings.setVisibility(View.GONE);
                }
            }
        });

        dialogBuilder.show();
    }

    private void setCalendarViewListener() {
        calendarView.setOnCalendarViewClickListener(new CalendarView.OnCalendarViewClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectCalendar = i;
                tvContentTitle.setText(calendarBean.links.get(position).name +
                        calendarBean.links.get(position).month.get(i).day
                        + getString(R.string.day));
                viewThings = view;
                thingsList.clear();
                if (calendarBean.links.get(position).month.get(i).festival == "") {

                } else {
                    thingsList.add(calendarBean.links.get(position).month.get(i).festival);
                }

                if (calendarBean.links.get(position).month.get(i).birthday == "") {

                } else {
                    thingsList.add(calendarBean.links.get(position).month.get(i).birthday
                            + getString(R.string.birthday));
                }
                if (db.find(tvContentTitle.getText().toString())) {
                    for (String s : db.findThings(tvContentTitle.getText().toString())) {
                        thingsList.add(s);
                    }
                }
                adapter.notifyDataSetChanged();
                if (thingsList.size() == 0) {
                    tvContentNoThings.setVisibility(View.VISIBLE);
                } else {
                    tvContentNoThings.setVisibility(View.GONE);
                }
            }
        });
    }

    class LvAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return thingsList.size();
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
            if (view == null) {
                view = View.inflate(getActivity(), R.layout.listview_season_content, null);
                viewHolder = new ViewHolder();
                viewHolder.textView1 = view.findViewById(R.id.tv_lv_season_1);
                viewHolder.textView2 = view.findViewById(R.id.tv_lv_season_2);

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.textView1.setText(getString(R.string.things) + String.valueOf(i + 1));
            viewHolder.textView2.setText(thingsList.get(i));

            return view;
        }
    }

    class ViewHolder {
        TextView textView1;
        TextView textView2;
    }

    private void setExcelView(ExcelView excelView) {
//        int id = excelView.getId();
//        switch (id){
//            case R.id.season_excel_view_1:
//                excelView.setTitleList(festivalTitle);
//                excelView.setWeigthList(new int[]{1,2,2});
//                excelView.setLayouttTitle();
//                ArrayList<String> list1 = new ArrayList<>();
//                ArrayList<String> list2 = new ArrayList<>();
//                ArrayList<String> list3 = new ArrayList<>();
//                for(int i2=0;i2<calendarBean.links.get(position).festival.size();i2++){
//                    list1.add(calendarBean.links.get(position).festival.get(i2).images);
//                    list2.add(calendarBean.links.get(position).festival.get(i2).name);
//                    list3.add(calendarBean.links.get(position).festival.get(i2).data);
//                }
//                ArrayList<ArrayList<String>> lists = new ArrayList<>();
//                lists.add(list1);
//                lists.add(list2);
//                lists.add(list3);
//
//                excelView.setDataList(lists);
//                excelView.setAdapter();
//                excelView.setListHeigh();
//                break;
//            case R.id.season_excel_view_2:
//                excelView.setTitleList(birthdayTitle);
//                excelView.setWeigthList(new int[]{1,1,2,1});
//                excelView.setLayouttTitle();
//                ArrayList<String> list2_1 = new ArrayList<>();
//                ArrayList<String> list2_2 = new ArrayList<>();
//                ArrayList<String> list2_3 = new ArrayList<>();
//                ArrayList<String> list2_4 = new ArrayList<>();
//                for(int i3=0;i3<calendarBean.links.get(position).birthday.size();i3++){
//                    int num = getListPosition(calendarBean.links.get(position).birthday.get(i3).name);
//
//                    list2_1.add(PeopleNameToEn.getNameEn(peopleBean.links.get(num).name)
//                    +"_icon");
//                    list2_2.add(peopleBean.links.get(num).name);
//                    StringBuffer stringBuffer = new StringBuffer();
//                    for(int k=0;k<peopleBean.links.get(num).like.size();k++){
//                        if(k != peopleBean.links.get(num).like.size()-1){
//                            stringBuffer.append(peopleBean.links.get(num).like.get(k).name+";");
//                        }else{
//                            stringBuffer.append(peopleBean.links.get(num).like.get(k).name);
//                        }
//                    }
//                    list2_3.add(stringBuffer.toString());
//                    list2_4.add(calendarBean.links.get(position).birthday.get(i3).data);
//                }
//                ArrayList<ArrayList<String>> lists2 = new ArrayList<>();
//                lists2.add(list2_1);
//                lists2.add(list2_2);
//                lists2.add(list2_3);
//                lists2.add(list2_4);
//
//                excelView.setDataList(lists2);
//                excelView.setAdapter();
//                excelView.setListHeigh();
//                break;
//        }

    }

    private int getListPosition(String name) {
        int num = 0;
        for (PeopleBean.PeoPle peoPle : peopleBean.links) {
            if (peoPle.name.equals(name)) {
                break;
            }
            num++;
        }
        return num;
    }
}

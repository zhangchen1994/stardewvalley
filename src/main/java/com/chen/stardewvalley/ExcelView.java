package com.chen.stardewvalley;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by zc on 2018/5/22.
 */

public class ExcelView extends ListView{
    public ExcelView(Context context) {
        super(context);
        init();
    }

    public ExcelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExcelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ExcelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    private void init(){
        setAdapter(new MyAdapter());
    }
    private void setAdapter(){
        setAdapter(new MyAdapter());
    }
    class MyAdapter extends BaseAdapter{

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
            ViewHolder viewHolder;
            if(view == null){
                viewHolder = new ViewHolder();
                view = View.inflate(getContext(),R.layout.excel_list_view,null);
                viewHolder.iv_title = view.findViewById(R.id.iv_excel_title);
                viewHolder.tv_0 = view.findViewById(R.id.tv_excel_0);
                viewHolder.tv_1 = view.findViewById(R.id.tv_excel_1);
                viewHolder.tv_2 = view.findViewById(R.id.tv_excel_2);
                viewHolder.tv_3 = view.findViewById(R.id.tv_excel_3);
                viewHolder.tv_4 = view.findViewById(R.id.tv_excel_4);
                viewHolder.tv_5 = view.findViewById(R.id.tv_excel_5);

                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }
            if(i == 0){
                viewHolder.iv_title.setVisibility(View.GONE);
                viewHolder.tv_0.setVisibility(View.VISIBLE);
                viewHolder.tv_1.setVisibility(View.VISIBLE);
                viewHolder.tv_2.setVisibility(View.VISIBLE);
                viewHolder.tv_3.setVisibility(View.VISIBLE);
                viewHolder.tv_4.setVisibility(View.VISIBLE);
                viewHolder.tv_5.setVisibility(View.VISIBLE);

                //view.setBackgroundColor(000000);
                viewHolder.tv_0.setText("图片");
                viewHolder.tv_1.setText("名称");
                viewHolder.tv_2.setText("花费");
                viewHolder.tv_3.setText("提升");
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)
                        viewHolder.tv_3.getLayoutParams();
                layoutParams.weight = 2;
                viewHolder.tv_3.setLayoutParams(layoutParams);
                viewHolder.tv_4.setText("位置");
                viewHolder.tv_5.setText("需求");

            }else{

            }
            return view;
        }
    }
    class ViewHolder{
        ImageView iv_title;
        TextView tv_0;
        TextView tv_1;
        TextView tv_2;
        TextView tv_3;
        TextView tv_4;
        TextView tv_5;

    }
}

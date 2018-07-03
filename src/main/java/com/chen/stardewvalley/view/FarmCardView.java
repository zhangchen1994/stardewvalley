package com.chen.stardewvalley.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chen.stardewvalley.R;
import com.chen.stardewvalley.domain.FarmBean;
import com.chen.stardewvalley.utils.DisplayUtils;
import com.chen.stardewvalley.utils.FarmCardAnimation;
import com.chen.stardewvalley.utils.GetImageIdByName;
import com.chen.stardewvalley.utils.JsonParse;
import com.chen.stardewvalley.utils.Rotate3dAnimation;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zc on 2018/6/9.
 */

public class FarmCardView extends RelativeLayout {
    private View view;
    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;
    private CardView cardView4;
    private CardView cardView5;
    private CardView cardView6;
    private CardView cardView7;
    private CardView cardView8;
    private CardView cardView9;
    public FarmCardListener listener;
    public static interface FarmCardListener{
        public void farmListclick(int i);
    }
    public void setOnFarmCardListener(FarmCardListener listener){
        this.listener = listener;
    }
    private ArrayList<CardView> cardList = new ArrayList<>();
    private int[] mainNums = new int[9];
    private Timer timer = new Timer();
    private FarmBean farmBean;
    private TimerTask timerTask;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int viewNum = getRandom(5) + 1;
            ArrayList<CardView> list = new ArrayList<>();
            ArrayList<Integer> numList = new ArrayList<>();
            for (int i = 0; i < viewNum; i++) {
                int listNum;
                while (true) {
                    listNum = getRandom(cardList.size());
                    if (numList.size() == 0) {
                        break;
                    }
                    boolean isHave = false;
                    for (int j : numList) {
                        if (j == listNum) {
                            isHave = true;
                            break;
                        }
                    }
                    if (!isHave) {
                        break;
                    }
                }
                numList.add(listNum);
                list.add(cardList.get(listNum));
            }
            for (int i=0;i<list.size();i++) {
                CardView view = list.get(i);
                int animation = getRandom(4);
                switch (animation){
                    case 0:
                        setMyRotaAnmition(view,numList.get(i));
                        break;
                    case 1:
                        setMyTranslation(view,numList.get(i));
                        break;
                    case 2:
                        setAlphaAnimation(view,numList.get(i));
                        break;
                    case 3:
                        setMyScaleAnimation(view,numList.get(i));
                        break;
                }
            }
        }
    };
    private int[] cardColors = new int[]{
            Color.parseColor("#ff98d1e1"),
            Color.parseColor("#ff7cdebd"),
            Color.parseColor("#FF51D169"),
            Color.parseColor("#FFC4D94E"),
            Color.parseColor("#FFCC79D6"),
            Color.parseColor("#ffd47996"),
            Color.parseColor("#FF7E84C1"),
    };

    public FarmCardView(Context context) {
        super(context);
        init();
    }

    public FarmCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FarmCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public FarmCardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        view = View.inflate(getContext(), R.layout.farm_card_view, null);
        addView(view);
        farmBean = JsonParse.farmBean;
        cardView1 = view.findViewById(R.id.card_1);
        cardView2 = view.findViewById(R.id.card_2);
        cardView3 = view.findViewById(R.id.card_3);
        cardView4 = view.findViewById(R.id.card_4);
        cardView5 = view.findViewById(R.id.card_5);
        cardView6 = view.findViewById(R.id.card_6);
        cardView7 = view.findViewById(R.id.card_7);
        cardView8 = view.findViewById(R.id.card_8);
        cardView9 = view.findViewById(R.id.card_9);
        cardList.add(cardView1);
        cardList.add(cardView2);
        cardList.add(cardView3);
        cardList.add(cardView4);
        cardList.add(cardView5);
        cardList.add(cardView6);
        cardList.add(cardView7);
        cardList.add(cardView8);
        cardList.add(cardView9);
        for (int i = 0; i < cardList.size(); i++) {
            CardView cardView = cardList.get(i);
            View view1 = View.inflate(getContext(), R.layout.farm_card_view_child_view, null);
            View view2 = View.inflate(getContext(), R.layout.farm_card_view_child_view, null);
            TextView textView1 = view1.findViewById(R.id.tv_name1);
            TextView textView2 = view1.findViewById(R.id.tv_name2);
            TextView textView3 = view1.findViewById(R.id.tv_name3);
            if(i != 2&&i != 6){
                ImageView imageView = view1.findViewById(R.id.iv_content);
                textView1.setText(farmBean.main.get(i).name);
                int num = getRandom(farmBean.main.get(i).images.size());
                mainNums[i] = num;
                textView2.setText(farmBean.main.get(i).images.get(num).name);
                imageView.setImageResource(GetImageIdByName.getImageId(
                        farmBean.main.get(i).images.get(num).image, getContext()
                ));
                textView3.setVisibility(GONE);
            }else{
                ImageView imageView = view1.findViewById(R.id.iv_content);
                textView3.setText(farmBean.main.get(i).name);
                int num = getRandom(farmBean.main.get(i).images.size());
                mainNums[i] = num;
                textView1.setText(farmBean.main.get(i).images.get(num).name);
                imageView.setImageResource(GetImageIdByName.getImageId(
                        farmBean.main.get(i).images.get(num).image, getContext()
                ));
                textView2.setVisibility(GONE);
            }
            view2.setVisibility(GONE);
            cardView.addView(view2);
            cardView.addView(view1);
        }
        setMyLayout();
        setColor();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };
        timer.schedule(timerTask, 3000, 5000);

        initFarmCardClick();
    }
    private void initFarmCardClick(){
        for(int i=0;i<cardList.size();i++){
            final int finalI = i;
            cardList.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        listener.farmListclick(finalI);
                    }
                }
            });
        }
    }

    private void setMyRotaAnmition(final CardView v, final int num) {
        post(new Runnable() {
            @Override
            public void run() {
                FarmCardAnimation farmCardAnimation = new FarmCardAnimation();
                farmCardAnimation.setAnimView(v, v.getWidth() / 2,
                        v.getHeight() / 2);
                farmCardAnimation.setonAnimationEnd(new FarmCardAnimation.Rotate3dListener() {
                    @Override
                    public void onAnimationEnd(int x, int y, int z) {
                        RelativeLayout relativeLayout;
                        if (v.getChildAt(0).getVisibility() == GONE) {
                            relativeLayout = (RelativeLayout) v.getChildAt(1);
                        } else {
                            relativeLayout = (RelativeLayout) v.getChildAt(0);
                        }
                        setCardView(num, relativeLayout);
                        relativeLayout.setBackgroundColor(cardColors[getRandom(cardColors.length)]);
                        Rotate3dAnimation rotateAnimation = new Rotate3dAnimation(90, 0,
                                v.getWidth() / 2,
                                v.getHeight() / 2, z, false);
                        rotateAnimation.setDuration(400);
                        rotateAnimation.setFillAfter(true);
                        rotateAnimation.setInterpolator(new DecelerateInterpolator());
                        v.startAnimation(rotateAnimation);
                    }
                });
            }
        });
    }

    private void setMyTranslation(final CardView view, final int num) {
        View view1 = view.getChildAt(1);
        View view2 = view.getChildAt(0);
        FarmCardAnimation farmCardAnimation = new FarmCardAnimation();
        final int color = cardColors[getRandom(cardColors.length)];
        if (view1.getVisibility() == GONE) {
            view1 = view.getChildAt(1);
            view2 = view.getChildAt(0);
            view.removeAllViews();
            view.addView(view1);
            view.addView(view2);
            final View finalView = view1;
            final View finalView1 = view2;
            farmCardAnimation.setTranslationListener(new FarmCardAnimation.TranslationListener() {
                @Override
                public void onAnimationStart(TranslateAnimation animation) {
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            setCardView(num, finalView);
                            finalView.setVisibility(VISIBLE);
                            finalView.setBackgroundColor(color);
                            TranslateAnimation translateAnimation = new TranslateAnimation(
                                    Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0,
                                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0
                            );
                            translateAnimation.setDuration(400);
                            finalView.setAnimation(translateAnimation);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            finalView1.setVisibility(GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            });
            farmCardAnimation.setTranslation(view2);
        } else {
            final View finalView2 = view2;
            final View finalView3 = view1;
            farmCardAnimation.setTranslationListener(new FarmCardAnimation.TranslationListener() {
                @Override
                public void onAnimationStart(TranslateAnimation animation) {
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            setCardView(num, finalView2);
                            finalView2.setVisibility(VISIBLE);
                            finalView2.setBackgroundColor(color);
                            TranslateAnimation translateAnimation = new TranslateAnimation(
                                    Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0,
                                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0
                            );
                            translateAnimation.setDuration(400);
                            finalView2.setAnimation(translateAnimation);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            finalView3.setVisibility(GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            });
            farmCardAnimation.setTranslation(view1);
        }
    }

    private void setAlphaAnimation(CardView view, final int num) {
        View view1 = view.getChildAt(1);
        View view2 = view.getChildAt(0);
        FarmCardAnimation farmCardAnimation = new FarmCardAnimation();
        if (view1.getVisibility() == GONE) {
            view1 = view.getChildAt(1);
            view2 = view.getChildAt(0);
            view.removeAllViews();
            view.addView(view1);
            view.addView(view2);
            final View finalView = view1;
            final View finalView1 = view2;
            farmCardAnimation.setAlphaListener(new FarmCardAnimation.AlphaListener() {
                @Override
                public void onAnimationStart(AlphaAnimation animation) {
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            setCardView(num, finalView);
                            finalView.setVisibility(VISIBLE);
                            finalView.setBackgroundColor(cardColors[getRandom(cardColors.length)]);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            finalView1.setVisibility(GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            });
            farmCardAnimation.setAlphaAnimation(view2);
        } else {
            final View finalView2 = view2;
            final View finalView3 = view1;
            farmCardAnimation.setAlphaListener(new FarmCardAnimation.AlphaListener() {
                @Override
                public void onAnimationStart(AlphaAnimation animation) {
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            setCardView(num, finalView2);
                            finalView2.setVisibility(VISIBLE);
                            finalView2.setBackgroundColor(cardColors[getRandom(cardColors.length)]);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            finalView3.setVisibility(GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            });
            farmCardAnimation.setAlphaAnimation(view1);
        }
    }

    private void setMyScaleAnimation(CardView view, final int num) {
        View view1 = view.getChildAt(1);
        View view2 = view.getChildAt(0);
        FarmCardAnimation farmCardAnimation = new FarmCardAnimation();
        if (view1.getVisibility() == GONE) {
            view1 = view.getChildAt(1);
            view2 = view.getChildAt(0);
            view.removeAllViews();
            view.addView(view1);
            view.addView(view2);
            final View finalView = view1;
            final View finalView1 = view2;
            farmCardAnimation.setScaleListener(new FarmCardAnimation.ScaleListener() {
                @Override
                public void onAnimationStart(ScaleAnimation animation) {
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            setCardView(num, finalView);
                            finalView.setVisibility(VISIBLE);
                            finalView.setBackgroundColor(cardColors[getRandom(cardColors.length)]);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            finalView1.setVisibility(GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            });
            farmCardAnimation.setScaleAnimation(view2);
        } else {
            final View finalView2 = view2;
            final View finalView3 = view1;
            farmCardAnimation.setScaleListener(new FarmCardAnimation.ScaleListener() {
                @Override
                public void onAnimationStart(ScaleAnimation animation) {
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            setCardView(num, finalView2);
                            finalView2.setVisibility(VISIBLE);
                            finalView2.setBackgroundColor(cardColors[getRandom(cardColors.length)]);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            finalView3.setVisibility(GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            });
            farmCardAnimation.setScaleAnimation(view1);
        }
    }

    private void setColor() {
        cardView1.setCardBackgroundColor(cardColors[getRandom(cardColors.length)]);
        for (CardView cardView : cardList) {
            RelativeLayout relativeLayout = (RelativeLayout) cardView.getChildAt(1);
            relativeLayout.setBackgroundColor(cardColors[getRandom(cardColors.length)]);
        }
    }

    private void setCardView(int num, View view) {

        TextView textView0 = view.findViewById(R.id.tv_name1);
        TextView textView = view.findViewById(R.id.tv_name2);
        TextView textView1 = view.findViewById(R.id.tv_name3);
        ImageView imageView = view.findViewById(R.id.iv_content);
        if(num != 2&&num != 6){
            textView0.setText(farmBean.main.get(num).name);
            int order_number = mainNums[num] + 1;
            mainNums[num] = order_number;
            if (order_number == farmBean.main.get(num).images.size()) {
                order_number = 0;
                mainNums[num] = 0;
            }
            textView.setText(farmBean.main.get(num).images.get(order_number).name);
            imageView.setImageResource(
                    GetImageIdByName.getImageId(farmBean.main.get(num).images.get(order_number)
                            .image, getContext())
            );
            textView1.setVisibility(GONE);
        }else{
            textView1.setText(farmBean.main.get(num).name);
            int order_number = mainNums[num] + 1;
            mainNums[num] = order_number;
            if (order_number == farmBean.main.get(num).images.size()) {
                order_number = 0;
                mainNums[num] = 0;
            }
            textView0.setText(farmBean.main.get(num).images.get(order_number).name);
            imageView.setImageResource(
                    GetImageIdByName.getImageId(farmBean.main.get(num).images.get(order_number)
                            .image, getContext())
            );
            textView.setVisibility(GONE);
        }


    }

    private void setMyLayout() {
        int wNum = 3;
        //
        RelativeLayout.LayoutParams params1 = (LayoutParams) cardView1.getLayoutParams();
        params1.width = ((getAndroiodScreenPropertyWidth() -
                DisplayUtils.dp2px(getContext(), 26)) / 3) * 2;
        params1.height = ((getAndroiodScreenPropertyWidth() -
                DisplayUtils.dp2px(getContext(), 26)) / 3) * 2;
        //
        RelativeLayout.LayoutParams params2 = (LayoutParams) cardView2.getLayoutParams();
        params2.width = ((getAndroiodScreenPropertyWidth() -
                DisplayUtils.dp2px(getContext(), 26)) / 3);
        params2.height = ((getAndroiodScreenPropertyWidth() -
                DisplayUtils.dp2px(getContext(), 26)) / 3) * 3;
        //
        RelativeLayout.LayoutParams params3 = (LayoutParams) cardView3.getLayoutParams();
        params3.width = ((((getAndroiodScreenPropertyWidth() -
                DisplayUtils.dp2px(getContext(), 26)) / 3) * 2) -
                DisplayUtils.dp2px(getContext(), 6)) / 2;
        params3.height = ((((getAndroiodScreenPropertyWidth() -
                DisplayUtils.dp2px(getContext(), 26)) / 3) * 2) -
                DisplayUtils.dp2px(getContext(), 6));
        //
        RelativeLayout.LayoutParams params4 = (LayoutParams) cardView4.getLayoutParams();
        params4.width = ((((getAndroiodScreenPropertyWidth() -
                DisplayUtils.dp2px(getContext(), 26)) / 3) * 2) -
                DisplayUtils.dp2px(getContext(), 6)) / 2;
        params4.height = ((getAndroiodScreenPropertyWidth() -
                DisplayUtils.dp2px(getContext(), 26)) / 3) -
                DisplayUtils.dp2px(getContext(), 6);
        //
        RelativeLayout.LayoutParams params5 = (LayoutParams) cardView5.getLayoutParams();
        params5.width = (getAndroiodScreenPropertyWidth() -
                DisplayUtils.dp2px(getContext(), 26)) -
                ((((getAndroiodScreenPropertyWidth() -
                        DisplayUtils.dp2px(getContext(), 26)) / 3) * 2) -
                        DisplayUtils.dp2px(getContext(), 6)) / 2;
        params5.height = ((getAndroiodScreenPropertyWidth() -
                DisplayUtils.dp2px(getContext(), 26)) / 3) * 2 +
                (((getAndroiodScreenPropertyWidth() -
                        DisplayUtils.dp2px(getContext(), 26)) / 3) * 2) -
                ((getAndroiodScreenPropertyWidth() -
                        DisplayUtils.dp2px(getContext(), 26)) / 3) * 3 -
                DisplayUtils.dp2px(getContext(), 6);
        //
        RelativeLayout.LayoutParams params6 = (LayoutParams) cardView6.getLayoutParams();
        params6.width = ((getAndroiodScreenPropertyWidth() -
                DisplayUtils.dp2px(getContext(), 26)) / 3) * 2;
        params6.height = ((getAndroiodScreenPropertyWidth() -
                DisplayUtils.dp2px(getContext(), 26)) / 3);
        //
        RelativeLayout.LayoutParams params7 = (LayoutParams) cardView7.getLayoutParams();
        params7.width = ((getAndroiodScreenPropertyWidth() -
                DisplayUtils.dp2px(getContext(), 26)) / 3);
        params7.height = ((getAndroiodScreenPropertyWidth() -
                DisplayUtils.dp2px(getContext(), 26)) / 3) * 2;
        //
        RelativeLayout.LayoutParams params8 = (LayoutParams) cardView8.getLayoutParams();
        params8.width = ((((getAndroiodScreenPropertyWidth() -
                DisplayUtils.dp2px(getContext(), 26)) / 3) * 2) -
                DisplayUtils.dp2px(getContext(), 6)) / 2;
        params8.height = ((getAndroiodScreenPropertyWidth() -
                DisplayUtils.dp2px(getContext(), 26)) / 3) -
                DisplayUtils.dp2px(getContext(), 6);
        //
        RelativeLayout.LayoutParams params9 = (LayoutParams) cardView9.getLayoutParams();
        params9.width = ((((getAndroiodScreenPropertyWidth() -
                DisplayUtils.dp2px(getContext(), 26)) / 3) * 2) -
                DisplayUtils.dp2px(getContext(), 6)) / 2;
        params9.height = ((getAndroiodScreenPropertyWidth() -
                DisplayUtils.dp2px(getContext(), 26)) / 3) -
                DisplayUtils.dp2px(getContext(), 6);
    }

    public int getAndroiodScreenPropertyWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）

        return width;
    }

    public int getAndroiodScreenPropertyeight() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）

        return height;
    }

    public int toolsBarHeight() {
        int ActionBarHeight = ((AppCompatActivity) getContext()).getSupportActionBar().getHeight();

        return ActionBarHeight;
    }

    private int getRandom(int num) {
        Random random = new Random();
        return random.nextInt(num);
    }
}

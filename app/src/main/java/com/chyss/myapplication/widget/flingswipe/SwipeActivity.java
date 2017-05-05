package com.chyss.myapplication.widget.flingswipe;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.chyss.myapplication.R;
import com.chyss.myapplication.utils.Logg;
import com.chyss.myapplication.utils.UnitUtils;
import com.chyss.myapplication.widget.flingswipe.swipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class SwipeActivity extends Activity {

    private List<String> al;
//    private ArrayAdapter<String> arrayAdapter;
    private SwipeAdapter swipeAdapter;
//    private int i;

    @InjectView(R.id.frame)
    SwipeFlingAdapterView flingContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flingswipe_activity);
        ButterKnife.inject(this);


        al = new ArrayList<>();
//        al.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1249988107,620775065&fm=23&gp=0.jpg");
        al.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=125580807,845707749&fm=23&gp=0.jpg");
        al.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1887792679,709769868&fm=23&gp=0.jpg");
        al.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4180958988,1444655516&fm=23&gp=0.jpg");
        al.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=34697706,3952588914&fm=23&gp=0.jpg");
        al.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2711774624,662743464&fm=23&gp=0.jpg");

//        arrayAdapter = new ArrayAdapter<>(this, R.layout.flingswipe_item_img, R.id.swip_img, al );
//        flingContainer.setAdapter(arrayAdapter);

        swipeAdapter = new SwipeAdapter(this,al);
        flingContainer.setAdapter(swipeAdapter);
        //由于在SwipeAdapter中.inflate(R.layout.flingswipe_item_img, null) root为null，导致child.getLayoutParams()返回为null
        //如果能正常获取到LayoutParams()，则不需要手动设置
        flingContainer.setParams(new FrameLayout.LayoutParams(UnitUtils.dp2px(this,250),UnitUtils.dp2px(this,170), Gravity.CENTER));

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                al.remove(0);
                swipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                makeToast(SwipeActivity.this, "Left!");
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                makeToast(SwipeActivity.this, "Right!");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                al.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=125580807,845707749&fm=23&gp=0.jpg");
                al.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1887792679,709769868&fm=23&gp=0.jpg");
                al.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4180958988,1444655516&fm=23&gp=0.jpg");
                swipeAdapter.notifyDataSetChanged();
                Logg.e("aboutto","---------------------------------");
//                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                makeToast(SwipeActivity.this, "Clicked!--------"+itemPosition);
            }
        });

    }

    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.right)
    public void right() {
        /**
         * Trigger the right event manually.
         */
        flingContainer.getTopCardListener().selectRight();
    }

    @OnClick(R.id.left)
    public void left() {
        flingContainer.getTopCardListener().selectLeft();
    }




}

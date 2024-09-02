package com.rzc.isibox.presentation.component.slider;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.rzc.isibox.R;
import com.rzc.isibox.master.MyView;

import java.util.ArrayList;
import java.util.List;

public class ImageSliderView extends MyView {

    private PagerAdapter adapter;
    private LinearLayout ln_node;
    private ViewPager pager_image;

    public ImageSliderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int setLayout() {
        return R.layout.component_view_imageslider;
    }

    @Override
    protected void initLayout() {
        pager_image = findViewById(R.id.pager_image);
        ln_node = findViewById(R.id.ln_node);
    }

    @Override
    protected void initListener() {
        pager_image.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i=0; i<ln_node.getChildCount(); i++){
                    View view = ln_node.getChildAt(i);
                    if (position == i){
                        view.setBackgroundResource(R.drawable.node2_selected);
                    }
                    else {
                        view.setBackgroundResource(R.drawable.node2_unselect);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void create(FragmentManager fragmentManager, ArrayList<ImageModel> imageModels) {
        super.create();

        int index = 0;
        adapter = new PagerAdapter(fragmentManager);
        for (ImageModel model : imageModels){
            adapter.addFragment(model);
            ln_node.addView(addNode(index == 0));
            index ++;
        }
        pager_image.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private View addNode(boolean selected){
        View view = new View(mActivity);

        view.setBackgroundResource(selected ?  R.drawable.node2_selected : R.drawable.node2_unselect);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(25, 25);
        lp.rightMargin = 8;
        lp.leftMargin = 8;
        view.setLayoutParams(lp);
        return view;
    }


    static class PagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();

        public PagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(ImageModel model) {
            ImageFragment fragment = ImageFragment.newInstance(model);
            fragmentList.add(fragment);
        }
    }


}

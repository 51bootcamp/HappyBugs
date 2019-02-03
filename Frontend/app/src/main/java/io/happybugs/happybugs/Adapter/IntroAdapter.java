package io.happybugs.happybugs.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.happybugs.happybugs.R;

public class IntroAdapter extends PagerAdapter {
    private Context cur;
    private LayoutInflater layoutInflater;
    private int[] slideLayouts = {
            R.layout.intro_slide_1,
            R.layout.intro_slide_2,
            R.layout.intro_slide_3
    };

    public IntroAdapter(Context context) {
        this.cur = context;
    }

    @Override
    public int getCount() {
        return slideLayouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
        return view == obj;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) cur.getSystemService(cur.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(slideLayouts[position], container, false);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}

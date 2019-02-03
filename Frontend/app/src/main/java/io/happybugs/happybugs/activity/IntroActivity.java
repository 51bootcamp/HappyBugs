package io.happybugs.happybugs.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.happybugs.happybugs.Adapter.IntroAdapter;
import io.happybugs.happybugs.R;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private Button btnNext;
    private Button btnSkip;
    private IntroAdapter introAdapter;
    private TextView[] slideDots;
    private PrefManager prefManager;
    private int currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstLaunch()) {
            launchMainScreen();
        }

        setContentView(R.layout.activity_intro);
        transparentStatusBar();

        viewPager = findViewById(R.id.viewPager_slide);
        dotsLayout = findViewById(R.id.dots_layout);
        btnNext = findViewById(R.id.btn_next);
        btnSkip = findViewById(R.id.btn_skip);

        btnSkip.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        introAdapter = new IntroAdapter(this);
        viewPager.setAdapter(introAdapter);
        viewPager.addOnPageChangeListener(pageChangeListener);

        addDotsIndicator(0);
    }

    private void launchMainScreen() {
        prefManager.setIsFirstLaunch(false);
        startActivity(new Intent(IntroActivity.this, SignInActivity.class));
        finish();
    }

    private void transparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    public void addDotsIndicator(int position) {
        slideDots = new TextView[introAdapter.getCount()];
        dotsLayout.removeAllViews();
        for (int i = 0; i < slideDots.length; i++) {
            slideDots[i] = new TextView(this);
            slideDots[i].setText(Html.fromHtml("•"));
            slideDots[i].setTextSize(35);
            slideDots[i].setTextColor(getResources().getColor(R.color.dot_inactive));
            dotsLayout.addView(slideDots[i]);
        }
        if (slideDots.length > 0) {
            slideDots[position].setTextColor(getResources().getColor(R.color.dot_active));
        }
    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            currentItem = position;
            addDotsIndicator(position);
            if (position == introAdapter.getCount() - 1) {
                btnNext.setText(getString(R.string.got_it));
                btnSkip.setVisibility(View.INVISIBLE);
            } else {
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if (currentItem < introAdapter.getCount() - 1) {
                    ++currentItem;
                    viewPager.setCurrentItem(currentItem);
                } else {
                    launchMainScreen();
                }
                break;
            case R.id.btn_skip:
                launchMainScreen();
                break;
        }
    }
}
package com.moggot.mytranslator;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;

import com.moggot.mytranslator.fragments.FavoritesListFragment;
import com.moggot.mytranslator.fragments.RootFragment;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    private ViewPager pager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = (ViewPager) findViewById(R.id.pager);
        final SlidePagerAdapter pagerAdapter = new SlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        PagerTabStrip header = (PagerTabStrip) findViewById(R.id.pager_header);

        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            header.setTabIndicatorColor(ContextCompat.getColor(this, R.color.pager_haeder_indicator));
        } else {
            header.setTabIndicatorColor(getResources().getColor(R.color.pager_haeder_indicator));
        }

    }

    public ViewPager getViewPager() {
        return pager;
    }

    public class SlidePagerAdapter extends FragmentPagerAdapter {

        static final int NUM_ITEMS = 2;

        public SlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0)
                return RootFragment.newInstance();
            else
                return FavoritesListFragment.newInstance();
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            SpannableStringBuilder sb = new SpannableStringBuilder(" ");
            Drawable drawable;
            if (position == 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    drawable = getResources().getDrawable(R.drawable.ic_tab_translate_24px, getTheme());
                } else {
                    drawable = getResources().getDrawable(R.drawable.ic_tab_translate_24px);
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    drawable = getResources().getDrawable(R.drawable.ic_tab_bookmark_24px, getTheme());
                } else {
                    drawable = getResources().getDrawable(R.drawable.ic_tab_bookmark_24px);
                }
            }
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
            sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
        }
    }

}

package com.moggot.mytranslator;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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

    }

    public ViewPager getViewPager() {
        return pager;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Fragment fragment = (RootFragment)pager.getAdapter().instantiateItem(pager, 0);
        if (fragment != null && fragment.isVisible()) {
            getSupportFragmentManager().putFragment(outState, Consts.EXTRA_STATE, fragment);
        }
        fragment = (FavoritesListFragment)pager.getAdapter().instantiateItem(pager, 1);
        if (fragment != null && fragment.isVisible()) {
            getSupportFragmentManager().putFragment(outState, Consts.EXTRA_STATE, fragment);
        }
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
                    drawable = getResources().getDrawable(R.drawable.ic_translate_selected_24px, getTheme());
                } else {
                    drawable = getResources().getDrawable(R.drawable.ic_translate_selected_24px);
                }
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
                sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                return sb;
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    drawable = getResources().getDrawable(R.drawable.ic_bookmark_24px, getTheme());
                } else {
                    drawable = getResources().getDrawable(R.drawable.ic_bookmark_24px);
                }
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
                sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return sb;
            }
        }
    }

}

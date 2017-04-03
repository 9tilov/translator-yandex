package com.moggot.mytranslator;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;

import com.moggot.mytranslator.adapter.FavoritesAdapter;
import com.moggot.mytranslator.adapter.HistoryAdapter;
import com.moggot.mytranslator.translator.Translator;

import java.util.List;

/**
 * Created by toor on 03.04.17.
 */

public class AnimatedTabHostListener implements TabHost.OnTabChangeListener {

    private static final int ANIMATION_TIME = 240;
    private TabHost tabHost;
    private View previousView;
    private Context context;

    private static final String LOG_TAG = "TabHost";

    /**
     * Constructor that takes the TabHost as a parameter and sets previousView to the currentView at instantiation
     *
     * @param context
     * @param tabHost
     */
    public AnimatedTabHostListener(Context context, TabHost tabHost) {
        this.context = context;
        this.tabHost = tabHost;
        this.previousView = tabHost.getCurrentView();
    }

    /**
     * When tabs change we fetch the current view that we are animating to and animate it and the previous view in the
     * appropriate directions.
     */
    @Override
    public void onTabChanged(String tabId) {

        View currentView = tabHost.getCurrentView();

        DataBase db = new DataBase(context);
        if (tabId.equals(context.getString(R.string.tag_favorites))) {

            Log.v(LOG_TAG, "change1");
            previousView.setAnimation(outToLeftAnimation());
            currentView.setAnimation(inFromRightAnimation());

            ListView listView = (ListView) ((Activity) context).findViewById(R.id.lvFavorites);
            List<Translator> records = db.getFavoritesRecords();
            FavoritesAdapter adapter = new FavoritesAdapter(context, records);
            listView.setAdapter(adapter);

            if (db.getFavoritesRecords().isEmpty())
                ((Button) ((Activity) context).findViewById(R.id.btnDeleteAllFavorites)).setVisibility(View.GONE);
            else
                ((Button) ((Activity) context).findViewById(R.id.btnDeleteAllFavorites)).setVisibility(View.VISIBLE);
        } else {
            Log.v(LOG_TAG, "change2");
            previousView.setAnimation(outToRightAnimation());
            currentView.setAnimation(inFromLeftAnimation());
            Fragment historyFragment = ((Activity) context).getFragmentManager().findFragmentByTag(Consts.TAG_FRAGMENT_HISTORY);
            if (historyFragment != null && historyFragment.isVisible()) {
                ListView listView = (ListView) historyFragment.getView().findViewById(R.id.lvHistory);
                List<Translator> records = db.getAllRecords();
                HistoryAdapter adapter = new HistoryAdapter(context, records);
                listView.setAdapter(adapter);
            }
        }
        previousView = currentView;

    }

    /**
     * Custom animation that animates in from right
     *
     * @return Animation the Animation object
     */
    private Animation inFromRightAnimation() {
        Animation inFromRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                0.0f);
        return setProperties(inFromRight);
    }

    /**
     * Custom animation that animates out to the right
     *
     * @return Animation the Animation object
     */
    private Animation outToRightAnimation() {
        Animation outToRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                1.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
        return setProperties(outToRight);
    }

    /**
     * Custom animation that animates in from left
     *
     * @return Animation the Animation object
     */
    private Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                0.0f);
        return setProperties(inFromLeft);
    }

    /**
     * Custom animation that animates out to the left
     *
     * @return Animation the Animation object
     */
    private Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
        return setProperties(outtoLeft);
    }

    /**
     * Helper method that sets some common properties
     *
     * @param animation the animation to give common properties
     * @return the animation with common properties
     */
    private Animation setProperties(Animation animation) {
        animation.setDuration(ANIMATION_TIME);
        animation.setInterpolator(new AccelerateInterpolator());
        return animation;
    }
}
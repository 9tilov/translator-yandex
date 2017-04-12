package com.moggot.mytranslator.adapter;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.moggot.mytranslator.DataBase;
import com.moggot.mytranslator.LangSharedPreferences;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.fragments.FavoritesListFragment;
import com.moggot.mytranslator.observer.AdapterFavoritesDisplay;
import com.moggot.mytranslator.observer.Display;
import com.moggot.mytranslator.observer.TranslatorData;
import com.moggot.mytranslator.translator.Translator;

import java.util.List;

/**
 * Created by toor on 30.03.17.
 */

public class AdapterFavorites extends BaseSwipeAdapter {

    private List<Translator> records;
    private DataBase db;
    private Fragment fragment;

    private static final String LOG_TAG = "AdapterFavorites";

    public AdapterFavorites(Fragment fragment, List<Translator> records) {
        this.records = records;
        this.fragment = fragment;
        this.db = new DataBase(fragment.getContext());
    }

    private void update() {
        records = db.getFavoritesRecords();
        if (fragment.getView() == null)
            return;
        if (records.isEmpty())
            ((Button) fragment.getView().findViewById(R.id.btnClearFavorites)).setVisibility(View.GONE);
        else
            ((Button) fragment.getView().findViewById(R.id.btnClearFavorites)).setVisibility(View.VISIBLE);

        notifyDatasetChanged();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        return LayoutInflater.from(fragment.getContext()).inflate(R.layout.history_item, null);
    }

    @Override
    public void fillValues(final int position, View convertView) {
        SwipeLayout swipeLayout = (SwipeLayout) convertView.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.adapterIwTrash));
            }
        });

        final Translator translatorAtPosition = getTranslator(position);

        convertView.findViewById(R.id.adapterRlDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                translatorAtPosition.setIsFavorites(false);
                db.editRecord(translatorAtPosition);
                FavoritesListFragment.FavoritesListEventListener favoritesListEventListener = ((FavoritesListFragment) fragment).getFavoritesListEventListener();
                Log.v(LOG_TAG, "favoritesListEventListener = " + favoritesListEventListener);
                if (favoritesListEventListener != null) {
                    favoritesListEventListener.deleteFavoritesFlag(translatorAtPosition);
                }
                closeItem(position);
                update();
            }
        });

        swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LangSharedPreferences.saveInputLanguage(fragment.getContext(), translatorAtPosition.getInputLanguage());
                LangSharedPreferences.saveOutputLanguage(fragment.getContext(), translatorAtPosition.getOutputLanguage());

                FavoritesListFragment.FavoritesListEventListener favoritesListEventListener = ((FavoritesListFragment) fragment).getFavoritesListEventListener();
                Log.v(LOG_TAG, "favoritesListEventListener = " + favoritesListEventListener);
                if (favoritesListEventListener != null) {
                    favoritesListEventListener.loadFavoriteItem(translatorAtPosition);
                }

            }

        });

        TranslatorData translatorData = new TranslatorData();
        Display adapterDisplay = new AdapterFavoritesDisplay(fragment.getContext(), convertView, translatorData);
        translatorData.setTranslator(translatorAtPosition);
        adapterDisplay.display();
    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int position) {
        return records.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private Translator getTranslator(int position) {
        return ((Translator) getItem(position));
    }
}

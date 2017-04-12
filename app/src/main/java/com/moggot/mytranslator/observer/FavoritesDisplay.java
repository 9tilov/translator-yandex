package com.moggot.mytranslator.observer;

import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.moggot.mytranslator.DataBase;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.adapter.AdapterFavorites;
import com.moggot.mytranslator.translator.Translator;

import java.util.List;

/**
 * Created by toor on 10.04.17.
 */

public class FavoritesDisplay extends Display {

    private static final String LOG_TAG = "FavoritesDisplay";

    private Fragment fragment;
    private DataBase db;

    public FavoritesDisplay(Fragment fragment, TranslatorData translatorData) {
        super(fragment.getContext());
        this.fragment = fragment;
        db = new DataBase(context);
        translatorData.registerObserver(this);
    }

    @Override
    public void display() {
        if (fragment.getView() == null)
            return;
        displayClearFavoritesButton();
        displayFavoritesList();
    }

    private void displayClearFavoritesButton() {
        if (db.getFavoritesRecords().isEmpty())
            ((Button) fragment.getView().findViewById(R.id.btnClearFavorites)).setVisibility(View.GONE);
        else
            ((Button) fragment.getView().findViewById(R.id.btnClearFavorites)).setVisibility(View.VISIBLE);
    }

    private void displayFavoritesList() {
        ListView listView = (ListView) fragment.getView().findViewById(R.id.lvFavorites);
        List<Translator> records = db.getFavoritesRecords();
        AdapterFavorites adapter = new AdapterFavorites(fragment, records);
        listView.setAdapter(adapter);
    }
}

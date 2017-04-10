package com.moggot.mytranslator.observer;

import android.content.Context;
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

    private View view;

    public FavoritesDisplay(Context context, View view, TranslatorData translatorData) {
        super(context);
        this.view = view;
        translatorData.registerObserver(this);
    }

    @Override
    public void display() {
        displayClearFavoritesButton();
        displayFavoritesList();
    }

    private void displayClearFavoritesButton() {
        DataBase db = new DataBase(context);
        if (db.getFavoritesRecords().isEmpty())
            ((Button) view.findViewById(R.id.btnClearFavorites)).setVisibility(View.GONE);
        else
            ((Button) view.findViewById(R.id.btnClearFavorites)).setVisibility(View.VISIBLE);
    }

    private void displayFavoritesList() {
        ListView listView = (ListView) view.findViewById(R.id.lvFavorites);
        DataBase db = new DataBase(context);
        List<Translator> records = db.getFavoritesRecords();
        AdapterFavorites adapter = new AdapterFavorites(context, records);
        listView.setAdapter(adapter);
    }
}

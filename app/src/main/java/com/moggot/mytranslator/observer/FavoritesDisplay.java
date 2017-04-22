package com.moggot.mytranslator.observer;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.moggot.mytranslator.DataBase;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.adapter.AdapterFavorites;
import com.moggot.mytranslator.fragments.FavoritesListFragment;
import com.moggot.mytranslator.translator.Translator;

import java.util.List;

/**
 * Класс для отображения данных на экране избранных слов
 */
public class FavoritesDisplay extends Display {

    private static final String LOG_TAG = "FavoritesDisplay";

    /**
     * Фрагмент, в котором необходимо отобразить данные
     */
    private Fragment fragment;

    /**
     * База данных
     */
    private DataBase db;

    /**
     * Конструктор
     *
     * @param fragment       - фрагмент, в котором необходимо отобразить данные
     * @param translatorData - данные транслятора
     */
    public FavoritesDisplay(Fragment fragment, TranslatorData translatorData) {
        super(fragment.getContext());
        this.fragment = fragment;
        db = new DataBase(context);
        translatorData.registerObserver(this);
    }

    /**
     * Отображение данных
     */
    @Override
    public void display() {
        if (fragment.getView() == null)
            return;
        displayClearFavoritesButton();
        displayFavoritesList();
    }

    /**
     * Отображение кнопки очистки списка избранных слов
     */
    private void displayClearFavoritesButton() {
        if (db.getFavoritesRecords().isEmpty())
            ((Button) fragment.getView().findViewById(R.id.btnClearFavorites)).setVisibility(View.GONE);
        else
            ((Button) fragment.getView().findViewById(R.id.btnClearFavorites)).setVisibility(View.VISIBLE);
    }

    /**
     * Отображение списка избранных слов
     */
    private void displayFavoritesList() {
        List<Translator> records = db.getFavoritesRecords();
        AdapterFavorites adapter = new AdapterFavorites(fragment, records);
        ((FavoritesListFragment) fragment).getListView().setAdapter(adapter);
    }

}
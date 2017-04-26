package com.moggot.multipreter.observer;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.moggot.multipreter.DataBase;
import com.moggot.multipreter.R;
import com.moggot.multipreter.adapter.AdapterFavorites;
import com.moggot.multipreter.fragments.FavoritesListFragment;
import com.moggot.multipreter.translator.Translator;

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
        try {
            displayClearFavoritesButton();
            displayFavoritesList();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Отображение кнопки очистки списка избранных слов
     */
    private void displayClearFavoritesButton() throws NullPointerException {
        if (fragment.getView() == null)
            throw new NullPointerException("getView() is null");
        if (db.getFavoritesRecords().isEmpty())
            ((Button) fragment.getView().findViewById(R.id.btnClearFavorites)).setVisibility(View.GONE);
        else
            ((Button) fragment.getView().findViewById(R.id.btnClearFavorites)).setVisibility(View.VISIBLE);
    }

    /**
     * Отображение списка избранных слов
     */
    private void displayFavoritesList() throws NullPointerException {
        if (((FavoritesListFragment) fragment).getListView() == null)
            throw new NullPointerException("getListView is null");
        List<Translator> records = db.getFavoritesRecords();
        AdapterFavorites adapter = new AdapterFavorites(fragment, records);
        ((FavoritesListFragment) fragment).getListView().setAdapter(adapter);
    }
}
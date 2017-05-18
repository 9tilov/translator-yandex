package com.moggot.multipreter.observer;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.moggot.multipreter.DataBase;
import com.moggot.multipreter.R;
import com.moggot.multipreter.adapter.AdapterHistory;
import com.moggot.multipreter.fragments.HistoryListFragment;
import com.moggot.multipreter.translator.Translator;

import java.util.List;

/**
 * Класс для отображения данных на экране истории переводов
 */
public class HistoryDisplay extends Display {

    private static final String LOG_TAG = HistoryDisplay.class.getSimpleName();

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
    public HistoryDisplay(Fragment fragment, TranslatorData translatorData) {
        this.fragment = fragment;
        db = new DataBase(fragment.getContext());
        translatorData.registerObserver(this);
    }

    /**
     * Отображение данных
     */
    @Override
    public void display() {
        try {
            displayClearHistoryButton();
            displayHistoryList();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Отображение кнопки очистки истории переводов
     */
    private void displayClearHistoryButton() throws NullPointerException {
        if (fragment.getView() == null)
            throw new NullPointerException("getView() is null");
        if (db.getAllRecords().isEmpty())
            ((Button) fragment.getView().findViewById(R.id.btnClearHistory)).setVisibility(View.GONE);
        else
            ((Button) fragment.getView().findViewById(R.id.btnClearHistory)).setVisibility(View.VISIBLE);
    }

    /**
     * Отображение истории переводов
     */
    private void displayHistoryList() throws NullPointerException {
        if (((HistoryListFragment) fragment).getListView() == null)
            throw new NullPointerException("getListView is null");

        List<Translator> records = db.getAllRecords();
        AdapterHistory adapter = new AdapterHistory(fragment, records);
        ((HistoryListFragment) fragment).getListView().setAdapter(adapter);
    }
}

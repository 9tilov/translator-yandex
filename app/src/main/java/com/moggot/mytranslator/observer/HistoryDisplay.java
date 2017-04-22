package com.moggot.mytranslator.observer;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.moggot.mytranslator.DataBase;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.adapter.AdapterHistory;
import com.moggot.mytranslator.fragments.HistoryListFragment;
import com.moggot.mytranslator.translator.Translator;

import java.util.List;

/**
 * Класс для отображения данных на экране истории переводов
 */
public class HistoryDisplay extends Display {

    private static final String LOG_TAG = "HistoryDisplay";

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
        displayClearHistoryButton();
        displayHistoryList();
    }

    /**
     * Отображение кнопки очистки истории переводов
     */
    private void displayClearHistoryButton() {
        if (db.getAllRecords().isEmpty())
            ((Button) fragment.getView().findViewById(R.id.btnClearHistory)).setVisibility(View.GONE);
        else
            ((Button) fragment.getView().findViewById(R.id.btnClearHistory)).setVisibility(View.VISIBLE);
    }

    /**
     * Отображение истории переводов
     */
    private void displayHistoryList() {
        List<Translator> records = db.getAllRecords();
        AdapterHistory adapter = new AdapterHistory(fragment, records);
        ((HistoryListFragment) fragment).getListView().setAdapter(adapter);
    }

}

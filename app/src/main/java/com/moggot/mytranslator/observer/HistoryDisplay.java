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
 * Created by toor on 07.04.17.
 */

public class HistoryDisplay extends Display {

    private static final String LOG_TAG = "HistoryDisplay";
    private Fragment fragment;
    private DataBase db;

    public HistoryDisplay(Fragment fragment, TranslatorData translatorData) {
        super(fragment.getContext());
        this.fragment = fragment;
        db = new DataBase(context);
        translatorData.registerObserver(this);
    }

    @Override
    public void display() {
        if (fragment.getView() == null)
            return;
        displayClearHistoryButton();
        displayHistoryList();
    }

    private void displayClearHistoryButton() {
        if (db.getAllRecords().isEmpty())
            ((Button) fragment.getView().findViewById(R.id.btnClearHistory)).setVisibility(View.GONE);
        else
            ((Button) fragment.getView().findViewById(R.id.btnClearHistory)).setVisibility(View.VISIBLE);
    }

    private void displayHistoryList() {
        List<Translator> records = db.getAllRecords();
        AdapterHistory adapter = new AdapterHistory(fragment, records);
        ((HistoryListFragment) fragment).getListView().setAdapter(adapter);
    }

}

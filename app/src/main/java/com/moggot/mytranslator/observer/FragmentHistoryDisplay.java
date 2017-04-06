package com.moggot.mytranslator.observer;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.moggot.mytranslator.DataBase;
import com.moggot.mytranslator.R;

/**
 * Created by toor on 06.04.17.
 */

public class FragmentHistoryDisplay extends Display {

    private static final String LOG_TAG = "HistoryDisplay";

    private View view;

    public FragmentHistoryDisplay(Context context, View view, TranslatorData translatorData) {
        super(context);
        this.view = view;
        translatorData.registerObserver(this);
    }

    @Override
    public void display() {
        displayClearHistoryButton();
    }

    private void displayClearHistoryButton() {
        DataBase db = new DataBase(context);
        if (db.getAllRecords().isEmpty())
            ((Button) view.findViewById(R.id.btnClearAllHistory)).setVisibility(View.GONE);
        else
            ((Button) view.findViewById(R.id.btnClearAllHistory)).setVisibility(View.VISIBLE);
    }
}


package com.moggot.mytranslator.observer;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.moggot.mytranslator.Conversation;
import com.moggot.mytranslator.DataBase;
import com.moggot.mytranslator.LangSharedPreferences;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.adapter.AdapterHistory;
import com.moggot.mytranslator.translator.Translator;

import java.util.List;

/**
 * Created by toor on 07.04.17.
 */

public class HistoryDisplay extends Display {

    private static final String LOG_TAG = "HistoryDisplay";

    private View view;

    public HistoryDisplay(Context context, View view, TranslatorData translatorData) {
        super(context);
        this.view = view;
        translatorData.registerObserver(this);
    }

    @Override
    public void display() {
        displayInputLang();
        displayOutputLang();
        displayClearButton();
        displayClearHistoryButton();
        displayHistoryList();
    }

    private void displayInputLang() {
        Conversation conversation = new Conversation(context);
        ((TextView) ((Activity) context).findViewById(R.id.tvInputLang)).setText(conversation.getLongLangName(translator.getInputLanguage()));
        LangSharedPreferences.saveInputLanguage(context, translator.getInputLanguage());
    }

    private void displayOutputLang() {
        Conversation conversation = new Conversation(context);
        ((TextView) ((Activity) context).findViewById(R.id.tvOutputLang)).setText(conversation.getLongLangName(translator.getOutputLanguage()));
        LangSharedPreferences.saveOutputLanguage(context, translator.getOutputLanguage());
    }

    private void displayClearButton() {
        ((Button) ((Activity) context).findViewById(R.id.btnClearText)).setVisibility(View.GONE);
    }

    private void displayClearHistoryButton() {
        DataBase db = new DataBase(context);
        if (db.getAllRecords().isEmpty())
            ((Button) view.findViewById(R.id.btnClearHistory)).setVisibility(View.GONE);
        else
            ((Button) view.findViewById(R.id.btnClearHistory)).setVisibility(View.VISIBLE);
    }

    private void displayHistoryList() {
        ListView listView = (ListView) view.findViewById(R.id.lvHistory);
        DataBase db = new DataBase(context);
        List<Translator> records = db.getAllRecords();
        AdapterHistory adapter = new AdapterHistory(context, records);
        listView.setAdapter(adapter);
    }

}

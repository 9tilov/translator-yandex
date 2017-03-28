package com.moggot.mytranslator.observer;

import android.content.Context;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.moggot.mytranslator.Conversation;
import com.moggot.mytranslator.R;
import com.moggot.mytranslator.LangSharedPreferences;
import com.moggot.mytranslator.language.Language;

/**
 * Created by toor on 24.03.17.
 */

public class AdapterDisplay implements Observer {

    private View view;
    private Language language;
    private Context context;

    public AdapterDisplay(Context context, View view, LangData langData) {
        this.view = view;
        this.context = context;
        langData.registerObserver(this);
    }

    @Override
    public void update(Language language) {
        this.language = language;
        display();
    }

    public void display() {
        displayLang();
        displayCheck();
    }

    private void displayLang() {
        Conversation conversation = new Conversation(context);
        ((TextView) view.findViewById(R.id.tvLang)).setText(conversation.getLongLangName(language.getName()));
    }

    private void displayCheck() {
        Language lang = LangSharedPreferences.loadLanguage(context, this.language.getType());
        if ((lang.getName()).equals(this.language.getName()))
            ((RadioButton) view.findViewById(R.id.radio1)).setChecked(true);
        else
            ((RadioButton) view.findViewById(R.id.radio1)).setChecked(false);
    }
}

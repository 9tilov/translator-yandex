package com.moggot.mytranslator.observer;

import android.content.Context;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.moggot.mytranslator.Conversation;
import com.moggot.mytranslator.LangSharedPreferences;
import com.moggot.mytranslator.R;

/**
 * Created by toor on 24.03.17.
 */

public class AdapterOutputLanguageDisplay extends Display {

    private View view;

    public AdapterOutputLanguageDisplay(Context context, View view, TranslatorData translatorData) {
        super(context);
        this.view = view;
        translatorData.registerObserver(this);
    }

    @Override
    public void display() {
        displayLang();
        displayCheck();
    }

    private void displayLang() {
        Conversation conversation = new Conversation(context);
        ((TextView) view.findViewById(R.id.tvLang)).setText(conversation.getLongLangName(translator.getOutputLanguage()));
    }

    private void displayCheck() {
        String outputLang = LangSharedPreferences.loadOutputLanguage(context);
        if (translator.getOutputLanguage().equals(outputLang))
            ((RadioButton) view.findViewById(R.id.check)).setChecked(true);
        else
            ((RadioButton) view.findViewById(R.id.check)).setChecked(false);
    }
}

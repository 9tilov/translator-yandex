package com.moggot.mytranslator.observer;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import com.moggot.mytranslator.Conversation;
import com.moggot.mytranslator.R;

/**
 * Created by toor on 26.03.17.
 */

public class LanguageDisplay extends Display {

    public LanguageDisplay(Context context, TranslatorData translatorData) {
        super(context);
        translatorData.registerObserver(this);
    }

    @Override
    public void display() {
        displayLang();
    }

    private void displayLang() {
        Conversation conversation = new Conversation(context);
        ((TextView) ((Activity) context).findViewById(R.id.tvInputLang)).setText(conversation.getLongLangName(translator.getInputLanguage()));
        ((TextView) ((Activity) context).findViewById(R.id.tvOutputLang)).setText(conversation.getLongLangName(translator.getOutputLanguage()));
    }
}

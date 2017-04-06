package com.moggot.mytranslator.observer;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.moggot.mytranslator.Conversation;
import com.moggot.mytranslator.LangSharedPreferences;
import com.moggot.mytranslator.R;

/**
 * Created by toor on 06.04.17.
 */

public class ActivityDisplay extends Display {

    private static final String LOG_TAG = "ActivityDisplay";

    public ActivityDisplay(Context context, TranslatorData translatorData) {
        super(context);
        translatorData.registerObserver(this);
    }

    @Override
    public void display() {
        displayText();
        displayInputLang();
        displayOutputLang();
        displayClearButton();
    }

    private void displayText() {
        ((TextView) ((Activity) context).findViewById(R.id.etText)).setText(translator.getText());
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
        ((Button) ((Activity) context).findViewById(R.id.btnClearText)).setVisibility(View.VISIBLE);
    }

}

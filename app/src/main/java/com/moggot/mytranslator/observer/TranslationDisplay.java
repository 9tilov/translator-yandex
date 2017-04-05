package com.moggot.mytranslator.observer;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.moggot.mytranslator.Conversation;
import com.moggot.mytranslator.R;

/**
 * Created by toor on 04.04.17.
 */

public class TranslationDisplay extends Display {

    private View view;

    private static final String LOG_TAG = "TranslationDisplay";

    public TranslationDisplay(Context context, View view, TranslatorData translatorData) {
        super(context);
        this.view = view;
        translatorData.registerObserver(this);
    }

    @Override
    public void display() {
        displayInputLang();
        displayOutputLang();
        displayText();
        dislpayTranslation();
        displayFavorites();
        displayDetails();
    }

    private void displayInputLang() {
        Conversation conversation = new Conversation(context);
        ((TextView) ((Activity) context).findViewById(R.id.tvInputLang)).setText(conversation.getLongLangName(translator.getInputLanguage()));
    }

    private void displayOutputLang() {
        Conversation conversation = new Conversation(context);
        ((TextView) ((Activity) context).findViewById(R.id.tvOutputLang)).setText(conversation.getLongLangName(translator.getOutputLanguage()));
    }

    private void displayText() {
        ((TextView) ((Activity) context).findViewById(R.id.etText)).setText(translator.getText());
    }

    private void dislpayTranslation() {
        ((TextView) view.findViewById(R.id.tvTranslation)).setText(translator.getTranslation());
    }

    private void displayFavorites() {
        if (translator.getIsFavorites())
            ((Button) view.findViewById(R.id.btnFavorites)).setBackgroundResource(R.drawable.ic_bookmark_24px);
        else
            ((Button) view.findViewById(R.id.btnFavorites)).setBackgroundResource(R.drawable.ic_bookmark_border_black_24px);
    }

    private void displayDetails() {
        ((TextView) view.findViewById(R.id.tvDetails)).setText(translator.getDetails());
    }
}
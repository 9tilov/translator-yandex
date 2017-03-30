package com.moggot.mytranslator.observer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.moggot.mytranslator.R;

/**
 * Created by toor on 30.03.17.
 */

public class AdapterFavoritesDisplay extends Display {

    private View view;

    public AdapterFavoritesDisplay(Context context, View view, TranslatorData translatorData) {
        super(context);
        this.view = view;
        translatorData.registerObserver(this);
    }

    @Override
    public void display() {
        displayText();
        displayTranslation();
        displayFavoritesFlag();
        displayInputLang();
        displayOutputLang();
    }

    private void displayText() {
        ((TextView) view.findViewById(R.id.adapterTvText)).setText(translator.getText());
    }

    private void displayTranslation() {
        ((TextView) view.findViewById(R.id.adapterTvTranslation)).setText(translator.getTranslation());
    }

    private void displayFavoritesFlag() {
        ((ImageView) view.findViewById(R.id.adapterIwFavorites)).setBackgroundResource(R.drawable.ic_bookmark_24px);
    }

    private void displayInputLang() {
        ((TextView) view.findViewById(R.id.adapterTvInputLang)).setText(translator.getInputLanguage());
    }

    private void displayOutputLang() {
        ((TextView) view.findViewById(R.id.adapterTvOutputLang)).setText(translator.getOutputLanguage());
    }
}

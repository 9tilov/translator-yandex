package com.moggot.mytranslator.observer;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.moggot.mytranslator.R;

/**
 * Created by toor on 07.04.17.
 */

public class TranslationDisplay extends Display {

    private static final String LOG_TAG = "TranslationDisplay";

    private View view;

    public TranslationDisplay(Context context, View view, TranslatorData translatorData) {
        super(context);
        this.view = view;
        translatorData.registerObserver(this);
    }

    @Override
    public void display() {
        dislpayTranslation();
        displayFavorites();
        displayDetails();
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

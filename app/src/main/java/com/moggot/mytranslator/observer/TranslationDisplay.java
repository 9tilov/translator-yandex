package com.moggot.mytranslator.observer;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.moggot.mytranslator.R;

/**
 * Created by toor on 07.04.17.
 */

public class TranslationDisplay extends Display {

    private static final String LOG_TAG = "TranslationDisplay";
    private Fragment fragment;

    public TranslationDisplay(Fragment fragment, TranslatorData translatorData) {
        super(fragment.getContext());
        this.fragment = fragment;
        translatorData.registerObserver(this);
    }

    @Override
    public void display() {
        if (fragment.getView() == null)
            return;
        dislpayTranslation();
        displayFavorites();
    }

    private void dislpayTranslation() {
        (fragment.getView().findViewById(R.id.rlTranslation)).setVisibility(View.VISIBLE);
        (fragment.getView().findViewById(R.id.llError)).setVisibility(View.GONE);

        ((TextView) fragment.getView().findViewById(R.id.tvTranslation)).setText(translator.getTranslation());
    }

    private void displayFavorites() {
        if (translator.getIsFavorites())
            ((Button) fragment.getView().findViewById(R.id.btnAddFavorites)).setBackgroundResource(R.drawable.ic_bookmark_24px);
        else
            ((Button) fragment.getView().findViewById(R.id.btnAddFavorites)).setBackgroundResource(R.drawable.ic_bookmark_border_24px);
    }
}

package com.moggot.mytranslator.observer;

import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
        displayDetails();
    }

    private void dislpayTranslation() {
        ((TextView) fragment.getView().findViewById(R.id.tvTranslation)).setText(translator.getTranslation());
    }

    private void displayFavorites() {
        if (translator.getIsFavorites())
            ((Button) fragment.getView().findViewById(R.id.btnAddFavorites)).setBackgroundResource(R.drawable.ic_bookmark_24px);
        else
            ((Button) fragment.getView().findViewById(R.id.btnAddFavorites)).setBackgroundResource(R.drawable.ic_bookmark_border_24px);
    }

    private void displayDetails() {
        ScrollView scrollViewDetails = (ScrollView) fragment.getView().findViewById(R.id.scrollDetails);
        scrollViewDetails.setVisibility(View.VISIBLE);
        ScrollView scrollViewTranslation = ((ScrollView) fragment.getView().findViewById(R.id.scrollTranslation));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) scrollViewTranslation.getLayoutParams();
        params.height = 0;
        if (!translator.getDetails().isEmpty())
            ((TextView) fragment.getView().findViewById(R.id.tvDetails)).setText(translator.getDetails());
        else {
            scrollViewDetails.setVisibility(View.GONE);
            params.height = LinearLayout.LayoutParams.MATCH_PARENT;
            ((TextView) fragment.getView().findViewById(R.id.tvTranslation)).setTextSize(TypedValue.COMPLEX_UNIT_SP, fragment.getContext().getResources().getDimension(R.dimen.text_size_dictionary));
        }
    }

}
